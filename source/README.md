# Source Code

The full buildable source for the tested randomized 1.3.4 NeoForge port is committed directly to this repository.

Main source locations:
- `src/main/java` — Java source code
- `src/main/resources` — NeoForge metadata, mixin configs, dialogue data, models, textures, sounds, recipes, and other mod resources
- `build.gradle`, `gradle.properties`, `settings.gradle` — build configuration
- `gradle/`, `gradlew`, `gradlew.bat` — Gradle wrapper
- `tools/` — upstream verification/development tools retained by the port

Target environment:
- Minecraft 1.21.1
- NeoForge 21.1.248
- Java 21
- EMF 3.3.5
- ETF 7.2.1
- ESF 0.8.2

This source matches the randomized 1.3.4 build that passed the verified NeoForge 21.1.248 compile/build pipeline. The voice randomization implementation is in `src/main/java/com/vnap/dialogue/ContextualDialogueController.java`.
