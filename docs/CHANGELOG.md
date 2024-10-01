# The Aether - Forge - 1.19.2-1.5.0

Additions

- Added spawn prevention methods for Swets and Aechor Plants. The former can be stopped with a Swet Banner, and the latter can be stopped by planting Purple or White Flowers around.
- Added many new sounds in places they were previously missing: Valkyries, Cockatrices, Sun Spirit, Blue Aerclouds, Aether Portals, and more.
- Added boss fight music.
- Added a new piece of the Bronze Dungeon that can be found on the surface of islands to indicate that a dungeon can be found below.
- Added a recipe for smelting a Victory Medal into a Gold Nugget.
- Added repairing recipes for Crossbows and Flint and Steel
  Added Quark compatibility for Flamerangs working against the Slider and for Runic Etchings to recolor enchantment glints on Gloves.
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
- Fix broken textures with Lootr.
- Fix a crash with Telepastries.
- Fix missing rarity text with Obscure Tooltips.
- Fix some mixin incompatibilities.

# The Aether - Forge - 1.19.2-1.4.2

Fixes
- Fix issue with Moa following.

# The Aether - Forge - 1.19.2-1.4.1

Changes

- Rework the follow behavior for Moas now be controlled by shift right-clicking with a Nature Staff instead of by empty hand.
- Split some Moa Skin elements between the saddle layer and a hat layer.
- Make the Berry Bush model opaque with the fast graphics option.

Fixes

- Fix Aerbunny boosts sometimes not resetting from jumping on Blue Aerclouds.
- Fix speed effects not working on Swets and Moas.
- Fix the wings of Phygs, Flying Cows, and Valkyries not flashing red when hit.
- Fix gloves letting the sleeve layer clip through them slightly.

# The Aether - Forge - 1.19.2-1.4.0

Additions

- Added a new placed feature `aether:enchanted_aether_grass_bonemeal` to use for bone meal on Enchanted Aether Grass instead of using the Vanilla-equivalent placed feature.
- Added a new tag `slider_damaging_projectiles` to support pickaxe-like projectiles. This includes Quark's Pickarang.
- Update ar_sa translation.
- Update ja_jp translation.
- Update sv_se translation.
- Update tok translation.
- Update uk_ua translation.
- Update zh_cn translation.

Changes

- More thrown items and projectiles are no longer deleted in the Aether void and will fall to the Overworld.
- Improve some of the debugging .mcfunctions for development.

Fixes

- Fix code for falling out of the Aether having performance issues.
- Fix edge cases where some modded pickaxe-like items can't damage the slider.
- Fix Aerbunny rotation on head being choppy.
- Fix Dungeon Keys not being useable from the player's offhand.
- Fix trivia prefix not copying trivia text's styling.

# The Aether - Forge - 1.19.2-1.3.1

Fixes

- Fixed Bronze and Silver advancement sounds not playing.

# The Aether - Forge - 1.19.2-1.3.0

Additions

- Added two new tags that are now used in relevant recipes: `aether:gems/zanite` and `aether:processed/gravitite`.
- Added a new registry `aether:advancement_sound_override` which makes it easier for addon developers to override advancement sounds like for dungeons.
- Added a new placed feature `aether:aether_grass_bonemeal` to use for bone meal on Aether Grass instead of using the Vanilla-equivalent placed feature.
- Added descriptions for the Inebriation and Remedy effects that can be viewed with mods like JEED.

Fixes

- Fixed the fuel duration message for REI not being localized without JEI installed.

# The Aether - Forge - 1.19.2-1.2.0

Additions

- Add a client config option `"Enables Hammer of Kingbdogz' cooldown overlay"` for disabling the Hammer of Kingbdogz' visual bar for cooldown.
- Add translation keys for dimension and dungeon names.
- Add a new event `BossFightEvent` for addon developers.
- Compatibility with REI.
- Compatibility with Combatify.
- Update de_at translation.
- Update en_ud translation.
- Update es_es translation.
- Update it_it translation.
- Update ja_jp translation.
- Update ms_my translation.
- Update pl_pl translation.
- Update ru_ru translation.
- Update sk_sk translation.
- Update sv_se translation.
- Update tok translation.
- Update uk_ua translation.
- Update zh_cn translation.

Fixes

- Fix gloves not taking durability damage.
- Fix dungeon entities being able to go through portals.
- Fix Thunder Crystals still being able to destroy items.
- Fix player passengers like Aerbunnies being able to be damaged by lightning weaponry.
- Fix the high disc having an incorrect redstone signal duration.
- Fix more issues with the Invisibility Cloak not syncing properly on multiplayer.
- Fix a warning and crash with JER.

# The Aether - Forge - 1.19.2-1.1.0

Additions

- Allow the Book of Lore to be placed in Chiseled Bookshelves.
- Add a beta resource pack texture for the Lootr variant of Mimics.
- Add Swet Balls to the Forge tag for Slimeballs.
- Update sk_sk.
- Update uk_ua.

Changes

- Disable fire tick within the Aether dimension.
- Change how Moa age is internally tracked to make aging able to be displayed by mods that show mob age info.
- Allow Skyroot Buckets to be used as Furnace fuel.
- Change the model of Aerclouds to make their interior appear hollow.
- Disallow worlds from being opened by the world preview system if they have never been opened with the Aether mod.

Fixes

- Fix performance overhead from biome checks for entities falling out of the Aether.
- Fix performance overhead from biome checks for converting block placements.
- Fix performance overhead from custom Tall Grass coloring.
- Fix inconsistent handling of damage modifiers with Holy Sword and Pig Slayer.
- Fix Aechor Plants being able to be leashed.
- Fix texture z-fighting on Phyg and Flying Cow wings at joints.
- Fix various mob and player layers not turning invisible when they should.
- Fix Parachutes not detecting block collision in all situations.
- Fix being able to jump on Blue Aerclouds before their effect is activated.
- Fix health refilling to full on respawn even when other mods modify the respawn health amount.
- Fix all items being able to be renamed to an easter egg in the Book of Lore.
- Fix null log spam about Moa Eggs from JER.
- Fix missing translation entry for blocks creative tab.

# The Aether - Forge - 1.19.2-1.0.0

View the full changelog here: https://gist.github.com/bconlon1/f2c8a57554bc8478ea7ade005df1b01d

# The Aether - Forge - 1.19.2-1.0.0-beta.2.2

Additions

- Update ms_my translation.
- Update uk_ua translation.

Changes

- Update Patreon logomark.

Fixes

- Fix server timeout from trying to access supporter data.
- Fix missing pixels on Stratus supporter skin texture.

# The Aether - Forge - 1.19.2-1.0.0-beta.2.1

Changes

- Made Tips compatibility more consistent and compatible with the trivia config.

Fixes

- Fix missing class crash with Apache Commons for servers on some machines.
- Fix crash from null Moa Skin selection when refreshing selection screen.
- Fix config for disabling gloves requirement for armor abilities not working.

# The Aether - Forge - 1.19.2-1.0.0-beta.2

Additions

- Implement the fully functional system for Patreon Moa Skins.
- Add a notification message over the player's hotbar when trying to use an item that doesn't work in the Aether.
- Add a config option `"Crystal Fruit Leaves consistency"` to allow Crystal Fruit Leaves to be right-clickable for harvesting.
- Add a config option `"Require gloves for set abilities"` to toggle whether Gloves are required with a set of armor for an armor set ability to work.
- Add a translation key for localizing the name of the Aether Loot rarity type for compatibility with other mods like Loot Beams.
- Update ru_ru translation.
- Update uk_ua translation.

Changes

- Rewrite Slider AI to use goals instead of brains to fix some issues with performance.
- Improved the check for what tools are considered pickaxes for the Slider fight for better compatibility with other mods' tools.
- Changed what Note Block instrument sounds can be made from certain Aether blocks.
- Make Sliders and Valkyrie Queens check a block's hardness to see if it is breakable before trying to break it.
- Included vanilla blocks to the Plunderer's Remorse advancement.
- Prevent colors given to item names with commands from displaying in the Book of Lore.

Fixes

- Fix a major performance issue resulting from the code for players falling out of the Aether.
- Fix the Slider trying to target killed players that have respawned outside the boss room.
- Fix silver hearts from Life Shards not shaking properly.
- Fix silver hearts from Life Shards rendering too far to the left in certain situations with other mods.
- Fix certain client statuses not being communicated to other clients, like total invisibility from the Invisibility Cloak.
- Fix the code for the Aether's boss health bars interfering with other mods' boss health bars.
- Fix the Lootr texture for Mimics showing up even when the Lootr config for using Vanilla textures is enabled.

# The Aether - Forge - 1.19.2-1.0.0-beta.1.3

Additions

- Add new Moa Skins (still work-in-progress).
- Add sounds for interacting using Swet Balls and Ambrosium Shards.
- Remove Pro Tips when the Tips mod is installed, and include all Pro Tips in Tips' selection instead.
- Update es_es translation.
- Update sk_sk translation.

Changes

- Split boss bar textures into separate files.

Fixes

- Fix crash with Optifine installed.
- Fix Moa Skins render incorrectly displaying.
- Fix JER error log from Moas.
- Fix extended reach breaking when interacting using the offhand.
- Fix Skyroot Boats and Skyroot Chest Boats not being dispensable.
- Fix an invisible multiplayer button still existing when the server button config is enabled.

# The Aether - Forge - 1.19.2-1.0.0-beta.1.2

Additions

- Add recipe for crafting the Grindstone with a Holystone Slab.

Changes

- Nerfed dart shooter damage and usage speed.
- Resized all body parts of the Moa's saddle model.
- Updated some lore entries to be more accurate to modern gameplay.

Fixes

- Fix crash with Creatures and Beasts and Mining Master
- Fix incorrect lighting with cold lightmap config.
- Fix missing Mimic sound warning.
- Fix missing chest loot with Turkish system language.

# The Aether - Forge - 1.19.2-1.0.0-beta.1.1

Changes

- Add a "Portal text y-coordinate in loading screens" config option to change the position of the "Ascending to the Aether" and "Descending from the Aether" text.

Fixes

- Fix custom GUIs from mods like Catalogue and Controlling not working with the Aether.
- Fix the Invisibility Cloak not appearing to apply visibility to players who have joined the game after the user equipped the accessory.
- Fix a rare overlap with the Aether's music when switching from creative mode to survival mode.
- Fix the Valkyrie Queen's dialogue screen showing up for all players in the vicinity.

# The Aether - Forge - 1.19.2-1.0.0-beta.1

**This beta is a full port of 1.19.4-1.0.0-beta.6.1 to 1.19.2!**