package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.providers.AetherItemModelProvider;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AetherItemModelData extends AetherItemModelProvider {
    public AetherItemModelData(PackOutput output, ExistingFileHelper helper) {
        super(output, Aether.MODID, helper);
    }

    @Override
    protected void registerModels() {
        this.handheldItem(AetherItems.SKYROOT_PICKAXE.get(), "tools/");
        this.handheldItem(AetherItems.SKYROOT_AXE.get(), "tools/");
        this.handheldItem(AetherItems.SKYROOT_SHOVEL.get(), "tools/");
        this.handheldItem(AetherItems.SKYROOT_HOE.get(), "tools/");

        this.handheldItem(AetherItems.HOLYSTONE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherItems.HOLYSTONE_AXE.get(), "tools/");
        this.handheldItem(AetherItems.HOLYSTONE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherItems.HOLYSTONE_HOE.get(), "tools/");

        this.handheldItem(AetherItems.ZANITE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherItems.ZANITE_AXE.get(), "tools/");
        this.handheldItem(AetherItems.ZANITE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherItems.ZANITE_HOE.get(), "tools/");

        this.handheldItem(AetherItems.GRAVITITE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherItems.GRAVITITE_AXE.get(), "tools/");
        this.handheldItem(AetherItems.GRAVITITE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherItems.GRAVITITE_HOE.get(), "tools/");

        this.handheldItem(AetherItems.VALKYRIE_PICKAXE.get(), "tools/");
        this.handheldItem(AetherItems.VALKYRIE_AXE.get(), "tools/");
        this.handheldItem(AetherItems.VALKYRIE_SHOVEL.get(), "tools/");
        this.handheldItem(AetherItems.VALKYRIE_HOE.get(), "tools/");

        this.handheldItem(AetherItems.SKYROOT_SWORD.get(), "weapons/");
        this.handheldItem(AetherItems.HOLYSTONE_SWORD.get(), "weapons/");
        this.handheldItem(AetherItems.ZANITE_SWORD.get(), "weapons/");
        this.handheldItem(AetherItems.GRAVITITE_SWORD.get(), "weapons/");

        this.lanceItem(AetherItems.VALKYRIE_LANCE.get(), "weapons/");

        this.handheldItem(AetherItems.FLAMING_SWORD.get(), "weapons/");
        this.handheldItem(AetherItems.LIGHTNING_SWORD.get(), "weapons/");
        this.handheldItem(AetherItems.HOLY_SWORD.get(), "weapons/");
        this.handheldItem(AetherItems.VAMPIRE_BLADE.get(), "weapons/");
        this.handheldItem(AetherItems.PIG_SLAYER.get(), "weapons/");
        this.nameableWeapon(AetherItems.CANDY_CANE_SWORD.get(), "weapons/", "green_candy_cane_sword");

        this.nameableWeapon(AetherItems.HAMMER_OF_KINGBDOGZ.get(), "weapons/", "hammer_of_jeb");

        this.handheldItem(AetherItems.LIGHTNING_KNIFE.get(), "weapons/");

        this.item(AetherItems.GOLDEN_DART.get(), "weapons/");
        this.item(AetherItems.POISON_DART.get(), "weapons/");
        this.item(AetherItems.ENCHANTED_DART.get(), "weapons/");

        this.dartShooterItem(AetherItems.GOLDEN_DART_SHOOTER.get(), "weapons/");
        this.dartShooterItem(AetherItems.POISON_DART_SHOOTER.get(), "weapons/");
        this.dartShooterItem(AetherItems.ENCHANTED_DART_SHOOTER.get(), "weapons/");

        this.bowItem(AetherItems.PHOENIX_BOW.get(), "weapons/");

        this.helmetItem(AetherItems.ZANITE_HELMET.get(), "armor/");
        this.chestplateItem(AetherItems.ZANITE_CHESTPLATE.get(), "armor/");
        this.leggingsItem(AetherItems.ZANITE_LEGGINGS.get(), "armor/");
        this.bootsItem(AetherItems.ZANITE_BOOTS.get(), "armor/");

        this.helmetItem(AetherItems.GRAVITITE_HELMET.get(), "armor/");
        this.chestplateItem(AetherItems.GRAVITITE_CHESTPLATE.get(), "armor/");
        this.leggingsItem(AetherItems.GRAVITITE_LEGGINGS.get(), "armor/");
        this.bootsItem(AetherItems.GRAVITITE_BOOTS.get(), "armor/");

        this.helmetItem(AetherItems.NEPTUNE_HELMET.get(), "armor/");
        this.chestplateItem(AetherItems.NEPTUNE_CHESTPLATE.get(), "armor/");
        this.leggingsItem(AetherItems.NEPTUNE_LEGGINGS.get(), "armor/");
        this.bootsItem(AetherItems.NEPTUNE_BOOTS.get(), "armor/");

        this.helmetItem(AetherItems.PHOENIX_HELMET.get(), "armor/");
        this.chestplateItem(AetherItems.PHOENIX_CHESTPLATE.get(), "armor/");
        this.leggingsItem(AetherItems.PHOENIX_LEGGINGS.get(), "armor/");
        this.bootsItem(AetherItems.PHOENIX_BOOTS.get(), "armor/");

        this.helmetItem(AetherItems.OBSIDIAN_HELMET.get(), "armor/");
        this.chestplateItem(AetherItems.OBSIDIAN_CHESTPLATE.get(), "armor/");
        this.leggingsItem(AetherItems.OBSIDIAN_LEGGINGS.get(), "armor/");
        this.bootsItem(AetherItems.OBSIDIAN_BOOTS.get(), "armor/");

        this.item(AetherItems.VALKYRIE_HELMET.get(), "armor/");
        this.item(AetherItems.VALKYRIE_CHESTPLATE.get(), "armor/");
        this.item(AetherItems.VALKYRIE_LEGGINGS.get(), "armor/");
        this.item(AetherItems.VALKYRIE_BOOTS.get(), "armor/");

        this.item(AetherItems.SENTRY_BOOTS.get(), "armor/");

        this.item(AetherItems.BLUE_BERRY.get(), "food/");
        this.item(AetherItems.ENCHANTED_BERRY.get(), "food/");
        this.item(AetherItems.WHITE_APPLE.get(), "food/");
        this.item(AetherItems.BLUE_GUMMY_SWET.get(), "food/");
        this.item(AetherItems.GOLDEN_GUMMY_SWET.get(), "food/");
        this.item(AetherItems.HEALING_STONE.get(), "food/");
        this.item(AetherItems.CANDY_CANE.get(), "food/");
        this.item(AetherItems.GINGERBREAD_MAN.get(), "food/");

        this.item(AetherItems.IRON_RING.get(), "accessories/");
        this.item(AetherItems.GOLDEN_RING.get(), "accessories/");
        this.item(AetherItems.ZANITE_RING.get(), "accessories/");
        this.item(AetherItems.ICE_RING.get(), "accessories/");

        this.item(AetherItems.IRON_PENDANT.get(), "accessories/");
        this.item(AetherItems.GOLDEN_PENDANT.get(), "accessories/");
        this.item(AetherItems.ZANITE_PENDANT.get(), "accessories/");
        this.item(AetherItems.ICE_PENDANT.get(), "accessories/");

        this.dyedGlovesItem(AetherItems.LEATHER_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.CHAINMAIL_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.IRON_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.GOLDEN_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.DIAMOND_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.NETHERITE_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.ZANITE_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.GRAVITITE_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.NEPTUNE_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.PHOENIX_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.OBSIDIAN_GLOVES.get(), "accessories/");
        this.glovesItem(AetherItems.VALKYRIE_GLOVES.get(), "accessories/");

        this.item(AetherItems.RED_CAPE.get(), "accessories/");
        this.item(AetherItems.BLUE_CAPE.get(), "accessories/");
        this.item(AetherItems.YELLOW_CAPE.get(), "accessories/");
        this.item(AetherItems.WHITE_CAPE.get(), "accessories/");
        this.item(AetherItems.SWET_CAPE.get(), "accessories/");
        this.item(AetherItems.INVISIBILITY_CLOAK.get(), "accessories/");
        this.item(AetherItems.AGILITY_CAPE.get(), "accessories/");
        this.item(AetherItems.VALKYRIE_CAPE.get(), "accessories/");

        this.item(AetherItems.GOLDEN_FEATHER.get(), "accessories/");
        this.item(AetherItems.REGENERATION_STONE.get(), "accessories/");
        this.item(AetherItems.IRON_BUBBLE.get(), "accessories/");
        this.item(AetherItems.SHIELD_OF_REPULSION.get(), "accessories/");

        this.handheldItem(AetherItems.SKYROOT_STICK.get(), "materials/");
        this.item(AetherItems.GOLDEN_AMBER.get(), "materials/");
        this.item(AetherItems.SWET_BALL.get(), "materials/");
        this.item(AetherItems.AECHOR_PETAL.get(), "materials/");
        this.item(AetherItems.AMBROSIUM_SHARD.get(), "materials/");
        this.item(AetherItems.ZANITE_GEMSTONE.get(), "materials/");

        this.rotatedItem(AetherItems.VICTORY_MEDAL.get(), "miscellaneous/");

        this.rotatedItem(AetherItems.BRONZE_DUNGEON_KEY.get(), "miscellaneous/");
        this.rotatedItem(AetherItems.SILVER_DUNGEON_KEY.get(), "miscellaneous/");
        this.rotatedItem(AetherItems.GOLD_DUNGEON_KEY.get(), "miscellaneous/");

        this.item(AetherItems.SKYROOT_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_WATER_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_POISON_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_REMEDY_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_MILK_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_POWDER_SNOW_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_COD_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_SALMON_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_PUFFERFISH_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_TROPICAL_FISH_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_AXOLOTL_BUCKET.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_TADPOLE_BUCKET.get(), "miscellaneous/");

        this.item(AetherItems.SKYROOT_BOAT.get(), "miscellaneous/");
        this.item(AetherItems.SKYROOT_CHEST_BOAT.get(), "miscellaneous/");

        this.item(AetherItems.COLD_PARACHUTE.get(), "miscellaneous/");
        this.item(AetherItems.GOLDEN_PARACHUTE.get(), "miscellaneous/");

        this.handheldItem(AetherItems.NATURE_STAFF.get(), "miscellaneous/");
        this.handheldItem(AetherItems.CLOUD_STAFF.get(), "miscellaneous/");

        this.moaEggItem(AetherItems.BLUE_MOA_EGG.get(), "miscellaneous/");
        this.moaEggItem(AetherItems.WHITE_MOA_EGG.get(), "miscellaneous/");
        this.moaEggItem(AetherItems.BLACK_MOA_EGG.get(), "miscellaneous/");

        this.item(AetherItems.LIFE_SHARD.get(), "miscellaneous/");

        this.item(AetherItems.MUSIC_DISC_AETHER_TUNE.get(), "miscellaneous/");
        this.item(AetherItems.MUSIC_DISC_ASCENDING_DAWN.get(), "miscellaneous/");
        this.item(AetherItems.MUSIC_DISC_CHINCHILLA.get(), "miscellaneous/");
        this.item(AetherItems.MUSIC_DISC_HIGH.get(), "miscellaneous/");
        this.item(AetherItems.MUSIC_DISC_KLEPTO.get(), "miscellaneous/");
        this.item(AetherItems.MUSIC_DISC_SLIDERS_WRATH.get(), "miscellaneous/");

        this.item(AetherItems.BOOK_OF_LORE.get(), "miscellaneous/");

        this.portalItem(AetherItems.AETHER_PORTAL_FRAME.get(), "miscellaneous/");

        this.eggItem(AetherItems.PHYG_SPAWN_EGG.get());
        this.eggItem(AetherItems.FLYING_COW_SPAWN_EGG.get());
        this.eggItem(AetherItems.SHEEPUFF_SPAWN_EGG.get());
        this.eggItem(AetherItems.MOA_SPAWN_EGG.get());
        this.eggItem(AetherItems.AERWHALE_SPAWN_EGG.get());
        this.eggItem(AetherItems.AERBUNNY_SPAWN_EGG.get());
        this.eggItem(AetherItems.WHIRLWIND_SPAWN_EGG.get());
        this.eggItem(AetherItems.BLUE_SWET_SPAWN_EGG.get());
        this.eggItem(AetherItems.GOLDEN_SWET_SPAWN_EGG.get());
        this.eggItem(AetherItems.EVIL_WHIRLWIND_SPAWN_EGG.get());
        this.eggItem(AetherItems.AECHOR_PLANT_SPAWN_EGG.get());
        this.eggItem(AetherItems.COCKATRICE_SPAWN_EGG.get());
        this.eggItem(AetherItems.ZEPHYR_SPAWN_EGG.get());
        this.eggItem(AetherItems.SENTRY_SPAWN_EGG.get());
        this.eggItem(AetherItems.MIMIC_SPAWN_EGG.get());
        this.eggItem(AetherItems.VALKYRIE_SPAWN_EGG.get());
        this.eggItem(AetherItems.FIRE_MINION_SPAWN_EGG.get());

        this.eggItem(AetherItems.SLIDER_SPAWN_EGG.get());
        this.eggItem(AetherItems.VALKYRIE_QUEEN_SPAWN_EGG.get());
        this.eggItem(AetherItems.SUN_SPIRIT_SPAWN_EGG.get());

        this.itemBlock(AetherBlocks.AETHER_GRASS_BLOCK.get());
        this.itemBlock(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
        this.itemBlock(AetherBlocks.AETHER_DIRT.get());
        this.itemBlock(AetherBlocks.QUICKSOIL.get());
        this.itemBlock(AetherBlocks.HOLYSTONE.get());
        this.itemBlock(AetherBlocks.MOSSY_HOLYSTONE.get());
        this.itemBlock(AetherBlocks.AETHER_FARMLAND.get());
        this.itemBlock(AetherBlocks.AETHER_DIRT_PATH.get());

        this.aercloudItem(AetherBlocks.COLD_AERCLOUD.get());
        this.aercloudItem(AetherBlocks.BLUE_AERCLOUD.get());
        this.aercloudItem(AetherBlocks.GOLDEN_AERCLOUD.get());

        this.itemBlock(AetherBlocks.ICESTONE.get());
        this.itemBlock(AetherBlocks.AMBROSIUM_ORE.get());
        this.itemBlock(AetherBlocks.ZANITE_ORE.get());
        this.itemBlock(AetherBlocks.GRAVITITE_ORE.get());

        this.itemBlock(AetherBlocks.SKYROOT_LEAVES.get());
        this.itemBlock(AetherBlocks.GOLDEN_OAK_LEAVES.get());
        this.itemBlock(AetherBlocks.CRYSTAL_LEAVES.get());
        this.itemBlock(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get());
        this.itemBlock(AetherBlocks.HOLIDAY_LEAVES.get());
        this.itemBlock(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get());

        this.itemBlock(AetherBlocks.SKYROOT_LOG.get());
        this.itemBlock(AetherBlocks.GOLDEN_OAK_LOG.get());
        this.itemBlock(AetherBlocks.STRIPPED_SKYROOT_LOG.get());
        this.itemBlock(AetherBlocks.SKYROOT_WOOD.get());
        this.itemBlock(AetherBlocks.GOLDEN_OAK_WOOD.get());
        this.itemBlock(AetherBlocks.STRIPPED_SKYROOT_WOOD.get());

        this.itemBlock(AetherBlocks.SKYROOT_PLANKS.get());
        this.itemBlock(AetherBlocks.HOLYSTONE_BRICKS.get());
        this.itemBlock(AetherBlocks.QUICKSOIL_GLASS.get());
        this.pane(AetherBlocks.QUICKSOIL_GLASS_PANE.get(), AetherBlocks.QUICKSOIL_GLASS.get(), "construction/");
        this.itemBlock(AetherBlocks.AEROGEL.get());

        this.itemBlock(AetherBlocks.AMBROSIUM_BLOCK.get());
        this.itemBlock(AetherBlocks.ZANITE_BLOCK.get());
        this.itemBlock(AetherBlocks.ENCHANTED_GRAVITITE.get());

        this.itemBlock(AetherBlocks.ALTAR.get());
        this.itemBlock(AetherBlocks.FREEZER.get());
        this.itemBlock(AetherBlocks.INCUBATOR.get());

        this.itemBlockFlat(AetherBlocks.AMBROSIUM_TORCH.get(), "utility/");
        this.item(AetherBlocks.SKYROOT_SIGN.get().asItem(), "miscellaneous/");
        this.item(AetherBlocks.SKYROOT_HANGING_SIGN.get().asItem(), "miscellaneous/");

        this.itemBlock(AetherBlocks.BERRY_BUSH.get());
        this.itemBlockFlat(AetherBlocks.BERRY_BUSH_STEM.get(), "natural/");

        this.itemBlockFlat(AetherBlocks.WHITE_FLOWER.get(), "natural/");
        this.itemBlockFlat(AetherBlocks.PURPLE_FLOWER.get(), "natural/");

        this.itemBlockFlat(AetherBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.itemBlockFlat(AetherBlocks.GOLDEN_OAK_SAPLING.get(), "natural/");

        this.itemBlock(AetherBlocks.CARVED_STONE.get());
        this.itemBlock(AetherBlocks.SENTRY_STONE.get());
        this.itemBlock(AetherBlocks.ANGELIC_STONE.get());
        this.itemBlock(AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.itemBlock(AetherBlocks.HELLFIRE_STONE.get());
        this.itemBlock(AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.itemLockedDungeonBlock(AetherBlocks.LOCKED_CARVED_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.itemLockedDungeonBlock(AetherBlocks.LOCKED_SENTRY_STONE.get(), AetherBlocks.SENTRY_STONE.get());
        this.itemLockedDungeonBlock(AetherBlocks.LOCKED_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.itemLockedDungeonBlock(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.itemLockedDungeonBlock(AetherBlocks.LOCKED_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.itemLockedDungeonBlock(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.itemTrappedDungeonBlock(AetherBlocks.TRAPPED_CARVED_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.itemTrappedDungeonBlock(AetherBlocks.TRAPPED_SENTRY_STONE.get(), AetherBlocks.SENTRY_STONE.get());
        this.itemTrappedDungeonBlock(AetherBlocks.TRAPPED_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.itemTrappedDungeonBlock(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.itemTrappedDungeonBlock(AetherBlocks.TRAPPED_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.itemTrappedDungeonBlock(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.itemBossDoorwayDungeonBlock(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.itemBossDoorwayDungeonBlock(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE.get(), AetherBlocks.SENTRY_STONE.get());
        this.itemBossDoorwayDungeonBlock(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.itemBossDoorwayDungeonBlock(AetherBlocks.BOSS_DOORWAY_LIGHT_ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.itemBossDoorwayDungeonBlock(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.itemBossDoorwayDungeonBlock(AetherBlocks.BOSS_DOORWAY_LIGHT_HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.itemTreasureDoorwayDungeonBlock(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.itemTreasureDoorwayDungeonBlock(AetherBlocks.TREASURE_DOORWAY_SENTRY_STONE.get(), AetherBlocks.SENTRY_STONE.get());
        this.itemTreasureDoorwayDungeonBlock(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.itemTreasureDoorwayDungeonBlock(AetherBlocks.TREASURE_DOORWAY_LIGHT_ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.itemTreasureDoorwayDungeonBlock(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.itemTreasureDoorwayDungeonBlock(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.lookalikeBlock(AetherBlocks.CHEST_MIMIC.get(), this.mcLoc("item/chest"));
        this.lookalikeBlock(AetherBlocks.TREASURE_CHEST.get(), this.mcLoc("item/chest"));

        this.itemBlock(AetherBlocks.PILLAR.get());
        this.itemBlock(AetherBlocks.PILLAR_TOP.get());

        this.itemBlock(AetherBlocks.PRESENT.get());

        this.itemFence(AetherBlocks.SKYROOT_FENCE.get(), AetherBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherBlocks.SKYROOT_FENCE_GATE.get());
        this.item(AetherBlocks.SKYROOT_DOOR.get().asItem(), "miscellaneous/");
        this.itemBlock(AetherBlocks.SKYROOT_TRAPDOOR.get(), "_bottom");
        this.itemButton(AetherBlocks.SKYROOT_BUTTON.get(), AetherBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.itemBlock(AetherBlocks.SKYROOT_PRESSURE_PLATE.get());

        this.itemButton(AetherBlocks.HOLYSTONE_BUTTON.get(), AetherBlocks.HOLYSTONE.get(), "natural/");
        this.itemBlock(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get());

        this.itemWallBlock(AetherBlocks.CARVED_WALL.get(), AetherBlocks.CARVED_STONE.get(), "dungeon/");
        this.itemWallBlock(AetherBlocks.ANGELIC_WALL.get(), AetherBlocks.ANGELIC_STONE.get(), "dungeon/");
        this.itemWallBlock(AetherBlocks.HELLFIRE_WALL.get(), AetherBlocks.HELLFIRE_STONE.get(), "dungeon/");
        this.itemWallBlock(AetherBlocks.HOLYSTONE_WALL.get(), AetherBlocks.HOLYSTONE.get(), "natural/");
        this.itemWallBlock(AetherBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.itemWallBlock(AetherBlocks.ICESTONE_WALL.get(), AetherBlocks.ICESTONE.get(), "natural/");
        this.itemWallBlock(AetherBlocks.HOLYSTONE_BRICK_WALL.get(), AetherBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.translucentItemWallBlock(AetherBlocks.AEROGEL_WALL.get(), AetherBlocks.AEROGEL.get(), "construction/");

        this.itemBlock(AetherBlocks.SKYROOT_STAIRS.get());
        this.itemBlock(AetherBlocks.CARVED_STAIRS.get());
        this.itemBlock(AetherBlocks.ANGELIC_STAIRS.get());
        this.itemBlock(AetherBlocks.HELLFIRE_STAIRS.get());
        this.itemBlock(AetherBlocks.HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get());
        this.itemBlock(AetherBlocks.ICESTONE_STAIRS.get());
        this.itemBlock(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get());
        this.itemBlock(AetherBlocks.AEROGEL_STAIRS.get());

        this.itemBlock(AetherBlocks.SKYROOT_SLAB.get());
        this.itemBlock(AetherBlocks.CARVED_SLAB.get());
        this.itemBlock(AetherBlocks.ANGELIC_SLAB.get());
        this.itemBlock(AetherBlocks.HELLFIRE_SLAB.get());
        this.itemBlock(AetherBlocks.HOLYSTONE_SLAB.get());
        this.itemBlock(AetherBlocks.MOSSY_HOLYSTONE_SLAB.get());
        this.itemBlock(AetherBlocks.ICESTONE_SLAB.get());
        this.itemBlock(AetherBlocks.HOLYSTONE_BRICK_SLAB.get());
        this.itemBlock(AetherBlocks.AEROGEL_SLAB.get());

        this.itemBlock(AetherBlocks.SUN_ALTAR.get());

        this.itemBlock(AetherBlocks.SKYROOT_BOOKSHELF.get());

        this.lookalikeBlock(AetherBlocks.SKYROOT_BED.get(), this.mcLoc("item/template_bed"));
    }
}
