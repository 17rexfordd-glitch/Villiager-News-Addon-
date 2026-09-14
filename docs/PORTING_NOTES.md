# NeoForge 1.21.1 Porting Notes

## Baseline

The known-good NeoForge baseline targets Minecraft 1.21.1 and NeoForge 21.1.248.

The port originated from upstream Villager News Addon Port 1.3.3 and was then updated to upstream 1.3.4 behavior.

## Important compatibility decisions

### Mod ID and resource namespace

- NeoForge mod ID: `villager_news_addon_port`
- Existing resource namespace retained: `villager-news-addon-port`

### Java

Java 21 is required.

### EMF integration

The runtime-tested dependency is EMF 3.3.5.

`EMFModelPartMixin` was verified against that exact runtime build. The cosmetic EMF hook is treated as optional so a future internal EMF change does not unnecessarily kill Minecraft startup.

### Minecraft 1.21.1 villager classes

Minecraft 1.21.1 uses villager classes under `net.minecraft.world.entity.npc`, not the newer package paths used by upstream 26.2 code in some places.

Do not copy upstream mixin target descriptors blindly. Verify every mixin target against 1.21.1 before changing it.

### Renderer differences

The newer upstream render-state path is not copied directly. Body-turn tracking and sign rendering are adapted to APIs available in Minecraft 1.21.1 / NeoForge 21.1.x.

### Dialogue selection

The current NeoForge build uses shared recent-history plus weighted shuffle bags. This intentionally reduces repetition without breaking contextual trigger selection or rare-line weighting.

## Known external warnings

Do not automatically treat these as Villager News failures unless a stack trace proves Villager News references them:

- missing ImmediatelyFast `BatchableBufferSource`
- missing Iris rendering classes
- missing 3D Skin Layers `CustomizableModelPart`

## Updating from upstream

For each new upstream release:

1. Diff the previous upstream release against the new release.
2. Separate semantic feature changes from Minecraft/Fabric API changes.
3. Port the semantic changes onto this 1.21.1 baseline.
4. Verify 1.21.1 method names and descriptors before adding or changing mixins.
5. Rebuild against NeoForge 21.1.248 and the exact EMF/ETF/ESF versions in `README.md`.
6. Runtime-test in the real modpack before replacing the working baseline.
