# Building the NeoForge 1.21.1 Port

## Supported build environment

Use:

- Java 21
- Minecraft 1.21.1
- NeoForge 21.1.248
- Entity Model Features 3.3.5
- Entity Texture Features 7.2.1
- Entity Sound Features 0.8.2

The known-good build has been compiled and tested with those exact versions.

## Upstream base

This port is derived from:

- Repository: `MarcYohannTheScripter/villager-news-addon-port`
- Upstream 1.3.4 commit: `9101177371739570fc97b91402c21d8edcc828e0`

The NeoForge project is maintained as a Minecraft 1.21.1 adaptation of that code. Do not copy newer Minecraft/Fabric APIs literally when updating this branch; translate them to their 1.21.1/NeoForge equivalents.

## Dependency policy

EMF, ETF, and ESF are external runtime dependencies and are not modified by this project.

For compatibility testing, always verify the exact dependency build before changing mixins or renderer integration. The known-good runtime versions are:

```text
EMF 3.3.5
ETF 7.2.1
ESF 0.8.2
```

## Regression rules

Before publishing a build:

1. Compile on Java 21 against NeoForge 21.1.248.
2. Run the full Gradle build.
3. Verify the JAR is a valid ZIP/JAR archive.
4. Verify `META-INF/neoforge.mods.toml` exists.
5. Verify both mixin configs are packaged.
6. Verify the handbook recipe parses correctly.
7. Verify the EMF mixin still targets the exact EMF 3.3.5 signatures or consciously update it for a new dependency version.
8. Launch the real Minecraft client and inspect `latest.log` for Villager News errors before promoting a build to the working baseline.

## Known-good mixin policy

Common Minecraft mixins remain strict (`required=true`, `defaultRequire=1`).

The client EMF cosmetic hook is nonessential and should fail safely rather than preventing Minecraft startup if an EMF internal changes.

## Voice randomization

The current build uses weighted shuffle bags and recent-history avoidance. When editing this code, keep these properties:

- contextual triggers must remain correct
- rare-line weights must remain meaningful
- no permanent exclusion of valid lines
- recent variants should be avoided when alternatives exist
- the system must eventually recycle the full variant pool
