package io.github.thebusybiscuit.souljars;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JarsListener implements Listener {

    private final SoulJars plugin;
    private final SlimefunItem emptyJar;

    public JarsListener(SoulJars plugin) {
        this.plugin = plugin;
        this.emptyJar = SlimefunItem.getById("SOUL_JAR");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Map<EntityType, Integer> mobs = plugin.getRequiredSouls();
        Player killer = event.getEntity().getKiller();
        if (killer == null || !mobs.containsKey(event.getEntityType())) {
            return;
        }

        SlimefunItem jar = SlimefunItem.getById(event.getEntityType().name() + "_SOUL_JAR");
        SlimefunItem filledJar = SlimefunItem.getById("FILLED_" + event.getEntityType().name() + "_SOUL_JAR");
        if (jar == null || filledJar == null || emptyJar == null) {
            return;
        }

        for (int slot = 0; slot < killer.getInventory().getSize(); slot++) {
            ItemStack stack = killer.getInventory().getItem(slot);
            if (!jar.isItem(stack)) {
                continue;
            }

            ItemMeta meta = stack.getItemMeta();
            if (meta == null || !meta.hasLore()) {
                continue;
            }
            List<String> lore = meta.getLore();
            if (lore == null || lore.size() < 2) {
                continue;
            }

            int currentSouls = parseSoulCount(lore.get(1));
            if (currentSouls < 0) {
                continue;
            }

            int souls = currentSouls + 1;
            int requiredSouls = mobs.get(event.getEntityType());
            if (souls >= requiredSouls) {
                if (stack.getAmount() > 1) {
                    decrement(stack);
                    killer.getInventory().addItem(filledJar.getItem().clone());
                } else {
                    killer.getInventory().setItem(slot, filledJar.getItem().clone());
                }
            } else {
                List<String> updatedLore = new ArrayList<>(lore);
                String label = ChatColor.stripColor(updatedLore.get(1));
                int separator = label == null ? -1 : label.indexOf(':');
                String prefix = separator >= 0 ? label.substring(0, separator) : "Infused Souls";
                updatedLore.set(1, ChatColor.translateAlternateColorCodes('&', "&7" + prefix + ": &e" + souls));

                if (stack.getAmount() > 1) {
                    decrement(stack);
                    ItemStack single = stack.clone();
                    single.setAmount(1);
                    ItemMeta singleMeta = single.getItemMeta();
                    if (singleMeta != null) {
                        singleMeta.setLore(updatedLore);
                        single.setItemMeta(singleMeta);
                    }
                    killer.getInventory().addItem(single);
                } else {
                    meta.setLore(updatedLore);
                    stack.setItemMeta(meta);
                }
            }
            return;
        }

        for (int slot = 0; slot < killer.getInventory().getSize(); slot++) {
            ItemStack stack = killer.getInventory().getItem(slot);
            if (emptyJar.isItem(stack)) {
                decrement(stack);
                killer.getWorld().dropItemNaturally(event.getEntity().getLocation(), jar.getItem().clone());
                return;
            }
        }
    }

    private static int parseSoulCount(String loreLine) {
        String plain = ChatColor.stripColor(loreLine);
        if (plain == null) {
            return -1;
        }
        int separator = plain.lastIndexOf(':');
        if (separator < 0 || separator + 1 >= plain.length()) {
            return -1;
        }
        try {
            return Integer.parseInt(plain.substring(separator + 1).trim());
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

    private static void decrement(ItemStack stack) {
        stack.setAmount(Math.max(0, stack.getAmount() - 1));
    }
}
