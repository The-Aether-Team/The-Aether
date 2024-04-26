# The Aether - NeoForge - 1.20.1-1.4.1

Changes

- Rework the follow behavior for Moas now be controlled by shift right-clicking with a Nature Staff instead of by empty hand.
- Split some Moa Skin elements between the saddle layer and a hat layer.
- Make the Berry Bush model opaque with the fast graphics option.

Fixes

- Fix Aerbunny boosts sometimes not resetting from jumping on Blue Aerclouds.
- Fix speed effects not working on Swets and Moas.
- Fix the wings of Phygs, Flying Cows, and Valkyries not flashing red when hit.
- Fix gloves letting the sleeve layer clip through them slightly.

# The Aether - NeoForge - 1.20.1-1.4.0

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

# The Aether - NeoForge - 1.20.1-1.3.1

Fixes

- Fixed Bronze and Silver advancement sounds not playing.

# The Aether - NeoForge - 1.20.1-1.3.0

Additions

- Added two new tags that are now used in relevant recipes: `aether:gems/zanite` and `aether:processed/gravitite`.
- Added a new registry `aether:advancement_sound_override` which makes it easier for addon developers to override advancement sounds like for dungeons.
- Added a new placed feature `aether:aether_grass_bonemeal` to use for bone meal on Aether Grass instead of using the Vanilla-equivalent placed feature.
- Added descriptions for the Inebriation and Remedy effects that can be viewed with mods like JEED.

Fixes

- Fixed the fuel duration message for REI not being localized without JEI installed.

# The Aether - NeoForge - 1.20.1-1.2.0

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
- Fix doorways being breakable with water.
- Fix dungeon entities being able to go through portals.
- Fix Thunder Crystals still being able to destroy items.
- Fix player passengers like Aerbunnies being able to be damaged by lightning weaponry.
- Fix the high disc having an incorrect redstone signal duration.
- Fix Aether Portals continuously breaking underwater.
- Fix more issues with the Invisibility Cloak not syncing properly on multiplayer.
- Fix a warning and crash with JER.

# The Aether - NeoForge - 1.20.1-1.1.0

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
- Disallow Fire Crystal damage from being negated by Fire Resistance.

Fixes

- Fix performance overhead from biome checks for entities falling out of the Aether.
- Fix performance overhead from biome checks for converting block placements.
- Fix performance overhead from custom Tall Grass coloring.
- Fix inconsistent handling of damage modifiers with Holy Sword and Pig Slayer.
- Fix Aechor Plants being able to be leashed.
- Fix texture z-fighting on Phyg and Flying Cow wings at joints.
- Fix generic slot textures being used for Curios slots instead of charm and ring slot textures.
- Fix various mob and player layers not turning invisible when they should.
- Fix Parachutes not detecting block collision in all situations.
- Fix being able to jump on Blue Aerclouds before their effect is activated.
- Fix overlay vignette transparency bleeding into the transparency of the Hammer of Kingbdogz cooldown text.
- Fix health refilling to full on respawn even when other mods modify the respawn health amount.
- Fix all items being able to be renamed to an easter egg in the Book of Lore.
- Fix null log spam about Moa Eggs from JER.

# The Aether - NeoForge - 1.20.1-1.0.0

View the full changelog here: https://gist.github.com/bconlon1/6576351ad7ea45b7e3fcb57e9ebed69a

# The Aether - NeoForge - 1.20.1-1.0.0-beta.2.2

Additions

- Update ms_my translation.
- Update uk_ua translation.

Changes

- Update Patreon logomark.

Fixes

- Fix server timeout from trying to access supporter data.
- Fix missing pixels on Stratus supporter skin texture.

# The Aether - NeoForge - 1.20.1-1.0.0-beta.2.1

Additions

- Update de_at translation.
- Update en_ud translation.
- Update es_es translation.
- Update es_mx translation.
- Update it_it translation.
- Update ja_jp translation.
- Update lol_us translation.
- Update ms_my translation.
- Update pl_pl translation.
- Update ru_ru translation.
- Update sk_sk translation.
- Update tok translation.
- Update uk_ua translation.
- Update zh_cn translation.

Changes

- Made Tips compatibility more consistent and compatible with the trivia config.

Fixes

- Fix crash from null Moa Skin selection when refreshing selection screen.
- Fix config for disabling gloves requirement for armor abilities not working.

# The Aether - NeoForge - 1.20.1-1.0.0-beta.2

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

# The Aether - NeoForge - 1.20.1-1.0.0-beta.1.4

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
- Fix extended reach breaking when interacting using the offhand.
- Fix Skyroot Boats and Skyroot Chest Boats not being dispensable. 
- Fix armor renders not disappearing when switching out gloves in the Smithing Table.
- Fix an invisible multiplayer button still existing when the server button config is enabled.

# The Aether - NeoForge - 1.20.1-1.0.0-beta.1.3

Additions

- Add recipe for crafting the Grindstone with a Holystone Slab.
- Configurable official server button (off by default).

Changes

- Nerfed dart shooter damage and usage speed.
- Resized all body parts of the Moa's saddle model.
- Updated some lore entries to be more accurate to modern gameplay.
- Clean up `SmithingScreenMixin` after Curios update.

# The Aether - NeoForge - 1.20.1-1.0.0-beta.1.2

Fixes

- Fix a mod incompatibility crash on servers.
- Fix custom GUIs from mods like Catalogue and Controlling not working with the Aether.
- Fix the Invisibility Cloak not appearing to apply visibility to players who have joined the game after the user equipped the accessory.
- Fix a rare overlap with the Aether's music when switching from creative mode to survival mode.
- Fix Moa textures being broken in the programmer art resource packs.
- Fix text in the JEI recipe menus having shadows when it shouldn't.

# The Aether - NeoForge - 1.20.1-1.0.0-beta.1.1

Additions

- Add Golden Amber as a valid armor trim material.
- Add a "Portal text y-coordinate in loading screens" config option to change the position of the "Ascending to the Aether" and "Descending from the Aether" text.

Fixes

- Fix servers randomly stalling out and crashing when trying to spawn an entity with an accessory.
- Fix the tooltips in the Moa Skins GUI rendering in the corner of the screen.
- Fix the Aether's title screen music not carrying over to options menus.
- Fix the Valkyrie Queen's dialogue screen showing up for all players in the vicinity.

# The Aether - NeoForge - 1.20.1-1.0.0-beta.1

**This beta is a full port of 1.19.4-1.0.0-beta.6.1 to 1.20.1!**

Additions

- Add Skyroot Hanging Signs.
- Add Zanite Gemstones and Enchanted Gravitite as valid armor trim materials.
- Add the ability to trimming Zanite Armor, Gravitite Armor, Neptune Armor, Phoenix Armor, and Obsidian Armor.
- Add the ability to trim gloves.
- Add accessory slots to Armor Stands, allowing them to be equipped with all visually displaying accessories.
- Add accessory slots to Zombies, Zombie Villagers, Husks, Skeletons, and Strays. All these mobs now have a chance to spawn with gloves alongside spawning with armor.
- Add accessory slots to Piglins and Zombified Piglins. Piglins can have a chance to spawn with Golden Gloves or Golden Pendants. These are also retained through zombification in the Overworld.
- Add Skyroot Bookshelf to `#enchantment_power_provider`.
- Add Aether Grass Block, Aether Dirt, and Aether Enchanted Grass Block to `#sniffer_diggable_block`.
- Add Holystone Button to `#stone_buttons`.
- Add Berry Bush and Bush Stem to `#sword_efficient`.

Changes

- Remove Starlight as a listed recommended dependency on mod distribution platforms. It is no longer needed for performance enhancements.
- Change mod compatibility for extinguishing Copper Lanterns from being for Supplementaries to Supplementaries Squared.
