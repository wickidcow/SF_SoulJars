package io.github.thebusybiscuit.souljars;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.BrokenSpawner;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

public class SoulJars extends JavaPlugin implements Listener, SlimefunAddon {

    private static final String JAR_TEXTURE = "bd1c777ee166c47cae698ae6b769da4e2b67f468855330ad7bddd751c5293f";
    private final Map<EntityType, Integer> mobs = new EnumMap<>(EntityType.class);

    private ItemGroup itemGroup;
    private RecipeType recipeType;
    private SlimefunItemStack emptyJar;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new Metrics(this, 5581);

        emptyJar = new SlimefunItemStack(
            "SOUL_JAR",
            JAR_TEXTURE,
            "&bSoul Jar &7(Empty)",
            "",
            "&rKill a mob while carrying this",
            "&rin your inventory to bind its soul."
        );

        ItemStack categoryIcon = emptyJar.clone();
        setNameAndLore(categoryIcon, "&bSoul Jars", "", "&a> Click to open");
        itemGroup = new ItemGroup(new NamespacedKey(this, "soul_jars"), categoryIcon);

        ItemStack recipeIcon = new ItemStack(Material.DIAMOND_SWORD);
        setNameAndLore(recipeIcon, "&cKill the specified mob", "&cwhile carrying an empty Soul Jar");
        recipeType = new RecipeType(new NamespacedKey(this, "mob_killing"), recipeIcon);

        ItemStack output = emptyJar.clone();
        output.setAmount(3);
        new SlimefunItem(
            itemGroup,
            emptyJar,
            RecipeType.ANCIENT_ALTAR,
            new ItemStack[] {
                SlimefunItems.EARTH_RUNE, new ItemStack(Material.SOUL_SAND), SlimefunItems.WATER_RUNE,
                new ItemStack(Material.SOUL_SAND), SlimefunItems.NECROTIC_SKULL, new ItemStack(Material.SOUL_SAND),
                SlimefunItems.AIR_RUNE, new ItemStack(Material.SOUL_SAND), SlimefunItems.FIRE_RUNE
            },
            output
        ).register(this);

        new JarsListener(this);

        FileConfiguration config = getConfig();
        for (String mob : config.getStringList("mobs")) {
            try {
                registerSoul(EntityType.valueOf(mob.toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException ex) {
                getLogger().log(Level.WARNING, "Skipping invalid mob type from config: {0}", mob);
            }
        }
        saveConfig();
    }

    private void registerSoul(EntityType type) {
        String name = humanize(type.name());
        String path = "souls-required." + type.name();
        FileConfiguration config = getConfig();
        int souls = config.isInt(path) ? Math.max(1, config.getInt(path)) : 128;
        if (!config.isInt(path)) {
            config.set(path, souls);
        }
        mobs.put(type, souls);

        Material mobEgg = Material.getMaterial(type.name() + "_SPAWN_EGG");
        if (mobEgg == null) {
            mobEgg = Material.ZOMBIE_SPAWN_EGG;
        }

        SlimefunItemStack jarItem = new SlimefunItemStack(
            type.name() + "_SOUL_JAR",
            JAR_TEXTURE,
            "&cSoul Jar &7(" + name + ")",
            "",
            "&7Infused Souls: &e1"
        );
        ItemStack killIcon = new ItemStack(mobEgg);
        setNameAndLore(killIcon, "&rKill " + souls + "x " + name);
        new UnplaceableBlock(
            itemGroup,
            jarItem,
            recipeType,
            new ItemStack[] { null, null, null, emptyJar, null, killIcon, null, null, null }
        ).register(this);

        SlimefunItemStack filledJarItem = new SlimefunItemStack(
            "FILLED_" + type.name() + "_SOUL_JAR",
            JAR_TEXTURE,
            "&cFilled Soul Jar &7(" + name + ")",
            "",
            "&7Infused Souls: &e" + souls
        );
        new FilledJar(
            itemGroup,
            filledJarItem,
            recipeType,
            new ItemStack[] { null, null, null, emptyJar, null, killIcon, null, null, null }
        ).register(this);

        BrokenSpawner brokenSpawner = SlimefunItems.BROKEN_SPAWNER.getItem(BrokenSpawner.class);
        SlimefunItemStack spawnerItem = new SlimefunItemStack(
            type.name() + "_BROKEN_SPAWNER",
            Material.SPAWNER,
            "&cBroken Spawner &7(" + name + ")"
        );
        new SlimefunItem(
            itemGroup,
            spawnerItem,
            RecipeType.ANCIENT_ALTAR,
            new ItemStack[] {
                new ItemStack(Material.IRON_BARS), SlimefunItems.EARTH_RUNE, new ItemStack(Material.IRON_BARS),
                SlimefunItems.EARTH_RUNE, filledJarItem, SlimefunItems.EARTH_RUNE,
                new ItemStack(Material.IRON_BARS), SlimefunItems.EARTH_RUNE, new ItemStack(Material.IRON_BARS)
            },
            brokenSpawner.getItemForEntityType(type)
        ).register(this);
    }

    private static String humanize(String enumName) {
        String[] parts = enumName.toLowerCase(Locale.ROOT).split("_");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (!result.isEmpty()) {
                result.append(' ');
            }
            result.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return result.toString();
    }

    private static void setNameAndLore(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        meta.setDisplayName(color(name));
        List<String> lines = new ArrayList<>(lore.length);
        for (String line : lore) {
            lines.add(color(line));
        }
        meta.setLore(lines);
        item.setItemMeta(meta);
    }

    private static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public Map<EntityType, Integer> getRequiredSouls() {
        return mobs;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/wickidcow/SF_SoulJars/issues";
    }
}
