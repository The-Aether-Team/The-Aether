# The Aether - NeoForge - 1.20.2-1.5.0

Additions

- Added spawn prevention methods for Swets and Aechor Plants. The former can be stopped with a Swet Banner, and the latter can be stopped by planting Purple or White Flowers around.
- Added many new sounds in places they were previously missing: Valkyries, Cockatrices, Sun Spirit, Blue Aerclouds, Aether Portals, and more.
- Added boss fight music.
- Added a new piece of the Bronze Dungeon that can be found on the surface of islands to indicate that a dungeon can be found below.
- Added a recipe for smelting a Victory Medal into a Gold Nugget.
- Added repairing recipes for Crossbows and Flint and Steel
- Added a new `"Aether Item Tooltips"` resource pack that can be enabled to detail item abilities in an item's tooltip.
- Added a new client config option `"Reposition attack message above hotbar"` for moving the notification of using the wrong tool against the Slider from the chat section to above the hotbar instead.
- Added a new server config option `"Configure Sun Altar dimensions"` for listing what dimensions the Sun Altar can be used in.
- Added `islandFoliage` and `stubFoliage` fields to the Gold Dungeon's structure JSONs to allow for foliage configuration.
- Added `processor_settings` fields to dungeon structure JSONs and created JSON files for all the dungeons' block processors.
- Added `entity.sentry.squish` and `entity.sentry.ambient` sound events (the latter of which is empty) for better resource pack configuration.
- Added the `DUNGEON_BLOCK_CONVERSIONS` field to boss entity classes to allow addons to configure what block types convert from locked to unlocked when a boss is defeated.
- Added a new disc.
- Added new Moa Skins for the Human Patreon Tier: Brown Moa, Red Moa, Green Moa, Purple Moa. Classic Moa has been renamed to Orange Moa.
- Added new Moa Skins for the Ascentan Patreon Tier: Medical Bot, Skeleton Moa.
- Added new Moa Skins for the Valkyrie Patreon Tier: Peacock Moa, Prehistoric Moa.
- Update ar_sa translation.
- Update en_ud translation.
- Update es_es translation.
- Update fr_fr translation.
- Update it_it translation.
- Update ja_jp translation.
- Update ms_my translation.
- Update pl_pl translation.
- Update ru_ru translation.
- Update sk_sk transaltion.
- Update sv_se translation.
- Update uk_ua translation.
- Update vi_vn translation.
- Update zh_cn translation.

Changes

- Allow the Valkyrie Queen to be damaged by ranged projectiles.
- Rework the balance of the Sun Spirit fight. When the boss is hit by an Ice Crystal, it now remains in a brief frozen state where the player can damage it directly by conventional means. Many values are also less random or less varied based on the boss's health or have been rebalanced in general according to the new boss behavior.
- Rework Valkyrie movement to be less floaty, allowing them to better navigate and lunge at the player in the tight corridors of the Silver Dungeon. They have also been given a cooldown for their lunge attack.
- Allow Fire Crystals to be damaged by players to reduce the projectile's lifespan.
- Rework Swet movement to be more floaty, to be more accurate to their behavior in older versions of the mod.
- Adjusted wool drops so that shearing a puffed Sheepuff will drop one extra wool block.
- Lowered blast resistance of Holystone Bricks
- Separate the effects of the `aether:aether_portal_activation_items` tag and `"Disables Aether Portal Creation"` config, so that the latter only affects creation by water. This allows for properly configuring portal creation to use a custom item.
- Releases are now run through an optimization plugin that leads to a slight reduction in JAR filesize.
- Improved music and sound quality.

Fixes

- Fix the Valkyrie Queen destroying dropped items with lightning in more rare situations.
- Fix items being destroyed when dying twice without picking them up.
- Fix Aerwhales and Zephyrs spawning in places without full sky exposure.
- Fix Cloud Minions having unlimited speed potential.
- Fix incorrect glove position in first-person.
- Fix incorrect redstone duration for High disc.
- Fix Moas not checking if they're saddled before running some mount behavior.
- Fix some issues with winged mobs' pathfinding.
- Fix some issues with mobs failing to pathfind when first spawned.
- Fix Darts having weird behavior when shot inside liquid.
- Fix a crash when switching menu themes.
- Fix Ruined Aether Portals not using addons' custom grass blocks in custom biomes.
- Fix a rare issue with music overlapping.
- Fix a rare crash from Bronze Dungeon generation.
- Fix a rare crash when fighting the Valkyrie Queen.
- Fix a server crash from AetherArmorMaterials.
- Fix some crashes when checking to debuff equipment.
- Fix a crash with Telepastries.
- Fix missing rarity text with Obscure Tooltips.
- Fix some mixin incompatibilities.

# The Aether - NeoForge - 1.20.2-1.4.2

Fixes
- Fix issue with Moa following.

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
