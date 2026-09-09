# SF_SoulJars

Maintained SoulJars fork for modern Slimefun servers.

SoulJars lets players capture mob souls in Slimefun items and use completed jars to create mob-specific broken spawners. The maintained fork preserves the original progression while updating the project for current Minecraft and Paper-family servers.

## Compatibility

Primary Slimefun targets:
- Slimefun Legacy
- Slimefun United

Additional compatibility targets:
- SlimefunGuguProject/Slimefun4
- Original Slimefun4-compatible API implementations

Server software:
- Paper
- Purpur
- Folia
- Leaf

Minecraft target: **1.21.11+**. Builds use Java 25 with Java 21 bytecode.

Soul processing is event-driven and does not run its own global repeating scheduler. Folia support assumes the installed Slimefun implementation is itself Folia-compatible.

## Dependency policy

This addon has no direct GuizhanLib dependency and does not use GuizhanLib APIs. Gugu compatibility is provided through the shared Slimefun addon API.

The maintained code also avoids the old Slimefun Dough config/updater helpers in its main flow, reducing coupling to implementation-specific utility libraries.

## Configuration

`config.yml` controls which mob soul jars are enabled and how many souls each mob requires.

## Maintenance

Original project by TheBusyBiscuit. Modern maintenance and compatibility work are provided by `wickidcow`.

Report current-version issues at this repository.
