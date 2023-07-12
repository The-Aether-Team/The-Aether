package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.providers.AetherLanguageProvider;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.data.PackOutput;

public class AetherLanguageData extends AetherLanguageProvider {
    public AetherLanguageData(PackOutput output) {
        super(output, Aether.MODID);
    }

    @Override
    protected void addTranslations() {
        addBlock(AetherBlocks.AETHER_PORTAL, "Aether Portal");
        addBlock(AetherBlocks.AETHER_GRASS_BLOCK, "Aether Grass Block");
        addBlock(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, "Enchanted Aether Grass Block");
        addBlock(AetherBlocks.AETHER_DIRT, "Aether Dirt");
        addBlock(AetherBlocks.QUICKSOIL, "Quicksoil");
        addBlock(AetherBlocks.HOLYSTONE, "Holystone");
        addBlock(AetherBlocks.MOSSY_HOLYSTONE, "Mossy Holystone");
        addBlock(AetherBlocks.AETHER_FARMLAND, "Aether Farmland");
        addBlock(AetherBlocks.AETHER_DIRT_PATH, "Aether Dirt Path");

        addBlock(AetherBlocks.COLD_AERCLOUD, "Cold Aercloud");
        addBlock(AetherBlocks.BLUE_AERCLOUD, "Blue Aercloud");
        addBlock(AetherBlocks.GOLDEN_AERCLOUD, "Golden Aercloud");

        addBlock(AetherBlocks.ICESTONE, "Icestone");
        addBlock(AetherBlocks.AMBROSIUM_ORE, "Ambrosium Ore");
        addBlock(AetherBlocks.ZANITE_ORE, "Zanite Ore");
        addBlock(AetherBlocks.GRAVITITE_ORE, "Gravitite Ore");

        addBlock(AetherBlocks.SKYROOT_LEAVES, "Skyroot Leaves");
        addBlock(AetherBlocks.GOLDEN_OAK_LEAVES, "Golden Oak Leaves");
        addBlock(AetherBlocks.CRYSTAL_LEAVES, "Crystal Leaves");
        addBlock(AetherBlocks.CRYSTAL_FRUIT_LEAVES, "Crystal Fruit Leaves");
        addBlock(AetherBlocks.HOLIDAY_LEAVES, "Holiday Leaves");
        addBlock(AetherBlocks.DECORATED_HOLIDAY_LEAVES, "Decorated Holiday Leaves");

        addBlock(AetherBlocks.SKYROOT_LOG, "Skyroot Log");
        addBlock(AetherBlocks.GOLDEN_OAK_LOG, "Golden Oak Log");
        addBlock(AetherBlocks.STRIPPED_SKYROOT_LOG, "Stripped Skyroot Log");
        addBlock(AetherBlocks.SKYROOT_WOOD, "Skyroot Wood");
        addBlock(AetherBlocks.GOLDEN_OAK_WOOD, "Golden Oak Wood");
        addBlock(AetherBlocks.STRIPPED_SKYROOT_WOOD, "Stripped Skyroot Wood");

        addBlock(AetherBlocks.SKYROOT_PLANKS, "Skyroot Planks");
        addBlock(AetherBlocks.HOLYSTONE_BRICKS, "Holystone Bricks");
        addBlock(AetherBlocks.QUICKSOIL_GLASS, "Quicksoil Glass");
        addBlock(AetherBlocks.QUICKSOIL_GLASS_PANE, "Quicksoil Glass Pane");
        addBlock(AetherBlocks.AEROGEL, "Aerogel");

        addBlock(AetherBlocks.AMBROSIUM_BLOCK, "Block of Ambrosium");
        addBlock(AetherBlocks.ZANITE_BLOCK, "Block of Zanite");
        addBlock(AetherBlocks.ENCHANTED_GRAVITITE, "Enchanted Gravitite");

        addBlock(AetherBlocks.ALTAR, "Altar");
        addBlock(AetherBlocks.FREEZER, "Freezer");
        addBlock(AetherBlocks.INCUBATOR, "Incubator");

        addBlock(AetherBlocks.AMBROSIUM_TORCH, "Ambrosium Torch");

        addBlock(AetherBlocks.SKYROOT_SIGN, "Skyroot Sign");

        addBlock(AetherBlocks.BERRY_BUSH_STEM, "Bush Stem");
        addBlock(AetherBlocks.BERRY_BUSH, "Berry Bush");

        addBlock(AetherBlocks.PURPLE_FLOWER, "Purple Flower");
        addBlock(AetherBlocks.WHITE_FLOWER, "White Flower");

        addBlock(AetherBlocks.SKYROOT_SAPLING, "Skyroot Sapling");
        addBlock(AetherBlocks.GOLDEN_OAK_SAPLING, "Golden Oak Sapling");

        addBlock(AetherBlocks.CARVED_STONE, "Carved Stone");
        addBlock(AetherBlocks.SENTRY_STONE, "Sentry Stone");
        addBlock(AetherBlocks.ANGELIC_STONE, "Angelic Stone");
        addBlock(AetherBlocks.LIGHT_ANGELIC_STONE, "Light Angelic Stone");
        addBlock(AetherBlocks.HELLFIRE_STONE, "Hellfire Stone");
        addBlock(AetherBlocks.LIGHT_HELLFIRE_STONE, "Light Hellfire Stone");

        addBlock(AetherBlocks.LOCKED_CARVED_STONE, "Locked Carved Stone");
        addBlock(AetherBlocks.LOCKED_SENTRY_STONE, "Locked Sentry Stone");
        addBlock(AetherBlocks.LOCKED_ANGELIC_STONE, "Locked Angelic Stone");
        addBlock(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE, "Locked Light Angelic Stone");
        addBlock(AetherBlocks.LOCKED_HELLFIRE_STONE, "Locked Hellfire Stone");
        addBlock(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE, "Locked Light Hellfire Stone");

        addBlock(AetherBlocks.TRAPPED_CARVED_STONE, "Trapped Carved Stone");
        addBlock(AetherBlocks.TRAPPED_SENTRY_STONE, "Trapped Sentry Stone");
        addBlock(AetherBlocks.TRAPPED_ANGELIC_STONE, "Trapped Angelic Stone");
        addBlock(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE, "Trapped Light Angelic Stone");
        addBlock(AetherBlocks.TRAPPED_HELLFIRE_STONE, "Trapped Hellfire Stone");
        addBlock(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE, "Trapped Light Hellfire Stone");

        addBlock(AetherBlocks.BOSS_DOORWAY_CARVED_STONE, "Boss Doorway Carved Stone");
        addBlock(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE, "Boss Doorway Sentry Stone");
        addBlock(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE, "Boss Doorway Angelic Stone");
        addBlock(AetherBlocks.BOSS_DOORWAY_LIGHT_ANGELIC_STONE, "Boss Doorway Light Angelic Stone");
        addBlock(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE, "Boss Doorway Hellfire Stone");
        addBlock(AetherBlocks.BOSS_DOORWAY_LIGHT_HELLFIRE_STONE, "Boss Doorway Light Hellfire Stone");

        addBlock(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE, "Treasure Doorway Carved Stone");
        addBlock(AetherBlocks.TREASURE_DOORWAY_SENTRY_STONE, "Treasure Doorway Sentry Stone");
        addBlock(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE, "Treasure Doorway Angelic Stone");
        addBlock(AetherBlocks.TREASURE_DOORWAY_LIGHT_ANGELIC_STONE, "Treasure Doorway Light Angelic Stone");
        addBlock(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE, "Treasure Doorway Hellfire Stone");
        addBlock(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE, "Treasure Doorway Light Hellfire Stone");

        addBlock(AetherBlocks.CHEST_MIMIC, "Chest Mimic");
        addBlock(AetherBlocks.TREASURE_CHEST, "Treasure Chest");

        addBlock(AetherBlocks.PILLAR, "Pillar");
        addBlock(AetherBlocks.PILLAR_TOP, "Pillar Top");

        addBlock(AetherBlocks.PRESENT, "Present");

        addBlock(AetherBlocks.SKYROOT_FENCE, "Skyroot Fence");
        addBlock(AetherBlocks.SKYROOT_FENCE_GATE, "Skyroot Fence Gate");
        addBlock(AetherBlocks.SKYROOT_DOOR, "Skyroot Door");
        addBlock(AetherBlocks.SKYROOT_TRAPDOOR, "Skyroot Trapdoor");
        addBlock(AetherBlocks.SKYROOT_BUTTON, "Skyroot Button");
        addBlock(AetherBlocks.SKYROOT_PRESSURE_PLATE, "Skyroot Pressure Plate");

        addBlock(AetherBlocks.HOLYSTONE_BUTTON, "Holystone Button");
        addBlock(AetherBlocks.HOLYSTONE_PRESSURE_PLATE, "Holystone Pressure Plate");

        addBlock(AetherBlocks.CARVED_WALL, "Carved Wall");
        addBlock(AetherBlocks.ANGELIC_WALL, "Angelic Wall");
        addBlock(AetherBlocks.HELLFIRE_WALL, "Hellfire Wall");
        addBlock(AetherBlocks.HOLYSTONE_WALL, "Holystone Wall");
        addBlock(AetherBlocks.MOSSY_HOLYSTONE_WALL, "Mossy Holystone Wall");
        addBlock(AetherBlocks.ICESTONE_WALL, "Icestone Wall");
        addBlock(AetherBlocks.HOLYSTONE_BRICK_WALL, "Holystone Brick Wall");
        addBlock(AetherBlocks.AEROGEL_WALL, "Aerogel Wall");

        addBlock(AetherBlocks.SKYROOT_STAIRS, "Skyroot Stairs");
        addBlock(AetherBlocks.CARVED_STAIRS, "Carved Stairs");
        addBlock(AetherBlocks.ANGELIC_STAIRS, "Angelic Stairs");
        addBlock(AetherBlocks.HELLFIRE_STAIRS, "Hellfire Stairs");
        addBlock(AetherBlocks.HOLYSTONE_STAIRS, "Holystone Stairs");
        addBlock(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, "Mossy Holystone Stairs");
        addBlock(AetherBlocks.ICESTONE_STAIRS, "Icestone Stairs");
        addBlock(AetherBlocks.HOLYSTONE_BRICK_STAIRS, "Holystone Brick Stairs");
        addBlock(AetherBlocks.AEROGEL_STAIRS, "Aerogel Stairs");

        addBlock(AetherBlocks.SKYROOT_SLAB, "Skyroot Slab");
        addBlock(AetherBlocks.CARVED_SLAB, "Carved Slab");
        addBlock(AetherBlocks.ANGELIC_SLAB, "Angelic Slab");
        addBlock(AetherBlocks.HELLFIRE_SLAB, "Hellfire Slab");
        addBlock(AetherBlocks.HOLYSTONE_SLAB, "Holystone Slab");
        addBlock(AetherBlocks.MOSSY_HOLYSTONE_SLAB, "Mossy Holystone Slab");
        addBlock(AetherBlocks.ICESTONE_SLAB, "Icestone Slab");
        addBlock(AetherBlocks.HOLYSTONE_BRICK_SLAB, "Holystone Brick Slab");
        addBlock(AetherBlocks.AEROGEL_SLAB, "Aerogel Slab");

        addBlock(AetherBlocks.SUN_ALTAR, "Sun Altar");
        addBlock(AetherBlocks.SKYROOT_BOOKSHELF, "Skyroot Bookshelf");
        addBlock(AetherBlocks.SKYROOT_BED, "Skyroot Bed");

        addBlock(AetherBlocks.FROSTED_ICE, "Frosted Ice");
        addBlock(AetherBlocks.UNSTABLE_OBSIDIAN, "Unstable Obsidian");


        addItem(AetherItems.SKYROOT_PICKAXE, "Skyroot Pickaxe");
        addItem(AetherItems.SKYROOT_AXE, "Skyroot Axe");
        addItem(AetherItems.SKYROOT_SHOVEL, "Skyroot Shovel");
        addItem(AetherItems.SKYROOT_HOE, "Skyroot Hoe");

        addItem(AetherItems.HOLYSTONE_PICKAXE, "Holystone Pickaxe");
        addItem(AetherItems.HOLYSTONE_AXE, "Holystone Axe");
        addItem(AetherItems.HOLYSTONE_SHOVEL, "Holystone Shovel");
        addItem(AetherItems.HOLYSTONE_HOE, "Holystone Hoe");

        addItem(AetherItems.ZANITE_PICKAXE, "Zanite Pickaxe");
        addItem(AetherItems.ZANITE_AXE, "Zanite Axe");
        addItem(AetherItems.ZANITE_SHOVEL, "Zanite Shovel");
        addItem(AetherItems.ZANITE_HOE, "Zanite Hoe");

        addItem(AetherItems.GRAVITITE_PICKAXE, "Gravitite Pickaxe");
        addItem(AetherItems.GRAVITITE_AXE, "Gravitite Axe");
        addItem(AetherItems.GRAVITITE_SHOVEL, "Gravitite Shovel");
        addItem(AetherItems.GRAVITITE_HOE, "Gravitite Hoe");

        addItem(AetherItems.VALKYRIE_PICKAXE, "Valkyrie Pickaxe");
        addItem(AetherItems.VALKYRIE_AXE, "Valkyrie Axe");
        addItem(AetherItems.VALKYRIE_SHOVEL, "Valkyrie Shovel");
        addItem(AetherItems.VALKYRIE_HOE, "Valkyrie Hoe");

        addItem(AetherItems.SKYROOT_SWORD, "Skyroot Sword");
        addItem(AetherItems.HOLYSTONE_SWORD, "Holystone Sword");
        addItem(AetherItems.ZANITE_SWORD, "Zanite Sword");
        addItem(AetherItems.GRAVITITE_SWORD, "Gravitite Sword");

        addItem(AetherItems.VALKYRIE_LANCE, "Valkyrie Lance");

        addItem(AetherItems.FLAMING_SWORD, "Flaming Sword");
        addItem(AetherItems.LIGHTNING_SWORD, "Lightning Sword");
        addItem(AetherItems.HOLY_SWORD, "Holy Sword");
        addItem(AetherItems.VAMPIRE_BLADE, "Vampire Blade");
        addItem(AetherItems.PIG_SLAYER, "Pig Slayer");
        addItem(AetherItems.CANDY_CANE_SWORD, "Candy Cane Sword");

        addItem(AetherItems.HAMMER_OF_KINGBDOGZ, "Hammer of Kingbdogz");

        addItem(AetherItems.LIGHTNING_KNIFE, "Lightning Knife");

        addItem(AetherItems.GOLDEN_DART, "Golden Dart");
        addItem(AetherItems.POISON_DART, "Poison Dart");
        addItem(AetherItems.ENCHANTED_DART, "Enchanted Dart");

        addItem(AetherItems.GOLDEN_DART_SHOOTER, "Golden Dart Shooter");
        addItem(AetherItems.POISON_DART_SHOOTER, "Poison Dart Shooter");
        addItem(AetherItems.ENCHANTED_DART_SHOOTER, "Enchanted Dart Shooter");

        addItem(AetherItems.PHOENIX_BOW, "Phoenix Bow");

        addItem(AetherItems.ZANITE_HELMET, "Zanite Helmet");
        addItem(AetherItems.ZANITE_CHESTPLATE, "Zanite Chestplate");
        addItem(AetherItems.ZANITE_LEGGINGS, "Zanite Leggings");
        addItem(AetherItems.ZANITE_BOOTS, "Zanite Boots");

        addItem(AetherItems.GRAVITITE_HELMET, "Gravitite Helmet");
        addItem(AetherItems.GRAVITITE_CHESTPLATE, "Gravitite Chestplate");
        addItem(AetherItems.GRAVITITE_LEGGINGS, "Gravitite Leggings");
        addItem(AetherItems.GRAVITITE_BOOTS, "Gravitite Boots");

        addItem(AetherItems.NEPTUNE_HELMET, "Neptune Helmet");
        addItem(AetherItems.NEPTUNE_CHESTPLATE, "Neptune Chestplate");
        addItem(AetherItems.NEPTUNE_LEGGINGS, "Neptune Leggings");
        addItem(AetherItems.NEPTUNE_BOOTS, "Neptune Boots");

        addItem(AetherItems.PHOENIX_HELMET, "Phoenix Helmet");
        addItem(AetherItems.PHOENIX_CHESTPLATE, "Phoenix Chestplate");
        addItem(AetherItems.PHOENIX_LEGGINGS, "Phoenix Leggings");
        addItem(AetherItems.PHOENIX_BOOTS, "Phoenix Boots");

        addItem(AetherItems.OBSIDIAN_HELMET, "Obsidian Helmet");
        addItem(AetherItems.OBSIDIAN_CHESTPLATE, "Obsidian Chestplate");
        addItem(AetherItems.OBSIDIAN_LEGGINGS, "Obsidian Leggings");
        addItem(AetherItems.OBSIDIAN_BOOTS, "Obsidian Boots");

        addItem(AetherItems.VALKYRIE_HELMET, "Valkyrie Helmet");
        addItem(AetherItems.VALKYRIE_CHESTPLATE, "Valkyrie Chestplate");
        addItem(AetherItems.VALKYRIE_LEGGINGS, "Valkyrie Leggings");
        addItem(AetherItems.VALKYRIE_BOOTS, "Valkyrie Boots");

        addItem(AetherItems.SENTRY_BOOTS, "Sentry Boots");

        addItem(AetherItems.BLUE_BERRY, "Blue Berry");
        addItem(AetherItems.ENCHANTED_BERRY, "Enchanted Berry");
        addItem(AetherItems.WHITE_APPLE, "White Apple");
        addItem(AetherItems.BLUE_GUMMY_SWET, "Blue Gummy Swet");
        addItem(AetherItems.GOLDEN_GUMMY_SWET, "Golden Gummy Swet");
        addItem(AetherItems.HEALING_STONE, "Healing Stone");
        addItem(AetherItems.CANDY_CANE, "Candy Cane");
        addItem(AetherItems.GINGERBREAD_MAN, "Ginger Bread Man");

        addItem(AetherItems.IRON_RING, "Iron Ring");
        addItem(AetherItems.GOLDEN_RING, "Golden Ring");
        addItem(AetherItems.ZANITE_RING, "Zanite Ring");
        addItem(AetherItems.ICE_RING, "Ice Ring");

        addItem(AetherItems.IRON_PENDANT, "Iron Pendant");
        addItem(AetherItems.GOLDEN_PENDANT, "Golden Pendant");
        addItem(AetherItems.ZANITE_PENDANT, "Zanite Pendant");
        addItem(AetherItems.ICE_PENDANT, "Ice Pendant");

        addItem(AetherItems.LEATHER_GLOVES, "Leather Gloves");
        addItem(AetherItems.CHAINMAIL_GLOVES, "Chainmail Gloves");
        addItem(AetherItems.IRON_GLOVES, "Iron Gloves");
        addItem(AetherItems.GOLDEN_GLOVES, "Golden Gloves");
        addItem(AetherItems.DIAMOND_GLOVES, "Diamond Gloves");
        addItem(AetherItems.NETHERITE_GLOVES, "Netherite Gloves");
        addItem(AetherItems.ZANITE_GLOVES, "Zanite Gloves");
        addItem(AetherItems.GRAVITITE_GLOVES, "Gravitite Gloves");
        addItem(AetherItems.NEPTUNE_GLOVES, "Neptune Gloves");
        addItem(AetherItems.PHOENIX_GLOVES, "Phoenix Gloves");
        addItem(AetherItems.OBSIDIAN_GLOVES, "Obsidian Gloves");
        addItem(AetherItems.VALKYRIE_GLOVES, "Valkyrie Gloves");

        addItem(AetherItems.RED_CAPE, "Red Cape");
        addItem(AetherItems.BLUE_CAPE, "Blue Cape");
        addItem(AetherItems.YELLOW_CAPE, "Yellow Cape");
        addItem(AetherItems.WHITE_CAPE, "White Cape");
        addItem(AetherItems.SWET_CAPE, "Swet Cape");
        addItem(AetherItems.INVISIBILITY_CLOAK, "Invisibility Cloak");
        addItem(AetherItems.AGILITY_CAPE, "Agility Cape");
        addItem(AetherItems.VALKYRIE_CAPE, "Valkyrie Cape");

        addItem(AetherItems.GOLDEN_FEATHER, "Golden Feather");
        addItem(AetherItems.REGENERATION_STONE, "Regeneration Stone");
        addItem(AetherItems.IRON_BUBBLE, "Iron Bubble");
        addItem(AetherItems.SHIELD_OF_REPULSION, "Shield of Repulsion");

        addItem(AetherItems.SKYROOT_STICK, "Skyroot Stick");
        addItem(AetherItems.GOLDEN_AMBER, "Golden Amber");
        addItem(AetherItems.SWET_BALL, "Swet Ball");
        addItem(AetherItems.AECHOR_PETAL, "Aechor Petal");
        addItem(AetherItems.AMBROSIUM_SHARD, "Ambrosium Shard");
        addItem(AetherItems.ZANITE_GEMSTONE, "Zanite Gemstone");

        addItem(AetherItems.VICTORY_MEDAL, "Victory Medal");

        addItem(AetherItems.BRONZE_DUNGEON_KEY, "Bronze Key");
        addItem(AetherItems.SILVER_DUNGEON_KEY, "Silver Key");
        addItem(AetherItems.GOLD_DUNGEON_KEY, "Gold Key");

        addItem(AetherItems.MUSIC_DISC_AETHER_TUNE, "Blue Music Disc");
        addDiscDesc(AetherItems.MUSIC_DISC_AETHER_TUNE, "Noisestorm - Aether Tune");
        addItem(AetherItems.MUSIC_DISC_ASCENDING_DAWN, "Valkyrie Music Disc");
        addDiscDesc(AetherItems.MUSIC_DISC_ASCENDING_DAWN, "Emile van Krieken - Ascending Dawn");
        addItem(AetherItems.MUSIC_DISC_CHINCHILLA, "Sepia Music Disc");
        addDiscDesc(AetherItems.MUSIC_DISC_CHINCHILLA, "RENREN - chinchilla");
        addItem(AetherItems.MUSIC_DISC_HIGH, "Super Music Disc");
        addDiscDesc(AetherItems.MUSIC_DISC_HIGH, "RENREN - high");

        addItem(AetherItems.SKYROOT_BUCKET, "Skyroot Bucket");
        addItem(AetherItems.SKYROOT_WATER_BUCKET, "Skyroot Water Bucket");
        addItem(AetherItems.SKYROOT_POISON_BUCKET, "Skyroot Poison Bucket");
        addItem(AetherItems.SKYROOT_REMEDY_BUCKET, "Skyroot Remedy Bucket");
        addItem(AetherItems.SKYROOT_MILK_BUCKET, "Skyroot Milk Bucket");
        addItem(AetherItems.SKYROOT_POWDER_SNOW_BUCKET, "Skyroot Powder Snow Bucket");
        addItem(AetherItems.SKYROOT_COD_BUCKET, "Skyroot Bucket of Cod");
        addItem(AetherItems.SKYROOT_SALMON_BUCKET, "Skyroot Bucket of Salmon");
        addItem(AetherItems.SKYROOT_PUFFERFISH_BUCKET, "Skyroot Bucket of Pufferfish");
        addItem(AetherItems.SKYROOT_TROPICAL_FISH_BUCKET, "Skyroot Bucket of Tropical Fish");
        addItem(AetherItems.SKYROOT_AXOLOTL_BUCKET, "Skyroot Bucket of Axolotl");
        addItem(AetherItems.SKYROOT_TADPOLE_BUCKET, "Skyroot Bucket of Tadpole");

        addItem(AetherItems.SKYROOT_BOAT, "Skyroot Boat");
        addItem(AetherItems.SKYROOT_CHEST_BOAT, "Skyroot Boat with Chest");

        addItem(AetherItems.COLD_PARACHUTE, "Cold Parachute");
        addItem(AetherItems.GOLDEN_PARACHUTE, "Golden Parachute");

        addItem(AetherItems.NATURE_STAFF, "Nature Staff");
        addItem(AetherItems.CLOUD_STAFF, "Cloud Staff");

        addItem(AetherItems.BLUE_MOA_EGG, "Blue Moa Egg");
        addItem(AetherItems.WHITE_MOA_EGG, "White Moa Egg");
        addItem(AetherItems.BLACK_MOA_EGG, "Black Moa Egg");

        addItem(AetherItems.LIFE_SHARD, "Life Shard");

        addItem(AetherItems.BOOK_OF_LORE, "Book of Lore");

        addItem(AetherItems.AETHER_PORTAL_FRAME, "Aether Portal Frame");

        addItem(AetherItems.AECHOR_PLANT_SPAWN_EGG, "Aechor Plant Spawn Egg");
        addItem(AetherItems.AERBUNNY_SPAWN_EGG, "Aerbunny Spawn Egg");
        addItem(AetherItems.AERWHALE_SPAWN_EGG, "Aerwhale Spawn Egg");
        addItem(AetherItems.COCKATRICE_SPAWN_EGG, "Cockatrice Spawn Egg");
        addItem(AetherItems.FIRE_MINION_SPAWN_EGG, "Fire Minion Spawn Egg");
        addItem(AetherItems.FLYING_COW_SPAWN_EGG, "Flying Cow Spawn Egg");
        addItem(AetherItems.MIMIC_SPAWN_EGG, "Mimic Spawn Egg");
        addItem(AetherItems.MOA_SPAWN_EGG, "Moa Spawn Egg");
        addItem(AetherItems.PHYG_SPAWN_EGG, "Phyg Spawn Egg");
        addItem(AetherItems.SENTRY_SPAWN_EGG, "Sentry Spawn Egg");
        addItem(AetherItems.SHEEPUFF_SPAWN_EGG, "Sheepuff Spawn Egg");
        addItem(AetherItems.BLUE_SWET_SPAWN_EGG, "Blue Swet Spawn Egg");
        addItem(AetherItems.GOLDEN_SWET_SPAWN_EGG, "Golden Swet Spawn Egg");
        addItem(AetherItems.WHIRLWIND_SPAWN_EGG, "Whirlwind Spawn Egg");
        addItem(AetherItems.EVIL_WHIRLWIND_SPAWN_EGG, "Evil Whirlwind Spawn Egg");
        addItem(AetherItems.VALKYRIE_SPAWN_EGG, "Valkyrie Spawn Egg");
        addItem(AetherItems.VALKYRIE_QUEEN_SPAWN_EGG, "Valkyrie Queen Spawn Egg");
        addItem(AetherItems.SLIDER_SPAWN_EGG, "Slider Spawn Egg");
        addItem(AetherItems.SUN_SPIRIT_SPAWN_EGG, "Sun Spirit Spawn Egg");
        addItem(AetherItems.ZEPHYR_SPAWN_EGG, "Zephyr Spawn Egg");


        addEntityType(AetherEntityTypes.PHYG, "Phyg");
        addEntityType(AetherEntityTypes.FLYING_COW, "Flying Cow");
        addEntityType(AetherEntityTypes.SHEEPUFF, "Sheepuff");
        addEntityType(AetherEntityTypes.MOA, "Moa");
        addEntityType(AetherEntityTypes.AERWHALE, "Aerwhale");
        addEntityType(AetherEntityTypes.AERBUNNY, "Aerbunny");

        addEntityType(AetherEntityTypes.BLUE_SWET, "Blue Swet");
        addEntityType(AetherEntityTypes.GOLDEN_SWET, "Golden Swet");
        addEntityType(AetherEntityTypes.WHIRLWIND, "Whirlwind");
        addEntityType(AetherEntityTypes.EVIL_WHIRLWIND, "Evil Whirlwind");
        addEntityType(AetherEntityTypes.AECHOR_PLANT, "Aechor Plant");
        addEntityType(AetherEntityTypes.COCKATRICE, "Cockatrice");
        addEntityType(AetherEntityTypes.ZEPHYR, "Zephyr");

        addEntityType(AetherEntityTypes.SENTRY, "Sentry");
        addEntityType(AetherEntityTypes.MIMIC, "Mimic");
        addEntityType(AetherEntityTypes.VALKYRIE, "Valkyrie");
        addEntityType(AetherEntityTypes.FIRE_MINION, "Fire Minion");

        addEntityType(AetherEntityTypes.SLIDER, "Slider");
        addEntityType(AetherEntityTypes.VALKYRIE_QUEEN, "Valkyrie Queen");
        addEntityType(AetherEntityTypes.SUN_SPIRIT, "Sun Spirit");

        addEntityType(AetherEntityTypes.SKYROOT_BOAT, "Boat");
        addEntityType(AetherEntityTypes.SKYROOT_CHEST_BOAT, "Boat with Chest");
        addEntityType(AetherEntityTypes.CLOUD_MINION, "Cloud Minion");
        addEntityType(AetherEntityTypes.COLD_PARACHUTE, "Cold Parachute");
        addEntityType(AetherEntityTypes.GOLDEN_PARACHUTE, "Golden Parachute");
        addEntityType(AetherEntityTypes.FLOATING_BLOCK, "Floating Block");
        addEntityType(AetherEntityTypes.TNT_PRESENT, "TNT Present");

        addEntityType(AetherEntityTypes.ZEPHYR_SNOWBALL, "Zephyr Snowball");
        addEntityType(AetherEntityTypes.FIRE_CRYSTAL, "Fire Crystal");
        addEntityType(AetherEntityTypes.CLOUD_CRYSTAL, "Cloud Crystal");
        addEntityType(AetherEntityTypes.ICE_CRYSTAL, "Ice Crystal");
        addEntityType(AetherEntityTypes.THUNDER_CRYSTAL, "Thunder Crystal");
        addEntityType(AetherEntityTypes.GOLDEN_DART, "Golden Dart");
        addEntityType(AetherEntityTypes.POISON_DART, "Poison Dart");
        addEntityType(AetherEntityTypes.ENCHANTED_DART, "Enchanted Dart");
        addEntityType(AetherEntityTypes.POISON_NEEDLE, "Poison Needle");
        addEntityType(AetherEntityTypes.LIGHTNING_KNIFE, "Lightning Knife");
        addEntityType(AetherEntityTypes.HAMMER_PROJECTILE, "Hammer Projectile");


        addEffect(AetherEffects.INEBRIATION, "Inebriation");


        addBiome(AetherBiomes.SKYROOT_MEADOW, "Skyroot Meadow");
        addBiome(AetherBiomes.SKYROOT_GROVE, "Skyroot Grove");
        addBiome(AetherBiomes.SKYROOT_WOODLAND, "Skyroot Woodland");
        addBiome(AetherBiomes.SKYROOT_FOREST, "Skyroot Forest");


        addContainerType(AetherMenuTypes.BOOK_OF_LORE, "Book of Lore");
        addContainerType(AetherMenuTypes.ALTAR, "Altar");
        addContainerType(AetherMenuTypes.FREEZER, "Freezer");
        addContainerType(AetherMenuTypes.INCUBATOR, "Incubator");
        addContainerType("bronze_treasure_chest", "Bronze Treasure Chest");
        addContainerType("silver_treasure_chest", "Silver Treasure Chest");
        addContainerType("gold_treasure_chest", "Gold Treasure Chest");
        addContainerType("sun_altar", "Sun Altar");


        addCreativeTab(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, "Aether Building Blocks");
        addCreativeTab(AetherCreativeTabs.AETHER_DUNGEON_BLOCKS, "Aether Dungeon Blocks");
        addCreativeTab(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, "Aether Natural Blocks");
        addCreativeTab(AetherCreativeTabs.AETHER_FUNCTIONAL_BLOCKS, "Aether Functional Blocks");
        addCreativeTab(AetherCreativeTabs.AETHER_REDSTONE_BLOCKS, "Aether Redstone Blocks");
        addCreativeTab(AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES, "Aether Equipment & Utilities");
        addCreativeTab(AetherCreativeTabs.AETHER_ARMOR_AND_ACCESSORIES, "Aether Armor & Accessories");
        addCreativeTab(AetherCreativeTabs.AETHER_FOOD_AND_DRINKS, "Aether Food & Drinks");
        addCreativeTab(AetherCreativeTabs.AETHER_INGREDIENTS, "Aether Ingredients");
        addCreativeTab(AetherCreativeTabs.AETHER_SPAWN_EGGS, "Aether Spawn Eggs");


        addAdvancement("the_aether", "The Aether");
        addAdvancement("enter_aether", "Hostile Paradise");
        addAdvancement("read_lore", "The More You Know!");
        addAdvancement("loreception", "Lore-Ception!");
        addAdvancement("blue_aercloud", "To Infinity and Beyond!");
        addAdvancement("obtain_egg", "Don't Count your Moas...");
        addAdvancement("obtain_petal", "Baby Food");
        addAdvancement("incubate_moa", "... Until they hatch!");
        addAdvancement("black_moa", "Let's Fly!");
        addAdvancement("zanite", "Exotic Hardware");
        addAdvancement("craft_altar", "Do You Believe in Magic?");
        addAdvancement("icestone", "Cold as Ice");
        addAdvancement("ice_accessory", "Cool Jewelery!");
        addAdvancement("enchanted_gravitite", "Pink is the New Blue");
        addAdvancement("gravitite_armor", "Defying Gravity");
        addAdvancement("mount_phyg", "When Phygs Fly");
        addAdvancement("bronze_dungeon", "Like a Bossaru!");
        addAdvancement("hammer_loot", "The Power of the Gods");
        addAdvancement("zephyr_hammer", "Ultimate Ban Hammer");
        addAdvancement("lance_loot", "Challenger to the Throne");
        addAdvancement("silver_dungeon", "Dethroned");
        addAdvancement("valkyrie_loot", "Earning your wings");
        addAdvancement("valkyrie_hoe", "Plunderer's Remorse");
        addAdvancement("regen_stone", "Battle Hardened");
        addAdvancement("gold_dungeon", "Extinguished");
        addAdvancement("phoenix_armor", "Fireproof");
        addAdvancement("obsidian_armor", "Ice Bucket Armor");
        addAdvancement("aether_sleep", "A well earned rest");

        addAdvancementDesc("the_aether", "It's not dead!");
        addAdvancementDesc("enter_aether", "Enter the Aether");
        addAdvancementDesc("read_lore", "Read a Book of Lore");
        addAdvancementDesc("loreception", "Put a Book of Lore inside a Book of Lore");
        addAdvancementDesc("blue_aercloud", "Bounce on a Blue Aercloud");
        addAdvancementDesc("obtain_egg", "Obtain a Moa Egg");
        addAdvancementDesc("obtain_petal", "Harvest an Aechor Petal from an Aechor Plant");
        addAdvancementDesc("incubate_moa", "Incubate a Moa");
        addAdvancementDesc("black_moa", "Ride a Black Moa");
        addAdvancementDesc("zanite", "Have a Zanite Gemstone in your inventory");
        addAdvancementDesc("craft_altar", "Craft an Altar");
        addAdvancementDesc("icestone", "Obtain Icestone");
        addAdvancementDesc("ice_accessory", "Use a Freezer and Icestone to freeze an accessory");
        addAdvancementDesc("enchanted_gravitite", "Use an Altar to obtain Enchanted Gravitite");
        addAdvancementDesc("gravitite_armor", "Have a full set of gravitite armor in your inventory");
        addAdvancementDesc("mount_phyg", "Fly on a Phyg!");
        addAdvancementDesc("bronze_dungeon", "Defeat the bronze boss");
        addAdvancementDesc("hammer_loot", "Obtain the Hammer of Kingbdogz from the Bronze Dungeon");
        addAdvancementDesc("zephyr_hammer", "Kill a Zephyr with the Hammer of Kingbdogz. Sweet Revenge!");
        addAdvancementDesc("lance_loot", "Obtain the Valkyrie Lance from the bronze dungeon. Time to challenge the Silver Dungeon!");
        addAdvancementDesc("silver_dungeon", "Defeat the silver boss");
        addAdvancementDesc("regen_stone", "Obtain a Regeneration Stone from the Valkyrie dungeon. The final dungeon awaits...");
        addAdvancementDesc("valkyrie_loot", "Obtain a piece of Valkyrie equipment from the Silver Dungeon");
        addAdvancementDesc("valkyrie_hoe", "I defeated the Silver Dungeon and all I got was this stupid Hoe");
        addAdvancementDesc("gold_dungeon", "Defeat the gold boss");
        addAdvancementDesc("phoenix_armor", "Obtain a piece of Phoenix armor from the Gold Dungeon");
        addAdvancementDesc("obsidian_armor", "Have a full set of obsidian armor in your inventory");
        addAdvancementDesc("aether_sleep", "Finally sleep in the Aether");


        addSubtitle("block", "aether_portal.ambient", "Aether Portal whooshes");
        addSubtitle("block", "aether_portal.trigger", "Aether Portal noise intensifies");
        addSubtitle("block", "chest_mimic.open", "Mimic awakens");
        addSubtitle("block", "altar.crackle", "Altar crackles");
        addSubtitle("block", "freezer.crackle", "Freezer crackles");
        addSubtitle("block", "incubator.crackle", "Incubator crackles");
        addSubtitle("block", "dungeon_trap.trigger", "Dungeon Trap activated");
        addSubtitle("block", "water.evaporate", "Water evaporated");

        addSubtitle("item", "dart_shooter.shoot", "Dart Shooter fired");
        addSubtitle("item", "lightning_knife.shoot", "Lightning Knife flies");
        addSubtitle("item", "hammer_of_kingbdogz.shoot", "Hammer fired");

        addSubtitle("item", "armor.equip_zanite", "Zanite armor clangs");
        addSubtitle("item", "armor.equip_gravitite", "Gravitite armor clinks");
        addSubtitle("item", "armor.equip_valkyrie", "Valkyrie armor clanks");
        addSubtitle("item", "armor.equip_neptune", "Neptune armor clinks");
        addSubtitle("item", "armor.equip_phoenix", "Phoenix armor clanks");
        addSubtitle("item", "armor.equip_obsidian", "Obsidian armor clanks");
        addSubtitle("item", "armor.equip_sentry", "Sentry armor clanks");

        addSubtitle("item", "accessory.equip_generic", "Accessory equips");
        addSubtitle("item", "accessory.equip_iron_ring", "Iron Ring jingles");
        addSubtitle("item", "accessory.equip_gold_ring", "Gold Ring jingles");
        addSubtitle("item", "accessory.equip_zanite_ring", "Zanite Ring jingles");
        addSubtitle("item", "accessory.equip_ice_ring", "Ice Ring jingles");
        addSubtitle("item", "accessory.equip_iron_pendant", "Iron Pendant jingles");
        addSubtitle("item", "accessory.equip_gold_pendant", "Gold Pendant jingles");
        addSubtitle("item", "accessory.equip_zanite_pendant", "Zanite Pendant jingles");
        addSubtitle("item", "accessory.equip_ice_pendant", "Ice Pendant jingles");
        addSubtitle("item", "accessory.equip_cape", "Cape rustles");

        addSubtitle("entity", "phyg.ambient", "Phyg oinks");
        addSubtitle("entity", "phyg.death", "Phyg dies");
        addSubtitle("entity", "phyg.hurt", "Phyg hurts");
        addSubtitle("entity", "phyg.saddle", "Saddle equips");
        addSubtitle("entity", "phyg.step", "Footsteps");

        addSubtitle("entity", "flying_cow.ambient", "Flying Cow moos");
        addSubtitle("entity", "flying_cow.death", "Flying Cow dies");
        addSubtitle("entity", "flying_cow.hurt", "Flying Cow hurts");
        addSubtitle("entity", "flying_cow.saddle", "Saddle equips");
        addSubtitle("entity", "flying_cow.milk", "Flying Cow gets milked");
        addSubtitle("entity", "flying_cow.step", "Footsteps");

        addSubtitle("entity", "sheepuff.ambient", "Sheepuff baahs");
        addSubtitle("entity", "sheepuff.death", "Sheepuff dies");
        addSubtitle("entity", "sheepuff.hurt", "Sheepuff hurts");
        addSubtitle("entity", "sheepuff.step", "Footsteps");

        addSubtitle("entity", "moa.ambient", "Moa calls");
        addSubtitle("entity", "moa.death", "Moa dies");
        addSubtitle("entity", "moa.hurt", "Moa hurts");
        addSubtitle("entity", "moa.saddle", "Saddle equips");
        addSubtitle("entity", "moa.step", "Footsteps");
        addSubtitle("entity", "moa.flap", "Moa flaps");
        addSubtitle("entity", "moa.egg", "Moa plops");

        addSubtitle("entity", "aerwhale.ambient", "Aerwhale whistles");
        addSubtitle("entity", "aerwhale.death", "Aerwhale cries");

        addSubtitle("entity", "aerbunny.death", "Aerbunny dies");
        addSubtitle("entity", "aerbunny.hurt", "Aerbunny squeals");
        addSubtitle("entity", "aerbunny.lift", "Aerbunny squeaks");

        addSubtitle("entity", "swet.attack", "Swet attacks");
        addSubtitle("entity", "swet.death", "Swet dies");
        addSubtitle("entity", "swet.hurt", "Swet hurts");
        addSubtitle("entity", "swet.jump", "Swet squishes");
        addSubtitle("entity", "swet.squish", "Swet squishes");

        addSubtitle("entity", "aechor_plant.shoot", "Aechor Plant shoots");
        addSubtitle("entity", "aechor_plant.hurt", "Aechor Plant hurts");
        addSubtitle("entity", "aechor_plant.death", "Aechor Plant dies");

        addSubtitle("entity", "cockatrice.shoot", "Cockatrice shoots");
        addSubtitle("entity", "cockatrice.ambient", "Cockatrice calls");
        addSubtitle("entity", "cockatrice.death", "Cockatrice dies");
        addSubtitle("entity", "cockatrice.hurt", "Cockatrice hurts");
        addSubtitle("entity", "cockatrice.flap", "Cockatrice flaps");

        addSubtitle("entity", "zephyr.shoot", "Zephyr spits");
        addSubtitle("entity", "zephyr.ambient", "Zephyr blows");
        addSubtitle("entity", "zephyr.death", "Zephyr dies");
        addSubtitle("entity", "zephyr.hurt", "Zephyr hurts");

        addSubtitle("entity", "item.pickup", "Item plops");

        addSubtitle("entity", "sentry.death", "Sentry dies");
        addSubtitle("entity", "sentry.hurt", "Sentry hurts");
        addSubtitle("entity", "sentry.jump", "Sentry squishes");

        addSubtitle("entity", "mimic.attack", "Mimic attacks");
        addSubtitle("entity", "mimic.death", "Mimic dies");
        addSubtitle("entity", "mimic.hurt", "Mimic hurts");
        addSubtitle("entity", "mimic.kill", "Mimic burps");

        addSubtitle("entity", "slider.awaken", "Slider awakens");
        addSubtitle("entity", "slider.ambient", "Slider drones");
        addSubtitle("entity", "slider.collide", "Slider smashes");
        addSubtitle("entity", "slider.move", "Slider slides");
        addSubtitle("entity", "slider.hurt", "Slider hurts");
        addSubtitle("entity", "slider.death", "Slider breaks");

        addSubtitle("entity", "valkyrie.death", "Valkyrie dies");
        addSubtitle("entity", "valkyrie.hurt", "Valkyrie hurts");

        addSubtitle("entity", "valkyrie_queen.death", "Valkyrie Queen dies");
        addSubtitle("entity", "valkyrie_queen.hurt", "Valkyrie Queen hurts");

        addSubtitle("entity", "sun_spirit.shoot", "Sun Spirit shoots");

        addSubtitle("entity", "cloud_minion.shoot", "Cloud Minion spits");

        addSubtitle("entity", "cloud_crystal.explode", "Crystal explodes");
        addSubtitle("entity", "fire_crystal.explode", "Crystal explodes");
        addSubtitle("entity", "ice_crystal.explode", "Crystal explodes");
        addSubtitle("entity", "thunder_crystal.explode", "Crystal explodes");

        addSubtitle("entity", "dart.hit", "Dart hits");


        addDeath("floating_block", "%1$s was squashed by a floating block");
        addDeath("floating_block.player", "%1$s was squashed by a floating block whilst fighting %2$s");
        addDeath("inebriation", "%1$s was inebriated");
        addDeath("inebriation.player", "%1$s was inebriated by %2$s");
        addDeath("crush", "%1$s was crushed by %2$s");
        addDeath("cloud_crystal", "%1$s was chilled by %2$s's Cloud Crystal");
        addDeath("fire_crystal", "%1$s was incinerated by %2$s's Fire Crystal");
        addDeath("ice_crystal", "%1$s was chilled by %2$s's Ice Crystal");
        addDeath("thunder_crystal", "%1$s was zapped by %2$s's Thunder Crystal");
        addDeath("incineration", "%1$s was incinerated by %2$s");


        addMenuText("button.world_preview", "W");
        addMenuText("button.theme", "T");
        addMenuText("button.quick_load", "Q");

        addMenuText("minecraft", "Normal Theme");
        addMenuText("aether", "Aether Theme");
        addMenuText("preview", "Toggle World");
        addMenuText("load", "Quick Load");

        addGuiText("pro_tip", "Pro Tip:");
        addGuiText("ascending", "Ascending to the Aether");
        addGuiText("descending", "Descending from the Aether");

        addGuiText("accessories.skins_button", "Moa Skins");
        addGuiText("accessories.customization_button", "Customization");

        addGuiText("sun_altar.time", "Time");

        addGuiText("recipebook.toggleRecipes.enchantable", "Showing Enchantable");
        addGuiText("recipebook.toggleRecipes.freezable", "Showing Freezable");
        addGuiText("recipebook.toggleRecipes.incubatable", "Showing Incubatable");

        addGuiText("jei.altar.enchanting", "Enchanting");
        addGuiText("jei.altar.repairing", "Repairing");
        addGuiText("jei.freezing", "Freezing");
        addGuiText("jei.incubating", "Incubating");

        addGuiText("jei.fuel", "Aether Fuel");

        addGuiText("jei.biome.tooltip", "Requires Biomes:");
        addGuiText("jei.biome.tooltip.biome", "Biome");
        addGuiText("jei.biome.tooltip.tag", "Biome Tag");
        addGuiText("jei.biome.tooltip.biomes", "Biomes in Tag");
        addGuiText("jei.properties.tooltip", "With Properties:");
        addGuiText("jei.bypass", "Except On:");
        addGuiText("jei.biome.ban.tooltip", "Blocked in Biomes:");

        addGuiText("jei.ambrosium_enchanting", "Ambrosium Enchanting");
        addGuiText("jei.swet_ball_conversion", "Swet Ball Conversion");
        addGuiText("jei.icestone_freezable", "Icestone Freezable");
        addGuiText("jei.accessory_freezable", "Accessory Freezable");
        addGuiText("jei.placement_conversion", "Placement Conversion");
        addGuiText("jei.item_placement_ban", "Item Use Prevention");
        addGuiText("jei.block_placement_ban", "Block Place Prevention");


        addGuiText("boss.message.far", "You must be in the boss room to interact.");

        addGuiText("slider.title", "the Slider");
        addGuiText("slider.message.attack.invalid", "Hmm. Perhaps I need to attack it with a Pickaxe?");

        // Valkyrie Dialogue
        addGuiText("valkyrie.dialog.1", "What's that? You want to fight? Aww, what a cute little human.");
        addGuiText("valkyrie.dialog.2", "You're not thinking of fighting a big, strong Valkyrie are you?");
        addGuiText("valkyrie.dialog.3", "I don't think you should bother me, you could get really hurt.");

        addGuiText("valkyrie.dialog.attack.1", "I'm not going easy on you!");
        addGuiText("valkyrie.dialog.attack.2", "You're gonna regret that!");
        addGuiText("valkyrie.dialog.attack.3", "Now you're in for it!");

        addGuiText("valkyrie.dialog.defeated.1", "Alright, alright! You win!");
        addGuiText("valkyrie.dialog.defeated.2", "Okay, I give up! Geez!");
        addGuiText("valkyrie.dialog.defeated.3", "Oww! Fine, here's your medal...");

        addGuiText("valkyrie.dialog.medal.1", "Umm... that's a nice pile of medallions you have there...");
        addGuiText("valkyrie.dialog.medal.2", "That's pretty impressive, but you won't defeat me.");
        addGuiText("valkyrie.dialog.medal.3", "You think you're a tough guy, eh? Well, bring it on!");

        addGuiText("valkyrie.dialog.playerdeath.1", "You want a medallion? Try being less pathetic.");
        addGuiText("valkyrie.dialog.playerdeath.2", "Maybe some day, %s... maybe some day.");
        addGuiText("valkyrie.dialog.playerdeath.3", "Humans aren't nearly as cute when they're dead.");

        // Valkyrie Queen Dialogue
        addGuiText("queen.dialog.answer", "This is a sanctuary for us Valkyries who seek rest.");
        addGuiText("queen.dialog.challenge", "Very well then. Bring me ten medals from my subordinates to prove your worth, then we'll see.");
        addGuiText("queen.dialog.defeated", "You are truly... a mighty warrior...");
        addGuiText("queen.dialog.deny_fight", "So be it then. Goodbye adventurer.");
        addGuiText("queen.dialog.fight", "This will be your final battle!");
        addGuiText("queen.dialog.goodbye", "Goodbye adventurer.");
        addGuiText("queen.dialog.no_medals", "Take your time.");
        addGuiText("queen.dialog.peaceful", "Sorry, I don't fight with weaklings.");
        addGuiText("queen.dialog.playerdeath", "As expected of a human.");
        addGuiText("queen.dialog.begin", "Now then, let's begin!");
        addGuiText("queen.dialog.ready", "If you wish to challenge me, strike at any time.");
        addGuiText("queen.title", "the Valkyrie Queen");

        addGuiText("player.dialog.challenge", "I wish to fight you!");
        addGuiText("player.dialog.deny_fight", "On second thought, I'd rather not.");
        addGuiText("player.dialog.have_medals", "I'm ready, I have the medals right here!");
        addGuiText("player.dialog.leave", "Nevermind");
        addGuiText("player.dialog.no_medals", "I'll return when I have them.");
        addGuiText("player.dialog.question", "What can you tell me about this place?");

        // Sun Spirit Dialogue
        addGuiText("sun_spirit.title", "the Sun Spirit");
        addGuiText("sun_spirit.dead", "Such bitter cold... is this the feeling... of pain?");
        addGuiText("sun_spirit.playerdeath", "Such is the fate of a being who opposes the might of the sun.");
        addGuiText("sun_spirit.line0", "You are certainly a brave soul to have entered this chamber.");
        addGuiText("sun_spirit.line1", "Begone human, you serve no purpose here.");
        addGuiText("sun_spirit.line2", "Your presence annoys me. Do you not fear my burning aura?");
        addGuiText("sun_spirit.line3", "I have nothing to offer you, fool. Leave me at peace.");
        addGuiText("sun_spirit.line4", "Perhaps you are ignorant. Do you wish to know who I am?");
        addGuiText("sun_spirit.line5.1", "I am a sun spirit, embodiment of Aether's eternal daylight. As");
        addGuiText("sun_spirit.line5.2", "long as I am alive, the sun will never set on this world.");
        addGuiText("sun_spirit.line6.1", "My body burns with the anger of a thousand beasts. No man,");
        addGuiText("sun_spirit.line6.2", "hero, or villain can harm me. You are no exception.");
        addGuiText("sun_spirit.line7.1", "You wish to challenge the might of the sun? You are mad.");
        addGuiText("sun_spirit.line7.2", "Do not further insult me or you will feel my wrath.");
        addGuiText("sun_spirit.line8", "This is your final warning. Leave now, or prepare to burn.");
        addGuiText("sun_spirit.line9", "As you wish, your death will be slow and agonizing.");
        addGuiText("sun_spirit.line10", "Did your previous death not satisfy your curiosity, human?");

        addGuiText("patreon.message", "Enjoying %s1? Check out our %s2 and %s3!");
        addGuiText("patreon.note", "This message will only display once.");

        addMoaSkinsText("title", "Moa Skins");

        addMoaSkinsText("text.donate", "Donate to the project to get Moa Skins!");
        addMoaSkinsText("text.reward", "Thank you for donating to the project!");

        addMoaSkinsText("button.apply", "Apply");
        addMoaSkinsText("button.remove", "Remove");
        addMoaSkinsText("button.donate", "Donate");
        addMoaSkinsText("button.connect", "Connect");
        addMoaSkinsText("button.refresh", "Refresh");

        addMoaSkinsText("pack.natural_moa_skins", "Natural Moa Skins");
        addMoaSkinsText("pack.lifetime_angel_moa_skins", "Lifetime Angel Moa Skins");
        addMoaSkinsText("pack.lifetime_valkyrie_moa_skins", "Lifetime Valkyrie Moa Skins");

        addMoaSkinsText("skin.blue_moa", "Blue Moa");
        addMoaSkinsText("skin.white_moa", "White Moa");
        addMoaSkinsText("skin.black_moa", "Black Moa");
        addMoaSkinsText("skin.orange_moa", "Orange Moa");
        addMoaSkinsText("skin.boko_yellow", "Boko Yellow");
        addMoaSkinsText("skin.crookjaw_purple", "Crookjaw Purple");
        addMoaSkinsText("skin.gharrix_red", "Gharrix Red");
        addMoaSkinsText("skin.halcian_pink", "Halcian Pink");
        addMoaSkinsText("skin.tivalier_green", "Tivalier Green");
        addMoaSkinsText("skin.arctic_moa", "Arctic Moa");
        addMoaSkinsText("skin.cockatrice_moa", "Cockatrice");
        addMoaSkinsText("skin.phoenix_moa", "Phoenix Moa");
        addMoaSkinsText("skin.sentry_moa", "Sentry Moa");
        addMoaSkinsText("skin.valkyrie_moa", "Valkyrie Moa");

        addMoaSkinsText("tooltip.title.access.pledging", "Pledge Access");
        addMoaSkinsText("tooltip.title.access.lifetime", "Lifetime Access");

        addMoaSkinsText("tooltip.pledging", "You have access to this skin while pledging to the %s tier!");
        addMoaSkinsText("tooltip.lifetime", "You have lifetime access to this skin!");

        addMoaSkinsText("tooltip.access.pledging", "Pledging to the %s tier will give you access to this skin during the pledge duration!");
        addMoaSkinsText("tooltip.access.lifetime", "Pledging to the %s tier will give you lifetime access to this skin!");


        addCustomizationText("title", "Customization");
        addCustomizationText("halo.on", "Player Halo: ON");
        addCustomizationText("halo.off", "Player Halo: OFF");
        addCustomizationText("halo.color", "Halo Color");
        addCustomizationText("developer_glow.on", "Developer Glow: ON");
        addCustomizationText("developer_glow.off", "Developer Glow: OFF");
        addCustomizationText("developer_glow.color", "Developer Glow Color");
        addCustomizationText("undo", "Undo");
        addCustomizationText("save", "Save");


        addLoreBookText("previous", "Prev.");
        addLoreBookText("next", "Next");
        addLoreBookText("book", "Book");
        addLoreBookText("of_lore", "Of Lore");
        addLoreBookText("item", "Item:");


        addGeneric("hammer_of_kingbdogz_cooldown", "Cooldown");

        addGeneric("life_shard_limit", "You can only use a total of %s Life Shards.");
        addGeneric("bronze_treasure_chest_locked", "This Treasure Chest must be unlocked with a Bronze Key.");
        addGeneric("silver_treasure_chest_locked", "This Treasure Chest must be unlocked with a Silver Key.");
        addGeneric("gold_treasure_chest_locked", "This Treasure Chest must be unlocked with a Gold Key.");
        addGeneric("sun_altar.in_control", "The sun spirit is still in control of this realm.");
        addGeneric("sun_altar.no_permission", "You don't have permission to use this.");
        addGeneric("sun_altar.no_power", "The sun spirit has no power over this realm.");

        addGeneric("dungeon.bronze_dungeon", "Bronze Dungeon");
        addGeneric("dungeon.silver_dungeon", "Silver Dungeon");
        addGeneric("dungeon.gold_dungeon", "Gold Dungeon");


        addCommand("menu.fix", "Reset world preview values");

        addCommand("capability.player.life_shards.set", "Set life shard count for %s to %s");

        addCommand("capability.time.eternal_day.set", "Set eternal day to %s");
        addCommand("capability.time.eternal_day.query", "Eternal day is set to %s");

        addCommand("sun_altar_whitelist.enabled", "Sun Altar's whitelist is now turned on");
        addCommand("sun_altar_whitelist.disabled", "Sun Altar's whitelist is now turned off");
        addCommand("sun_altar_whitelist.none", "There are no whitelisted players");
        addCommand("sun_altar_whitelist.list", "There are %s whitelisted players: %s");
        addCommand("sun_altar_whitelist.add.success", "Added %s to the Sun Altar's whitelist");
        addCommand("sun_altar_whitelist.remove.success", "Removed %s from the Sun Altar's whitelist");
        addCommand("sun_altar_whitelist.reloaded", "Reloaded the Sun Altar's whitelist");
        addCommand("sun_altar_whitelist.alreadyOn", "Sun Altar's whitelist is already turned on");
        addCommand("sun_altar_whitelist.alreadyOff", "Sun Altar's whitelist is already turned off");
        addCommand("sun_altar_whitelist.add.failed", "Player is already whitelisted to use the Sun Altar");
        addCommand("sun_altar_whitelist.remove.failed", "Player is not whitelisted to use the Sun Altar");


        addKeyInfo("category", "Aether");
        addKeyInfo("open_accessories.desc", "Open/Close Accessories Inventory");
        addKeyInfo("gravitite_jump_ability.desc", "Activate Gravitite Jump");
        addKeyInfo("invisibility_toggle.desc", "Toggle Invisibility");


        addCuriosIdentifier("aether_pendant", "Pendant");
        addCuriosIdentifier("aether_cape", "Cape");
        addCuriosIdentifier("aether_ring", "Ring");
        addCuriosIdentifier("aether_shield", "Shield");
        addCuriosIdentifier("aether_gloves", "Gloves");
        addCuriosIdentifier("aether_accessory", "Accessory");

        addCuriosModifier("aether_pendant", "When around neck:");
        addCuriosModifier("aether_cape", "When on back:");
        addCuriosModifier("aether_ring", "When worn as ring:");
        addCuriosModifier("aether_shield", "When worn as shield:");
        addCuriosModifier("aether_gloves", "When on hands:");
        addCuriosModifier("aether_accessory", "When worn as accessory:");

        addLore(AetherItems.AECHOR_PETAL, "The petal of an Aechor Plant; they have a sweet aroma to them. These are a Moa's favorite food and can be used to feed baby Moas.");
        addLore(AetherBlocks.AEROGEL, "The result of the Aether's unique climate and lava combining. These blocks can be crafted into various decorative blocks and are blast-resistant.");
        addLore(AetherBlocks.AEROGEL_SLAB, "Crafted from Aerogel. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.AEROGEL_STAIRS, "Crafted from Aerogel. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherBlocks.AEROGEL_WALL, "Crafted from Aerogel. Can be used for decorative enclosures and defenses. Great for keeping nasty intruders away!");
        addLore(AetherBlocks.AETHER_DIRT, "A pale dirt. It can be found in the Aether and can also be used to grow native trees. It can grow into grass with enough light.");
        addLore(AetherBlocks.AETHER_DIRT_PATH, "A type of path made from flattening Aether Dirt with a Shovel. These can be good for long roads or just exterior gardens.");
        addLore(AetherItems.AETHER_PORTAL_FRAME, "A portable frame containing the power to travel to the Aether or back to the Overworld. Not something you're likely to find naturally.");
        addLore(AetherBlocks.AETHER_FARMLAND, "Farmland tilled from Aether Dirt; one of the few uses for Hoes in the Aether.");
        addLore(AetherBlocks.AETHER_GRASS_BLOCK, "Grass found in the Aether dimension. It can be used to grow trees native to the Aether. It is much more pale than normal grass.");
        addLore(AetherItems.AGILITY_CAPE, "A slightly rare Cape found in Bronze and Silver Dungeons. It makes the wearer's legs stronger, therefore they can walk up blocks instantly.");
        addLore(AetherBlocks.ALTAR, "Used to enchant items and repair armor. They are powered by Ambrosium Shards.");
        addLore(AetherBlocks.AMBROSIUM_ORE, "The most common ore in the Aether. The ore's drops can be doubled with skyroot tools.");
        addLore(AetherItems.AMBROSIUM_SHARD, "The Aether's Coal equivalent. These have the healing property of restoring a small amount of health when eaten.");
        addLore(AetherBlocks.AMBROSIUM_BLOCK, "A block of pure ambrosium. These are useful for storing large quantities of fuel.");
        addLore(AetherBlocks.AMBROSIUM_TORCH, "The main light source in the Aether, made from a Skyroot Stick and Ambrosium Shard.");
        addLore(AetherBlocks.ANGELIC_SLAB, "Crafted from Angelic Stone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.ANGELIC_STONE, "Angelic Stone is the main block that makes up Silver Dungeons. There is a chance some of them can be traps when generated in dungeons, but you cannot collect them as traps. It is unbreakable until you have defeated the boss, but it's worth it for that block.");
        addLore(AetherBlocks.ANGELIC_WALL, "Crafted from Angelic Stone. Can be used for decorative enclosures and defenses. Great for keeping nasty intruders away!");
        addLore(AetherBlocks.ANGELIC_STAIRS, "Crafted from Angelic Stone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherBlocks.BERRY_BUSH, "These bushes can be found all over the Aether. They provide Blue Berries when broken and then turn into bare stems. If placed on Enchanted Aether Grass, the drops are doubled.");
        addLore(AetherBlocks.BERRY_BUSH_STEM, "This is the result of harvesting the Blue Berries from a Berry Bush. It can take some time for them to grow back.");
        addLore(AetherItems.BLACK_MOA_EGG, "An egg laid by a Black Moa. Hatching this provides a Black Moa with 8 mid-air jumps, the best and most rare one!");
        addLore(AetherBlocks.BLUE_AERCLOUD, "A pale blue cloud found close to the ground. It has very bouncy properties and can help you reach high places.");
        addLore(AetherItems.BLUE_BERRY, "Harvested from Berry Bushes, this is the most common food source in the Aether. It has very weak hunger restoration. You can enchant these in an Altar for much better hunger restoration.");
        addLore(AetherItems.BLUE_CAPE, "A Blue Cape that has a silky feeling to it. It's crafted using Blue Wool.");
        addLore(AetherItems.BLUE_GUMMY_SWET, "A sweet-smelling gummy, it can be found in random chests in Bronze and Silver dungeons. It fully restores the player's hunger when eaten. Very useful for boss fights.");
        addLore(AetherItems.BLUE_MOA_EGG, "An egg laid by a Blue Moa. Hatching this provides a Blue Moa with 3 mid-air jumps. The most common Moa.");
        addLore(AetherItems.BOOK_OF_LORE, "A large book containing many lore entries. It describes every object in detail.");
        addLore(AetherItems.BRONZE_DUNGEON_KEY, "A dull key that is dropped from the Slider after being defeated. You can use it to claim the treasure you earned!");
        addLore(AetherItems.CANDY_CANE, "Dropped by mobs killed with a Candy Cane Sword! They can be used to repair this weapon, and they're a very tasty treat.");
        addLore(AetherItems.CANDY_CANE_SWORD, "A Sword made from decorative candy. Randomly drops Candy Canes when used. These are dropped from presents that are under Holiday Trees.");
        addLore(AetherBlocks.CARVED_SLAB, "Crafted from Carved Stone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.CARVED_STAIRS, "Crafted from Carved Stone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherBlocks.CARVED_STONE, "The stone found in Bronze Dungeons. This stone has a gray color to it and can be mined as a decorative block.");
        addLore(AetherBlocks.CARVED_WALL, "Crafted from Carved Stone. Can be used for decorative enclosures and defenses. Great for keeping nasty intruders away!");
        addLore(AetherItems.CHAINMAIL_GLOVES, "A very rare pair of Gloves, needed to complete the chain armor set.");
        addLore(AetherBlocks.CHEST_MIMIC, "It may look like a normal chest, but it really isn't. As soon as you right-click on it, a Chest Mimic will pop out! These appear in Bronze and Silver Dungeons.");
        addLore(AetherItems.CLOUD_STAFF, "A staff with a light and fluffy top. It summons small Cloud Minions to fight next to the user for a short period of time by shooting large Cloud Crystals.");
        addLore(AetherBlocks.COLD_AERCLOUD, "A cold cloud found in the skies of the Aether. It can be used to make Cold Parachutes, and break drops that would otherwise be very dangerous.");
        addLore(AetherItems.COLD_PARACHUTE, "A quickly made Parachute. It's fluffy to the touch and is made from Cold Aerclouds. It has one use.");
        addLore(AetherBlocks.CRYSTAL_FRUIT_LEAVES, "Crystal Leaves that are home to White Apples.");
        addLore(AetherBlocks.CRYSTAL_LEAVES, "Leaves that come from Crystal Trees, they generate on floating islands. Sometimes they have fruit on them, which can cure Inebriation.");
        addLore(AetherBlocks.DECORATED_HOLIDAY_LEAVES, "Holiday Leaves that have been decorated with lovely little baubles for extra holiday cheer!");
        addLore(AetherItems.DIAMOND_GLOVES, "A pair of Gloves, needed to complete the diamond armor set.");
        addLore(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, "An Aether Grass Block that has been enchanted to allow for increasing harvest rates of Blue Berries.");
        addLore(AetherItems.ENCHANTED_BERRY, "An excellent food source, it tastes quite good and restores a massive amount of hunger.");
        addLore(AetherItems.ENCHANTED_DART, "The ammo for Enchanted Dart Shooters. This is the strongest of the darts, made by enchanting Golden Darts.");
        addLore(AetherItems.ENCHANTED_DART_SHOOTER, "A Dart Shooter that shoots Enchanted Darts; the strongest one!");
        addLore(AetherBlocks.ENCHANTED_GRAVITITE, "After putting Gravitite Ore into an Altar and enchanting it, you get Enchanted Gravitite. This can be made into gravitite tools and armor. When powered with a redstone signal, Enchanted Gravitite blocks will float into the air, similarly to Gravitite Ore.");
        addLore(AetherItems.FLAMING_SWORD, "An ancient Sword which flames its foes to a burning crisp. It almost hurts to touch! Using this on animals can make them drop cooked meat!");
        addLore(AetherBlocks.FREEZER, "You can freeze various items using this, such as aerclouds or Water Buckets. Its source of power is Icestone.");
        addLore(AetherItems.GINGERBREAD_MAN, "Found in presents under Holiday Trees, these are the most common holiday item. They are very abundant when dropped from Presents.");
        addLore(AetherItems.GOLD_DUNGEON_KEY, "A key that has a shiny finish. It is dropped by the Sun Spirit after you defeat him. You can use it to claim the legendary treasure in the back room!");
        addLore(AetherBlocks.GOLDEN_AERCLOUD, "A golden cloud found in small quantities, higher in the air than normal. The properties are similar to Cold Aerclouds, but the Golden Parachutes they produce have much more durability.");
        addLore(AetherItems.GOLDEN_AMBER, "These round golden orbs can be obtained by mining Golden Oak Logs with a Zanite or Gravitite Axe. Their main purpose is to craft Golden Darts and Dart Shooters.");
        addLore(AetherItems.GOLDEN_GUMMY_SWET, "A sour-tasting gummy, it can be found in random chests in Bronze and Silver Dungeons. It fully restores the player's hunger when eaten. Very useful for boss fights.");
        addLore(AetherItems.GOLDEN_DART, "The ammo for Golden Dart Shooters. Crafted with Skyroot Sticks and Golden Amber, enchanting these converts them to Enchanted Darts.");
        addLore(AetherItems.GOLDEN_DART_SHOOTER, "A Dart Shooter that shoots Golden Darts; enchanting it on an Altar improves its attack power!");
        addLore(AetherItems.GOLDEN_FEATHER, "A fluffy feather found in Silver Dungeons. When worn, the wearer becomes lighter than air and can descend slowly.");
        addLore(AetherItems.GOLDEN_GLOVES, "A pair of Gloves, needed to complete the golden armor set.");
        addLore(AetherBlocks.GOLDEN_OAK_LEAVES, "These golden leaves generate with Golden Oak Trees. They spawn golden particles in a radius of 5 blocks. They yield Golden Oak Saplings when decayed.");
        addLore(AetherBlocks.GOLDEN_OAK_LOG, "A Skyroot Log which contains Golden Amber inside. When broken with an Axe, they drop Skyroot Logs, and if the Axe is Zanite or Gravitite, they will drop Golden Amber.");
        addLore(AetherBlocks.GOLDEN_OAK_SAPLING, "These large saplings, when planted, will grow into huge Golden Oak Trees! You can use Bone Meal to speed up the process.");
        addLore(AetherBlocks.GOLDEN_OAK_WOOD, "A Skyroot Log which contains Golden Amber Inside. When broken with an Axe, they drop Skyroot Logs, and if the Axe is Zanite or Gravitite, they will drop Golden Amber. Crafted to have bark on all sides.");
        addLore(AetherItems.GOLDEN_PARACHUTE, "The best Parachute in the Aether. It has 20 uses and is made with Golden Aerclouds.");
        addLore(AetherItems.GOLDEN_PENDANT, "An aesthetic accessory made of gold.");
        addLore(AetherItems.GOLDEN_RING, "An aesthetic accessory made of gold.");
        addLore(AetherItems.GRAVITITE_AXE, "Part of the Aether's best tool tier. This Axe not only makes wood blocks float, but it can mine Golden Oak Logs for Golden Amber!");
        addLore(AetherItems.GRAVITITE_BOOTS, "Part of the Aether's best armor set. When the full set is worn, you get an extra high jump!");
        addLore(AetherItems.GRAVITITE_CHESTPLATE, "Part of the Aether's best armor set. When the full set is worn, you get an extra high jump!");
        addLore(AetherItems.GRAVITITE_GLOVES, "Part of the Aether's best armor set, needed to complete the gravitite set.");
        addLore(AetherItems.GRAVITITE_HELMET, "Part of the Aether's best armor set. When the full set is worn, you get an extra high jump!");
        addLore(AetherItems.GRAVITITE_LEGGINGS, "Part of the Aether's best armor set. When the full set is worn, you get an extra high jump!");
        addLore(AetherBlocks.GRAVITITE_ORE, "This is the Aether's rarest ore. It has floating properties and will float upward when there is nothing above it. These can be enchanted into Enchanted Gravitite blocks.");
        addLore(AetherItems.GRAVITITE_PICKAXE, "Part of the Aether's best tool tier. When mining with this powerful tool, you can right-click on any stone block, and it will levitate into the air! You can mine any ore with this Pickaxe.");
        addLore(AetherItems.GRAVITITE_SHOVEL, "Part of the Aether's best tool tier. This Shovel has the special ability to make dirt blocks or sand levitate! When combined with enchantments such as Efficiency, it will instantly break Aether Dirt!");
        addLore(AetherItems.GRAVITITE_SWORD, "Part of the Aether's best tool tier. When attacking with this powerful weapon, anything you hit will be flung into the air, causing lots of damage!");
        addLore(AetherItems.GRAVITITE_HOE, "Part of the Aether's best tool tier. This Hoe not only can make weirdly specific blocks float, but it can also till dirt, which totally justifies its existence.");
        addLore(AetherItems.HEALING_STONE, "Obtained from enchanting Holystone, it can be used as a reliable healing source, providing Regeneration. It has a surprisingly juicy flavor.");
        addLore(AetherBlocks.HELLFIRE_SLAB, "Crafted from Hellfire Stone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.HELLFIRE_STONE, "A warm, red stone that makes up 90% of the Gold Dungeon's interior. It cannot be destroyed until the Sun Spirit is defeated.");
        addLore(AetherBlocks.HELLFIRE_WALL, "Crafted from Hellfire Stone. Can be used for decorative enclosures and defenses. Great for keeping nasty intruders away!");
        addLore(AetherBlocks.HELLFIRE_STAIRS, "Crafted from Hellfire Stone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherBlocks.HOLIDAY_LEAVES, "Leaves on Holiday Trees! It snows near them, and there are always Presents nearby!");
        addLore(AetherItems.HOLY_SWORD, "An ancient Sword which does heavy amounts of damage to undead foes. A perfect weapon for traversing the Overworld.");
        addLore(AetherBlocks.HOLYSTONE, "The Aether's native rock. It can be used in various ways, such as tool creation, construction, and being able to be crafted into Holystone Bricks.");
        addLore(AetherItems.HOLYSTONE_AXE, "One of Aether's stone tools. It mines faster than skyroot tools, as well as dropping random Ambrosium Shards.");
        addLore(AetherBlocks.HOLYSTONE_BRICKS, "Used as a building material native to the Aether. It is made from Holystone and is sturdier than it too.");
        addLore(AetherBlocks.HOLYSTONE_BRICK_SLAB, "Crafted from Holystone Bricks. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.HOLYSTONE_BRICK_STAIRS, "Crafted from Holystone Bricks. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherBlocks.HOLYSTONE_BRICK_WALL, "Crafted from Holystone Bricks. Can be used for decorative enclosures and defenses. Great for keeping nasty intruders away!");
        addLore(AetherBlocks.HOLYSTONE_BUTTON, "Crafted from Holystone, a Button used to activate mechanisms and redstone.");
        addLore(AetherItems.HOLYSTONE_HOE, "One of the Aether's stone tools. It has more durability than the Skyroot Hoe, as well as dropping random Ambrosium Shards. Which makes it more useful than most Hoes.");
        addLore(AetherItems.HOLYSTONE_PICKAXE, "One of the Aether's stone tools. It can mine Zanite Ore and will randomly drop Ambrosium Shards while mining.");
        addLore(AetherBlocks.HOLYSTONE_PRESSURE_PLATE, "Crafted from Holystone, a Pressure Plate used to activate mechanisms and redstone.");
        addLore(AetherItems.HOLYSTONE_SHOVEL, "One of the Aether's stone tools. It can mine Quicksoil and all other blocks a Stone Shovel can mine, except it drops random Ambrosium Shards.");
        addLore(AetherBlocks.HOLYSTONE_SLAB, "Crafted from Holystone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.HOLYSTONE_STAIRS, "Crafted from Holystone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherItems.HOLYSTONE_SWORD, "One of the Aether's stone tools. It can drop Ambrosium Shards at random while attacking.");
        addLore(AetherBlocks.HOLYSTONE_WALL, "Crafted from Holystone. Can be used for decorative enclosures and defenses. Great for keeping nasty intruders away!");
        addLore(AetherItems.ICE_PENDANT, "A Pendant which allows you to freeze water and lava when walked on.");
        addLore(AetherItems.ICE_RING, "A Ring which allows you to freeze water and lava when walked on.");
        addLore(AetherBlocks.ICESTONE, "Icestone is a common ore that can be used as fuel for a Freezer. It can be used to freeze nearby liquids such as lava and water.");
        addLore(AetherBlocks.ICESTONE_SLAB, "Crafted from Icestone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.ICESTONE_STAIRS, "Crafted from Icestone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherBlocks.ICESTONE_WALL, "Crafted from Icestone. Can be used for decorative enclosures and defenses. Great for keeping nasty intruders away!");
        addLore(AetherBlocks.INCUBATOR, "Used to incubate Moas. You use Ambrosium Torches for the fuel. The process can take quite some time, but it is worth the wait once your Moa of choice hatches!");
        addLore(AetherItems.INVISIBILITY_CLOAK, "A cloak that makes the wearer completely invisible! Since mobs cannot see you, they cannot attack you. Sneak up on your enemies with it!");
        addLore(AetherItems.IRON_BUBBLE, "A common dungeon loot item. It allows for the wearer to breathe underwater indefinitely.");
        addLore(AetherItems.IRON_GLOVES, "A pair of Gloves, needed to complete the iron armor set.");
        addLore(AetherItems.IRON_PENDANT, "An aesthetic accessory made of iron.");
        addLore(AetherItems.IRON_RING, "An aesthetic accessory made of iron.");
        addLore(AetherItems.LEATHER_GLOVES, "Dyeable Gloves to match your Leather Tunic! This is needed to complete the leather armor set.");
        addLore(AetherItems.LIFE_SHARD, "A very rare item found in Gold Dungeons. Using this will give you an extra permanent heart! The feeling of using it is very strange and hard to describe.");
        addLore(AetherBlocks.LIGHT_ANGELIC_STONE, "The light version of Angelic Stone. It is less common than Angelic Stone, but it looks really nice as a decorative block.");
        addLore(AetherBlocks.LIGHT_HELLFIRE_STONE, "The light version of Hellfire Stone. It can be found in Gold Dungeons along with Hellfire Stone, but like its counterpart, it cannot be collected until the boss is defeated.");
        addLore(AetherItems.LIGHTNING_KNIFE, "Small knives that, when thrown, summon lightning bolts where they land.");
        addLore(AetherItems.LIGHTNING_SWORD, "An ancient Sword which summons lightning to its foes.");
        addLore(AetherBlocks.MOSSY_HOLYSTONE, "A more aged Holystone, it is found near dungeons and has pale colored vines growing on it; very decorative.");
        addLore(AetherBlocks.MOSSY_HOLYSTONE_SLAB, "Crafted from Mossy Holystone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, "Crafted from Mossy Holystone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherBlocks.MOSSY_HOLYSTONE_WALL, "Crafted from Mossy Holystone. Can be used for decorative enclosures and defenses. Great for keeping nasty intruders away!");
        addLore(AetherItems.NATURE_STAFF, "A staff that can allow for commanding tamed Moas. It can allow them to sit on the ground.");
        addLore(AetherItems.NEPTUNE_BOOTS, "Found in Silver Dungeons, this armor allows for faster swimming in water. The full set is a much better replacement for the Depth Strider enchantment.");
        addLore(AetherItems.NEPTUNE_CHESTPLATE, "Found in Silver Dungeons, this armor allows for faster swimming in water. Combined with an Iron Bubble, this armor set is super useful for defeating underwater temples.");
        addLore(AetherItems.NEPTUNE_GLOVES, "Found in Silver Dungeons, these gloves are required to complete the Neptune armor set, which allows for underwater walking.");
        addLore(AetherItems.NEPTUNE_HELMET, "Found in Silver Dungeons, this armor allows faster swimming in water. Combined with an Iron Bubble, this armor set is super useful for defeating underwater temples.");
        addLore(AetherItems.NEPTUNE_LEGGINGS, "Found in Silver Dungeons, this armor allows faster swimming in water. Combined with an Iron Bubble, this armor set is super useful for defeating underwater temples.");
        addLore(AetherItems.NETHERITE_GLOVES, "A pair of Gloves, needed to complete the Netherite armor set.");
        addLore(AetherItems.HAMMER_OF_KINGBDOGZ, "A mighty hammer that shoots heavy projectiles at mobs. It's said that Kingbdogz actually held this hammer.");
        addLore(AetherItems.OBSIDIAN_BOOTS, "A super powerful piece of armor, more powerful than diamond. This armor is only obtainable by standing in water while wearing Phoenix armor, converting it to obsidian.");
        addLore(AetherItems.OBSIDIAN_CHESTPLATE, "A super powerful piece of armor, more powerful than diamond. This armor is only obtainable by standing in water while wearing Phoenix armor, converting it to obsidian.");
        addLore(AetherItems.OBSIDIAN_GLOVES, "Needed to complete the obsidian armor set, you can obtain these by standing in water while wearing Phoenix Gloves.");
        addLore(AetherItems.OBSIDIAN_HELMET, "A super powerful piece of armor, more powerful than diamond. This armor is only obtainable by standing in water while wearing Phoenix armor, converting it to obsidian.");
        addLore(AetherItems.OBSIDIAN_LEGGINGS, "A super powerful piece of armor, more powerful than diamond. This armor is only obtainable by standing in water while wearing Phoenix armor, converting it to obsidian.");
        addLore(AetherItems.PHOENIX_BOOTS, "Found in Gold Dungeons, when fully worn, this armor set allows for fire resistance; you can swim in lava as well. Be careful when standing in water, as it will turn into obsidian armor.");
        addLore(AetherItems.PHOENIX_BOW, "Found as dungeon loot, this bow is very heated and can shoot flaming Arrows! It's warm to the touch.");
        addLore(AetherItems.PHOENIX_CHESTPLATE, "Found in Gold Dungeons, when fully worn, this armor set allows for fire resistance; you can swim in lava as well. Be careful when standing in water, as it will turn into obsidian armor.");
        addLore(AetherItems.PHOENIX_GLOVES, "Found in Gold Dungeons, these Gloves are required to finish the Phoenix armor set. If you stand in water for long enough, they will turn into Obsidian Gloves.");
        addLore(AetherItems.PHOENIX_HELMET, "Found in Gold Dungeons, when fully worn, this armor set allows for fire resistance; you can swim in lava as well. Be careful when standing in water, as it will turn into obsidian armor.");
        addLore(AetherItems.PHOENIX_LEGGINGS, "Found in Gold Dungeons, when fully worn, this armor set allows for fire resistance; you can swim in lava as well. Be careful when standing in water, as it will turn into obsidian armor.");
        addLore(AetherItems.PIG_SLAYER, "Kills any Pig-type mob with nothing with a single blow, or stronger ones with a couple blows. But why would you want to do that? Great for traversing the Nether.");
        addLore(AetherBlocks.PILLAR, "The main part of the Silver Dungeon's decorative pillars; they spawn all around the dungeon and are excellent for building.");
        addLore(AetherBlocks.PILLAR_TOP, "The top of the Silver Dungeon's decorative pillars; they look excellent and are great for building.");
        addLore(AetherItems.POISON_DART, "The ammo for Poison Dart Shooters, these are made by coating Golden Darts with poison!");
        addLore(AetherItems.POISON_DART_SHOOTER, "A Dart Shooter that shoots Poison Darts. Shooting this at something infects them with a deadly poison!");
        addLore(AetherBlocks.PRESENT, "A wonderful holiday gift, open it for a surprise! Be careful, as there's a chance you could get ssssssurprised!");
        addLore(AetherBlocks.PURPLE_FLOWER, "These pretty violet flowers can be found in large numbers around the Aether. They can be crafted into Purple Dye when placed in a Crafting Table.");
        addLore(AetherBlocks.QUICKSOIL, "A silky sand with extremely slippery properties. It can be seen floating on the side of the Aether's islands. Be careful around it, or you'll fall off.");
        addLore(AetherBlocks.QUICKSOIL_GLASS, "After enchanting Quicksoil, you can get this tinted glass. It is slightly slippery and makes for a great window.");
        addLore(AetherBlocks.QUICKSOIL_GLASS_PANE, "A thin decorative variant of Quicksoil Glass. It is slightly slippery and makes for a great window.");
        addLore(AetherItems.RED_CAPE, "A rough-feeling Cape that is crafted using Red Wool.");
        addLore(AetherItems.REGENERATION_STONE, "A treasure found in Silver Dungeons. This stone makes the wearer feel healthy and heals their wounds. The effects can stack with other Regeneration Stones.");
        addLore(AetherItems.SHIELD_OF_REPULSION, "A shield that protects the user from most projectiles. It will reflect the projectile back at the thrower and cause damage to them. Each time a projectile is reflected, the shield will be damaged.");
        addLore(AetherItems.SENTRY_BOOTS, "Found in Bronze Dungeons, these Boots protect you from fall damage, allowing you to fall from great heights.");
        addLore(AetherBlocks.SENTRY_STONE, "The light version of Carved Stone, also found in Bronze Dungeons.");
        addLore(AetherItems.SILVER_DUNGEON_KEY, "A reflective key that is given to you by the Valkyrie Queen after defeating her. Use it to claim the treasure she left behind!");
        addLore(AetherItems.SKYROOT_AXE, "One of the Aether's wooden tools. It can be used to double Skyroot Log drops. Simple but useful for beginners.");
        addLore(AetherItems.SKYROOT_AXOLOTL_BUCKET,"A Skyroot Bucket with a friendly Axolotl in it. Axolotls can be incredibly helpful aquatic partners. So, it's best to keep one on hand if you're exploring wet environments.");
        addLore(AetherBlocks.SKYROOT_BED, "Crafted with wool and Skyroot Planks. You can only sleep at night to skip nighttime, but during the day you can set your respawn point in the Aether. You cannot sleep while monsters are nearby.");
        addLore(AetherItems.SKYROOT_BOAT, "While the Aether does not have many large bodies of water, a Boat can occasionally be a useful tool for crossing large distances over ice!");
        addLore(AetherItems.SKYROOT_CHEST_BOAT, "A Skyroot Boat with a handy Chest in the back. It is useful for transporting more items over long stretches of water, which are famously difficult to find in the Aether.");
        addLore(AetherBlocks.SKYROOT_BOOKSHELF, "Crafted from Skyroot Planks and Books. Bookshelves can be used to enhance the enchanting capabilities of an Enchanting Table.");
        addLore(AetherItems.SKYROOT_BUCKET, "A handcrafted bucket used to contain Aechor Plant poison, a nice remedy, or even just plain water!");
        addLore(AetherBlocks.SKYROOT_BUTTON, "Crafted from Skyroot Planks, a Button used to activate mechanisms and redstone.");
        addLore(AetherItems.SKYROOT_COD_BUCKET, "A bucket with a Cod in it! Make sure to keep the water topped up or your new friend won't survive the journey home.");
        addLore(AetherBlocks.SKYROOT_DOOR, "Crafted from Skyroot Planks, an ornate Door helpful for keeping an enclosed and safe space without worry of monsters wandering in.");
        addLore(AetherBlocks.SKYROOT_FENCE, "Crafted from Skyroot Planks and Sticks. Great for keeping your livestock safe from wandering predators!");
        addLore(AetherBlocks.SKYROOT_FENCE_GATE, "Crafted from Sticks and Skyroot Planks. Gives a homely entrance and exit to your precious enclosures.");
        addLore(AetherItems.SKYROOT_HOE, "One of the Aether's wooden tools, used to till dirt to allow for the planting of crops. Famously underappreciated.");
        addLore(AetherBlocks.SKYROOT_LEAVES, "These leaves generate with Skyroot Trees. They can drop Skyroot Saplings when decaying.");
        addLore(AetherBlocks.SKYROOT_LOG, "These spawn with Skyroot Trees. They can be double-dropped with Skyroot Axes. When put in a Crafting Table, they will provide 4 Skyroot Planks.");
        addLore(AetherBlocks.SKYROOT_WOOD, "Skyroot Logs crafted to be smooth on all sides. When put in a Crafting Table, they will provide 4 Skyroot Planks.");
        addLore(AetherItems.SKYROOT_MILK_BUCKET, "A bucket full of fresh milk; drink it to heal potion effects.");
        addLore(AetherItems.SKYROOT_PICKAXE, "One of the Aether's wooden tools. When mining Holystone or Ambrosium Ore, the drops will be doubled. This is even better when combined with an enchantment such as Fortune when mining Ambrosium Shards!");
        addLore(AetherBlocks.SKYROOT_PLANKS, "Skyroot Planks can be made into various tools, blocks, and items. They are crafted from Skyroot Logs and make a great building material.");
        addLore(AetherBlocks.SKYROOT_PRESSURE_PLATE, "Crafted from Skyroot Planks. A wooden Pressure Plate used to activate mechanisms and redstone.");
        addLore(AetherItems.SKYROOT_POISON_BUCKET, "A Skyroot Bucket that has been filled with a deadly poison. Better not drink it! It can be used to craft Poison Darts and Dart Shooters. Enchant it to cure the poison in it.");
        addLore(AetherItems.SKYROOT_POWDER_SNOW_BUCKET, "A Skyroot Bucket that has been filled with Powder Snow. With quick reactions, it can be used to break the deadliest of falls, even in the Nether!");
        addLore(AetherItems.SKYROOT_PUFFERFISH_BUCKET, "A Skyroot Bucket with a Pufferfish inside. This item serves as a useful way to transport the defensive and poisonous Pufferfish.");
        addLore(AetherItems.SKYROOT_REMEDY_BUCKET, "A Skyroot Bucket containing a soothing remedy. It has a strong smell. Drinking this will cure deadly poison.");
        addLore(AetherItems.SKYROOT_SALMON_BUCKET, "A Skyroot Bucket containing a Salmon. Useful for transporting live Salmon over long stretches of land.");
        addLore(AetherBlocks.SKYROOT_SAPLING, "These small green saplings will grow into Skyroot Trees. They can be grown faster with Bone Meal.");
        addLore(AetherItems.SKYROOT_SHOVEL, "One of the Aether's wooden tools. It doubles Aether Dirt and Quicksoil drops.");
        addLore(AetherBlocks.SKYROOT_SIGN, "Crafted from Skyroot Planks and Sticks. A helpful sign perfect for writing messages and directions on.");
        addLore(AetherBlocks.SKYROOT_SLAB, "Crafted from Skyroot Planks. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addLore(AetherBlocks.SKYROOT_STAIRS, "Crafted from Skyroot Planks. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addLore(AetherItems.SKYROOT_STICK, "Crafted from Skyroot Planks. Used to create various tools and items. There's nothing else too special about it.");
        addLore(AetherItems.SKYROOT_SWORD, "One of the Aether's wooden tools. It has a low amount of durability and doubles mob drops. It makes an excellent weapon for collecting food when combined with enchantments such as Looting and Fire Aspect.");
        addLore(AetherItems.SKYROOT_TADPOLE_BUCKET, "A Skyroot Bucket with a Tadpole inside. Transporting a Tadpole to a new biome can change what type of Frog it grows up into!");
        addLore(AetherBlocks.SKYROOT_TRAPDOOR, "Crafted from Skyroot Planks. A Trapdoor is useful for covering one block wide entryways. Often used to add extra protection to staircases.");
        addLore(AetherItems.SKYROOT_TROPICAL_FISH_BUCKET,"A Skyroot Bucket containing a Tropical Fish. A helpful way to transport and catalog the numerous varieties of Tropical Fish.");
        addLore(AetherItems.SKYROOT_WATER_BUCKET, "A Skyroot Bucket that is filled to the brim with water.");
        addLore(AetherBlocks.STRIPPED_SKYROOT_LOG, "A Skyroot Log that has had its bark stripped away with an Axe. When put in a Crafting Table, they will provide 4 Skyroot Planks.");
        addLore(AetherBlocks.STRIPPED_SKYROOT_WOOD, "Stripped Skyroot Logs crafted to be smooth on all sides. When put in a Crafting Table, they will provide 4 Skyroot Planks.");
        addLore(AetherBlocks.SUN_ALTAR, "An altar containing the power to control the sun itself! It is dropped by the Sun Spirit after you defeat him. Use it to control the time of day.");
        addLore(AetherItems.SWET_CAPE, "A common Cape that is found in Bronze Dungeons. It allows for the wearer to ride Swets, as they become friendly when they see someone wearing it.");
        addLore(AetherItems.SWET_BALL, "A gooey orb that is dropped from Blue Swets. It can be used to fertilize soil. Another use is to put it alongside string to make a lead.");
        addLore(AetherBlocks.TREASURE_CHEST, "A Treasure Chest, these are found after defeating a Bronze, Silver, or Gold dungeon. They can be opened with the key from the dungeon boss.");
        addLore(AetherItems.VALKYRIE_AXE, "A tool unique to the Silver Dungeon. This Axe has a very far reach and very high attack power. You can use this to your advantage, but be warned, the attack cooldown is very high.");
        addLore(AetherItems.VALKYRIE_BOOTS, "An armor unique to the Silver Dungeon. When fully worn, you are granted temporary flight. Quite useful for getting to high places.");
        addLore(AetherItems.VALKYRIE_CAPE, "A rare Cape that is found in Silver Dungeons. When worn, the wearer becomes lighter than air and can descend slowly.");
        addLore(AetherItems.VALKYRIE_CHESTPLATE, "An armor unique to the Silver Dungeon. When fully worn, you are granted temporary flight. Quite useful for getting to high places.");
        addLore(AetherItems.VALKYRIE_GLOVES, "An armor unique to the Silver Dungeon. The Gloves are needed to complete the armor set, which grants temporary flight.");
        addLore(AetherItems.VALKYRIE_HELMET, "An armor unique to the Silver Dungeon. When fully worn, you are granted temporary flight. Quite useful for getting to high places.");
        addLore(AetherItems.VALKYRIE_HOE, "A tool unique to the Silver Dungeon. This Hoe has an incredibly far reach, allowing you to pointlessly till dirt from a safe distance.");
        addLore(AetherItems.VALKYRIE_LANCE, "A tool unique to the Bronze Dungeon. This long-range weapon is very good for defeating Zephyrs and Valkyrie Queens.");
        addLore(AetherItems.VALKYRIE_LEGGINGS, "An armor unique to the Silver Dungeon. When fully worn, you are granted temporary flight. Quite useful for getting to high places.");
        addLore(AetherItems.VALKYRIE_PICKAXE, "A tool unique to the Silver Dungeon. This Pickaxe is very useful when it comes to mining blocks from under islands, as it has a very far reach, almost double the normal reach! It is also quite useful for fighting the Slider.");
        addLore(AetherItems.VALKYRIE_SHOVEL, "A tool unique to the Silver Dungeon. This Shovel can help you reach Quicksoil from a safer distance; quite useful wouldn't you say?");
        addLore(AetherItems.VAMPIRE_BLADE, "A mysterious Sword that has life-stealing abilities. Holding it just makes you feel creepily empty...");
        addLore(AetherItems.VICTORY_MEDAL, "Proof of defeating a lesser Valkyrie. Use these to prove to the Valkyrie Queen you are worthy enough to fight her!");
        addLore(AetherItems.WHITE_APPLE, "One of the only known cures for the Aether's deadly poison. You can find them on Crystal Trees.");
        addLore(AetherItems.WHITE_CAPE, "A light and fluffy Cape that is made from White Wool.");
        addLore(AetherBlocks.WHITE_FLOWER, "These extremely good-smelling roses can make great gifts to a friend or loved one. They spawn in large groups around the Aether.");
        addLore(AetherItems.WHITE_MOA_EGG, "An egg laid by a White Moa. Hatching this provides a White Moa with 4 mid-air jumps. This Moa is decently rare.");
        addLore(AetherItems.YELLOW_CAPE, "A bright Yellow Cape that is crafted using Yellow Wool.");
        addLore(AetherItems.ZANITE_AXE, "One of the Aether's mid-tier tools, you can mine wood faster than stone, and it will get even faster as the tool is worn down.");
        addLore(AetherBlocks.ZANITE_BLOCK, "A block of compacted Zanite Gemstones. They can power beacons and have a sleek look to them.");
        addLore(AetherItems.ZANITE_BOOTS, "Part of the zanite armor set, equivalent in protection to iron.");
        addLore(AetherItems.ZANITE_CHESTPLATE, "Part of the zanite armor set, equivalent in protection to iron.");
        addLore(AetherItems.ZANITE_GEMSTONE, "The Aether's version of iron. These shiny purple gems can be made into tools and armor that increase in strength when used.");
        addLore(AetherItems.ZANITE_GLOVES, "A pair of Gloves, needed to complete the zanite armor set.");
        addLore(AetherItems.ZANITE_HOE, "One of the Aether's mid-tier tools, it has more durability than the Holystone Hoe, and it will get even faster at mining very oddly specific blocks as the tool is worn down.");
        addLore(AetherItems.ZANITE_HELMET, "Part of the zanite armor set, equivalent in protection to iron.");
        addLore(AetherItems.ZANITE_LEGGINGS, "Part of the zanite armor set, equivalent in protection to iron.");
        addLore(AetherBlocks.ZANITE_ORE, "A slightly rare ore that drops Zanite Gemstones when broken with stone tools.");
        addLore(AetherItems.ZANITE_PENDANT, "A Pendant that allows you to mine faster. It doesn't last for long, so use it wisely!");
        addLore(AetherItems.ZANITE_PICKAXE, "One of the Aether's mid-tier tools, it mines what iron can, except as its durability decreases, it gets faster. Be careful though, as when it's repaired it will be as slow as before!");
        addLore(AetherItems.ZANITE_RING, "A Ring which allows you to mine faster. It doesn't last for long, so use it wisely!");
        addLore(AetherItems.ZANITE_SHOVEL, "One of the Aether's mid-tier tools, as you mine Aether Dirt and Quicksoil, the durability will decrease and the Shovel will get faster. If you wait long enough, it will start breaking blocks instantly! Be careful repairing it, as it will be slower when there is more durability.");
        addLore(AetherItems.ZANITE_SWORD, "One of the Aether's mid-tier tools, the attack damage on this Sword starts at iron level and will increase up to twice as strong as it loses durability.");
        addLore(AetherItems.MUSIC_DISC_AETHER_TUNE, "A music disc that plays \"Aether Tune\" by Noisestorm.");
        addLore(AetherItems.MUSIC_DISC_ASCENDING_DAWN, "A music disc that plays \"Ascending Dawn\" by Emile van Krieken.");
        addLore(AetherItems.MUSIC_DISC_CHINCHILLA,"A music disc that plays \"chinchilla\" by RENREN.");
        addLore(AetherItems.MUSIC_DISC_HIGH,"A music disc that plays \"high\" by RENREN.");
        addLoreUnique("item.aether.hammer_of_jeb", "A mysterious hammer that can shoot projectiles. At one point, it vanished from existence. It's said this was actually Jeb's hammer at one point.");


        addProTip("skyroot_tool_ability", "Skyroot tools gain double drops from blocks.");
        addProTip("holystone_tool_ability", "Holystone tools occasionally generate Ambrosium Shards.");
        addProTip("zanite_tool_ability", "Zanite tools gain strength the more they are used.");
        addProTip("gravitite_tool_ability", "Gravitite tools can levitate blocks when right-clicking.");
        addProTip("white_aerclouds", "Cold Aerclouds prevent fall damage when landed upon.");
        addProTip("blue_aerclouds", "Blue Aerclouds are bouncy, and launch mobs very high in the air.");
        addProTip("phyg_saddle", "Placing a Saddle on Flying Pigs makes them a mount.");
        addProTip("moa_egg_incubation", "Moa Eggs can be incubated into baby Moas with the Incubator.");
        addProTip("gravitite_ore_enchanting", "Gravitite Ore can be enchanted into Enchanted Gravitite.");
        addProTip("enchanted_gravitite_floating", "Enchanted Gravitite only floats up when powered.");
        addProTip("enchanted_gravitite_crafting", "Enchanted Gravitite can be crafted into armor and tools.");
        addProTip("gravitite_armour_ability","Gravitite armor grants you higher jumps and no fall damage.");
        addProTip("moa_nature_staff", "Baby Moas will stay put when right clicking them with a Nature Staff.");
        addProTip("golden_oak_amber", "Golden Oak Trees yield valuable Golden Amber in their logs.");
        addProTip("altar_repairing", "Altars can repair damaged items and enhance existing items.");
        addProTip("glowstone_portal_forming", "Place water into a Glowstone frame for a Hostile Paradise.");
        addProTip("champs", "The Champs and Champettes are pretty baller.");
        addProTip("ambrosium_shard_fuel", "Ambrosium Shards are great for fueling Altars.");
        addProTip("icestone_freezing_blocks", "Icestone freezes water into Ice and lava into Obsidian.");
        addProTip("mimic_chest", "Some dungeons in the Aether contain Chests which are Chest Mimics.");
        addProTip("cold_parachute_crafting", "Cold Parachutes can be crafted with 4 Cold Aercloud blocks.");
        addProTip("parachute_activation", "Parachutes auto-activate when falling off an island.");
        addProTip("golden_parachute_durability", "Golden Parachutes have 20 uses rather than one.");
        addProTip("aerogel_explosion_resistance", "Aerogel acts as an explosion-resistant, transparent block.");
        addProTip("quicksoil_sliding", "Quicksoil increases the speed of walking mobs and sliding items.");
        addProTip("dungeon_rewards", "Dungeons can contain extremely powerful and unique rewards.");
        addProTip("dungeon_tiers", "Dungeons have various difficulties: from Bronze, Silver, to Gold.");
        addProTip("enchant_blue_disk", "Normal music discs can be enchanted into a blue version.");
        addProTip("harvest_aechor_poison", "You can harvest Aechor Plant's poison with Skyroot Buckets.");
        addProTip("darts_no_gravity", "Golden, Poison, and Enchanted Darts are not affected by gravity.");
        addProTip("dart_shooter_crafting", "Dart Shooters can be crafted with Skyroot Planks and Golden Amber.");
        addProTip("remedy_bucket_enchanting", "A Skyroot Remedy Bucket for poison can be obtained by enchanting poison.");
        addProTip("phoenix_armor_submerging", "Try submerging yourself in water while wearing Phoenix armor.");
        addProTip("zephyr_shooting", "Zephyrs shoot Snowballs with a force that can throw you off islands.");
        addProTip("aether_day_length", "Days in the Aether realm last 3 times longer than surface days.");
        addProTip("sheepuff_puff", "Sheepuff occasionally puff their wool out, making them float.");
        addProTip("victory_medal_drop", "When Valkyries are defeated they will drop a Victory Medal.");
        addProTip("dig_straight_down", "Never dig straight down.");
        addProTip("drops", "If a drop looks too big, it probably is.");
        addProTip("shelter", "A shelter made of dirt is still a shelter.");
        addProTip("creepers", "Don't fear the Creeper.");
        addProTip("close_door", "Don't leave your house without closing the door.");
        addProTip("watch_your_step", "Watch where you step - deep shafts can be anywhere.");
        addProTip("check_surroundings", "Always check your surroundings before entering a fight.");
        addProTip("mining", "You can always come back later and mine some more.");
        addProTip("spare_stack", "Always keep a spare stack of blocks in your active inventory.");
        addProTip("raw_meat", "Raw meat is better than no meat.");
        addProTip("risk_taking", "Don't take huge risks far from home.");
        addProTip("do_things", "The best time to do anything is before it's too late.");
        addProTip("expectations", "Never expect that nothing will happen while you're gone.");
        addProTip("respect", "Always treat modders with respect.");
        addProTip("slimes", "Slimes do exist... I think.");
        addProTip("security", "Only give away your personal information to PayPal.");
        addProTip("difficulty", "Easier rarely means funner.");
        addProTip("portal_misclick", "Be careful not to misclick when building portals.");
        addProTip("the_game", "The game is only as fun as you make it.");
        addProTip("golden_apples", "The leaves of Golden Oak trees occasionally drop Golden Apples.");


        addServerConfig("gameplay", "enable_bed_explosions", "Vanilla's beds will explode in the Aether");
        addServerConfig("gameplay", "tools_debuff", "Tools that aren't from the Aether will mine Aether blocks slower than tools that are from the Aether");
        addServerConfig("gameplay", "edible_ambrosium", "Ambrosium Shards can be eaten to restore a half heart of health");
        addServerConfig("gameplay", "healing_gummy_swets", "Gummy Swets when eaten restore full health instead of full hunger");
        addServerConfig("gameplay", "maximum_life_shards", "Determines the limit of the amount of Life Shards a player can consume to increase their health");
        addServerConfig("gameplay", "hammer_of_kingbdogz_cooldown", "Determines the cooldown in ticks for the Hammer of Kingbdogz's ability");
        addServerConfig("gameplay", "cloud_staff_cooldown", "Determines the cooldown in ticks for the Cloud Staff's ability");

        addServerConfig("loot", "spawn_golden_feather", "Allows the Golden Feather to spawn in the Silver Dungeon loot table");
        addServerConfig("loot", "spawn_valkyrie_cape", "Allows the Valkyrie Cape to spawn in the Silver Dungeon loot table");

        addServerConfig("world_generation", "generate_tall_grass", "Determines whether the Aether should generate Tall Grass blocks on terrain or not");
        addServerConfig("world_generation", "generate_holiday_tree_always", "Determines whether Holiday Trees should always be able to generate when exploring new chunks in the Aether, if true, this overrides 'Generate Holiday Trees seasonally'");
        addServerConfig("world_generation", "generate_holiday_tree_seasonally", "Determines whether Holiday Trees should be able to generate during the time frame of December and January when exploring new chunks in the Aether, only works if 'Generate Holiday Trees always' is set to false");

        addServerConfig("multiplayer", "balance_invisibility_cloak", "Makes the Invisibility Cloak more balanced in PVP by disabling equipment invisibility temporarily after attacks");
        addServerConfig("multiplayer", "invisibility_visibility_time", "Sets the time in ticks that it takes for the player to become fully invisible again after attacking when wearing an Invisibility Cloak; only works with 'Balance Invisibility Cloak for PVP'");
        addServerConfig("multiplayer", "sun_altar_whitelist", "Makes it so that only whitelisted users or anyone with permission level 4 can use the Sun Altar on a server");

        addServerConfig("modpack", "spawn_in_aether", "Spawns the player in the Aether dimension; this is best enabled alongside other modpack configuration to avoid issues");
        addServerConfig("modpack", "disable_aether_portal", "Prevents the Aether Portal from being created normally in the mod");
        addServerConfig("modpack", "disable_falling_to_overworld", "Prevents the player from falling back to the Overworld when they fall out of the Aether");
        addServerConfig("modpack", "disable_eternal_day", "Removes eternal day so that the Aether has a normal daylight cycle even before defeating the Sun Spirit");
        addServerConfig("modpack", "portal_destination_dimension_ID", "Sets the ID of the dimension that the Aether Portal will send the player to");
        addServerConfig("modpack", "portal_return_dimension_ID", "Sets the ID of the dimension that the Aether Portal will return the player to");


        addCommonConfig("gameplay", "use_curios_menu", "Use the default Curios menu instead of the Aether's Accessories Menu. WARNING: Do not enable this without emptying your equipped accessories");
        addCommonConfig("gameplay", "start_with_portal", "On world creation, the player is given an Aether Portal Frame item to automatically go to the Aether with");
        addCommonConfig("gameplay", "enable_startup_loot", "When the player enters the Aether, they are given a Book of Lore and Golden Parachutes as starting loot");
        addCommonConfig("gameplay", "repeat_sun_spirit_dialogue", "Determines whether the Sun Spirit's dialogue when meeting him should play through every time you meet him");
        addCommonConfig("gameplay", "show_patreon_message", "Determines if a message that links The Aether mod's Patreon should show");


        addClientConfig("rendering", "legacy_models", "Changes Zephyr and Aerwhale rendering to use their old models from the b1.7.3 version of the mod");
        addClientConfig("rendering", "disable_aether_skybox", "Disables the Aether's custom skybox in case you have a shader that is incompatible with custom skyboxes");
        addClientConfig("rendering", "colder_lightmap", "Removes warm-tinting of the lightmap in the Aether, giving the lighting a colder feel");
        addClientConfig("rendering", "green_sunset", "Enables a green-tinted sunrise and sunset in the Aether, similar to the original mod");

        addClientConfig("gui", "enable_aether_menu", "Changes the vanilla Minecraft menu into the Aether menu");
        addClientConfig("gui", "enable_aether_menu_button", "Adds a button to the top right of the main menu screen to toggle between the Aether and vanilla menu");
        addClientConfig("gui", "enable_world_preview", "Changes the background panorama into a preview of the latest played world");
        addClientConfig("gui", "enable_world_preview_button", "Adds a button to the top right of the main menu screen to toggle between the panorama and world preview");
        addClientConfig("gui", "enable_quick_load_button", "Adds a button to the top right of the main menu screen to allow quick loading into a world if the world preview is enabled");
        addClientConfig("gui", "menu_type_toggles_alignment", "Determines that menu elements will align left if the menu's world preview is active, if true, this overrides 'Align menu elements left'");
        addClientConfig("gui", "align_vanilla_menu_elements_left", "Aligns the elements of the vanilla menu to the left, only works if 'Align menu left with world preview' is set to false");
        addClientConfig("gui", "align_aether_menu_elements_left", "Aligns the elements of the Aether menu to the left, only works if 'Align menu left with world preview' is set to false");
        addClientConfig("gui", "enable_trivia", "Adds random trivia and tips to the bottom of loading screens");
        addClientConfig("gui", "enable_silver_hearts", "Makes the extra hearts given by life shards display as silver colored");
        addClientConfig("gui", "disable_accessory_button", "Disables the Aether's accessories button from appearing in GUIs");
        addClientConfig("gui", "button_inventory_x", "The x-coordinate of the accessories button in the inventory and curios menus");
        addClientConfig("gui", "button_inventory_y", "The y-coordinate of the accessories button in the inventory and curios menus");
        addClientConfig("gui", "button_creative_x", "The x-coordinate of the accessories button in the creative menu");
        addClientConfig("gui", "button_creative_y", "The y-coordinate of the accessories button in the creative menu");
        addClientConfig("gui", "button_accessories_x", "The x-coordinate of the accessories button in the accessories menu");
        addClientConfig("gui", "button_accessories_y", "The y-coordinate of the accessories button in the accessories menu");
        addClientConfig("gui", "layout_perks_x", "The x-coordinate of the perks button layout when in the pause menu");
        addClientConfig("gui", "layout_perks_y", "The y-coordinate of the perks button layout when in the pause menu");

        addClientConfig("audio", "music_backup_min_delay", "Sets the minimum delay for the Aether's music manager to use if needing to reset the song delay outside the Aether");
        addClientConfig("audio", "music_backup_max_delay", "Sets the maximum delay for the Aether's music manager to use if needing to reset the song delay outside the Aether");
        addClientConfig("audio", "disable_music_manager", "Disables the Aether's internal music manager, if true, this overrides all other audio configs");
        addClientConfig("audio", "disable_aether_menu_music", "Disables the Aether's menu music in case another mod implements its own, only works if 'Disables Aether music manager' is false");
        addClientConfig("audio", "disable_vanilla_world_preview_menu_music", "Disables the menu music on the vanilla world preview menu, only works if 'Disables Aether music manager' is false");
        addClientConfig("audio", "disable_aether_world_preview_menu_music", "Disables the menu music on the Aether world preview menu, only works if 'Disables Aether music manager' is false");


        addPackTitle("125", "Aether 1.2.5 Textures");
        addPackTitle("b173", "Aether b1.7.3 Textures");
        addPackTitle("ctm", "Aether CTM Fix");
        addPackTitle("colorblind", "Aether Colorblind Textures");
        addPackTitle("curios", "Aether Curios Override");
        addPackTitle("freezing", "Aether Temporary Freezing");

        addPackDescription("mod", "Aether Resources");
        addPackDescription("125", "The classic look of the Aether from 1.2.5");
        addPackDescription("b173", "The original look of the Aether from b1.7.3");
        addPackDescription("ctm", "Fixes Quicksoil Glass Panes when using CTM");
        addPackDescription("colorblind", "Changes textures for color blindness accessibility");
        addPackDescription("curios", "Replace Accessories Menu with Curios' menu");
        addPackDescription("freezing", "Ice Accessories create temporary blocks");
    }
}
