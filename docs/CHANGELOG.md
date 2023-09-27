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