<div align="center">

# SF_SoulJars — Slimefun Legacy
### Maintained soul capture and spawner progression for modern Paper servers

SoulJars lets players capture mob souls in Slimefun items and use completed jars to create mob-specific broken spawners while preserving the classic addon progression.

[![Build](https://github.com/wickidcow/SF_SoulJars/actions/workflows/maven.yml/badge.svg)](https://github.com/wickidcow/SF_SoulJars/actions/workflows/maven.yml)
[![Slimefun Legacy](https://img.shields.io/badge/Slimefun-Legacy-6bd425)](https://github.com/wickidcow/Slimefun-Legacy)
[![Paper](https://img.shields.io/badge/Server-Paper%2026.2-blue)](https://papermc.io/)
[![Java](https://img.shields.io/badge/Build-Java%2025-orange)](https://adoptium.net/)
[![License](https://img.shields.io/badge/License-GPLv3-blue)](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE)

[Releases](https://github.com/wickidcow/SF_SoulJars/releases) · [Builds](https://github.com/wickidcow/SF_SoulJars/actions) · [Issues](https://github.com/wickidcow/SF_SoulJars/issues)

</div>

> [!IMPORTANT]
> **SF_SoulJars is an unofficial, independently maintained downstream fork of SoulJars.** It is maintained by `wickidcow` for [AlbionMC.com](https://albionmc.com) and the wider Slimefun community. It is not an official release of the original SoulJars project, the original Slimefun project, Slimefun United, or the SlimefunGuguProject.
>
> **NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

---
## What is SF_SoulJars?

SoulJars adds mob-specific soul collection to Slimefun. Players collect souls into jars and can use completed jars in spawner progression. The maintained fork preserves the original behavior while updating the addon for Minecraft 1.21.11+, current Paper-family servers, and modern Slimefun APIs.

`config.yml` controls which mob soul jars are enabled and how many souls each mob requires. Soul processing is event-driven and does not require its own global repeating scheduler.

---
## Download and build

Release JARs use the maintained Slimefun-addon naming convention:

`SF_SoulJars1.0.0.jar`

The project builds with **Java 25** while targeting **Java 21 bytecode**. The production compatibility baseline is **Slimefun Legacy 4.1.48 on Paper 26.2**. Development builds are available from GitHub Actions and versioned release JARs are published on the Releases page.

---
## Compatibility

Primary target: **Slimefun Legacy**.

Compatibility is also validated against Slimefun United, SlimefunGuguProject/Slimefun4, and original Slimefun4-compatible APIs. Paper is the primary server family; Purpur, Folia and Leaf are compatibility targets. Folia support assumes the installed Slimefun implementation is itself Folia-compatible.

The maintained code avoids obsolete Slimefun Dough config/updater helpers and external Slimefun utility-library dependencies in its main flow.

---
## Credits and project lineage

Original SoulJars project and authorship belong to **TheBusyBiscuit** and its contributors. Modern compatibility and Slimefun Legacy maintenance are provided by **wickidcow**.

This fork exists to preserve and maintain that work for current servers—not to replace the original developers or claim their work as its own. Upstream authorship, copyright notices, and license obligations remain respected.

---
## Independence, trademarks and non-affiliation

**NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

SF_SoulJars and this maintenance fork are independent community projects. They are not affiliated with, endorsed by, sponsored by, approved by, or operated by Mojang Studios or Microsoft Corporation. References to Minecraft, Slimefun, Paper, upstream projects, companies, products, or communities are for identification, compatibility, attribution, and interoperability only.

Minecraft, Mojang Studios, Microsoft, and other third-party names, logos, brands, and trademarks remain the property of their respective owners. No sponsorship, partnership, ownership, or endorsement is claimed or implied.

---
## License

SF_SoulJars is distributed under the [GNU General Public License v3.0](https://github.com/wickidcow/Slimefun-Legacy/blob/master/LICENSE), consistent with the GPLv3 licensing of the upstream project.

Upstream authorship and copyright remain with the original SoulJars authors and contributors. Copyright in later modifications remains with the contributors who authored those changes.
