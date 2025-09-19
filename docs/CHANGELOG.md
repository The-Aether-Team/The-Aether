# The Aether - NeoForge - 1.21.1-1.5.9

Additions

- Added a new tag `pacifies_swets` for accessories that can be worn to pacify Swets, e.g. Swet Cape.
- Added a new tag `double_drops_override` for modpack makers to give blocks doubled loot drops, independent of whether the block was placed by the player.
- Update en_ud translation.
- Update es_es translation.
- Update es_mx translation.
- Update fr_fr translation.
- Update it_it translation.
- Update ja_jp translation.
- Update ms_my translation.
- Update ru_ru translation.
- Update sk_sk translation.
- Update sv_se translation.
- Update uk_ua translation.
- Update zh_cn translation.

Changes

- The player can now swim in water while riding mounted mobs from the Aether.
- Inebriation particles now pull from the Purple Dye item texture instead of Red Dye.
- Improved wording of various entries in the Book of Lore.
- The `enchanted_grass` block tag is now used instead of the Enchanted Aether Grass Block for grass coloring and the interaction behavior between Foxes and Berry Bushes.
- Improved compatibility with Just Enough Effect Descriptions (JEED). Items that apply effects are now displayed with descriptions.
- Updated Cumulus to 2.0.7. This includes various bug fixes and improved compatibility with mods like Essential.
- Updated Accessories to 1.1.0-beta.49. This includes various bug fixes and solves crashes with using newer versions of owo-lib.

Fixes

- Fix an optimization issue on large servers with too many packets sent between clients.
- Fix an optimization issue caused by Aechor Plant and Swet spawn condition tests.
- Fix the `FluidBucketWrapper` capability not properly supporting Milk.
- Fix some overlays not being hidden by F1.
- Fix issues with Aerbunnies not properly saving on the player's head and causing desyncs.
- Fix a rare issue where trying to mount and dismount from any mountable mobs in the mod too quickly would cause a desync.
- Fix Skyroot Door items not using `DoubleHighBlockItem`.
- Fix Leather Gloves not using the `IClientItemExtensions` NeoForge hook for determining default color.
- Fix a compatibility crash with the `"Gummy Swets restore health"` config option from being loaded too early.
- Fix a compatibility crash with the `"Disables Aether music manager"` config option from being loaded too early.
- Fix a compatibility crash with the tool debuff system crashing for modded entities that didn't have the `attack_damage` attribute.

# The Aether - NeoForge - 1.21.1-1.5.8

Changes

- The Sun Spirit no longer goes through blocks, but will break blocks in its path.
- Crystal Islands now support generating with surface blocks of modded biomes.

Fixes

- Fix Cumulus world preview crash loops by adding a failsafe that disables the preview after a crash.
- Fix Tips compatibility causing game resources to reload twice during startup.
- Fix compatibility with gloves not working with some mods that change arm rotations.

# The Aether - NeoForge - 1.21.1-1.5.7

Additions

- Added a new tag `ore_bearing_ground/holystone` for holystone.
- Added a new tag `holystone_ore_replaceables` for holystone.

Fixes

- Crashes relating to Cumulus and Accessories.

# The Aether - NeoForge - 1.21.1-1.5.6

Fixes

- Fix client config option `"Disables the Aether's Moa Skins button from appearing in GUIs"` not working properly.

# The Aether - NeoForge - 1.21.1-1.5.5

Changes

- Skyroot Buckets, Skyroot Water Buckets, and Skyroot Milk Buckets now have a `FluidBucketWrapper` capability.
- Item slot data for the Altar and Freezer are now transferred to the client.

Fixes

- Fix a crash from mobs dropping accessories.
- Fix buckets not having the `BUCKET_ENTITY_DATA` item component by default.
- Fix a crash with the music manager.
- Fix the Slider ignoring players that aren't in line of sight.

# The Aether - NeoForge - 1.21.1-1.5.4

Additions

- Added a new client config option `"Disables the Aether's Moa Skins button from appearing in GUIs"`![img.png](img.png) for disabling the Moa Skins button; this config will only apply for players who do not have any skins.
- Added a message when damaging the Sun Spirit with an Ice Crystal to indicate it can be damaged by conventional means as well.
- Update es_es translation.
- Update fr_fr translation.
- Update ja_jp translation.
- Update ms_my translation.
- Update ru_ru translation.
- Update sk_sk translation.
- Update sv_se translation.
- update uk_ua translation.

Fixes

- Fix using the accessories menu hotkey `I` will no longer override opening a mount's inventory.
- Fix Hammer of Kingbdogz cooldown bar not displaying when equipped to the offhand.
- Fix gloves not disappearing from the Smithing Table menu's armor stand after removing.
- Fix `MinecraftAccessor` in the wrong location.
- Fix a config-loading crash from the Aether's day cycle system.
- Fix not being able to create Immersive Portals' portals from the Aether to the Overworld.

# The Aether - NeoForge - 1.21.1-1.5.3

Fixes

- Fix crash when fighting the Sun Spirit with Twilight Forest installed.

# The Aether - NeoForge - 1.21.1-1.5.2

Fixes

- Fix Aerogel Walls culling adjacent block faces that they shouldn't.
- Fix double drops related NPE crash.

# The Aether - NeoForge - 1.21.1-1.5.1

Additions

- Added a new common server option `"Overworld-length Aether time cycle"` for changing the Aether's time from 3-times the length of the Overworld's day cycle to the same length as it.
- Added a new common server option `"Syncs time cycles"` for making the Aether's time sync with the Overworld's when it hits noon after eternal day has been banished.
- Added a new common client option `"Disables Aether's clouds"` for configuring whether clouds should render in the Aether dimension.
- Added a new tag `ores_in_ground/holystone` for Aether ores.
- Compatibility with Immersive Portals.
- Update de_at translation.
- Update en_ud translation.
- Update es_es translation.
- Update es_mx translation.
- Update fr_fr translation.
- Update it_it translation.
- Update ja_jp translation.
- Update ko_kr translation.
- Update lol_us translation.
- Update ms_my translation.
- Update pl_pl translation.
- Update ru_ru translation.
- Update sk_sk translation.
- Update sv_se translation.
- Update tok translation.
- Update uk_ua translation.
- Update zh_cn translation.

Fixes

- Fix mount mid-air jumps being triggered when jumping from the ground.
- Fix Sun Spirit and Fire Minion audio not playing.
- Fix the Aether Portal trigger sound not playing.
- Fix some discs playing in stereo mode.
- Fix Slider velocity being slower than it should be.
- Fix Aerwhales getting stuck on overhangs.
- Fix movement keys that cancel each other still disabling the Shield of Repulsion without moving.
- Fix TNT Presents having incorrect gravity.
- Fix Dart Shooters not accepting Infinity in creative mode.
- Fix Aether materials not working with new armor trims.
- Fix Skyroot Poison and Remedy Buckets not being in the `buckets` tag.
- Fix Skyroot Buckets not being in the `buckets/empty` tag.
- Fix a crash when trying to load a loot modifier asynchronously.
- Fix Supplementaries compatibility.

# The Aether - NeoForge - 1.21.1-1.5.1-beta.4

Changes

- Update Cumulus to 2.0.0. This includes a rework to the menu registration and the movement of world preview system code from Aether to Cumulus. The Aether/Minecraft Theme button is also now replaced by Cumulus' Menu List button.

Fixes

- Fix Altar and Freezer output not working correctly.
- Fix the eternal day check for sleeping in the Aether.
- Fix Valkyrie Lance being enchantable with Sweeping Edge.
- Fix meat drops from Aether animals not being cooked when killed with Fire Aspect in one hit.
- Fix a desync with the Aether tool debuff config in multiplayer.
- Fix an edge case with the Slider's movement math breaking down at high health numbers.
- Fix Shield of Repulsion deflection not working properly.
- Fix a potential edge case with the Shield of Repulsion overriding other mods cancellation of `ProjectileImpactEvent`.
- Fix the optional Shield of Repulsion tooltip being incorrect.
- Fix compatibility support for the Tips mod.
- Fix some configs being checked earlier in mod-loading than necessary to make crash logs more clear in incompatibility edge cases.

# The Aether - NeoForge - 1.21.1-1.5.1-beta.3

Fixes

- Fix eternal day not functioning correctly.
- Fix Silver Dungeons sometimes not generating with aerclouds.
- Fix an incorrect tooltip for Gravitite Armor.
- Fix first-person Shield of Repulsion rendering for players without slim arms.
- Fix projectiles getting stuck on top of the Slider.
- Fix glove modifiers being hardcoded to a specific slot.
- Fix cape textures not being correctly separated per-player.
- Fix Moa Skins not registering on the client.
- Fix incorrect enchantment selection for Valkyrie Lance.

# The Aether - NeoForge - 1.21.1-1.5.1-beta.2

Fixes

- Fix a null crash from the helper for moving accessories from Curios to the new system.
- Fix a null crash from Moa Skin loading.
- Fix Zephyr Snowballs having incorrect shooting trajectory.
- Fix the Sentry's hitbox being larger than it should be.
- Fix Valkyrie Queens not unlocking the full Silver Dungeon when defeated.
- Fix first-person glove rendering for players without slim arms.
- Fix incorrect lengths for some discs in their `jukebox_song` files.
- Fix missing mod logo images.

# The Aether - NeoForge - 1.21.1-1.5.1-beta.1

- Port 1.20.4-1.5.1 to 1.21.1.
