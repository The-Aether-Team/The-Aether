# The Aether - NeoForge - 1.20.4-1.4.1

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

# The Aether - NeoForge - 1.20.4-1.4.0

**This release is a full port of 1.20.2-1.4.0 to NeoForge 1.20.4!**

Additions

- Added a new placed feature `aether:enchanted_aether_grass_bonemeal` to use for bone meal on Enchanted Aether Grass instead of using the Vanilla-equivalent placed feature.
- Added a new tag `slider_damaging_projectiles` to support pickaxe-like projectiles. This includes Quark's Pickarang.
- Update ar_sa translation.
- Update es_es translation.
- Update es_mx translation.
- Update it_it translation.
- Update ja_jp translation.
- Update lol_us translation.
- Update pl_pl translation.
- Update ru_ru translation.
- Update sk_sk translation.
- Update sv_se translation.
- Update tok translation.
- Update uk_ua translation.
- Update zh_cn translation.

Changes

- Convert the Moa Types registry to be data-driven.
- More thrown items and projectiles are no longer deleted in the Aether void and will fall to the Overworld.
- Boss Doorway indicator particles and Whirlwind particles now display even with minimal particle settings.
- Improve some of the debugging .mcfunctions for development.
- Switch to using DeferredHolder for registry fields.
- Made BossFightEvent, FreezeEvent, and PlacementBanEvent abstract.

Fixes

- Fix code for falling out of the Aether having performance issues.
- Fix edge cases where some modded pickaxe-like items can't damage the slider.
- Fix Aerbunny rotation on head being choppy.
- Fix Dungeon Keys not being useable from the player's offhand.
- Fix trivia prefix not copying trivia text's styling.

