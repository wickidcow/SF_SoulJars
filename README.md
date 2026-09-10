<div align="center">

# SF_SoulJars — Slimefun Legacy
### Classic SoulJars with modern Minecraft mob coverage

[![Build](https://github.com/wickidcow/SF_SoulJars/actions/workflows/maven.yml/badge.svg)](https://github.com/wickidcow/SF_SoulJars/actions/workflows/maven.yml)
[![License](https://img.shields.io/badge/License-GPLv3-blue)](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE)

</div>

> [!IMPORTANT]
> SF_SoulJars is an unofficial, independently maintained downstream fork. **NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

## Preserved behavior

Soul collection, partial jars, filled jars and broken-spawner crafting keep the original gameplay model and IDs. Existing server `mobs` and `souls-required` values remain authoritative.

## Modern mob support

The maintained default roster adds current mobs such as Allay, Armadillo, Axolotl, Bogged, Breeze, Camel, Camel Husk, Copper Golem, Creaking, Happy Ghast, Nautilus, Parched, Sniffer, Sulfur Cube and Zombie Nautilus. Bosses are not added to the default roster.

### 1.0.2 balancing pass

Modern mobs now ship with sensible default soul requirements instead of every new entity implicitly costing 128 souls. Common/passive mobs generally use 64, normal hostile mobs remain around 128, and rarer/dangerous mobs use 192-256. Server-configured values always win, so existing worlds are not forcibly rebalanced.

Release JAR: `SF_SoulJars1.0.2.jar`

Built with Java 25 targeting Java 21 bytecode for Slimefun Legacy/Paper 26.2+, with shared API compatibility retained for Slimefun United, SlimefunGuguProject/Slimefun4 and original Slimefun4-compatible implementations. Paper, Purpur, Folia and Leaf are supported targets where the Slimefun implementation itself is compatible.

No direct GuizhanLib dependency is used.

## License and attribution

Distributed under the [GNU General Public License v3.0](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE). Upstream authorship and copyright remain with the original authors and contributors.
