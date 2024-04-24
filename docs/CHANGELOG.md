# The Aether - NeoForge - 1.20.2-1.4.1

Changes

- Rework the follow behavior for Moas now be controlled by shift right-clicking with a Nature Staff instead of by empty hand.
- Split some Moa Skin elements between the saddle layer and a hat layer.
- Make the Berry Bush model opaque with the fast graphics option.

Fixes

- Fix Aerbunny boosts sometimes not resetting from jumping on Blue Aerclouds.
- Fix speed effects not working on Swets and Moas.
- Fix the wings of Phygs, Flying Cows, and Valkyries not flashing red when hit.
- Fix gloves letting the sleeve layer clip through them slightly.
- Fix the selected Moa Skin slot texture being missing after 1.20.2.

# The Aether - NeoForge - 1.20.2-1.4.0

Additions

- Added a new placed feature `aether:enchanted_aether_grass_bonemeal` to use for bone meal on Enchanted Aether Grass instead of using the Vanilla-equivalent placed feature.
- Added a new tag `slider_damaging_projectiles` to support pickaxe-like projectiles. This includes Quark's Pickarang.
- Update sk_sk translation.

Changes

- More thrown items and projectiles are no longer deleted in the Aether void and will fall to the Overworld.
- Improve some of the debugging .mcfunctions for development.

Fixes

- Fix code for falling out of the Aether having performance issues.
- Fix edge cases where some modded pickaxe-like items can't damage the slider.
- Fix Aerbunny rotation on head being choppy.
- Fix Dungeon Keys not being useable from the player's offhand.
- Fix trivia prefix not copying trivia text's styling.

# The Aether - NeoForge - 1.20.2-1.3.2

Fixes

- Fixed the mod crashing on dedicated servers.
- Fixed some in-world block recipes not working properly.

# The Aether - NeoForge - 1.20.2-1.3.1

Fixes

- Fixed Bronze and Silver advancement sounds not playing.

# The Aether - NeoForge - 1.20.2-1.3.0

**This release is a full port of 1.20.1-1.2.0 to NeoForge 1.20.2!**

Additions

- Added two new tags that are now used in relevant recipes: `aether:gems/zanite` and `aether:processed/gravitite`.
- Added a new registry `aether:advancement_sound_override` which makes it easier for addon developers to override advancement sounds like for dungeons.
- Added a new placed feature `aether:aether_grass_bonemeal` to use for bone meal on Aether Grass instead of using the Vanilla-equivalent placed feature.
- Added descriptions for the Inebriation and Remedy effects that can be viewed with mods like JEED.

Changes

- Prefixed screen overlays with the Aether's mod ID internally.

Fixes

- Fixed the fuel duration message for REI not being localized without JEI installed.
- Fixed some sprites in crafting stations being a couple pixels off.
