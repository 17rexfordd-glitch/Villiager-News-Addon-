# Contributing

This repository tracks the Minecraft 1.21.1 / NeoForge port of Villager News Addon Port.

Before changing code:

1. Keep Minecraft at 1.21.1 unless the project is intentionally being retargeted.
2. Keep NeoForge compatibility with 21.1.248 unless a newer build has been explicitly tested.
3. Do not copy newer upstream Minecraft/Fabric APIs literally; translate them to valid 1.21.1/NeoForge equivalents.
4. Verify every changed mixin target against the exact runtime class and descriptor.
5. Preserve contextual dialogue semantics when changing randomness or cooldown behavior.
6. Do not remove functionality just to make a build launch.
7. Compile, build, and runtime-test before calling a change complete.

For bugs, attach `latest.log` and any crash report.
