# Villager News Addon Port — NeoForge 1.21.1

A NeoForge 1.21.1 port of the **Villager News Add-On** for Minecraft Java Edition, based on the upstream Villager News Addon Port by MarcYohannTheScripter.

This repository tracks the **Minecraft 1.21.1 / NeoForge** build maintained for this project.

## Current build

- **Mod version:** 1.3.4
- **Minecraft:** 1.21.1
- **NeoForge:** 21.1.248
- **Java:** 21
- **Entity Model Features:** 3.3.5
- **Entity Texture Features:** 7.2.1
- **Entity Sound Features:** 0.8.2

The current 1.3.4 build includes the upstream 1.3.4 bug fixes plus additional dialogue-randomization improvements for the NeoForge 1.21.1 port.

## Features

- Villager News models, textures, sounds, dialogue, animations, and special characters
- 2,212 voice clips across 523 dialogue groups
- Context-aware villager dialogue
- Multi-villager conversations
- Special characters including The Mayor, Testificate Man, Villager #5, Villager #9, Villager Unreachable, and Wooly
- Villager cosmetics and special trades
- Craftable Villager News Handbook
- EMF / ETF / ESF integration
- NeoForge-native networking, events, item registration, settings, and client registration
- Improved randomized voice-line selection with stronger repeat prevention while retaining contextual and rare-line weighting

## Voice randomization changes

The NeoForge 1.21.1 build extends the upstream 1.3.4 dialogue selection logic:

- recently played variants are avoided when alternatives exist
- recent-history depth is increased for larger dialogue groups
- weighted selection is preserved so rare lines remain rare
- shared history reduces immediate repeats between different villagers
- ambient dialogue choices are randomized rather than following a predictable fixed cycle

Context rules are still preserved. Villagers should only choose dialogue appropriate to the event or situation that triggered it.

## Installation

1. Install **Minecraft 1.21.1**.
2. Install **NeoForge 21.1.248**.
3. Install compatible versions of:
   - Entity Model Features 3.3.5
   - Entity Texture Features 7.2.1
   - Entity Sound Features 0.8.2
4. Place those dependency JARs and the Villager News Addon Port JAR in your Minecraft `mods` folder.
5. Launch Minecraft with Java 21.

Do not install multiple Villager News Addon Port versions at the same time.

## Tested environment

The current build is compiled and build-verified against:

```text
Minecraft 1.21.1
NeoForge 21.1.248
EMF 3.3.5
ETF 7.2.1
ESF 0.8.2
Java 21
```

## Port notes

This is not a simple loader rename. The 1.21.1 NeoForge port includes platform- and version-specific adaptations for networking, event registration, client rendering, configuration, villager data, dialogue control, item registration, and mixins.

The port also keeps startup-stage logging so future compatibility failures can be narrowed down quickly.

## Upstream

Original Java port:

- https://github.com/MarcYohannTheScripter/villager-news-addon-port

The NeoForge port follows upstream changes while translating code that depends on newer Minecraft or Fabric APIs into equivalent Minecraft 1.21.1 / NeoForge behavior.

## Credits

Villager News and the original creative assets were created by **Oreville Studios Ltd** and **Element Animation**.

The upstream Java port was created by **MarcYohannTheScripter**.

This repository does not claim ownership of the original Villager News models, textures, sounds, dialogue, names, or other creative assets. See `LICENSE` for the licensing notice.

## Status

**1.3.4 NeoForge 1.21.1 is the current working baseline.**
