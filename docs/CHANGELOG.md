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
