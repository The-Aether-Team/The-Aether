package com.gildedgames.aether.core.data;

import com.gildedgames.aether.core.data.provider.AetherItemModelProvider;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class AetherItemModelData extends AetherItemModelProvider
{
    public AetherItemModelData(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, fileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Aether Item Models";
    }

    @Override
    protected void registerModels() {
        handheldItem(AetherItems.SKYROOT_PICKAXE, "tools/");
        handheldItem(AetherItems.SKYROOT_AXE, "tools/");
        handheldItem(AetherItems.SKYROOT_SHOVEL, "tools/");
        handheldItem(AetherItems.SKYROOT_HOE, "tools/");

        handheldItem(AetherItems.HOLYSTONE_PICKAXE, "tools/");
        handheldItem(AetherItems.HOLYSTONE_AXE, "tools/");
        handheldItem(AetherItems.HOLYSTONE_SHOVEL, "tools/");
        handheldItem(AetherItems.HOLYSTONE_HOE, "tools/");

        handheldItem(AetherItems.ZANITE_PICKAXE, "tools/");
        handheldItem(AetherItems.ZANITE_AXE, "tools/");
        handheldItem(AetherItems.ZANITE_SHOVEL, "tools/");
        handheldItem(AetherItems.ZANITE_HOE, "tools/");

        handheldItem(AetherItems.GRAVITITE_PICKAXE, "tools/");
        handheldItem(AetherItems.GRAVITITE_AXE, "tools/");
        handheldItem(AetherItems.GRAVITITE_SHOVEL, "tools/");
        handheldItem(AetherItems.GRAVITITE_HOE, "tools/");

        handheldItem(AetherItems.VALKYRIE_PICKAXE, "tools/");
        handheldItem(AetherItems.VALKYRIE_AXE, "tools/");
        handheldItem(AetherItems.VALKYRIE_SHOVEL, "tools/");
        handheldItem(AetherItems.VALKYRIE_HOE, "tools/");

        handheldItem(AetherItems.SKYROOT_SWORD, "weapons/");
        handheldItem(AetherItems.HOLYSTONE_SWORD, "weapons/");
        handheldItem(AetherItems.ZANITE_SWORD, "weapons/");
        handheldItem(AetherItems.GRAVITITE_SWORD, "weapons/");

        lanceItem(AetherItems.VALKYRIE_LANCE, "weapons/");

        handheldItem(AetherItems.FLAMING_SWORD, "weapons/");
        handheldItem(AetherItems.LIGHTNING_SWORD, "weapons/");
        handheldItem(AetherItems.HOLY_SWORD, "weapons/");
        handheldItem(AetherItems.VAMPIRE_BLADE, "weapons/");
        handheldItem(AetherItems.PIG_SLAYER, "weapons/");
        nameableWeapon(AetherItems.CANDY_CANE_SWORD, "weapons/", "green_candy_cane_sword");

        nameableWeapon(AetherItems.HAMMER_OF_NOTCH, "weapons/", "hammer_of_jeb");

        handheldItem(AetherItems.LIGHTNING_KNIFE, "weapons/");

        item(AetherItems.GOLDEN_DART, "weapons/");
        item(AetherItems.POISON_DART, "weapons/");
        item(AetherItems.ENCHANTED_DART, "weapons/");

        dartShooterItem(AetherItems.GOLDEN_DART_SHOOTER, "weapons/");
        dartShooterItem(AetherItems.POISON_DART_SHOOTER, "weapons/");
        dartShooterItem(AetherItems.ENCHANTED_DART_SHOOTER, "weapons/");

        bowItem(AetherItems.PHOENIX_BOW, "weapons/");

        item(AetherItems.ZANITE_HELMET, "armor/");
        item(AetherItems.ZANITE_CHESTPLATE, "armor/");
        item(AetherItems.ZANITE_LEGGINGS, "armor/");
        item(AetherItems.ZANITE_BOOTS, "armor/");

        item(AetherItems.GRAVITITE_HELMET, "armor/");
        item(AetherItems.GRAVITITE_CHESTPLATE, "armor/");
        item(AetherItems.GRAVITITE_LEGGINGS, "armor/");
        item(AetherItems.GRAVITITE_BOOTS, "armor/");

        item(AetherItems.NEPTUNE_HELMET, "armor/");
        item(AetherItems.NEPTUNE_CHESTPLATE, "armor/");
        item(AetherItems.NEPTUNE_LEGGINGS, "armor/");
        item(AetherItems.NEPTUNE_BOOTS, "armor/");

        item(AetherItems.PHOENIX_HELMET, "armor/");
        item(AetherItems.PHOENIX_CHESTPLATE, "armor/");
        item(AetherItems.PHOENIX_LEGGINGS, "armor/");
        item(AetherItems.PHOENIX_BOOTS, "armor/");

        item(AetherItems.OBSIDIAN_HELMET, "armor/");
        item(AetherItems.OBSIDIAN_CHESTPLATE, "armor/");
        item(AetherItems.OBSIDIAN_LEGGINGS, "armor/");
        item(AetherItems.OBSIDIAN_BOOTS, "armor/");

        item(AetherItems.VALKYRIE_HELMET, "armor/");
        item(AetherItems.VALKYRIE_CHESTPLATE, "armor/");
        item(AetherItems.VALKYRIE_LEGGINGS, "armor/");
        item(AetherItems.VALKYRIE_BOOTS, "armor/");

        item(AetherItems.SENTRY_BOOTS, "armor/");

        item(AetherItems.BLUE_BERRY, "food/");
        item(AetherItems.ENCHANTED_BERRY, "food/");
        item(AetherItems.WHITE_APPLE, "food/");
        item(AetherItems.BLUE_GUMMY_SWET, "food/");
        item(AetherItems.GOLDEN_GUMMY_SWET, "food/");
        item(AetherItems.HEALING_STONE, "food/");
        item(AetherItems.CANDY_CANE, "food/");
        item(AetherItems.GINGERBREAD_MAN, "food/");

        item(AetherItems.IRON_RING, "accessories/");
        item(AetherItems.GOLDEN_RING, "accessories/");
        item(AetherItems.ZANITE_RING, "accessories/");
        item(AetherItems.ICE_RING, "accessories/");

        item(AetherItems.IRON_PENDANT, "accessories/");
        item(AetherItems.GOLDEN_PENDANT, "accessories/");
        item(AetherItems.ZANITE_PENDANT, "accessories/");
        item(AetherItems.ICE_PENDANT, "accessories/");

        dyedItem(AetherItems.LEATHER_GLOVES, "accessories/");
        item(AetherItems.CHAINMAIL_GLOVES, "accessories/");
        item(AetherItems.IRON_GLOVES, "accessories/");
        item(AetherItems.GOLDEN_GLOVES, "accessories/");
        item(AetherItems.DIAMOND_GLOVES, "accessories/");
        item(AetherItems.NETHERITE_GLOVES, "accessories/");
        item(AetherItems.ZANITE_GLOVES, "accessories/");
        item(AetherItems.GRAVITITE_GLOVES, "accessories/");
        item(AetherItems.NEPTUNE_GLOVES, "accessories/");
        item(AetherItems.PHOENIX_GLOVES, "accessories/");
        item(AetherItems.OBSIDIAN_GLOVES, "accessories/");
        item(AetherItems.VALKYRIE_GLOVES, "accessories/");

        item(AetherItems.RED_CAPE, "accessories/");
        item(AetherItems.BLUE_CAPE, "accessories/");
        item(AetherItems.YELLOW_CAPE, "accessories/");
        item(AetherItems.WHITE_CAPE, "accessories/");
        item(AetherItems.SWET_CAPE, "accessories/");
        item(AetherItems.INVISIBILITY_CLOAK, "accessories/");
        item(AetherItems.AGILITY_CAPE, "accessories/");
        item(AetherItems.VALKYRIE_CAPE, "accessories/");

        item(AetherItems.GOLDEN_FEATHER, "accessories/");
        item(AetherItems.REGENERATION_STONE, "accessories/");
        item(AetherItems.IRON_BUBBLE, "accessories/");
        item(AetherItems.REPULSION_SHIELD, "accessories/");

        handheldItem(AetherItems.SKYROOT_STICK, "materials/");
        item(AetherItems.GOLDEN_AMBER, "materials/");
        item(AetherItems.SWET_BALL, "materials/");
        item(AetherItems.AECHOR_PETAL, "materials/");
        item(AetherItems.AMBROSIUM_SHARD, "materials/");
        item(AetherItems.ZANITE_GEMSTONE, "materials/");

        item(AetherItems.VICTORY_MEDAL, "miscellaneous/");

        item(AetherItems.BRONZE_DUNGEON_KEY, "miscellaneous/");
        item(AetherItems.SILVER_DUNGEON_KEY, "miscellaneous/");
        item(AetherItems.GOLD_DUNGEON_KEY, "miscellaneous/");

        item(AetherItems.SKYROOT_BUCKET, "miscellaneous/");
        item(AetherItems.SKYROOT_WATER_BUCKET, "miscellaneous/");
        item(AetherItems.SKYROOT_POISON_BUCKET, "miscellaneous/");
        item(AetherItems.SKYROOT_REMEDY_BUCKET, "miscellaneous/");
        item(AetherItems.SKYROOT_MILK_BUCKET, "miscellaneous/");

        item(AetherItems.COLD_PARACHUTE, "miscellaneous/");
        item(AetherItems.GOLDEN_PARACHUTE, "miscellaneous/");

        handheldItem(AetherItems.NATURE_STAFF, "miscellaneous/");
        handheldItem(AetherItems.CLOUD_STAFF, "miscellaneous/");

        moaEggItem(AetherItems.BLUE_MOA_EGG, "miscellaneous/");
        moaEggItem(AetherItems.WHITE_MOA_EGG, "miscellaneous/");
        moaEggItem(AetherItems.BLACK_MOA_EGG, "miscellaneous/");
        moaEggItem(AetherItems.ORANGE_MOA_EGG, "miscellaneous/");

        item(AetherItems.LIFE_SHARD, "miscellaneous/");

        item(AetherItems.MUSIC_DISC_AETHER_TUNE, "miscellaneous/");
        item(AetherItems.MUSIC_DISC_ASCENDING_DAWN, "miscellaneous/");
        item(AetherItems.MUSIC_DISC_WELCOMING_SKIES, "miscellaneous/");
        item(AetherItems.MUSIC_DISC_LEGACY, "miscellaneous/");

        item(AetherItems.BOOK_OF_LORE, "miscellaneous/");

        item(AetherItems.AETHER_PORTAL_FRAME, "miscellaneous/");

        eggItem(AetherItems.PHYG_SPAWN_EGG);
        eggItem(AetherItems.FLYING_COW_SPAWN_EGG);
        eggItem(AetherItems.SHEEPUFF_SPAWN_EGG);
        eggItem(AetherItems.MOA_SPAWN_EGG);
        eggItem(AetherItems.AERWHALE_SPAWN_EGG);
        eggItem(AetherItems.WHIRLWIND_SPAWN_EGG);
        eggItem(AetherItems.EVIL_WHIRLWIND_SPAWN_EGG);
        eggItem(AetherItems.AECHOR_PLANT_SPAWN_EGG);
        eggItem(AetherItems.COCKATRICE_SPAWN_EGG);
        eggItem(AetherItems.ZEPHYR_SPAWN_EGG);
        eggItem(AetherItems.SENTRY_SPAWN_EGG);
        eggItem(AetherItems.MIMIC_SPAWN_EGG);
        eggItem(AetherItems.AERBUNNY_SPAWN_EGG);
        eggItem(AetherItems.BLUE_SWET_SPAWN_EGG);
        eggItem(AetherItems.GOLDEN_SWET_SPAWN_EGG);

        itemBlock(AetherBlocks.AETHER_GRASS_BLOCK);
        itemBlock(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK);
        itemBlock(AetherBlocks.AETHER_DIRT);
        itemBlock(AetherBlocks.QUICKSOIL);
        itemBlock(AetherBlocks.HOLYSTONE);
        itemBlock(AetherBlocks.MOSSY_HOLYSTONE);
        itemBlock(AetherBlocks.AETHER_FARMLAND);

        itemBlock(AetherBlocks.COLD_AERCLOUD);
        itemBlock(AetherBlocks.BLUE_AERCLOUD);
        itemBlock(AetherBlocks.GOLDEN_AERCLOUD);
        itemBlock(AetherBlocks.PINK_AERCLOUD);

        itemBlock(AetherBlocks.ICESTONE);
        itemBlock(AetherBlocks.AMBROSIUM_ORE);
        itemBlock(AetherBlocks.ZANITE_ORE);
        itemBlock(AetherBlocks.GRAVITITE_ORE);

        itemBlock(AetherBlocks.SKYROOT_LEAVES);
        itemBlock(AetherBlocks.GOLDEN_OAK_LEAVES);
        itemBlock(AetherBlocks.CRYSTAL_LEAVES);
        itemBlock(AetherBlocks.CRYSTAL_FRUIT_LEAVES);
        itemBlock(AetherBlocks.HOLIDAY_LEAVES);
        itemBlock(AetherBlocks.DECORATED_HOLIDAY_LEAVES);

        itemBlock(AetherBlocks.SKYROOT_LOG);
        itemBlock(AetherBlocks.GOLDEN_OAK_LOG);
        itemBlock(AetherBlocks.STRIPPED_SKYROOT_LOG);
        itemBlock(AetherBlocks.SKYROOT_WOOD);
        itemBlock(AetherBlocks.GOLDEN_OAK_WOOD);
        itemBlock(AetherBlocks.STRIPPED_SKYROOT_WOOD);

        itemBlock(AetherBlocks.SKYROOT_PLANKS);
        itemBlock(AetherBlocks.HOLYSTONE_BRICKS);
        itemBlock(AetherBlocks.QUICKSOIL_GLASS);
        itemBlock(AetherBlocks.AEROGEL);

        itemBlock(AetherBlocks.ZANITE_BLOCK);
        itemBlock(AetherBlocks.ENCHANTED_GRAVITITE);

        itemBlock(AetherBlocks.ALTAR);
        itemBlock(AetherBlocks.FREEZER);
        itemBlock(AetherBlocks.INCUBATOR);

        itemTorch(AetherBlocks.AMBROSIUM_TORCH, "utility/");
        item(() -> AetherBlocks.SKYROOT_SIGN.get().asItem(), "miscellaneous/");

        itemBlock(AetherBlocks.BERRY_BUSH);
        itemBlockFlat(AetherBlocks.BERRY_BUSH_STEM, "natural/");

        itemBlockFlat(AetherBlocks.WHITE_FLOWER, "natural/");
        itemBlockFlat(AetherBlocks.PURPLE_FLOWER, "natural/");

        itemBlockFlat(AetherBlocks.SKYROOT_SAPLING, "natural/");
        itemBlockFlat(AetherBlocks.GOLDEN_OAK_SAPLING, "natural/");

        itemBlock(AetherBlocks.CARVED_STONE);
        itemBlock(AetherBlocks.SENTRY_STONE);
        itemBlock(AetherBlocks.ANGELIC_STONE);
        itemBlock(AetherBlocks.LIGHT_ANGELIC_STONE);
        itemBlock(AetherBlocks.HELLFIRE_STONE);
        itemBlock(AetherBlocks.LIGHT_HELLFIRE_STONE);

        itemLockedDungeonBlock(AetherBlocks.LOCKED_CARVED_STONE, AetherBlocks.CARVED_STONE);
        itemLockedDungeonBlock(AetherBlocks.LOCKED_SENTRY_STONE, AetherBlocks.SENTRY_STONE);
        itemLockedDungeonBlock(AetherBlocks.LOCKED_ANGELIC_STONE, AetherBlocks.ANGELIC_STONE);
        itemLockedDungeonBlock(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE, AetherBlocks.LIGHT_ANGELIC_STONE);
        itemLockedDungeonBlock(AetherBlocks.LOCKED_HELLFIRE_STONE, AetherBlocks.HELLFIRE_STONE);
        itemLockedDungeonBlock(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE, AetherBlocks.LIGHT_HELLFIRE_STONE);

        itemTrappedDungeonBlock(AetherBlocks.TRAPPED_CARVED_STONE, AetherBlocks.CARVED_STONE);
        itemTrappedDungeonBlock(AetherBlocks.TRAPPED_SENTRY_STONE, AetherBlocks.SENTRY_STONE);
        itemTrappedDungeonBlock(AetherBlocks.TRAPPED_ANGELIC_STONE, AetherBlocks.ANGELIC_STONE);
        itemTrappedDungeonBlock(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE, AetherBlocks.LIGHT_ANGELIC_STONE);
        itemTrappedDungeonBlock(AetherBlocks.TRAPPED_HELLFIRE_STONE, AetherBlocks.HELLFIRE_STONE);
        itemTrappedDungeonBlock(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE, AetherBlocks.LIGHT_HELLFIRE_STONE);

        itemBlock(AetherBlocks.PILLAR);
        itemBlock(AetherBlocks.PILLAR_TOP);

        itemBlock(AetherBlocks.PRESENT);

        itemFence(AetherBlocks.SKYROOT_FENCE, AetherBlocks.SKYROOT_PLANKS, "construction/");
        itemBlock(AetherBlocks.SKYROOT_FENCE_GATE);
        item(() -> AetherBlocks.SKYROOT_DOOR.get().asItem(), "miscellaneous/");
        itemBlock(AetherBlocks.SKYROOT_TRAPDOOR, "_bottom");

        itemWallBlock(AetherBlocks.CARVED_WALL, AetherBlocks.CARVED_STONE, "dungeon/");
        itemWallBlock(AetherBlocks.ANGELIC_WALL, AetherBlocks.ANGELIC_STONE, "dungeon/");
        itemWallBlock(AetherBlocks.HELLFIRE_WALL, AetherBlocks.HELLFIRE_STONE, "dungeon/");
        itemWallBlock(AetherBlocks.HOLYSTONE_WALL, AetherBlocks.HOLYSTONE, "natural/");
        itemWallBlock(AetherBlocks.MOSSY_HOLYSTONE_WALL, AetherBlocks.MOSSY_HOLYSTONE, "natural/");
        itemWallBlock(AetherBlocks.ICESTONE_WALL, AetherBlocks.ICESTONE, "natural/");
        itemWallBlock(AetherBlocks.HOLYSTONE_BRICK_WALL, AetherBlocks.HOLYSTONE_BRICKS, "construction/");
        itemWallBlock(AetherBlocks.AEROGEL_WALL, AetherBlocks.AEROGEL, "construction/");

        itemBlock(AetherBlocks.SKYROOT_STAIRS);
        itemBlock(AetherBlocks.CARVED_STAIRS);
        itemBlock(AetherBlocks.ANGELIC_STAIRS);
        itemBlock(AetherBlocks.HELLFIRE_STAIRS);
        itemBlock(AetherBlocks.HOLYSTONE_STAIRS);
        itemBlock(AetherBlocks.MOSSY_HOLYSTONE_STAIRS);
        itemBlock(AetherBlocks.ICESTONE_STAIRS);
        itemBlock(AetherBlocks.HOLYSTONE_BRICK_STAIRS);
        itemBlock(AetherBlocks.AEROGEL_STAIRS);

        itemBlock(AetherBlocks.SKYROOT_SLAB);
        itemBlock(AetherBlocks.CARVED_SLAB);
        itemBlock(AetherBlocks.ANGELIC_SLAB);
        itemBlock(AetherBlocks.HELLFIRE_SLAB);
        itemBlock(AetherBlocks.HOLYSTONE_SLAB);
        itemBlock(AetherBlocks.MOSSY_HOLYSTONE_SLAB);
        itemBlock(AetherBlocks.ICESTONE_SLAB);
        itemBlock(AetherBlocks.HOLYSTONE_BRICK_SLAB);
        itemBlock(AetherBlocks.AEROGEL_SLAB);

        itemBlock(AetherBlocks.SUN_ALTAR);

        itemBlock(AetherBlocks.SKYROOT_BOOKSHELF);
    }
}
