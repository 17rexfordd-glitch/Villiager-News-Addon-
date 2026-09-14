# Release Checklist

Before publishing a Villager News NeoForge build:

- [ ] Minecraft target is 1.21.1.
- [ ] NeoForge target is 21.1.248.
- [ ] Java 21 is used.
- [ ] EMF 3.3.5, ETF 7.2.1, and ESF 0.8.2 compatibility is checked.
- [ ] `compileJava` succeeds.
- [ ] Full Gradle `build` succeeds.
- [ ] Output JAR passes archive integrity testing.
- [ ] `META-INF/neoforge.mods.toml` is packaged.
- [ ] Common and client mixin JSON files are packaged.
- [ ] Handbook recipe parses on Minecraft 1.21.1.
- [ ] Voice-line selection still preserves contextual rules and rare weighting.
- [ ] Minecraft reaches the main menu with the real modpack.
- [ ] A world can be entered and villagers render correctly.
- [ ] `latest.log` is checked for Villager News errors.
- [ ] SHA-256 is recorded in `RELEASES.md`.
- [ ] Release JAR and matching sources JAR are kept together.
