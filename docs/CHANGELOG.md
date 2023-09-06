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
