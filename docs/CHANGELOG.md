# The Aether - Forge - 1.19.4-1.0.0

## Overview

- The Aether has undergone a full code rewrite from scratch, so there are numerous internal changes between the 1.12.2 version and this version.
- All known bugs from past versions such as memory leaks have also been fixed due to the complete code rewrite.
- All the textures in the mod have also been revamped to better fit modern versions of Minecraft.
- Various content has been made to fall more in line with older versions of The Aether such as b1.7.3 and 1.2.5.
- Various content and mechanics have received better balancing; some balancing changes may be too minor to list or to remember to list.
- Some changes may not be listed but can be found in previous beta release changelogs.

## Blocks

Additions

- Aether Dirt Path
  - A variant of Dirt Paths made from flattening Aether Grass Blocks and Aether Dirt with a shovel.
- Aether Farmland
  - A variant of Farmland made from tilling Aether Grass Blocks and Aether Dirt with a hoe.
- Skyroot Wood
  - A wood block crafted from Skyroot Logs.
- Stripped Skyroot Log
  - A log block made by stripping Skyroot Logs or Golden Oak Logs with an axe.
- Stripped Skyroot Wood
  - A wood block made by stripping Skyroot Wood or Golden Oak Wood with an axe.
- Golden Oak Wood
  - A wood block crafted from Golden Oak Logs.
- Skyroot Door, Trapdoor, Pressure Plate, Button, and Sign
  - Crafted using Skyroot Planks and Skyroot Sticks where needed.
- Holystone Pressure Plate and Button
  - Crafted from Holystone.
- Quicksoil Glass Pane
  - A glass pane block crafted from Quicksoil Glass.
- Icestone Stairs, Slabs, and Walls
  - Crafted from Icestone.
  - Exhibit the same freezing behavior as Icestone.
- Block of Ambrosium
  - Crafted from 9 Ambrosium Shards, similar to other blocks of ore.
- Locked Dungeon Stone
  - Variants of Carved Stone, Sentry Stone, Angelic Stone, Light Angelic Stone, Hellfire Stone, and Light Hellfire Stone.
  - Act as unbreakable blocks within a dungeon.
  - Will unlock and convert into their default unlocked counterparts once the boss is defeated.
  - Holding the block in hand in Creative mode will render a glowing lock icon over any matching block in-world.
- Trapped Dungeon Stone
  - Variants of Carved Stone, Sentry Stone, Angelic Stone, Light Angelic Stone, Hellfire Stone, and Light Hellfire Stone.
  - Act as unbreakable blocks within a dungeon.
  - Will unlock once stepped on, after which they spawn a mob and convert to their normal block counterpart.
  - Holding the block in hand in Creative mode will render a glowing exclamation point icon over any matching block in-world.
  - Trapped Carved Stone and Trapped Sentry Stone will spawn a Sentry when stepped on.
  - Trapped Angelic Stone and Trapped Light Angelic Stone will spawn a Valkyrie when stepped on.
  - Trapped Hellfire Stone and Trapped Light Hellfire Stone will spawn a Fire Minion when stepped on.
    - The Gold Dungeon does not feature this block, so these are technically unused.
- Boss Doorway Dungeon Stone
  - Variants of Carved Stone, Sentry Stone, Angelic Stone, Light Angelic Stone, Hellfire Stone, and Light Hellfire Stone.
  - Generates as an invisible block in doorways to boss rooms
  - Turns solid when a boss fight begins.
  - Prevents blocks from being placed inside boss doorways.
  - Prevents bosses from leaving the boss room.
  - Can be toggled between solid and invisible in Creative mode by right-clicking.
  - Holding the block in hand in Creative mode will render a glowing door icon over any matching block in-world.
- Treasure Doorway Dungeon Stone
  - Variants of Carved Stone, Sentry Stone, Angelic Stone, Light Angelic Stone, Hellfire Stone, and Light Hellfire Stone.
  - Act as unbreakable blocks within a dungeon.
  - Turns to air when the boss is defeated, revealing the boss room's Treasure Chest compartment.
  - Holding the block in hand in Creative mode will render a glowing chest icon over any matching block in-world.
- Frosted Ice
  - A variant of Minecraft's Frosted Ice that melts regardless of light level.
  - Can only be created by ice accessories if the "Temporary Freezing" datapack is enabled.
- Unstable Obsidian
  - A type of Obsidian that slowly melts into Lava after placement.
  - Can only be created by ice accessories if the "Temporary Freezing" datapack is enabled.

Changes

- Aether Grass Block
  - Renamed from Aether Grass, for parity with Minecraft.
- Quicksoil
  - Now limits the maximum movement speed of entities walking on the block.
- Holystone
  - Can now be generated renewably using a setup similar to Basalt generators, with Soul Soil replaced by Quicksoil and Blue Ice replaced by a Magma Block.
- Mossy Holystone
  - Can now be crafted using Holystone and Vines or Moss, similar to Mossy Cobblestone.
- Cold Aercloud and Golden Aercloud
  - Can now be jumped on at height inside the block instead of just the bottom of the block.
- Blue Aercloud
  - Will now spawn less particles if an entity is stuck in the block.
- Pink Aercloud
  - Removed.
- Golden Oak Log and Golden Oak Wood
  - Stripping these blocks with a Zanite Axe or Gravitite Axe can drop Golden Amber.
- Skyroot Leaves, Crystal Leaves, Crystal Fruit Leaves, Holiday Leaves, and Decorated Holiday Leaves
  - Now occasionally drop Skyroot Sticks when broken.
- Golden Oak Leaves
  - Now occasionally drop Skyroot Sticks when broken.
  - Can now drop Golden Apples with an extremely low probability when broken.
- Skyroot Sapling
  - Can now be placed in Flower Pots.
- Golden Oak Sapling
  - Can now be placed in Flower Pots.
- Purple Flower
  - Can now be crafted into Suspicious Stew, giving the Inebriation effect.
  - Can now be placed in Flower Pots.
- White Flower
  - Can now be crafted into Suspicious Stew, giving the Slow Falling effect.
  - Can now be crafted into White Dye instead of Light Gray Dye.
  - Can now be placed in Flower Pots.
- Bush Stem
  - Renamed from Berry Bush Stem, for parity with Aether 1.2.5.
  - Can now be placed in Flower Pots.
- Berry Bush
  - Can now be placed in Flower Pots.
- Icestone
  - The block's ability to freeze fluids is now data-driven using the `aether:icestone_freezable` recipe type.
- Gravitite Ore
  - When the block stops floating at the same position as another block such as a Torch, it will break and drop the other block and take its place.
- Holystone Bricks
  - Can now be crafted with Holystone in the Stonecutter.
- Aerogel and Aerogel Stairs, Slabs, and Walls
  - When placed next to another Aerogel-type block, the neighboring face will be hidden similar to glass blocks.
- Carved Stone, Angelic Stone, and Hellfire Stone
  - Can now be crafted with their light counterparts in the Stonecutter
  - Flattened blockstates by handling all separate dungeon block behavior with new block variants (Locked, Trapped, Boss Doorway, and Treasure Doorway).
- Sentry Stone, Light Angelic Stone, and Light Hellfire Stone
  - Can now be crafted with their non-light counterparts in the Stonecutter
  - Flattened blockstates by handling all separate dungeon block behavior with new block variants (Locked, Trapped, Boss Doorway, and Treasure Doorway).
- Pillar and Pillar Top
  - Can now be oriented in 6 different directions when placed.
  - Can now be crafted with Angelic Stone in the Stonecutter.
- Treasure Chest
  - No longer generates its loot table based on what key is used to unlock it, instead the loot table is determined by the dungeon's structure generation.
  - Can only be opened by the key that correlates to the dungeon that the Treasure Chest is in.
  - Will now drop when broken with a pickaxe after the chest has been unlocked.
- Altar
  - Renamed from Enchanter.
  - Added recipe book support in the crafting menu.
  - Recipes are now data-driven with the `aether:enchanting` and `aether:repairing` recipe types.
  - Reduced item enchanting time for recipes.
  - Remove enchanting speed boost from placing Enchanted Gravitite under the Altar.
  - Will now display a custom name in their inventory menu if renamed using an Anvil.
  - Can now handle extracting buckets with Hoppers.
- Freezer
  - Added recipe book support in the crafting menu.
  - Recipes are now data-driven with the `aether:freezing` recipe type.
  - Reduced item freezing time for recipes.
  - Remove freezing speed boost from placing Icestone under the Freezer.
  - Will now display a custom name in their inventory menu if renamed using an Anvil.
  - Can now handle extracting buckets with Hoppers.
- Incubator
  - Added recipe book support in the crafting menu.
  - Recipes are now data-driven with the `aether:incubation` recipe type.
  - Reduced egg incubation time.
  - Remove incubation speed boost from placing Magma Blocks under the Incubator.
  - Will now display a custom name in their inventory menu if renamed using an Anvil.
  - Now has Hopper support for item inputs.
- Sun Altar
  - Now has a whitelist in `config/aether/sun_altar_whitelist.json` for what players are allowed to use the Sun Altar in multiplayer.
    - This whitelist file is formatted the exact same as the normal server player whitelist.
  - Will now display a custom name in their inventory menu if renamed using an Anvil.
- Stairs, Slabs, and Walls
  - Can now be crafted using the Stonecutter.

## Items

Additions

- Hoes
  - A hoe now exists for every tool material tier.
- Skyroot Fish Buckets
  - A Skyroot Bucket equivalent now exists for every type of bucket of fish.
- Skyroot Powder Snow Bucket
  - A Skyroot Bucket variant of the Powder Snow Bucket.
- Skyroot Boat
  - A Boat variant crafted with Skyroot Planks.
- Skyroot Boat with Chest
  - A Boat with Chest variant crafted with Skyroot Planks.
- Netherite Gloves
  - Crafted by smithing using Diamond Gloves and a Netherite Ingot.

Changes

- Ambrosium Shard
  - Can now be eaten, restoring half a heart. This acts as parity with Aether b1.7.3.
  - Its ability to enchant Aether Grass Blocks into Enchanted Aether Grass Blocks is now data-driven using the `aether:ambrosium_enchanting` recipe type.
  - Now usable as Furnace fuel.
- Swet Ball
  - Its ability to convert Dirt into Grass Blocks is now data-driven using the `aether:swet_ball_conversion` recipe type.
  - Added new conversions:
    - Dirt to Mycelium in Mushroom Fields.
    - Grass Blocks to Podzol in Old Growth Taigas and Bamboo Jungles.
    - Netherrack to Crimson Nylium in Crimson Forests.
    - Netherrack to Warped Nylium in Warped Forests.
- Blue Moa Egg, White Moa Egg, and Black Moa Egg
  - Can now be stacked to 64.
  - Can now be applied to Monster Spawners.
- Orange Moa Egg
  - Removed.
- Foods
  - Have had their hunger, saturation, and eating time values rebalanced.
- Blue Gummy Swet and Golden Gummy Swet
  - Changed item name color from white to green. This acts as parity with Aether b1.7.3 and 1.2.5.
- Skyroot Bucket
  - Can now pick up water using a dispenser.
- Skyroot Water Bucket
  - Can now be placed using a dispenser.
- Book of Lore
  - The GUI visuals have been improved to look better and match the Aether b1.7.3 version.
  - Removed entries for Minecraft content.
  - Lore entries for items are now handled in the mod's language files, and can be added or changed using Resource Packs.
- Music Discs
  - Removed discs for Welcoming Skies and Legacy.
- Bronze Key, Silver Key, and Gold Key
  - Are now immune to lightning.
- Swords
  - Only apply their abilities when the player's attack charge is not on cooldown.
- Valkyrie Lance, Valkyrie Shovel, Valkyrie Pickaxe, and Valkyrie Axe
  - Reduced attack speeds slightly to balance out their extended attack reach.
  - No longer extend the player's reach in the opposite hand to the one holding the tool.
- Darts
  - Now render as stuck in the player when shot.
- Poison Dart Shooter
  - Now crafted using an Aechor Petal instead of a Skyroot Poison Bucket.
- Holy Sword
  - Enchanting with Smite will increase the power of its ability.
- Lightning Sword
  - Summoned lightning will no longer damage the player.
- Lightning Knife
  - Summoned lightning will no longer damage the player.
  - Can now be shot from a Dispenser.
- Flaming Sword
  - Enchanting with Fire Aspect will increase the power of its ability.
- Phoenix Bow
  - Enchanting with Flame will increase the power of its ability.
  - Now works with all types of arrows.
- Pig Slayer
  - Works on all newly added pig-type mobs.
  - Stronger pig-type mobs cannot be instantly killed, but instead take 2-3 hits.
- Hammer of Kingbdogz
  - Renamed from Hammer of Notch.
  - Can now be shot from a Dispenser.
- Gravitite Armor
  - The ability is now dependent on a configurable keybind, but the keybind defaults to `space` (the jump key).
- Valkyrie Armor
  - The armor's wing animation has been made more smooth.
- Neptune Armor
  - Enchanting Neptune Boots with Depth Strider will increase the power of the armor set's ability.
- Phoenix Armor
  - Provides better vision when in lava.
  - Takes longer to convert into Obsidian Armor.
  - Enchanting Phoenix Boots with Depth Strider will increase the power of the armor set's ability.
- Obsidian Armor
  - Buffed to match the stats of Netherite Armor.
- Accessories
  - The accessory system now uses Curios API for greater mod compatibility.
  - Can now be equipped using a Dispenser.
- Leather Gloves, Chainmail Gloves, Iron Gloves, Golden Gloves, and Diamond Gloves
  - Moved to the "Combat" tab in the Creative inventory.
- Gloves
  - Removed the slim arm textures and models.
  - Removed the toggle for what skin layer to display on.
  - Now provide a small damage boost when worn.
  - Now receive durability damage when attacking.
  - No longer turn invisible with the Invisibility effect.
- Zanite Ring and Zanite Pendant
  - When multiple are worn at once, their ability will stack.
  - Now provide a slight mining boost before any durability has been lost.
- Ice Ring and Ice Pendant
  - The system for these accessories being able to freeze fluids is now data-driven using the `aether:accessory_freezable` recipe type.
  - Now prevent damage when standing on Magma Blocks.
- Yellow Cape, Red Cape, and Blue Cape
  - Can be converted into a White Cape when washed in a Cauldron.
- Swet Cape
  - Changed item name color from green to white. This acts as parity with Aether b1.7.3 and 1.2.5.
- Invisibility Cloak
  - The invisibility ability can now be toggled with a keybind, which defaults to the `v` key.
  - When attacking with the Invisibility Cloak, nearby mobs will be alerted of your presence.
- Shield of Repulsion
  - Now renders on the player's hand in first person.
  - The Shield of Repulsion now only turns off during key inputs, instead of if the player is moving regardless of input.

## Mobs

Additions

- Passive Whirlwind
  - A whirlwind of white smoke particles.
  - Randomly spawns and throws items out of itself.
- Evil Whirlwind
  - A whirlwind of black smoke particles.
  - Randomly spawns and throws items out of itself.
  - Has a chance to spawn and throw out a Creeper.
- Blue Swet
  - A blue-colored slime.
  - Drops Swet Balls and Blue Aerclouds
- Golden Swet
  - A yellow-colored slime.
  - Drops Glowstone.

Changes

- Bee
  - Can now pollinate Bush Stems.
- Fox
  - Can now harvest Berry Bushes.
- Mobs
  - All Aether mob animations have been made more smooth.
  - All spawnrates have been greatly balanced.
- Phyg
  - Now has better movement handling when mounted.
  - Will now descend faster when crouching while mounted.
- Flying Cow
  - Now has better movement handling when mounted.
  - Will now descend faster when crouching while mounted.
- Moa
  - Now have different spawnrates based on color.
  - Can now be healed with Aechor Petals.
  - Using pick block on a Moa will return its corresponding Moa Egg.
  - Removed Orange Moa.
- Aerbunny
  - Will now cry and flee from the player if hit.
  - Can now jump multiple times when traveling midair.
  - Will now be saved with the player when logging out.
- Aerwhale
  - Can now be tied to Leads.
  - Now has better AI to handle getting stuck on blocks.
  - Now has better spawning parameters to avoid getting stuck in places they cannot fly out of.
- Swet
  - Split into two separate mobs: Blue Swet and Golden Swet.
  - Will slowly shrink until death when in water.
- Whirlwind
  - Split into two separate mobs: Passive Whirlwind and Evil Whirlwind.
- Aechor Plant
  - Will now spawn purple particles when hit.
- Cockatrice
  - No longer spawns during weather.
- Zephyr
  - Now has better spawning parameters to avoid getting stuck in places they cannot fly out of.
- Mimic
  - Will now immediately target the player once spawned.
  - Will now drop basic loot items when killed.
- Sentry
  - Added new explosion particles to match Aether b1.7.3.
- Valkyrie
  - Can no longer teleport outside the Silver Dungeon.
- Fire Minion
  - Will now immediately target the player once spawned.
- Bosses
  - Now have improved support for boss fights on multiplayer servers.
    - A boss fight will continue until all the players in a boss room have been killed.
    - When a boss is killed, all players will be granted the advancement for defeating it.
  - Will now deactivate if they are brought outside the boss room or if players escape the boss room.
  - Are now unable to destroy blocks outside the boss room.
- Slider
  - Now acts like a solid block when asleep.
  - Now has better navigation, and is able to get unstuck from most situations.
  - Now has slightly randomized movement pattern selection, requiring more attention to the Slider's movement from a player.
  - Will attempt to target different players depending on which has dealt the most damage.
  - Now has some rebalanced stats to better fit the modern Minecraft combat system.
  - Will now disable Shields on collision.
- Valkyrie Queen
  - Can no longer teleport outside of the boss room.
  - Will now display chat messages to all players in the boss room.
  - Will attempt to target different players depending on which has dealt the most damage.
  - Now has some rebalanced stats to better fit the modern Minecraft combat system.
- Sun Spirit
  - Has had numerous rebalances to the boss fight in an attempt to make it less tedious and more enjoyable
    - The Sun Spirit now has a larger hitbox that is easier to hit with Ice Crystals.
    - Ice Crystals that it spawns have a lower speed, making them easier to reach.
    - Other improvements have also been made to Ice Crystals.

## Non-mob Entities

Additions

- Skyroot Boat
  - A Boat variant crafted with Skyroot Planks.
- Skyroot Boat with Chest
  - A Boat with Chest variant crafted with Skyroot Planks.
- Cloud Crystal
  - A crystal projectile shot by Cloud Minions using the Cloud Staff.
  - Inflicts Weakness.
  - Damages Fire Minions and Blazes.

Changes

- Cloud Minion
  - Now shoots Cloud Crystals instead of Ice Crystals.
- Cold Parachute and Golden Parachute
  - Now follow the player's rotations when mounted.
  - Improved the movement handling to be more in line with Aether b1.7.3.
  - Holding shift will dismount the player from the parachute.
- Floating Block
  - Entities can no longer stand on floating blocks.
- Zephyr Snowball
  - Can now be blocked with a Shield.
- Ice Crystal
  - Now has a larger hitbox for entity collision.
  - Now has a faster velocity when hit by the player.
- Fire Crystal
  - Inflicts more damage.
- Thunder Crystal
  - No longer spawns lightning if the player is dead, to avoid destroying items.

## World Generation

Additions

- Skyroot Forest
  - A biome populated by Skyroot Trees.
- Skyroot Grove
  - A biome populated by sparsely distributed Skyroot Trees.
- Skyroot Meadow
  - A biome populated by a few occasional Skyroot Trees.
- Skyroot Woodland
  - A biome with slightly less Skyroot Trees than the Skyroot Forest.

Changes

- Terrain
  - The floating island generation has been changed to be as similar to the original Aether b1.7.3 terrain as possible.
  - The biome distribution now includes 4 different biomes instead of 1 with a layout similar to Aether b1.7.3.
  - Everything from trees to clouds have had their distribution rates balanced to better match the Aether b1.7.3 version.
  - Grass now spawns on islands.
- Ores
  - The distribution of ores has been made more balanced for gameplay.
- Dungeons
  - The loot tables of all dungeons now include a wider variety of items and have had rebalanced rates.
  - All dungeons' generation rates have been made more balanced for gameplay.
- Bronze Dungeon
  - Now uses a better system for checking where it can spawn in islands and where it can't, making it more common and able to be found consistently across islands of different shapes and sizes.
- Silver Dungeon and Gold Dungeon
  - Now have a wider range of y-levels that they can spawn in, whether it be near the void or on top of islands.
  - The maximum y-level they can generate at has been reduced, making it more accessible and not far off above islands.
  - Now ensure the entrance is not embedded in terrain.
  - Now blend better into any terrain it generates within.

## Command Format

Additions

- `/aether time`
  - Behaves like Minecraft's `/time` command.
  - `/aether time set`
    - Syntax: `/aether time set <value>`
      - `<value>`: `day`, `noon`, `night`, `midnight`, an integer time argument.
  - `/aether time add`
    - Syntax: `/aether time add <value>`
      - `<value>`: An integer time argument.
  - `/aether time query`
    - Syntax: `/aether time query <value>`
      - `<value>`: `daytime`, `day`.
- `/aether eternal_day`
  - Controls the Aether dimension's eternal day.
  - `/aether eternal_day set`
    - Syntax: `/aether eternal_day set <value>`
      - `<value>`: A boolean argument.
  - `/aether eternal_day query`
- `/aether player life_shards set`
  - Sets the amount of Life Shard hearts that the player has.
  - Syntax: `/aether player life_shards set <value>`
    - `<value>`: An integer argument, with a range of 0-10.
- `/aether sun_altar_whitelist`
  - Behaves like Minecraft's `/whitelist` command.
  - `/aether sun_altar_whitelist on`
  - `/aether sun_altar_whitelist off`
  - `/aether sun_altar_whitelist list`
  - `/aether sun_altar_whitelist add`
    - Syntax: `/aether sun_altar_whitelist add <value>`
      - `<value>`: A player or player selector argument.
  - `/aether sun_altar_whitelist remove`
    - Syntax: `/aether sun_altar_whitelist remove <value>`
      - `<value>`: A player or player selector argument.
  - `/aether sun_altar_whitelist reload`
- `/aether world_preview_fix`
  - Refreshes data for the world preview system, in case something with it messes up.

## Gameplay

Additions

- Equipment Debuffs
  - Non-Aether equipment (armor, tools, and weapons) is now debuffed in the Aether, promoting usage of the dimension's equipment instead.
- Advancements
  - Added new advancements to better guide player progression in gameplay:
    - The Aether
      - It's not dead!
    - Exotic Hardware
      - Have a Zanite Gemstone in your inventory
    - The More You Know!
      - Read a Book of Lore
    - Don't Count your Moas...
      - Obtain a Moa Egg
    - Baby Food
      - Harvest an Aechor petal from an Aechor Plant
    - Let's Fly!
      - Ride a Black Moa
    - Cold as Ice
      - Obtain Icestone
    - Cool Jewelry!
      - Use a Freezer and Icestone to freeze an accessory
    - Defying Gravity
      - Have a full set of gravitite armor in your inventory
    - The Power of the Gods
      - Obtain the Hammer of Kingbdogz from the Bronze Dungeon
    - Ultimate Ban Hammer
      - Kill a Zephyr with the Hammer of Kingbdogz. Sweet Revenge!
    - Challenger to the Throne
      - Obtain the Valkyrie Lance from the Bronze Dungeon. Time to challenge the Silver Dungeon!
    - Earning Your wings
      - Obtain a piece of Valkyrie equipment from the Silver Dungeon
    - Plunderer's Remorse
      - I defeated the Silver Dungeon and all I got was this stupid Hoe
    - Battle Hardened
      - Obtain a Regeneration Stone from the Valkyrie dungeon. The final dungeon awaits...
    - Fireproof
      - Obtain a piece of Phoenix armor from the Gold Dungeon
    - Ice Bucket Armor
      - Have a full set of obsidian armor in your inventory
    - A Well Earned Rest
      - Finally sleep in the Aether
- Creative Inventory
  - Added Aether Building Blocks
  - Added Aether Dungeon Blocks
  - Added Aether Natural Blocks
  - Added Aether Functional Blocks
  - Added Aether Redstone Blocks
  - Added Aether Equipment & Utilities
  - Added Aether Armor & Accessories
  - Added Aether Food & Drinks
  - Added Aether Ingredients
  - Added Aether Spawn Eggs
- Moa Skins
  - Players who have donated to the Aether Team at any point in time will now be able to access cosmetic skins for Moas through a button in the accessories menu.
  - Skins will automatically apply to the player's last mounted Moa.
  - The system allows connecting through Patreon, but players can also contact the developers directly for skins if they have donated through another service in the past.
  - The available skins are:
    - Blue Moa
    - White Moa
    - Black Moa
    - Classic Moa
    - Boko Yellow
    - Crookjaw Purple
    - Gharrix Red
    - Halcian Pink
    - Tivalier Green
    - Arctic Moa
    - Cockatrice
    - Phoenix Moa
    - Sentry Moa
    - Valkyrie Moa

Changes

- Aether Portal
  - Portals can no longer generate inside dungeon boss rooms or on top of Blue Aerclouds.
- Day and Night Cycle
  - The cycle for day and night in the Aether is 3 now times longer than the Overworld.
  - The sun and moon will now fade when going under the horizon in the Aether.
- Weather
  - The sky will now get less dark in the Aether during weather events in the Overworld.
- Falling out of the Aether
  - Items that fall out of the Aether from being dropped by the player intentionally or after death will be teleported to the Overworld, instead of being destroyed.
- Item Placement Prevention
  - Made item and block placement prevention data-driven.
    - Item and block placement by hand is prevented using the `aether:item_placement_ban` recipe type.
    - Block creation in-world is prevented using the `aether:block_placement_ban` recipe type.
    - Converting blocks into other blocks when placed in-world is handled using the `placement_conversion` recipe type.
- Accessories System
  - The accessories menu now has various new features for parity with Minecraft's default inventory, such as the recipe book, effects displays, and the "Destroy Item" button in Creative mode.
  - A new "Moa Skins" button has been added to the top left corner.
- Advancements
  - Renamed "Now you're family" to "... Until They hatch!".
  - The advancement for incubating a Moa now actually checks for incubating a Moa, instead of crafting an Incubator.
  - "Pink is the New Blue" is now about obtaining Enchanted Gravitite instead of a Gravitite Tool.
- Creative inventory
  - Removed Aether Blocks
  - Removed Aether Tools
  - Removed Aether Weapons
  - Removed Aether Armor
  - Removed Aether Foods
  - Removed Aether Accessories
  - Removed Aether Materials
  - Removed Aether Miscellaneous

## General

Additions

- Title Screen
  - Added the world preview system from Aether b1.7.3, allowing the player to view their last played world as the title screen panorama, and quickly enter it when loaded.
  - The world preview can be toggled with the "W" button on the menu.
  - The world can be quick-loaded with the "Q" button on the menu.
- Splashes
  - Added "Happy anniversary to the Aether!" on July 22nd.
- Music
  - Added "Clouds" by Emile van Krieken back into the game.
- Resource Packs
  - Added the built-in resource pack "Aether 1.2.5 Textures", which can be selected for programmer art texture variants from the Aether 1.2.5 version.
  - Added the built-in resource pack "Aether Colorblind Textures", which be selected to give Aerclouds unique patterns to distinguish them for color blindness accessibility.
- Data Packs
  - Added the built-in data pack "Aether Temporary Freezing", which can be selected to make ice accessories create temporary blocks like Frosted Ice and Unstable Obsidian, instead of permanent ones.
- Recipe
  - Added new recipe types for crafting stations:
    - `aether:repairing`
    - `aether:enchanting`
    - `aether:freezing`
    - `aether:incubation`
  - Added new recipe types for in-world block conversions:
    - `aether:ambrosium_enchanting`
    - `aether:swet_ball_conversion`
    - `aether:icestone_freezable`
    - `aether:accessory_freezable`
    - `aether:placement_conversion`
  - Added new recipe types for banning blocks and items from being placed in the Aether dimension:
    - `aether:item_placement_ban`
    - `aether:block_placement_ban`
- Tags
  - Block tags:
    - `#aether:treated_as_vanilla_block`
      - Blocks that are added by the mod but should act like vanilla blocks for the purpose of not having tool debuffs.
      - Contains Chest Mimic, Frosted Ice, and Unstable Obsidian.
    - `#aether:aether_portal_blocks`
      - Blocks that can be used to construct an Aether Portal.
      - Contains Glowstone.
    - `#aether:aether_portal_blacklist`
      - Blocks that an Aether Portal isn't allowed to generate on.
      - Contains Blue Aercloud, `#aether:locked_dungeon_blocks`, `#aether:boss_doorway_dungeon_blocks`, and `#aether:treasure_doorway_dungeon_blocks`.
    - `#aether:aether_island_blocks`
      - Blocks that make up islands in the Aether, used for determining where Quicksoil can generate.
      - Contains Aether Dirt, Aether Grass Block, and Holystone.
    - `#aether:aether_dirt`
      - Blocks that count as Dirt or Grass for plants in the Aether.
      - Contains Aether Grass Block, Enchanted Aether Grass Block, and Aether Dirt.
    - `#aether:enchanted_grass`
      - Blocks that count as Enchanted Grass that increase plant drops.
      - Contains Enchanted Aether Grass.
    - `#aether:holystone`
      - Blocks that count as Holystone.
      - Contains Holystone and Mossy Holystone.
    - `#aether:aerclouds`
      - Blocks that count as Aerclouds.
      - Contains Cold Aercloud, Blue Aercloud, and Golden Aercloud.
    - `#aether:skyroot_logs`
      - Blocks that count as Skyroot Logs.
      - Contains Skyroot Log, Skyroot Wood, Stripped Skyroot Log, and Stripped Skyroot Wood.
    - `#aether:golden_oak_logs`
      - Blocks that count as Golden Oak Logs.
      - Contains Golden Oak Log and Golden Oak Wood.
    - `#aether:allowed_bucket_pickup`
      - Blocks that can be picked up by Skyroot Buckets.
      - Contains Powder Snow.
    - `#aether:aerogel`
      - Blocks that are made of Aerogel.
      - Contains Aerogel, Aerogel Wall, Aerogel Stairs, and Aerogel Slab.
    - `#aether:dungeon_blocks`
      - Regular dungeon blocks.
      - Contains Carved Stone, Sentry Stone, Angelic Stone, Light Angelic Stone, Hellfire Stone, and Light Hellfire Stone.
    - `#aether:locked_dungeon_blocks`
      - Locked unbreakable dungeon blocks.
      - Contains Locked Carved Stone, Locked Sentry Stone, Locked Angelic Stone, Locked Light Angelic Stone, Locked Hellfire Stone, and Locked Light Hellfire Stone.
    - `#aether:trapped_dungeon_blocks`
      - Trapped dungeon blocks that spawn mobs.
      - Contains Trapped Carved Stone, Trapped Sentry Stone, Trapped Angelic Stone, Trapped Light Angelic Stone, Trapped Hellfire Stone, and Trapped Light Hellfire Stone.
    - `#aether:boss_doorway_dungeon_blocks`
      - Dungeon blocks used for boss room doorways.
      - Contains Boss Doorway Carved Stone, Boss Doorway Sentry Stone, Boss Doorway Angelic Stone, Boss Doorway Light Angelic Stone, Boss Doorway Hellfire Stone, and Boss Doorway Light Hellfire Stone.
    - `#aether:treasure_doorway_dungeon_blocks`
      - Dungeon blocks used for treasure room doorways.
      - Contains Treasure Doorway Carved Stone, Treasure Doorway Sentry Stone, Treasure Doorway Angelic Stone, Treasure Doorway Light Angelic Stone, Treasure Doorway Hellfire Stone, and Treasure Doorway Light Hellfire Stone.
    - `#aether:sentry_blocks`
      - Bronze Dungeon stone blocks.
      - Contains Carved Stone, Sentry Stone, Locked Carved Stone, Locked Sentry Stone, Trapped Carved Stone, Trapped Sentry Stone, Boss Doorway Carved Stone, Boss Doorway Sentry Stone, Treasure Doorway Carved Stone, Treasure Doorway Sentry Stone, Carved Stairs, Carved Slab, and Carved Wall.
    - `#aether:angelic_blocks`
      - Silver Dungeon stone blocks.
      - Contains Angelic Stone, Light Angelic Stone, Locked Angelic Stone, Locked Light Angelic Stone, Trapped Angelic Stone, Trapped Light Angelic Stone, Boss Doorway Angelic Stone, Boss Doorway Light Angelic Stone, Treasure Doorway Angelic Stone, Treasure Doorway Light Angelic Stone, Angelic Stairs, Angelic Slab, and Angelic Wall.
    - `#aether:hellfire_blocks`
      - Gold Dungeon stone blocks.
      - Contains Hellfire Stone, Light Hellfire Stone, Locked Hellfire Stone, Locked Light Hellfire Stone, Trapped Hellfire Stone, Trapped Light Hellfire Stone, Boss Doorway Hellfire Stone, Boss Doorway Light Hellfire Stone, Treasure Doorway Hellfire Stone, Treasure Doorway Light Hellfire Stone, Hellfire Stairs, Hellfire Slab, and Hellfire Wall.
    - `#aether:slider_unbreakable`
      - Blocks that the Slider can't break.
      - Contains Barrier, Bedrock, End Portal, End Portal Frame, End Gateway, Command Block, Repeating Command Block, Chain Command Block, Structure Block, Jigsaw, Moving Piston, Light, Reinforced Deepslate, `#aether:locked_dungeon_blocks`, `#aether:trapped_dungeon_blocks`, `#aether:boss_doorway_dungeon_blocks`, and `#aether:treasure_doorway_dungeon_blocks`.
    - `#aether:valkyrie_queen_unbreakable`
      - Blocks that the Valkyrie Queen can't break.
      - Contains Water, Bedrock, End Portal, End Portal Frame, End Gateway, Command Block, Repeating Command Block, Chain Command Block, Structure Block, Jigsaw, Moving Piston, Light, Reinforced Deepslate, `#aether:locked_dungeon_blocks`, `#aether:trapped_dungeon_blocks`, `#aether:boss_doorway_dungeon_blocks`, and `#aether:treasure_doorway_dungeon_blocks`.
    - `#aether:non_bronze_dungeon_spawnable`
      - Blocks that the Bronze Dungeon can't spawn in.
      - Contains Water.
    - `#aether:non_tunnel_replaceable`
      - Blocks that Bronze Dungeon tunnels can't overwrite.
      - Contains Air, Water, Chest, Chest Mimic, Treasure Chest, `#aether:locked_dungeon_blocks`, `#aether:trapped_dungeon_blocks`, `#aether:boss_doorway_dungeon_blocks`, and `#aether:treasure_doorway_dungeon_blocks`.
    - `#aether:gravitite_ability_blacklist`
      - Blocks that Gravitite Tools can't float.
      - Contains `#minecraft:buttons`, `#minecraft:pressure_plates`, `#minecraft:trapdoors`, `#minecraft:fence_gates`.
    - `#aether:aether_animals_spawnable_on`
      - Blocks that Aether animals can spawn on.
      - Contains Aether Grass Block.
    - `#aether:swet_spawnable_on`
      - Blocks that Swets can spawn on.
      - Contains Aether Grass Block.
    - `#aether:aechor_plant_spawnable_on`
      - Blocks that Aechor Plants can spawn on.
      - Contains Aether Grass Block.
    - `#aether:cockatrice_spawnable_blacklist`
      - Blocks that Cockatrices can't spawn on.
    - `#aether:infiniburn`
      - Blocks that fire will burn infinitely on in the Aether dimension.
      - Contains `#minecraft:infiniburn_overworld`.
    - `#aether:allowed_flammables`
      - Blocks that fire can be placed on in the Aether dimension.
      - Contains Soul Sand, Soul Soil, `#aether:infiniburn`, and `#aether:hellfire_blocks`.
    - `#aether:valkyrie_teleportable_on`
      - Blocks that Valkyries are allowed to teleport to.
      - Contains Locked Angelic Stone and Locked Light Angelic Stone.
    - `#aether:treated_as_aether_block`
      - Blocks that are treated as Aether blocks and should follow tool debuff rules.
  - Item tags:
    - `#aether:aether_dirt`
      - Copied from respective block tag.
    - `#aether:holystone`
      - Copied from respective block tag.
    - `#aether:aerclouds`
      - Copied from respective block tag.
    - `#aether:skyroot_logs`
      - Copied from respective block tag.
    - `#aether:golden_oak_logs`
      - Copied from respective block tag.
    - `#aether:aerogel`
      - Copied from respective block tag.
    - `#aether:dungeon_blocks`
      - Copied from respective block tag.
    - `#aether:locked_dungeon_blocks`
      - Copied from respective block tag.
    - `#aether:trapped_dungeon_blocks`
      - Copied from respective block tag.
    - `#aether:boss_doorway_dungeon_blocks`
      - Copied from respective block tag.
    - `#aether:treasure_doorway_dungeon_blocks`
      - Copied from respective block tag.
    - `#aether:sentry_blocks`
      - Copied from respective block tag.
    - `#aether:angelic_blocks`
      - Copied from respective block tag.
    - `#aether:hellfire_blocks`
      - Copied from respective block tag.
    - `#aether:crafts_skyroot_planks`
      - Items that craft into Skyroot Planks.
      - Contains `#aether:skyroot_logs` and `#aether:golden_oak_logs`.
    - `#aether:planks_crafting`
      - Items that act as planks for crafting Skyroot things.
      - Contains Skyroot Planks.
    - `#aether:skyroot_stick_crafting`
      - Items that act as planks for crafting Skyroot Sticks.
      - Contains Skyroot Planks.
    - `#aether:skyroot_tool_crafting`
      - Items that act as planks for crafting Skyroot Tools.
      - Contains Skyroot Planks.
    - `#aether:milk_bucket_crafting`
      - Items used in the place of Milk Buckets in crafting.
      - Contains Skyroot Milk Bucket and Milk Bucket.
    - `#aether:water_bucket_crafting`
      - Items used in the place of Water Buckets in crafting.
      - Contains Skyroot Water Bucket and Water Bucket.
    - `#aether:aether_portal_activation_items`
      - Items used to activate the Aether portal.
    - `#aether:book_of_lore_materials`
      - Items that can be used in crafting the Book of Lore.
      - Contains `#forge:dusts/glowstone`, Flint, and Ambrosium Shard.
    - `#aether:skyroot_stick`
      - Items that take the place of Skyroot Sticks in crafting.
      - Contains Skyroot Stick.
    - `#aether:swet_balls`
      - Items that take the place of Swet Balls in crafting.
      - Contains Swet Balls.
    - `#aether:golden_amber_harvesters`
      - Items that can be used to harvest Golden Amber from Golden Oak Logs and Golden Oak Wood.
      - Contains Zanite Axe, Gravitite Axe, and Valkyrie Axe.
    - `#aether:treated_as_aether_item`
      - Items that behave as Aether items so that they bypass the tool debuff for Aether blocks.
    - `#aether:no_skyroot_double_drops`
      - Items that should not ever be doubled by a Skyroot Sword when dropped from an entity.
      - Contains `#aether:dungeon_keys`, Victory Medal, Skyroot Pickaxe, Iron Ring, Golden Amber, Zanite Gemstone, Holystone Pickaxe, Player Head, Skeleton Skull, Creeper Head, Zombie Head, Wither Skeleton Skull, Dragon Head, and Nether Star.
    - `#aether:pig_drops`
      - Items that should have their drops increased by a Pig Slayer.
      - Contains Porkchop and Cooked Porkchop.
    - `#aether:darts`
      - Dart items shot from Dart Shooters.
      - Contains Golden Dart, Poison Dart, and Enchanted Dart.
    - `#aether:dart_shooters`
      - Dart Shooters that shoot Darts.
      - Contains Golden Dart Shooter, Poison Dart Shooter, and Enchanted Dart Shooter.
    - `#aether:deployable_parachutes`
      - Types of Parachutes that should be automatically deployed from the inventory when falling fast enough.
      - Contains Cold Parachute and Golden Parachute.
    - `#aether:dungeon_keys`
      - Dungeon keys dropped from Bosses.
      - Contains Bronze Dungeon Key, Silver Dungeon Key, and Gold Dungeon Key.
    - `#aether:accepted_music_discs`
      - A list of Music Discs that can be enchanted into the Music Disc for Aether Tune.
      - Contains 11, 13, Blocks, Chirp, Far, Mall, Mellohi, Stal, Wait, Ward, Otherside.
    - `#aether:save_nbt_in_recipe`
      - Output items in Altar and Freezer recipes that should keep the NBT from the input item.
      - Contains Enchanted Dart Shooter, Ice Ring, and Ice Pendant.
    - `#aether:moa_eggs`
      - Moa egg items.
      - Contains Blue Moa Egg, White Moa Egg, and Black Moa Egg.
    - `#aether:freezable_buckets`
      - Buckets that can be frozen into ice.
      - Contains Water Bucket and Skyroot Water Bucket.
    - `#aether:freezable_rings`
      - Rings that can be frozen into Ice Rings.
      - Contains Iron Ring and Golden Ring.
    - `#aether:freezable_pendants`
      - Pendants that can be frozen into Ice Pendants.
      - Contains Iron Pendant and Golden Pendant.
    - `#aether:slider_damaging_items`
      - Items that are capable of damaging the Slider.
      - Contains `#minecraft:pickaxes`.
    - `#aether:phyg_temptation_items`
      - Items that attract Phygs.
      - Contains Blue Berry.
    - `#aether:flying_cow_temptation_items`
      - Items that attract Flying Cows.
      - Contains Blue Berry.
    - `#aether:sheepuff_temptation_items`
      - Items that attract Sheepuffs.
      - Contains Blue Berry.
    - `#aether:aerbunny_temptation_items`
      - Items that attract Aerbunnies.
      - Contains Blue Berry.
    - `#aether:moa_temptation_items`
      - Items that attract Moas.
      - Contains Nature Staff.
    - `#aether:moa_food_items`
      - Items that can be fed to Moas.
      - Contains Aechor Petal.
    - `#aether:skyroot_repairing`
      - Items that can repair Skyroot Equipment in an Anvil.
      - Contains Skyroot Planks.
    - `#aether:holystone_repairing`
      - Items that can repair Holystone Equipment in an Anvil.
      - Contains Holystone.
    - `#aether:zanite_repairing`
      - Items that can repair Zanite Equipment in an Anvil.
      - Contains Zanite Gemstone.
    - `#aether:gravitite_repairing`
      - Items that can repair Gravitite Equipment in an Anvil.
      - Contains Enchanted Gravitite.
    - `#aether:valkyrie_repairing`
      - Items that can repair Valkyrie Equipment in an Anvil.
    - `#aether:flaming_repairing`
      - Items that can repair the Flaming Sword in an Anvil.
    - `#aether:lightning_repairing`
      - Items that can repair the Lightning Sword in an Anvil.
    - `#aether:holy_repairing`
      - Items that can repair the Holy Sword in an Anvil.
    - `#aether:vampire_repairing`
      - Items that can repair the Vampire Blade in an Anvil.
    - `#aether:pig_slayer_repairing`
      - Items that can repair the Pig Slayer in an Anvil.
    - `#aether:hammer_of_kingbdogz_repairing`
      - Items that can repair the Hammer of Kingbdogz in an Anvil.
    - `#aether:candy_cane_repairing`
      - Items that can repair the Candy Cane Sword in an Anvil.
      - Contains Candy Cane.
    - `#aether:neptune_repairing`
      - Items that can repair Neptune Equipment in an Anvil.
    - `#aether:phoenix_repairing`
      - Items that can repair Phoenix Equipment in an Anvil.
    - `#aether:obsidian_repairing`
      - Items that can repair Obsidian Equipment in an Anvil.
    - `#aether:sentry_repairing`
      - Items that can repair Sentry Boots in an Anvil.
    - `#aether:ice_repairing`
      - Items that can repair ice accessories in an Anvil.
    - `#aether:tools/lances`
      - Tool items that are Aether lances.
      - Contains Valkyrie Lance.
    - `#aether:tools/hammers`
      - Tool items that are Aether hammers.
      - Contains Hammer of Kingbdogz.
    - `#aether:accessories_rings`
      - Accessory items that are rings.
      - Contains Iron Ring, Golden Ring, Zanite Ring, and Ice Ring.
    - `#aether:accessories_pendants`
      - Accessory items that are pendants.
      - Contains Iron Pendant, Golden Pendant, Zanite Pendant, and Ice Pendant.
    - `#aether:accessories_gloves`
      - Accessory items that are gloves.
      - Contains Leather Gloves, Chainmail Gloves, Iron Gloves, Golden Gloves, Diamond Gloves, Netherite Gloves, Zanite Gloves, Gravitite Gloves, Neptune Gloves, Phoenix Gloves, Obsidian Gloves, and Valkyrie Gloves.
    - `#aether:accessories_capes`
      - Accessory items that are capes.
      - Contains Red Cape, Blue Cape, Yellow Cape, White Cape, Swet Cape, Invisibility Cloak, Agility Cape, and Valkyrie Cape.
    - `#aether:accessories_miscellaneous`
      - Accessory items that are miscellaneous.
      - Contains Golden Feather, Regeneration Stone, and Iron Bubble.
    - `#aether:accessories_shields`
      - Accessory items that are shields.
      - Contains Shield of Repulsion.
    - `#curios:aether_ring`
      - Accessory items that can be equipped to the Aether Ring slot.
      - Contains `#aether:accessories_rings`.
    - `#curios:aether_pendant`
      - Accessory items that can be equipped to the Aether Pendant slot.
      - Contains `#aether:accessories_pendants`.
    - `#curios:aether_gloves`
      - Accessory items that can be equipped to the Aether Gloves slot.
      - Contains `#aether:accessories_gloves`.
    - `#curios:aether_cape`
      - Accessory items that can be equipped to the Aether Cape slot.
      - Contains `#aether:accessories_capes`.
    - `#curios:aether_accessory`
      - Accessory items that can be equipped to the Aether Accessory slot.
      - Contains `#aether:accessories_miscellaneous`.
    - `#curios:aether_shield`
      - Accessory items that can be equipped to the Aether Shield slot.
      - Contains `#aether:accessories_shields`.
    - `#aether:accessories`
      - All accessory items.
      - Contains `#aether:accessories_rings`, `#aether:accessories_pendants`, `#aether:accessories_gloves`, `#aether:accessories_capes`, `#aether:accessories_miscellaneous`, and `#aether:accessories_shields`.
  - Entity tags:
    - `#aether:swets`
      - Swet mobs.
      - Contains Blue Swet and Golden Swet.
    - `#aether:whirlwind_unaffected`
      - Entities that can't be flung by Whirlwinds.
      - Contains Aechor Plant and `#aether:bosses`
    - `#aether:pigs`
      - Entities that classify as Pigs for the Pig Slayer.
      - Contains Pig, Phyg, Piglin, Piglin Brute, Zombified Piglin, Hoglin, and Zoglin.
    - `#aether:fire_mob`
      - Entities that are made of fire and are damaged by Cloud Crystals.
      - Contains Blaze and Fire Minion.
    - `#aether:no_skyroot_double_drops`
      - Entities that should not have their drops doubled by a Skyroot Sword.
      - Contains Player, Wither, and Ender Dragon.
    - `#aether:no_ambrosium_drops`
      - Entities that should not drop Ambrosium when hit with a Skyroot Sword.
      - Contains Player.
    - `#aether:unlaunchable`
      - Entities that should not be able to be launched by a Gravitite Sword.
      - Contains Aechor Plant.
    - `#aether:no_candy_cane_drops`
      - Entities that should not drop Candy Canes when hit with a Candy Cane Sword.
      - Contains Player.
    - `#aether:deflectable_projectiles`
      - Projectile entities that can be deflected by the Shield of Repulsion
      - Contains `#minecraft:arrows`, Egg, Small Fireball, Fireball, Snowball, Llama Spit, Trident, Shulker Bullet, Golden Dart, Poison Dart, Enchanted Dart, Poison Needle, Zephyr Snowball, Lightning Knife, and Hammer Projectile.
    - `#aether:ignore_invisibility`
      - Entities that will still detect the player regardless of if they're wearing an Invisibility Cloak.
      - Contains `#forge:bosses`, Guardian, and Elder Guardian.
    - `#aether:unhookable`
      - Entities that can't be hooked by a Fishing Rod.
      - Contains Aechor Plant, Whirlwind, Evil Whirlwind, Slider, and Sun Spirit.
    - `#aether:treated_as_aether_entity`
      - Mobs that are treated as being from the Aether for equipment debuffs.
    - `#aether:treated_as_vanilla_entity`
      - Mobs that are treated as being not from the Aether so that they avoid equipment debuffs.
  - Fluid tags:
    - `#aether:allowed_bucket_pickup`
      - Fluids that are able to be picked up by Skyroot Buckets.
      - Contains Water and Flowing Water.
  - Biome tags:
    - `#aether:is_aether`
      - Biomes that are in the Aether dimension.
      - Contains Skyroot Meadow, Skyroot Grove, Skyroot Woodland, and Skyroot Forest.
    - `#aether:has_large_aercloud`
      - Biomes that can have Large Aerclouds.
      - Contains `#aether:is_aether`.
    - `#aether:has_bronze_dungeon`
      - Biomes that can have Bronze Dungeons.
      - Contains `#aether:is_aether`.
    - `#aether:has_silver_dungeon`
      - Biomes that can have Silver Dungeons.
      - Contains `#aether:is_aether`.
    - `#aether:has_gold_dungeon`
      - Biomes that can have Gold Dungeons.
      - Contains `#aether:is_aether`.
    - `#aether:mycelium_conversion`
      - Biomes where Dirt can be converted to Mycelium by Swet Balls.
      - Contains Mushroom Fields.
    - `#aether:podzol_conversion`
      - Biomes where Grass can be converted to Podzol by Swet Balls.
      - Contains Old Growth Pine Taiga, Old Growth Spruce Taiga, and Bamboo Jungle.
    - `#aether:crimson_nylium_conversion`
      - Biomes where Netherrack can be converted to Crimson Nylium by Swet Balls.
      - Contains Crimson Forest.
    - `#aether:warped_nylium_conversion`
      - Biomes where Netherrack can be converted to Warped Nylium by Swet Balls.
      - Contains Warped Forest.
    - `#aether:ultracold`
      - Biomes where fire and lava-related items should be banned from placement.
      - Contains `#aether:is_aether`.
    - `#aether:no_wheat_seeds`
      - Biomes where Grass can't drop Wheat Seeds.
      - Contains `#aether:is_aether`.
    - `#aether:fall_to_overworld`
      - Biomes where the player can fall into the Overworld.
      - Contains `#aether:is_aether`.
    - `#aether:display_travel_text`
      - Biomes where the text for ascending and descending between the Aether can be displayed in the loading screen.
      - Contains `#aether:is_aether`, `#minecraft:is_overworld`, and The Void.
    - `#aether:aether_music`
      - Biomes where Aether music can play.
      - Contains `#aether:is_aether`.
  - Structure tags:
    - `#aether:dungeons`
      - Dungeon structures in the Aether.
      - Contains Bronze Dungeon, Silver Dungeon, and Gold Dungeon.
  - Damage type tags:
    - `#aether:is_cold`
      - Cold damage types.
      - Contains Ice Crystal (Damage Type).
- Configuration
  - Server (per-world) options:
    - "Beds explode"
      - "Vanilla's beds will explode in the Aether"
      - Default value: `false`
    - "Debuff non-Aether tools"
      - "Tools that aren't from the Aether will mine Aether blocks slower than tools that are from the Aether"
      - Default value: `true`
    - "Ambrosium Shards are edible"
      - "Ambrosium Shards can be eaten to restore a half heart of health"
      - Default value: `true`
    - "Berry Bush consistency"
      - "Makes Berry Bushes and Bush Stems behave consistently with Sweet Berry Bushes"
      - Default value: `false`
    - "Gummy Swets restore health"
      - "Gummy Swets when eaten restore full health instead of full hunger"
      - Default value: `false`
    - "Maximum consumable Life Shards"
      - "Determines the limit of the amount of Life Shards a player can consume to increase their health"
      - Default value: `10`
    - "Cooldown for the Hammer of Kingbdogz"
      - "Determines the cooldown in ticks for the Hammer of Kingbdogz's ability"
      - Default value: `75`
    - "Cooldown for the Cloud Staff"
      - "Determines the cooldown in ticks for the Cloud Staff's ability"
      - Default value: `40`
    - "Golden Feather in loot"
      - "Allows the Golden Feather to spawn in the Silver Dungeon loot table"
      - Default value: `false`
    - "Valkyrie Cape in loot"
      - "Allows the Valkyrie Cape to spawn in the Silver Dungeon loot table"
      - Default value: `true`
    - "Generate Tall Grass in the Aether"
      - "Determines whether the Aether should generate Tall Grass blocks on terrain or not"
      - Default value: `true`
    - "Generate Holiday Trees always"
      - "Determines whether Holiday Trees should always be able to generate when exploring new chunks in the Aether, if true, this overrides 'Generate Holiday Trees seasonally'"
      - Default value: `false`
    - "Generate Holiday Trees seasonally"
      - "Determines whether Holiday Trees should be able to generate during the time frame of December and January when exploring new chunks in the Aether, only works if 'Generate Holiday Trees always' is set to false"
      - Default value: `true`
    - "Balance Invisibility Cloak for PVP"
      - "Makes the Invisibility Cloak more balanced in PVP by disabling equipment invisibility temporarily after attacks"
      - Default value: `false`
    - "Invisibility Cloak visibility timer"
      - "Sets the time in ticks that it takes for the player to become fully invisible again after attacking when wearing an Invisibility Cloak; only works with 'Balance Invisibility Cloak for PVP'"
      - Default value: `50`
    - "Only whitelisted users access Sun Altars"
      - "Makes it so that only whitelisted users or anyone with permission level 4 can use the Sun Altar on a server"
      - Default value: `false`
    - "Spawns the player in the Aether"
      - "Spawns the player in the Aether dimension; this is best enabled alongside other modpack configuration to avoid issues"
      - Default value: `false`
    - "Disables Aether Portal creation"
      - "Prevents the Aether Portal from being created normally in the mod"
      - Default value: `false`
    - "Disables falling into the Overworld"
      - "Prevents the player from falling back to the Overworld when they fall out of the Aether"
      - Default value: `false`
    - "Disables eternal day"
      - "Removes eternal day so that the Aether has a normal daylight cycle even before defeating the Sun Spirit"
      - Default value: `false`
    - "Sets portal destination dimension"
      - "Sets the ID of the dimension that the Aether Portal will send the player to"
      - Default value: `aether:the_aether`
    - "Sets portal return dimension"
      - "Sets the ID of the dimension that the Aether Portal will return the player to"
      - Default value: `minecraft:overworld`
  - Common (global) options:
    - "Use default Curios' menu"
      - "Use the default Curios menu instead of the Aether's Accessories Menu. WARNING: Do not enable this without emptying your equipped accessories"
      - Default value: `false`
    - "Gives player Aether Portal Frame item"
      - "On world creation, the player is given an Aether Portal Frame item to automatically go to the Aether with"
      - Default value: `false`
    - "Gives starting loot on entry"
      - "When the player enters the Aether, they are given a Book of Lore and Golden Parachutes as starting loot"
      - Default value: `true`
    - "Repeat Sun Spirit's battle dialogue"
      - "Determines whether the Sun Spirit's dialogue when meeting him should play through every time you meet him"
      - Default value: `true`
    - "Show Patreon message"
      - "Determines if a message that links The Aether mod's Patreon should show"
      - Default value: `true`
  - Client options:
    - "Switches to legacy mob models"
      - "Changes Zephyr and Aerwhale rendering to use their old models from the b1.7.3 version of the mod"
      - Default value: `false`
    - "Disables Aether custom skybox"
      - "Disables the Aether's custom skybox in case you have a shader that is incompatible with custom skyboxes"
      - Default value: `false`
    - "Makes lightmap colder"
      - "Removes warm-tinting of the lightmap in the Aether, giving the lighting a colder feel"
      - Default value: `false`
    - "Enables green sunrise/sunset"
      - "Enables a green-tinted sunrise and sunset in the Aether, similar to the original mod"
      - Default value: `false`
    - "Enables Aether menu button"
      - "Adds a button to the top right of the main menu screen to toggle between the Aether and vanilla menu"
      - Default value: `true`
    - "Enables world preview"
      - "Changes the background panorama into a preview of the latest played world"
      - Default value: `false`
    - "Enables toggle world button"
      - "Adds a button to the top right of the main menu screen to toggle between the panorama and world preview"
      - Default value: `true`
    - "Enables quick load button"
      - "Adds a button to the top right of the main menu screen to allow quick loading into a world if the world preview is enabled"
      - Default value: `true`
    - "Align menu elements left with world preview"
      - "Determines that menu elements will align left if the menu's world preview is active, if true, this overrides 'Align menu elements left'"
      - Default value: `false`
    - "Default Aether menu style"
      - "Determines the default Aether menu style to switch to with the menu theme button"
      - Default value: `aether:the_aether_left`
    - "Default Minecraft menu style"
      - "Determines the default Minecraft menu style to switch to with the menu theme button"
      - Default value: `cumulus_menus:minecraft`
    - "Enables random trivia"
      - "Adds random trivia and tips to the bottom of loading screens"
      - Default value: `true`
    - "Enables silver life shard hearts"
      - "Makes the extra hearts given by life shards display as silver colored"
      - Default value: `true`
    - "Disables the accessories button"
      - "Disables the Aether's accessories button from appearing in GUIs"
      - Default value: `false`
    - "Button x-coordinate in inventory menus"
      - "The x-coordinate of the accessories button in the inventory and curios menus"
      - Default value: `27`
    - "Button y-coordinate in inventory menus"
      - "The y-coordinate of the accessories button in the inventory and curios menus"
      - Default value: `68`
    - "Button x-coordinate in creative menu"
      - "The x-coordinate of the accessories button in the creative menu"
      - Default value: `74`
    - "Button y-coordinate in creative menu"
      - "The y-coordinate of the accessories button in the creative menu"
      - Default value: `40`
    - "Button x-coordinate in accessories menu"
      - "The x-coordinate of the accessories button in the accessories menu"
      - Default value: `9`
    - "Button y-coordinate in accessories menu"
      - "The y-coordinate of the accessories button in the accessories menu"
      - Default value: `68`
    - "Layout x-coordinate in pause menu"
      - "The x-coordinate of the perks button layout when in the pause menu"
      - Default value: `-116`
    - "Layout y-coordinate in pause menu"
      - "The y-coordinate of the perks button layout when in the pause menu"
      - Default value: `0`
    - "Set backup minimum music delay"
      - "Sets the minimum delay for the Aether's music manager to use if needing to reset the song delay outside the Aether"
      - Default value: `12000`
    - "Set backup maximum music delay"
      - "Sets the maximum delay for the Aether's music manager to use if needing to reset the song delay outside the Aether"
      - Default value: `24000`
    - "Disables Aether music manager"
      - "Disables the Aether's internal music manager, if true, this overrides all other audio configs"
      - Default value: `false`
    - "Disables Aether menu music"
      - "Disables the Aether's menu music in case another mod implements its own, only works if 'Disables Aether music manager' is false"
      - Default value: `false`
    - "Disables vanilla world preview menu music"
      - "Disables the menu music on the vanilla world preview menu, only works if 'Disables Aether music manager' is false"
      - Default value: `false`
    - "Disables Aether world preview menu music"
      - "Disables the menu music on the Aether world preview menu, only works if 'Disables Aether music manager' is false"
      - Default value: `false`
    - "Disable Cumulus button"
      - "Disables the Cumulus menu selection screen button on launch"
      - Default value: `true`
- API
  - Moa Types
    - The mod adds a new registry named `aether:moa_type`, which can be used by other mods to add new variants of Moas.
  - Events
    - Multiple new Forge event types have been added for mod mechanics and behaviors:
      - `EggLayEvent`
      - `FreezeEvent`
        - `FreezeEvent.FreezeFromBlock`
        - `FreezeEvent.FreezeFromItem`
      - `ItemUseConvertEvent`
      - `PlacementBanEvent`
        - `PlacementBanEvent.CheckItem`
        - `PlacementBanEvent.CheckBlock`
        - `PlacementBanEvent.SpawnParticles`
      - `PlacementConvertEvent`
      - `TriggerTrapEvent`
      - `ValkyrieTeleportEvent`
- Mod Compatibility
  - CraftTweaker
    - Support for adding and removing fuels from Altars, Freezers, and Incubators using ZenScript.
  - JEI
    - Support for all new recipe types.
    - Support for Altar, Freezer, and Incubator fuels.
  - Jade
    - Disguise the Chest Mimic as a Chest in the Jade display.
  - Lootr
    - Give the Chest Mimic a special texture matching Lootr's Loot Chests.
- Translations
  - Translations are now done through Crowdin.
  - Partial translation for Chinese Simplified.
  - Partial translation for English (upside down).
  - Partial translation for Italian.
  - Partial translation for Japanese.
  - Partial translation for LOLCAT.
  - Partial translation for Malay.
  - Partial translation for Polish.
  - Partial translation for Toki Pona.
  - Partial translation for Ukrainian.
- Easter Eggs
  - New ones 😉.

Changes

- Title Screen
  - Now handled using Cumulus.
- Pro Tips
  - Removed various trivia lines that weren't relevant to content in the Aether.
- Tags
  - Block tags:
    - `#minecraft:wooden_stairs`
      - Added Skyroot Stairs.
    - `#minecraft:wooden_slabs`
      - Added Skyroot Slab.
    - `#minecraft:wooden_fences`
      - Added Skyroot Fence.
    - `#minecraft:wooden_doors`
      - Added Skyroot Door.
    - `#minecraft:wooden_trapdoors`
      - Added Skyroot Trapdoor.
    - `#minecraft:wooden_buttons`
      - Added Skyroot Button.
    - `#minecraft:wooden_pressure_plates`
      - Added Skyroot Pressure Plate.
    - `#minecraft:buttons`
      - Added Skyroot Button and Holystone Button.
    - `#minecraft:pressure_plates`
      - Added Skyroot Pressure Plate and Holystone Pressure Plate.
    - `#minecraft:saplings`
      - Added Skyroot Sapling and Golden Oak Sapling.
    - `#minecraft:logs_that_burn`
      - Added `#aether:skyroot_logs` and `#aether:golden_oak_logs`.
    - `#minecraft:stairs`
      - Added Skyroot Stairs, Carved Stairs, Angelic Stairs, Hellfire Stairs, Holystone Stairs, Mossy Holystone Stairs, Icestone Stairs, Holystone Brick Stairs, and Aerogel Stairs.
    - `#minecraft:slabs`
      - Added Skyroot Slab, Carved Slab, Angelic Slab, Hellfire Slab, Holystone Slab, Mossy Holystone Slab, Icestone Slab, Holystone Brick Slab, and Aerogel Slab.
    - `#minecraft:walls`
      - Added Carved Wall, Angelic Wall, Hellfire Wall, Holystone Wall, Mossy Holystone Wall, Icestone Wall, Holystone BrWallab, and Aerogel Wall.
    - `#minecraft:leaves`
      - Added Skyroot Leaves, Golden Oak Leaves, Crystal Leaves, Crystal Fruit Leaves, Holiday Leaves, Decorated Holiday Leaves.
    - `#minecraft:small_flowers`
      - Added Purple Flower and White Flower.
    - `#minecraft:beds`
      - Added Skyroot Bed.
    - `#minecraft:dirt`
      - Added `#aether:aether_dirt`.
    - `#minecraft:flower_pots`
      - Added Potted Berry Bush, Potted Berry Bush Stem, Potted Purple Flower, Potted White Flower, Potted Skyroot Sapling, and potted Golden Oak Sapling.
    - `#minecraft:enderman_holdable`
      - Added `#aether:aether_dirt`, Quicksoil, Purple Flower, and White Flower.
    - `#minecraft:valid_spawn`
      - Added `#aether:aether_dirt`.
    - `#minecraft:impermeable`
      - Added Quicksoil Glass.
    - `#minecraft:bamboo_plantable_on`
      - Added `#aether:aether_dirt`.
    - `#minecraft:signs`
      - Added Skyroot Sign and Skyroot Wall Sign.
    - `#minecraft:standing_signs`
      - Added Skyroot Sign.
    - `#minecraft:wall_signs`
      - Added Skyroot Wall Sign.
    - `#minecraft:dragon_immune`
      - Added `#aether:locked_dungeon_blocks`, `#aether:trapped_dungeon_blocks`, `#aether:boss_doorway_dungeon_blocks`, `#aether:treasure_doorway_dungeon_blocks`, and `#aether:aerogel`.
    - `#minecraft:wither_immune`
      - Added `#aether:locked_dungeon_blocks`, `#aether:trapped_dungeon_blocks`, `#aether:boss_doorway_dungeon_blocks`, and `#aether:treasure_doorway_dungeon_blocks`.
    - `#minecraft:bee_growables`
      - Added Berry Bush Stem.
    - `#minecraft:portals`
      - Added Aether Portal.
    - `#minecraft:beacon_base_blocks`
      - Added Zanite Block and Enchanted Gravitite.
    - `#minecraft:wall_post_override`
      - Added Ambrosium Torch.
    - `#minecraft:fence_gates`
      - Added Skyroot Fence Gate.
    - `#minecraft:mineable_with_pickaxe`
      - Added Holystone, Mossy Holystone, Icestone, Ambrosium Ore, Zanite Ore, Gravitite Ore, Holystone Bricks, Aerogel, Ambrosium Block, Zanite Block, Enchanted Gravitite, Altar, Freezer, Incubator, Carved Stone, Sentry Stone, Angelic Stone, Light Angelic Stone, Hellfire Stone, Light Hellfire Stone, Treasure Chest, Pillar, Pillar Top, Holystone Button, Holystone Pressure Plate, Carved Wall, Angelic Wall, Hellfire Wall, Holystone Wall, Mossy Holystone Wall, Icestone Wall, Holystone Brick Wall, Aerogel Wall, Carved Stairs, Angelic Stairs, Hellfire Stairs, Holystone Stairs, Mossy Holystone Stairs, Icestone Stairs, Holystone Brick Stairs, Aerogel Stairs, Carved Slab, Angelic Slab, Hellfire Slab, Holystone Slab, Mossy Holystone Slab, Icestone Slab, Holystone Brick Slab, Aerogel Slab, and Sun Altar.
    - `#minecraft:mineable_with_axe`
      - Added Skyroot Log, Golden Oak Log, Stripped Skyroot Log, Skyroot Wood, Golden Oak Wood, Stripped Skyroot Wood, Skyroot Planks, Skyroot Sign, Skyroot Wall Sign, Berry Bush Stem, Chest Mimic, Skyroot Fence, Skyroot Fence Gate, Skyroot Door, Skyroot Trapdoor, Skyroot Button, Skyroot Pressure Plate, Skyroot Stairs, Skyroot Slab, Skyroot Bookshelf, and Skyroot Bed.
    - `#minecraft:mineable_with_shovel`
      - Added Aether Grass Block, Enchanted Aether Grass Block, Aether Dirt, Quicksoil, Aether Farmland, and Aether Dirt Path.
    - `#minecraft:mineable_with_hoe`
      - Added Cold Aercloud, Blue Aercloud, Golden Aercloud, Skyroot Leaves, Golden Oak Leaves, Crystal Leaves, Crystal Fruit Leaves, Holiday Leaves, Decorated Holiday Leaves, and Berry Bush.
    - `#minecraft:needs_stone_tool`
      - Added Icestone, Icestone Stairs, Icestone Slab, Icestone Wall, Zanite Ore, Zanite Block, Carved Stone, Sentry Stone, Carved Stairs, Carved Slab, Carved Wall, Angelic Stone, Light Angelic Stone, Angelic Stairs, Angelic Slab, Angelic Wall, Hellfire Stone, Light Hellfire Stone, Hellfire Stairs, Hellfire Slab, Hellfire Wall, Treasure Chest, Pillar, and Pillar Top.
    - `#minecraft:needs_iron_tool`
      - Added Gravitite Ore and Enchanted Gravitite.
    - `#minecraft:needs_diamond_tool`
      - Added Aerogel, Aerogel Stairs, Aerogel Slab, and Aerogel Wall.
    - `#minecraft:convertable_to_mud`
      - Added Aether Dirt.
    - `#minecraft:sculk_replaceable`
      - Added `#aether:holystone`, Aether Dirt, and Quicksoil.
    - `#minecraft:snaps_goat_horn`
      - Added `#aether:skyroot_logs`, `#aether:golden_oak_logs`, Holystone, Icestone, Ambrosium Ore, Zanite Ore, and Gravitite Ore.
    - `#minecraft:snow_layer_cannot_survive_on`
      - Added Icestone.
    - `#forge:bookshelves`
      - Added Skyroot Bookshelf.
    - `#forge:fence_gates/wooden`
      - Added Skyroot Fence Gate.
    - `#forge:fences/wooden`
      - Added Skyroot Fence.
    - `#forge:glass/colorless`
      - Added Quicksoil Glass.
    - `#forge:glass_panes/colorless`
      - Added Quicksoil Glass Pane.
    - `#forge:ore_rates_singular`
      - Added Ambrosium Ore, Zanite Ore, and Gravitite Ore.
    - `#forge:ores`
      - Added Ambrosium Ore, Zanite Ore, and Gravitite Ore.
    - `#forge:stone`
      - Added `#aether:holystone`
    - `#forge:storage_blocks`
      - Added Ambrosium Block and Zanite Block.
  - Item tags:
    - `#forge:bookshelves`
      - Added Skyroot Bookshelf.
    - `#forge:fence_gates/wooden`
      - Added Skyroot Fence Gate.
    - `#forge:fences/wooden`
      - Added Skyroot Fence.
    - `#forge:fence_gates`
      - Added Skyroot Fence Gate.
    - `#forge:fences`
      - Added Skyroot Fence.
    - `#forge:eggs`
      - Added `#aether:moa_eggs`.
    - `#forge:gems`
      - Added Zanite Gemstone.
    - `#forge:glass/colorless`
      - Added Quicksoil Glass.
    - `#forge:glass_panes/colorless`
      - Added Quicksoil Glass Pane.
    - `#forge:ore_rates_singular`
      - Added Ambrosium Ore, Zanite Ore, and Gravitite Ore.
    - `#forge:ores`
      - Added Ambrosium Ore, Zanite Ore, and Gravitite Ore.
    - `#forge:rods/wooden`
      - Added Skyroot Sticks.
    - `#forge:storage_blocks`
      - Added Ambrosium Block and Zanite Block.
    - `#forge:tools`
      - Added `#aether:tools/hammers`
    - `#forge:tools/bows
      - Added Phoenix Bow.
    - `#forge:armors/helmets`
      - Added Zanite Helmet, Gravitite Helmet, Neptune Helmet, Phoenix Helmet, Obsidian Helmet, and Valkyrie Helmet.
    - `#forge:armors/chestplates`
      - Added Zanite Chestplate, Gravitite Chestplate, Neptune Chestplate, Phoenix Chestplate, Obsidian Chestplate, and Valkyrie Chestplate.
    - `#forge:armors/leggings`
      - Added Zanite Leggings, Gravitite Leggings, Neptune Leggings, Phoenix Leggings, Obsidian Leggings, and Valkyrie Leggings.
    - `#forge:armors/boots`
      - Added Zanite Boots, Gravitite Boots, Neptune Boots, Phoenix Boots, Obsidian Leggings, Valkyrie Boots, and Sentry Boots.
    - `#randomium:blacklist`
      - Added `#aether:locked_dungeon_blocks`, `#aether:trapped_dungeon_blocks`, `#aether:boss_doorway_dungeon_blocks`, `#aether:treasure_doorway_dungeon_blocks`, Chest Mimic, and Treasure Chest.
    - `#minecraft:stone_crafting_materials
      - Added Holystone.
    - `#minecraft:wooden_stairs`
      - Added Skyroot Stairs.
    - `#minecraft:wooden_slabs`
      - Added Skyroot Slab.
    - `#minecraft:wooden_fences`
      - Added Skyroot Fence.
    - `#minecraft:wooden_doors`
      - Added Skyroot Door.
    - `#minecraft:wooden_trapdoors`
      - Added Skyroot Trapdoor.
    - `#minecraft:wooden_buttons`
      - Added Skyroot Button.
    - `#minecraft:wooden_pressure_plates`
      - Added Skyroot Pressure Plate.
    - `#minecraft:saplings`
      - Added Skyroot Sapling and Golden Oak Sapling.
    - `#minecraft:logs_that_burn`
      - Added `#aether:skyroot_logs` and `#aether:golden_oak_logs`.
    - `#minecraft:stairs`
      - Added Skyroot Stairs, Carved Stairs, Angelic Stairs, Hellfire Stairs, Holystone Stairs, Mossy Holystone Stairs, Icestone Stairs, Holystone Brick Stairs, and Aerogel Stairs.
    - `#minecraft:slabs`
      - Added Skyroot Slab, Carved Slab, Angelic Slab, Hellfire Slab, Holystone Slab, Mossy Holystone Slab, Icestone Slab, Holystone Brick Slab, and Aerogel Slab.
    - `#minecraft:walls`
      - Added Carved Wall, Angelic Wall, Hellfire Wall, Holystone Wall, Mossy Holystone Wall, Icestone Wall, Holystone BrWallab, and Aerogel Wall.
    - `#minecraft:leaves`
      - Added Skyroot Leaves, Golden Oak Leaves, Crystal Leaves, Crystal Fruit Leaves, Holiday Leaves, Decorated Holiday Leaves.
    - `#minecraft:small_flowers`
      - Added Purple Flower and White Flower.
    - `#minecraft:beds`
      - Added Skyroot Bed.
    - `#minecraft:piglin_loved`
      - Added Victory Medal, Golden Ring, Golden Pendant, and Golden Gloves.
    - `#minecraft:fox_food`
      - Added Blue Berry and Enchanted Berry.
    - `#minecraft:signs`
      - Added Skyroot Sign.
    - `#minecraft:music_discs`
      - Added Aether Tune, Ascending Dawn, chinchilla, and high.
    - `#minecraft:beacon_payment_icons`
      - Added Zanite Gemstone and Enchanted Gravitite.
    - `#minecraft:boats`
      - Added Skyroot Boat.
    - `#minecraft:chest_boats`
      - Added Skyroot Chest Boat.
    - `#minecraft:cluster_max_harvestables`
      - Added Skyroot Pickaxe, Holystone Pickaxe, Zanite Pickaxe, Gravitite Pickaxe, and Valkyrie Pickaxe.
    - `#minecraft:swords`
      - Added Skyroot Sword, Holystone Sword, Zanite Sword, Gravitite Sword, Flaming Sword, Lightning Sword, Holy Sword, Vampire Blade, Pig Slayer, Candy Cane Sword, and `#aether:tools/lances`.
    - `#minecraft:axes`
      - Added Skyroot Axe, Holystone Axe, Zanite Axe, Gravitite Axe, and Valkyrie Axe.
    - `#minecraft:pickaxes`
      - Added Skyroot Pickaxe, Holystone Pickaxe, Zanite Pickaxe, Gravitite Pickaxe, and Valkyrie Pickaxe.
    - `#minecraft:shovels`
      - Added Skyroot Shovel, Holystone Shovel, Zanite Shovel, Gravitite Shovel, and Valkyrie Shovel.
    - `#minecraft:hoes`
      - Added Skyroot Hoe, Holystone Hoe, Zanite Hoe, Gravitite Hoe, and Valkyrie Hoe.
  - Entity tags:
    - `#forge:bosses`
      - Added Slider, Valkyrie Queen, and Sun Spirit.
    - `#minecraft:impact_projectiles`
      - Added Golden Dart, Poison Dart, Enchanted Dart, Lightning Knife, and Hammer Projectile.
    - `#minecraft:powder_snow_walkable_mobs`
      - Added Aerbunny
    - `#minecraft:freeze_hurts_extra_types`
      - Added Fire Minion
    - `#minecraft:frog_food`
      - Added Blue Swet, Golden Swet, and Sentry.
    - `#minecraft:fall_damage_immune`
      - Added Phyg, Flying Cow, Moa, Aerwhale, Aerbunny, Whirlwind, Evil Whirlwind, Cockatrice, Zephyr, Slider, Valkyrie, Valkyrie Queen, and Sun Spirit.
    - `#minecraft:dismounts_underwater`
      - Added Phyg, Flying Cow, Moa, Aerbunny, Blue Swet, and Golden Swet.
  - Biome tags:
    - `#minecraft:spawns_cold_variant_frogs`
      - Added `#aether:is_aether`.
  - Damage type tags:
    - `#minecraft:bypasses_armor`
      - Added Armor Piercing Attack and Inebriation (Damage Type).
    - `#minecraft:damages_helmet`
      - Added Floating Block (Damage Type).
    - `#minecraft:is_fire`
      - Added Incineration and Fire Crystal (Damage Type).
    - `#minecraft:is_projectile`
      - Added Cloud Crystal (Damage Type), Fire Crystal (Damage Type), Ice Crystal (Damage Type), and Thunder Crystal (Damage Type).

# The Aether - Forge - 1.19.4-1.0.0-beta.7.2

Additions

- Update ms_my translation.
- Update uk_ua translation.

Changes

- Update Patreon logomark.

Fixes

- Fix server timeout from trying to access supporter data.
- Fix missing pixels on Stratus supporter skin texture.

# The Aether - Forge - 1.19.4-1.0.0-beta.7.1

Changes

- Made Tips compatibility more consistent and compatible with the trivia config.

Fixes

- Fix crash from null Moa Skin selection when refreshing selection screen.
- Fix config for disabling gloves requirement for armor abilities not working.

# The Aether - Forge - 1.19.4-1.0.0-beta.7

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

# The Aether - Forge - 1.19.4-1.0.0-beta.6.4

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
- Fix an invisible multiplayer button still existing when the server button config is enabled.


# The Aether - Forge - 1.19.4-1.0.0-beta.6.3

Additions

- Add recipe for crafting the Grindstone with a Holystone Slab.

Changes

- Nerfed dart shooter damage and usage speed.
- Resized all body parts of the Moa's saddle model.
- Updated some lore entries to be more accurate to modern gameplay.

# The Aether - Forge - 1.19.4-1.0.0-beta.6.2

Changes

- Add a "Portal text y-coordinate in loading screens" config option to change the position of the "Ascending to the Aether" and "Descending from the Aether" text.
- Changed the UV map of Moa textures to have individual textures for each feather, wing, and leg.

Fixes

- Fix custom GUIs from mods like Catalogue and Controlling not working with the Aether.
- Fix the Invisibility Cloak not appearing to apply visibility to players who have joined the game after the user equipped the accessory.
- Fix a rare overlap with the Aether's music when switching from creative mode to survival mode.
- Fix the Valkyrie Queen's dialogue screen showing up for all players in the vicinity.

# The Aether - Forge - 1.19.4-1.0.0-beta.6.1

Changes

- Reordered Neptune Armor in the creative menu to be listed before Valkyrie Armor.

Fixes

- Fix Fire Crystals not moving when spawned by the Sun Spirit.
- Fix the Invisibility Cloak PVP config not properly disabling invisibility during attacks.
- Fix Hoppers being able to extract items from locked Treasure Chests.

# The Aether - Forge - 1.19.4-1.0.0-beta.6

***This beta contains breaking changes; addons will have to update accordingly to be compatible.***

Additions

- Add a new Moa Skins GUI accessible from a button with a crown icon in the Aether's accessories menu. This is where donator skins for Moas can be applied.
  - Although the GUI is implemented, the linking for donator accounts is not fully implemented yet and may not function for some players. It will be fully functional by release.
- Add a Patreon and Discord link message. This will only show up once per all worlds within a game instance (e.g. a Prism Launcher instance, the Minecraft Launcher instance tied to `.minecraft`, etc.) to minimize intrusiveness.
- Add Ruined Aether Portals, which can be enabled by selecting the "Aether Ruined Portals" data pack on world creation.
- Add a new Remedy status effect, which functions the same as how using remedy items like White Apples worked previously, except the player will be immune to Inebriation while the effect is active on top of being cured of it when the item is used.
- Add gloves to vanilla loot tables with a random chance to replace armor items.
- Add a recipe for crafting a Lectern with a Skyroot Bookshelf.
- Add the "Aether Temporary Freezing" data pack as an option when creating a world.
- Add a config option to allow for spawning in the Aether dimension upon world creation.
- Add a config option to make Berry Bushes function like Sweet Berry Bushes.
- Add a new block tag `non_bronze_dungeon_spawnable` for data pack or addon developers to prevent Bronze Dungeons from generating in certain blocks.
- Add support for modifying the fuel for Altars, Freezers, and Incubators using CraftTweaker scripting.
- Add support for extinguishing Supplementaries' Copper Lanterns in the Aether dimension.
- Add a custom menu API, which will also be released as a separate mod in the future.
- Add numerous JavaDocs throughout the source code for developers.
- Update uk_ua translation.
- Update tok translation.

Changes

- Finish a codebase cleanup that was started before beta.1. This has introduced breaking changes.
- Make joining and exiting worlds loaded by the world preview feature much faster.
- Change the distribution of Gravitite Ore to be triangular as opposed to uniform, matching ore distribution changes to vanilla in 1.18. This makes Gravitite Ore slightly more common and also be able to generate with slightly larger veins (2-4 blocks instead of 1-2 blocks).
- Move Neptune Armor from the Silver Dungeon treasure loot table into the Bronze Dungeon treasure loot table for better accuracy to b1.7.3 implementation. The armor has been rebalanced accordingly to be between iron and diamond tier.
- Rebalance the vanilla tool debuff in the Aether to be less harsh. Tools efficiency will only be decreased to the same speed as mining by hand at minimum.
- Rebalance the Hammer of Kingbdogz' projectile to have a shorter cooldown, increased damage, and greater speed.
- Rebalance dart projectiles to have a greater speed.
- Change Icestone to have a delay for refreezing blocks that are broken by the player.
- Make neighboring Aerogel-type blocks fully see-through, similar to Glass blocks.
- Increase the opacity of Blue and Golden Aerclouds in the b1.7.3 resource pack.
- Change the Nature Staff item name from green to white.
- Change the Invisibility Cloak's attack detection for projectiles to be based on projectile hit instead of projectile shoot.
- Increase the follow speed of Cloud Minions proportional to the speed of the player.
- Make Cloud Crystals damage Fire Minions.
- Disallow players from mounting Swets that are partially dissolved.
- Allow Aechor Plants to be mounted in Boats and Minecarts.
- Destroy Fire Crystals once the Sun Spirit dies.
- Prevent Bronze Dungeons from generating in water.
- Make the drop bonuses from Enchanted Aether Grass data-driven using Forge's global loot modifier system.
- Make the Gold Dungeon island's grass dependent on the biome, for addons and data packs that use different surface grass.
- Allow height ranges for dungeon generation to be modified with data packs.
- Allow the creative menu tooltips for what dungeon a loot item can be found in to be data-driven using tags.
- Change boss names to be generated using the entity's RNG instead of a unique RNG.
- Change protection of `AetherNoiseBuilders#aetherSurfaceRules` from `private` to `public`.
- Change protection of methods in `HandRenderHooks` from `private` to `public`.
- Remove mixin config.

Fixes

- Fix the Aether logo in the mod's title screen being resized improperly by GUI scaling.
- Fix the "Minecraft (Left)" title screen not shifting the logo to the left.
- Fix world corruption issues when loading the same world using world preview from two Minecraft instances at the same time.
- Fix the world preview panorama being tied to framerate.
- Fix the world preview panorama not being adjustable with the accessibility option for panorama scroll speed.
- Fix the world preview panorama not having the correct camera position.
- Fix the world preview attempting to reload the world when it shouldn't.
- Fix the world preview sometimes not displaying when it should.
- Fix aether5.ogg not playing.
- Fix high and chinchilla not playing.
- Fix the mining speed for Holystone Brick Slabs being incorrect.
- Fix placing too many Aerogel Stairs causing world loading issues.
- Fix obtaining Holystone Bricks and dungeon blocks unlocking Stone Brick-related recipes.
- Fix the overlays for special dungeon blocks sometimes breaking after swapping resource packs.
- Fix dungeon keys being consumed even when opening Treasure Chests in creative.
- Fix mobs targeting the player from much farther distances than they should be.
- Fix rare boss despawning.
- Fix a crash caused by bosses trying to break blocks without a dungeon.
- Fix an exploit with trapdoors allowing the player to not be reachable by the Slider.
- Fix an exploit with the Slider getting stuck in a corner in multiplayer after a player dies.
- Fix Aerwhales not being affected by Leads.
- Fix Swets becoming too small without dying when dissolving in water.
- Fix Swets sometimes jittering when first mounting.
- Fix Swets jittering too fast when on a Slime Block.
- Fix rare crashes from Moas.
- Fix Moas only playing their wing flap noise when mounted.
- Fix Aerbunnies' pathfinding getting them stuck on blocks and unable able to land.
- FIx Aerbunnies being afraid of the player for too long.
- Fix Aechor Plant eye height not scaling with size.
- Fix the multiplayer mount desync issue, again, maybe.
- Fix clipping exploit using parachutes.
- Fix the Hammer of Kingbdogz' projectiles not always damaging mobs.
- Fix TNT not properly launching from Blue Aerclouds if moving too fast.
- Fix projectiles sliding across Aerclouds instead of sticking like they should.
- Fix Firework Rockets disabling the Invisibility Cloak.
- Fix crystal projectiles not being properly centered to their hitbox.
- Fix Aether Grass Blocks created by lake features not having double drops.
- Fix Aerwhales animation for pitch rotation not being smooth.
- Fix the Zephyr's animation for shooting Snowballs not being synced with when the Snowball is shot.
- Fix the Slider's damage tilt speed being too slow.
- Fix the default hand model not being properly positioned in first-person when wearing the Shield of Repulsion.
- Fix capitalization inconsistencies for trivia and lore entries.
- Fix locked dungeon blocks not being in dungeon block tags.
- Fix the Silver Dungeon loot tables being in the Bronze Dungeon data path.
- Fix the `aether:moa_type` registry not appearing in `registryAccess`.
- Fix indicators added by Jade overlapping with the Aether's boss bars.
- Fix Chest Mimics showing up as Chests instead of Loot Chests in Jade indicators when Lootr is installed.

# The Aether - Forge - 1.19.4-1.0.0-beta.5.1

Fixes

- Fix mobs not targeting players.

# The Aether - Forge - 1.19.4-1.0.0-beta.5

Additions

- Add config option for disabling the Aether's accessories menu and using Curios' menu instead.
- Add block breaking behavior to the Valkyrie Queen.
- Add renewability method for Holystone.
- Add keybind `V` to toggle the Invisibility Cloak's ability.
- Add Randomium support with blacklist tag.
- Add recipes for Moa Eggs to work as eggs in crafting.
- Add missing lore entry for Skyroot Wood.
- Add new tags for accessories: `#accessories_capes`, `#accessories_gloves`, `#accessories_miscellaneous`, `#accessories_pendants`, `#accessories_rings`, `#accessories_shields`. Addon developers should use these instead of Curios' tags.
- Add `RecipeType` field to `ItemUseConversionEvent`.
- Update uk_ua translation.
- Update ms_my translation.
- Update ja_jp translation.
- Update pl_pl translation.
- Update zh_cn translation.
- Update tok translation.
- Update en_ud translation.
- Update lol_us translation.

Changes

- Change the Pillar Top model to be multi-directional.
- Allow Presents to be dropped with silk touch.
- Decrease default Moa hitbox size.
- Decrease Moa hitbox when sitting and when a baby.
- Decrease Cockatrice hitbox size.
- Improve Moa entity targetting.
- Disallow projectiles shot by the player from being bounced by their Shield of Repulsion.
- Remove the Golden Feather from Silver Dungeon loot by default.
- Update texture colors for Golden Gummy Swet and Golden Parachute.
- Move some recipes out of the `minecraft` data directory into the `aether` data directory.

Fixes

- Fix Bronze Dungeon boss rooms being able to overlap.
- Fix Bronze Dungeons being able to spawn far out of terrain.
- Fix pickaxes losing more durability to the Slider than they should.
- Fix the Slider playing walking sounds.
- Fix Silver Dungeon chests breaking on generation.
- Fix bone meal not growing Purple Flowers on Aether Grass Blocks.
- Fix some entities like TNT not being launched by Blue Aerclouds.
- Fix critical hits not working with slow falling entities and accessories.
- Fix Valkyrie Armor allowing clipping through blocks.
- Fix Valkyrie Armor wing animation speed.
- Fix the tool debuff in the Aether not working while underwater.
- Fix issues with falling out of the Aether while flying.
- Fix Flying Cow health being greater than it should be.
- Fix Hammer of Kingbdogz projectiles following player motion when they shouldn't.
- Fix Hammer of Kingbdogz projectile impact particles not always spawning.
- Fix incorrect lore entry for Stripped Skyroot Wood.
- Fix capitalization in various lore entries.
- Fix Netherite Gloves not being craftable with the 1.20 experimental data pack.
- Fix some animations playing when the game is paused.
- Fix `ItemUseConversionEvent` being called more than it should.

# The Aether - Forge - 1.19.4-1.0.0-beta.4

Additions

- Add JEI recipe support for displaying in-world recipes (e.g. freezing liquids with Icestone).
- Add equipment debuffs for non-Aether weapons and armor.
- Add stonecutting recipes for Icestone.
- Add smelting and blasting recipes for iron and gold accessories into nuggets.
- Add config option to make the Invisibility Cloak temporarily render the player's equipment after they attack, to make the accessory more balanced in PVP.
- Add config option for the duration of the Invisibility Cloak's temporary equipment rendering.
- Add sound event for Aechor Plants being hurt to make the sound customizable with resource packs.
- Add `EggLayEvent` for addon developers to modify Moa egg laying behavior.
- Add constructors for accessory classes that take `ResourceLocations`s instead of `String`s, allowing addon developers to use their own mod IDs for accessory textures.

Changes

- Change Gravitite to generate more exposed at lower y-levels and generate higher up but with less exposure.
- Allow only items dropped deliberately or from death to fall out of the Aether.
- Change attacking with the Invisibility Cloak to cause more mobs in the surrounding area to notice you.
- Update colorblind Aercloud textures.
- Increase the mining speed buff of Zanite Accessories.
- Include the Golden Feather in Silver Dungeon loot by default.
- Reduce the vertical speeds for Neptune Armor with Depth Strider to be less extreme.
- Prevent baby Moas from targetting mobs.
- Prevent the player from using blocks with inventories (e.g. Crafting Tables or Chests) when holding an item or block that can't be placed in the Aether.
- Prevent Ice Accessories from freezing liquid source blocks.
- Reduce music file size.
- Switch to having most of the Aether's config options be configurable per-world.
- Remove the slim and non-sleeve variants of Glove models to simplify their implementation.
- Remove config option for Ice Acessories being able to temporarily freeze liquids; this will return in the future as an official datapack.
- Remove translations temporarily. We are in the process of translation review after switching to a new translation service; translations will return by release.

Fixes

- Fix Blue Aerclouds interrupting Elytra flight.
- Fix Fortune not working on Berry Bushes.
- Fix Quicksoil still rarely causing crashes from spinning in boats.
- Fix rare multiplayer desync between client and server when dismounting mobs.
- Fix Sheepuffs not being able to be sheared with dispensers.
- Fix bosses and other unintentional mobs being able to be hooked with fishing rods.
- Fix Aechor Plants being able to jump.
- Fix Valkyries only teleporting on regular dungeon blocks isntead of locked dungeon blocks.
- Fix Skyroot Buckets not using a tag for their crafting material.
- Fix Aerwhales becoming invisible at the edge of the player's screen.
- Fix flight exploits with accessories, Aerbunnies, and Slow Falling Potions.
- Fix a missing slab on Silver Dungeon staircases.
- Fix Carved Stairs lore entry referring to Sentry Stone instead of Carved Stone.
- Fix Aercloud generation code randomizing blockstates in datapacks.
- Fix addons not being able to use non-Aether mod IDs with the Aether's language data generator.
- Fix incompatibilites with Cape rendering and other mods that modify capes.
- Fix some accessory modifier tooltips being unlocalized with addons.

# The Aether - Forge - 1.19.4-1.0.0-beta.3

Additions

- Add enchanting recipe in Altar for Golden Aerclouds.
- Add Name Tags to Silver Dungeon loot.
- Add damage type tag `is_cold` for damage that affects the Sun Spirit.
- Add configuration options for the position of the Accessories menu button in various screens.
- Add configuration option to disable the Accessories menu button.
- Update de_de translation.
- Update en_ud translation.

Changes

- Changed Ice accessories and Icestone to no longer affect waterlogged blocks.
- Allow pressing the key for opening the Accessories menu to also work for closing various other menus.
- Updated Candy Cane lore to be more accurate.
- Decrease chance of Zanite accessories losing durability, making them last twice as long.
- Removed the default inclusion of Aether accessories in Curios tags due to compatibility issues; an official datapack will be made available on release for this.

Fixes

- Fix softlock crash with mounts on servers.
- Fix mobs not properly respecting spawning categories.
- Fix Moas not avoiding dangerous blocks when following players.
- Fix Quicksoil not working after fixing a related crash in a previous build.
- Fix issues with Valkyrie Armor checking for ground collision.
- Fix issues with Aerclouds not being detected as ground in various instances.
- Fix Skyroot Buttons and Skyroot Pressure Plates not working as Furnace fuel.
- Fix Aether pickaxes not giving the proper amount of drops from Amethyst Clusters.
- Fix players being able to sleep in the Aether during thunderstorms while eternal day is active.
- Fix issues with item deletion by Thunder Crystals.
- Fix Moas targetting Swets ridden by players.
- Fix crosshair flickering when falling with an Aerbunny equipped.
- Fix Sun Altar slider moving off the screen.
- Fix Whirlwinds getting stuck in vertical corners.
- Fix inconsistent duration of the Slider's hit recoil after being damaged.
- Fix Cloud Minions riding in boats.
- Fix desync with falling out of Aether biomes in the Overworld.
- Fix certain water and lava-related behavior not applying to modded fluids.
- Fix missing localization for Fire Crystal and Ice Crystal projectiles.
- Fix players not being able to throw items by clicking outside of the Accessories menu window.
- Fix the material tag for crafting Skyroot tools not being used for buckets.
- Fix null error logged by Sun Altars and Treasure Chests.
- Fix debug logging being left in where it shouldn't.

# The Aether - Forge - 1.19.4-1.0.0-beta.2

Additions

- Add internal resource pack for colorblind accessibility for Aercloud textures.
- Add Jade compatibility for dungeon block tooltips.
- Add Lootr compatibility for Chest Mimics.
- Add recipe for Cakes using Skyroot Milk Buckets.
- Add tags for crafting Skyroot Sticks and Skyroot Tools.
- Make Moas immune to Inebriation.
- Make Moas be able to target and attack Aechor Plants and Swets.
- Make Frogs spawn as their cold variant in the Aether.
- Update zh_cn translation.
- Update uk_ua translation.

Changes

- Make Moas follow players by default after dismounting.
- Increase walking speed for Moas.
- Increase Moas vertical falling speed and decrease their horizontal flight speed when not mounted.
- Balance spawnrates for Swets, Aechor Plants, Cockatrices, and Zephyrs.
- Allow items to fall out of the Aether.
- Make the hotkey for Gravitite Armor's ability be spacebar by default.
- Make Sheepuffs retain their wool color after being sheared.
- Disallow Aechor Plants being able to be launched by Gravitite Swords.

Fixes

- Fix Aerwhales and Zephyrs spawning indoors.
- Fix Cockatrices spawning during thunderstorms when they shouldn't.
- Fix Holystone Tools dropping Ambrosium from blocks without hardness.
- Fix Curios' "Toggle Visibility" tooltip for accessory slot visibility buttons getting stuck on the screen and preventing clicking.
- Fix Music Discs not reducing volume with distance.
- Fix duplication exploits with Skyroot Tools' double drops ability.
- Fix Neptune Armor not applying a boost for downwards swimming speed.
- Fix the the "Debuff non-Aether tools" config not properly working on servers.
- Fix The Aether breaking other mods' custom banner patterns.
- Fix kelp causing Sliders to crash.
- Fix spinning too fast in boats on Quicksoil causing a crash.
- Fix the Silver Dungeon's cloud bed not having double drops from Skyroot Tools.
- Fix biome names not being localized for minimap mods.
- Fix mounts not being able to be dismounted in water.
- Fix Swets being squished with NoAI.
- Fix Blue Aerclouds not properly restoring Moa jumps.
- Fix z-fighting on Valkyrie model.
- Fix mounts not being able to be dismounted in minecarts.
- Fix Whirlwind item spawning sound not being localized.
- Fix mounts getting stopped midair on Blue Aerclouds.
- Fix aether1 and aether2 music files being named incorrectly according to the OST.
- Fix loot tables not using the forge:shears tag.
- Fix jump desyncs on servers with Valkyrie Armor's ability.
- Fix Valkyrie Armor not resetting flight in water.
- Fix Valkyrie Armor trying to activate flight in water.
- Fix Whirlwinds being able to ride boats.
- Fix Zanite accessories losing durability from blocks without hardness.
- Fix glove durability going down based on amount of enemies hurt.
- Fix various Aether entities not appearing translucent when invisible in spectator mode.
- Fix various Aether entities not receiving knockback on death.

# The Aether - Forge - 1.19.4-1.0.0-beta.1

The Aether has undergone a full code rewrite between 1.12.2 and 1.19.4. A full changelog of everything that has been fixed and improved will be ready for full release, but as of beta we do not have one yet. For more information about the changes, you can ask around our Discord at https://discord.gg/aethermod.
