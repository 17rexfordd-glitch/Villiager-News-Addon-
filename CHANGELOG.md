# Changelog

## 1.3.4 — NeoForge 1.21.1 randomized build

Target environment:

- Minecraft 1.21.1
- NeoForge 21.1.248
- Java 21
- Entity Model Features 3.3.5
- Entity Texture Features 7.2.1
- Entity Sound Features 0.8.2

### NeoForge port fixes

- Corrected Minecraft 1.21.1 villager mixin targets.
- Added NeoForge-native registration and networking paths.
- Added startup-stage logging for easier compatibility diagnosis.
- Made the cosmetic EMF client hook fail safely instead of killing startup if a future EMF internal changes.
- Fixed the Villager News Handbook recipe for Minecraft 1.21.1 parsing.

### Upstream 1.3.4 changes ported

- Improved first-player-encounter handling.
- Improved player-context dialogue behavior.
- Shared recent voice-variant history between villagers.
- Improved villager-to-villager conversation partner selection.
- Improved special villager profession and trade handling.
- Improved facing/turning responsiveness.
- Ported the sign-position correction.
- Added body-rotation tracking through a 1.21.1-compatible render path.

### Additional NeoForge dialogue randomization

- Added weighted shuffle-bag voice selection.
- Increased recent-history avoidance for larger dialogue groups.
- Preserved rare-line weighting.
- Reduced immediate repeats between different villagers.
- Randomized ambient dialogue selection that previously followed predictable cycling.
