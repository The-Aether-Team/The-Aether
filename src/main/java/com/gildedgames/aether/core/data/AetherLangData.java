package com.gildedgames.aether.core.data;

import com.gildedgames.aether.common.registry.*;
import com.gildedgames.aether.core.data.provider.AetherLanguageProvider;
import net.minecraft.data.DataGenerator;

public class AetherLangData extends AetherLanguageProvider
{
    public AetherLangData(DataGenerator gen) {
        super(gen);
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

        addBlock(AetherBlocks.COLD_AERCLOUD, "Cold Aercloud");
        addBlock(AetherBlocks.BLUE_AERCLOUD, "Blue Aercloud");
        addBlock(AetherBlocks.GOLDEN_AERCLOUD, "Golden Aercloud");
        addBlock(AetherBlocks.PINK_AERCLOUD, "Pink Aercloud");

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
        addBlock(AetherBlocks.AEROGEL, "Aerogel");

        addBlock(AetherBlocks.ZANITE_BLOCK, "Zanite Block");
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

        addItem(AetherItems.HAMMER_OF_NOTCH, "Hammer of Notch");

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
        addItem(AetherItems.REPULSION_SHIELD, "Repulsion Shield");

        addItem(AetherItems.SKYROOT_STICK, "Skyroot Stick");
        addItem(AetherItems.GOLDEN_AMBER, "Golden Amber");
        addItem(AetherItems.SWET_BALL, "Swet Ball");
        addItem(AetherItems.AECHOR_PETAL, "Aechor Petal");
        addItem(AetherItems.AMBROSIUM_SHARD, "Ambrosium Shard");
        addItem(AetherItems.ZANITE_GEMSTONE, "Zanite Gemstone");

        addItem(AetherItems.VICTORY_MEDAL, "Victory Medal");

        addItem(AetherItems.BRONZE_DUNGEON_KEY, "Bronze Key");
        addItem(AetherItems.SILVER_DUNGEON_KEY, "Silver Key");
        addItem(AetherItems.GOLD_DUNGEON_KEY, "Golden Key");

        addItem(AetherItems.MUSIC_DISC_AETHER_TUNE, "Blue Music Disc");
        addDiscDesc(AetherItems.MUSIC_DISC_AETHER_TUNE, "Noisestorm - Aether Tune");
        addItem(AetherItems.MUSIC_DISC_ASCENDING_DAWN, "Valkyrie Music Disc");
        addDiscDesc(AetherItems.MUSIC_DISC_ASCENDING_DAWN, "Emile van Krieken - Ascending Dawn");
        addItem(AetherItems.MUSIC_DISC_WELCOMING_SKIES, "Welcoming Skies");
        addDiscDesc(AetherItems.MUSIC_DISC_WELCOMING_SKIES, "Voyed - Welcoming Skies");
        addItem(AetherItems.MUSIC_DISC_LEGACY, "Legacy");
        addDiscDesc(AetherItems.MUSIC_DISC_LEGACY, "Jon Lachney - Legacy");

        addItem(AetherItems.SKYROOT_BUCKET, "Skyroot Bucket");
        addItem(AetherItems.SKYROOT_WATER_BUCKET, "Skyroot Water Bucket");
        addItem(AetherItems.SKYROOT_POISON_BUCKET, "Skyroot Poison Bucket");
        addItem(AetherItems.SKYROOT_REMEDY_BUCKET, "Skyroot Remedy Bucket");
        addItem(AetherItems.SKYROOT_MILK_BUCKET, "Skyroot Milk Bucket");

        addItem(AetherItems.COLD_PARACHUTE, "Cold Parachute");
        addItem(AetherItems.GOLDEN_PARACHUTE, "Golden Parachute");

        addItem(AetherItems.NATURE_STAFF, "Nature Staff");
        addItem(AetherItems.CLOUD_STAFF, "Cloud Staff");

        addItem(AetherItems.BLUE_MOA_EGG, "Blue Moa Egg");
        addItem(AetherItems.WHITE_MOA_EGG, "White Moa Egg");
        addItem(AetherItems.BLACK_MOA_EGG, "Black Moa Egg");
        addItem(AetherItems.ORANGE_MOA_EGG, "Orange Moa Egg");

        addItem(AetherItems.LIFE_SHARD, "Life Shard");

        addItem(AetherItems.BOOK_OF_LORE, "Book of Lore");

        addItem(AetherItems.AETHER_PORTAL_FRAME, "Aether Portal Frame");

        addItem(AetherItems.AECHOR_PLANT_SPAWN_EGG, "Aechor Plant Spawn Egg");
        addItem(AetherItems.AERBUNNY_SPAWN_EGG, "Aerbunny Spawn Egg");
        addItem(AetherItems.AERWHALE_SPAWN_EGG, "Aerwhale Spawn Egg");
        addItem(AetherItems.COCKATRICE_SPAWN_EGG, "Cockatrice Spawn Egg");
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
        addItem(AetherItems.ZEPHYR_SPAWN_EGG, "Zephyr Spawn Egg");


        addEntityType(AetherEntityTypes.PHYG, "Phyg");
        addEntityType(AetherEntityTypes.FLYING_COW, "Flying Cow");
        addEntityType(AetherEntityTypes.SHEEPUFF, "Sheepuff");
        addEntityType(AetherEntityTypes.MOA, "Moa");
        addEntityType(AetherEntityTypes.AERBUNNY, "Aerbunny");
        addEntityType(AetherEntityTypes.AERWHALE, "Aerwhale");
        addEntityType(AetherEntityTypes.BLUE_SWET, "Blue Swet");
        addEntityType(AetherEntityTypes.GOLDEN_SWET, "Golden Swet");
        addEntityType(AetherEntityTypes.WHIRLWIND, "Whirlwind");
        addEntityType(AetherEntityTypes.EVIL_WHIRLWIND, "Evil Whirlwind");
        addEntityType(AetherEntityTypes.AECHOR_PLANT, "Aechor Plant");
        addEntityType(AetherEntityTypes.COCKATRICE, "Cockatrice");
        addEntityType(AetherEntityTypes.ZEPHYR, "Zephyr");
        addEntityType(AetherEntityTypes.SENTRY, "Sentry");
        addEntityType(AetherEntityTypes.MIMIC, "Mimic");

        addEntityType(AetherEntityTypes.FLOATING_BLOCK, "Floating Block");
        addEntityType(AetherEntityTypes.LIGHTNING_KNIFE, "Lightning Knife");
        addEntityType(AetherEntityTypes.ZEPHYR_SNOWBALL, "Zephyr Snowball");
        addEntityType(AetherEntityTypes.GOLDEN_DART, "Golden Dart");
        addEntityType(AetherEntityTypes.POISON_DART, "Poison Dart");
        addEntityType(AetherEntityTypes.ENCHANTED_DART, "Enchanted Dart");
        addEntityType(AetherEntityTypes.HAMMER_PROJECTILE, "Hammer Projectile");


        addEffect(AetherEffects.INEBRIATION, "Inebriation");


        addContainerType(AetherContainerTypes.BOOK_OF_LORE, "Book of Lore");
        addContainerType(AetherContainerTypes.ALTAR, "Altar");
        addContainerType(AetherContainerTypes.FREEZER, "Freezer");
        addContainerType(AetherContainerTypes.INCUBATOR, "Incubator");
        addContainerType("bronze_dungeon_chest", "Bronze Dungeon Chest");
        addContainerType("silver_dungeon_chest", "Silver Dungeon Chest");
        addContainerType("gold_dungeon_chest", "Gold Dungeon Chest");
        addContainerType("sun_altar", "Sun Altar");


        addItemGroup(AetherItemGroups.AETHER_BLOCKS, "Aether Blocks");
        addItemGroup(AetherItemGroups.AETHER_TOOLS, "Aether Tools");
        addItemGroup(AetherItemGroups.AETHER_WEAPONS, "Aether Weapons");
        addItemGroup(AetherItemGroups.AETHER_ARMOR, "Aether Armor");
        addItemGroup(AetherItemGroups.AETHER_FOOD, "Aether Foods");
        addItemGroup(AetherItemGroups.AETHER_ACCESSORIES, "Aether Accessories");
        addItemGroup(AetherItemGroups.AETHER_MATERIALS, "Aether Materials");
        addItemGroup(AetherItemGroups.AETHER_MISC, "Aether Miscellaneous");


        addAdvancement("enter_aether", "Hostile Paradise");
        addAdvancement("read_lore", "The More You Know!");
        addAdvancement("loreception", "Lore-Ception!");
        addAdvancement("blue_aercloud", "To Infinity and Beyond!");
        addAdvancement("incubator", "Now You're Family");
        addAdvancement("altar", "Do You Believe in Magic?");
        addAdvancement("gravitite_tools", "Pink is the New Blue");
        addAdvancement("mount_phyg", "When Phygs Fly");
        addAdvancement("bronze_dungeon", "Like a Bossaru!");
        addAdvancement("silver_dungeon", "Dethroned");
        addAdvancement("gold_dungeon", "Extinguished");


        addAdvancementDesc("enter_aether", "Enter the Aether");
        addAdvancementDesc("read_lore", "Read a Book of Lore");
        addAdvancementDesc("loreception", "Put a Book of Lore inside a Book of Lore");
        addAdvancementDesc("blue_aercloud", "Bounce on a Blue Aercloud");
        addAdvancementDesc("incubator", "Incubate a Moa");
        addAdvancementDesc("altar", "Craft an Altar");
        addAdvancementDesc("gravitite_tools", "Craft a Gravitite tool");
        addAdvancementDesc("mount_phyg", "Fly on a Phyg");
        addAdvancementDesc("bronze_dungeon", "Defeat the Bronze Dungeon boss");
        addAdvancementDesc("silver_dungeon", "Defeat the Silver Dungeon boss");
        addAdvancementDesc("gold_dungeon", "Defeat the Golden Dungeon boss");


        addSubtitle("block", "aether_portal.ambient", "Aether Portal whooshes");
        addSubtitle("block", "aether_portal.trigger", "Aether Portal noise intensifies");
        addSubtitle("block", "chest_mimic.open", "Mimic awakens");
        addSubtitle("block", "dungeon_trap.trigger", "Dungeon Trap activated");

        addSubtitle("item", "dart_shooter.shoot", "Dart Shooter fired");
        addSubtitle("item", "lightning_knife.shoot", "Lightning Knife flies");
        addSubtitle("item", "hammer_of_notch.shoot", "Hammer fired");

        addSubtitle("item", "armor.equip_zanite", "Zanite armor clanks");
        addSubtitle("item", "armor.equip_gravitite", "Gravitite armor clangs");
        addSubtitle("item", "armor.equip_valkyrie", "Valkyrie armor clinks");
        addSubtitle("item", "armor.equip_neptune", "Neptune armor jingles");
        addSubtitle("item", "armor.equip_phoenix", "Phoenix armor clinks");
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

        addSubtitle("entity", "cockatrice.shoot", "Cockatrice shoots");
        addSubtitle("entity", "cockatrice.ambient", "Cockatrice calls");
        addSubtitle("entity", "cockatrice.death", "Cockatrice dies");
        addSubtitle("entity", "cockatrice.hurt", "Cockatrice hurts");
        addSubtitle("entity", "cockatrice.flap", "Cockatrice flaps");

        addSubtitle("entity", "zephyr.shoot", "Zephyr spits");
        addSubtitle("entity", "zephyr.ambient", "Zephyr blows");
        addSubtitle("entity", "zephyr.death", "Zephyr dies");
        addSubtitle("entity", "zephyr.hurt", "Zephyr hurts");

        addSubtitle("entity", "sentry.death", "Sentry dies");
        addSubtitle("entity", "sentry.hurt", "Sentry hurts");
        addSubtitle("entity", "sentry.jump", "Sentry squishes");

        addSubtitle("entity", "mimic.attack", "Mimic attacks");
        addSubtitle("entity", "mimic.death", "Mimic dies");
        addSubtitle("entity", "mimic.hurt", "Mimic hurts");
        addSubtitle("entity", "mimic.kill", "Mimic burps");

        addSubtitle("entity", "slider.awaken", "Slider awakens");
        addSubtitle("entity", "slider.collide", "Slider smashes");
        addSubtitle("entity", "slider.move", "Slider slides");
        addSubtitle("entity", "slider.death", "Slider breaks");

        addSubtitle("entity", "sun_spirit.shoot", "Sun Spirit shoots");

        addSubtitle("entity", "cloud_minion.shoot", "Cloud Minion spits");

        addSubtitle("entity", "cloud_crystal.explode", "Crystal explodes");

        addSubtitle("entity", "dart.hit", "Dart hits");



        addDeath("inebriation", "%1$s was inebriated");
        addDeath("inebriation.player", "%1$s was inebriated by %2$s");
        addDeath("ice_crystal", "%1$s was chilled by %2$s's Ice Crystal");



        addMenuText("minecraft", "Normal Theme");
        addMenuText("aether", "Aether Theme");


        addGuiText("pro_tip", "Pro Tip:");
        addGuiText("ascending", "Ascending to the Aether");
        addGuiText("descending", "Descending from the Aether");


        addGuiText("accessories.perks_button", "Customization & Perks");


        addGuiText("sun_altar.time", "Time");


        addCustomizationText("title", "Customization & Perks");
        addCustomizationText("gloves.arm", "Glove Layer: Arm");
        addCustomizationText("gloves.sleeve", "Glove Layer: Sleeve");
        addCustomizationText("halo.on", "Player Halo: ON");
        addCustomizationText("halo.off", "Player Halo: OFF");
        addCustomizationText("developer_glow.on", "Developer Glow: ON");
        addCustomizationText("developer_glow.off", "Developer Glow: OFF");


        addLoreBookText("previous", "Prev.");
        addLoreBookText("next", "Next");
        addLoreBookText("book", "Book");
        addLoreBookText("of_lore", "Of Lore");
        addLoreBookText("item", "Item:");


        addMessage("hammer_of_notch_cooldown", "Cooldown");


        addMessage("life_shard_limit", "You can only use a total of %s Life Shards.");
        addMessage("bronze_dungeon_chest_locked", "This Treasure Chest must be unlocked with a Bronze Key.");
        addMessage("silver_dungeon_chest_locked", "This Treasure Chest must be unlocked with a Silver Key.");
        addMessage("gold_dungeon_chest_locked", "This Treasure Chest must be unlocked with a Golden Key.");
        addMessage("sun_altar.in_control", "The sun spirit is still in control of this realm.");
        addMessage("sun_altar.no_permission", "You don't have permission to use this.");
        addMessage("sun_altar.no_power", "The sun spirit has no power over this realm.");



        addKeyInfo("category", "Aether");
        addKeyInfo("open_accessories.desc", "Open/Close Accessories Inventory");


        addCuriosIdentifier("aether_pendant", "Pendant");
        addCuriosIdentifier("aether_cape", "Cape");
        addCuriosIdentifier("aether_ring", "Ring");
        addCuriosIdentifier("aether_shield", "Shield");
        addCuriosIdentifier("aether_gloves", "Gloves");
        addCuriosIdentifier("aether_accessory", "Accessory");

        addCuriosModifier("aether_gloves", "When on hands:");


        addItemLore(AetherItems.AECHOR_PETAL, "The petal of an Aechor Plant, they have a sweet aroma to them. These are a Moa's favorite food, and can be used to feed baby Moas.");
        addBlockLore(AetherBlocks.AEROGEL, "The result of the Aether's unique climate and lava combining. It can be crafted into various decorative blocks and is blast resistant.");
        addBlockLore(AetherBlocks.AEROGEL_SLAB, "Crafted from Aerogel. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.AEROGEL_STAIRS, "Crafted from Aerogel. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlockLore(AetherBlocks.AEROGEL_WALL, "Crafted from Aerogel. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addBlockLore(AetherBlocks.AETHER_DIRT, "A pale dirt. It can be found in the Aether, and can also be used to grow the native trees. It can grow into grass with enough light.");
        addItemLore(AetherItems.AETHER_PORTAL_FRAME, "A portable frame containing the power to travel to the Aether or back to the Overworld. Not something you're likely to find naturally.");
        addBlockLore(AetherBlocks.AETHER_GRASS_BLOCK, "Grass found in the Aether dimension. It can be used to grow the trees native to the Aether. It is much more pale than normal Grass.");
        addItemLore(AetherItems.AGILITY_CAPE, "A slightly rare cape, it can be seen in Bronze and Silver Dungeons. It makes the wearer's legs stronger, therefore they can walk up blocks instantly.");
        addBlockLore(AetherBlocks.ALTAR, "Used to enchant items and repair armor. They are powered by Ambrosium Shards. The enchanting process can take some time, but if you place an Enchanted Gravitite block under it, the process is sped up by a huge amount.");
        addBlockLore(AetherBlocks.AMBROSIUM_ORE, "The most common ore in the Aether. The drops can be doubled with Skyroot tools.");
        addItemLore(AetherItems.AMBROSIUM_SHARD, "Aether's coal equivalent, they have a healing property when eaten, and restore a small amount of health.");
        addBlockLore(AetherBlocks.AMBROSIUM_TORCH, "The main light source for the Aether, made with a Skyroot Stick and Ambrosium.");
        addBlockLore(AetherBlocks.ANGELIC_SLAB, "Crafted from Angelic Stone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.ANGELIC_STONE, "Angelic Stone is the main block that makes up Silver Dungeons. There is a chance some of them can be traps when generated in dungeons, but you cannot collect them as traps. It is unbreakable until you have defeated the boss, but it's worth it for that block.");
        addBlockLore(AetherBlocks.ANGELIC_WALL, "Crafted from Angelic Stone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addBlockLore(AetherBlocks.ANGELIC_STAIRS, "Crafted from Angelic Stone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlockLore(AetherBlocks.BERRY_BUSH, "These bushes can be found all over the Aether. They provide Blue Berries when broken, and then turn into bare stems. If placed on Enchanted Aether Grass the drops are doubled.");
        addBlockLore(AetherBlocks.BERRY_BUSH_STEM, "This is the result of harvesting the Blue Berries from a Berry Bush. It can take some time for them to grow back.");
        addItemLore(AetherItems.BLACK_MOA_EGG, "An Egg laid by a Black Moa. Hatching this provides a Black Moa with 8 mid-air jumps, the best and most rare one!");
        addBlockLore(AetherBlocks.BLUE_AERCLOUD, "A pale blue cloud found close to the ground. It has very bouncy properties, and can help you reach high places.");
        addItemLore(AetherItems.BLUE_BERRY, "Harvested from Berry Bushes, this is the most common food source in the Aether. It has very weak hunger restoration. You can enchant these in an Altar for much better hunger restoration.");
        addItemLore(AetherItems.BLUE_CAPE, "A Blue Cape that has a silky feeling to it. It's crafted using Blue Wool.");
        addItemLore(AetherItems.BLUE_GUMMY_SWET, "A sweet smelling gummy, it can be found in random chests in Bronze and Silver dungeons. It restores the player’s hunger to full. Very useful for boss fights.");
        addItemLore(AetherItems.BLUE_MOA_EGG, "An Egg laid by a Blue Moa. Hatching this provides a Blue Moa with 3 mid-air jumps. The most common Moa.");
        addItemLore(AetherItems.BOOK_OF_LORE, "A large book containing many lore entries written by the wise Lorist. It describes every object in detail.");
        addItemLore(AetherItems.BRONZE_DUNGEON_KEY, "A dull key that is dropped from the Slider after being defeated. You can use it to claim the treasure you earned!");
        addItemLore(AetherItems.CANDY_CANE, "Found in presents under Holiday trees! They can be used to repair Candy Cane swords, and are a very tasty treat.");
        addItemLore(AetherItems.CANDY_CANE_SWORD, "A sword made from decorative candy. Randomly drops Candy Canes when used. These are dropped from presents that are under Holiday Trees.");
        addBlockLore(AetherBlocks.CARVED_SLAB, "Crafted from Carved Stone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.CARVED_STAIRS, "Crafted from Sentry Stone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlockLore(AetherBlocks.CARVED_STONE, "The Stone found in Bronze Dungeons. This stone has a gray color to it, and can be mined as a decorative block.");
        addBlockLore(AetherBlocks.CARVED_WALL, "Crafted from Carved Stone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addItemLore(AetherItems.CHAINMAIL_GLOVES, "A very rare part of chain armor, it is needed to complete the Chain Armor set.");
        addBlockLore(AetherBlocks.CHEST_MIMIC, "It may look like a normal chest, but it really isn't. As soon as you right click on it, a chest mimic will pop out! These appear in Bronze and Silver dungeons.");
        addItemLore(AetherItems.CLOUD_STAFF, "A staff with a light and fluffy top. It summons small Cloud Sentries to fight next to the user for a short period of time by shooting large Iceballs.");
        addBlockLore(AetherBlocks.COLD_AERCLOUD, "A cold cloud found in the skies of the Aether. It can be used to make Parachutes, and break drops that would otherwise be very dangerous.");
        addItemLore(AetherItems.COLD_PARACHUTE, "A quickly made parachute. It's fluffy to the touch and is made from Cold Aerclouds. It has one use.");
        addBlockLore(AetherBlocks.CRYSTAL_FRUIT_LEAVES, "Crystal Leaves that are home to White Apples.");
        addBlockLore(AetherBlocks.CRYSTAL_LEAVES, "Leaves that come from Crystal Trees, they generate on floating islands. Sometimes they have fruit on them, which can cure poison.");
        addBlockLore(AetherBlocks.DECORATED_HOLIDAY_LEAVES, "Holiday Leaves that have been decorated with lovely little baubles for extra holiday cheer!");
        addItemLore(AetherItems.DIAMOND_GLOVES, "Part of the Diamond Armor set, it is needed to complete the Diamond Armor set.");
        addBlockLore(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, "Aether Grass enchanted which allows for increasing harvest rates of Blue Berries.");
        addItemLore(AetherItems.ENCHANTED_BERRY, "An excellent food source, it tastes quite good and restores a massive amount of hunger.");
        addItemLore(AetherItems.ENCHANTED_DART, "The ammo for Enchanted Dart Shooters. This is the strongest of the darts, made by enchanting Golden Darts.");
        addItemLore(AetherItems.ENCHANTED_DART_SHOOTER, "A Dart Shooter which shoots Enchanted Darts, the strongest one!");
        addBlockLore(AetherBlocks.ENCHANTED_GRAVITITE, "After putting Gravitite Ore into an Altar and enchanting it, you get Enchanted Gravitite. This can be made into Gravitite Tools and Armor. When powered with a Redstone signal, Enchanted Gravitite Blocks will float into the air, similarly to Gravitite Ore.");
        addItemLore(AetherItems.FLAMING_SWORD, "An ancient sword which flames its foes to a burning crisp. It almost hurts to touch! Using this on animals can make them drop cooked meat!");
        addBlockLore(AetherBlocks.FREEZER, "You can freeze various items using this, such as Aerclouds, or Water Buckets. It's source of power is Icestone. You can speed up the freezing process by placing an Icestone under the Freezer.");
        addItemLore(AetherItems.GINGERBREAD_MAN, "Found in presents under Holiday trees, these are the most common of Christmas items. They are very abundant when dropped from presents.");
        addItemLore(AetherItems.GOLD_DUNGEON_KEY, "A key that has a shiny finish. It is dropped by the Sun Spirit after you defeat him. You can use it to claim the legendary treasure in the back room!");
        addBlockLore(AetherBlocks.GOLDEN_AERCLOUD, "A golden cloud found in small quantities, higher in the air than normal. The properties are similar to Cold Aerclouds, but the Parachutes they produce have much more durability.");
        addItemLore(AetherItems.GOLDEN_AMBER, "These round golden orbs can be obtained by mining Golden Oak Logs with a Gravitite Axe. Their main purpose is to craft Golden Darts, and Dart Shooters.");
        addItemLore(AetherItems.GOLDEN_GUMMY_SWET, "A sour tasting gummy, it can be found in random chests in Bronze and Silver dungeons. It restores the player’s hunger to full. Very useful for boss fights.");
        addItemLore(AetherItems.GOLDEN_DART, "The ammo for Golden Dart Shooters. Crafted with Skyroot Sticks and Golden Amber, enchanting these converts them to Enchanted Darts.");
        addItemLore(AetherItems.GOLDEN_DART_SHOOTER, "A Dart Shooter which shoots Golden Darts, enchanting it on an Altar improves it's attack power!");
        addItemLore(AetherItems.GOLDEN_FEATHER, "A fluffy feather found in Silver Dungeons. When worn, the wearer becomes lighter than air and can descend slowly.");
        addItemLore(AetherItems.GOLDEN_GLOVES, "Part of the Golden Armor set, it is needed to complete the Gold Armor set.");
        addBlockLore(AetherBlocks.GOLDEN_OAK_LEAVES, "These golden Leaves generate with Golden Oak trees. They spawn golden particles in a radius of 5 blocks. They yield Golden Oak Saplings when decayed.");
        addBlockLore(AetherBlocks.GOLDEN_OAK_LOG, "Skyroot Log which contains Golden Amber inside. When broken with an Axe they drop Skyroot Logs, and if the Axe is Gravitite, they will drop Golden Amber.");
        addBlockLore(AetherBlocks.GOLDEN_OAK_SAPLING, "These large saplings when planted will grow into huge Golden Oak trees! You can use Bone Meal to speed up the process.");
        addBlockLore(AetherBlocks.GOLDEN_OAK_WOOD, "Skyroot Log which contains Golden Amber Inside. When broken with an Axe they drop Skyroot Logs, and if the Axe is Gravitite, they will drop Golden Amber. Crafted to have bark on all sides.");
        addItemLore(AetherItems.GOLDEN_PARACHUTE, "The best parachute in the Aether. It has 20 uses and is made with Golden Aerclouds.");
        addItemLore(AetherItems.GOLDEN_PENDANT, "An aesthetic accessory made of gold.");
        addItemLore(AetherItems.GOLDEN_RING, "An aesthetic accessory made of gold.");
        addItemLore(AetherItems.GRAVITITE_AXE, "Part of Aether's best tool tier, this Axe not only can make wood blocks float, but it can mine Golden Oak logs for Golden Amber!");
        addItemLore(AetherItems.GRAVITITE_BOOTS, "Part of Aether's best armor set, when the full set is worn, you get an extra high jump!");
        addItemLore(AetherItems.GRAVITITE_CHESTPLATE, "Part of Aether's best armor set, when the full set is worn, you get an extra high jump!");
        addItemLore(AetherItems.GRAVITITE_GLOVES, "Part of Aether's best armor set, needed to complete the Gravitite set.");
        addItemLore(AetherItems.GRAVITITE_HELMET, "Part of Aether's best armor set, when the full set is worn, you get an extra high jump!");
        addItemLore(AetherItems.GRAVITITE_LEGGINGS, "Part of Aether's best armor set, when the full set is worn, you get an extra high jump!");
        addBlockLore(AetherBlocks.GRAVITITE_ORE, "This is Aether's rarest ore. It has floating properties and will float upward when there is nothing above it. These can be Enchanted into Enchanted Gravitite Blocks.");
        addItemLore(AetherItems.GRAVITITE_PICKAXE, "Part of Aether's best tool tier, when mining with this powerful tool, you can right-click on any stone block and it will levitate into the air! You can mine any ore with this Pickaxe.");
        addItemLore(AetherItems.GRAVITITE_SHOVEL, "Part of Aether's best tool tier, this shovel has the special ability to make dirt blocks, or sand levitate! When combined with enchantments such as Efficiency, it will instantly break Aether Dirt!");
        addItemLore(AetherItems.GRAVITITE_SWORD, "Part of Aether's best tool tier, when attacking with this powerful weapon, anything you hit will be flung into the air, causing lots of damage! Use this to your advantage, as it works even while your sword is cooling down!");
        addItemLore(AetherItems.GRAVITITE_HOE, "Part of Aether's best tool tier, this hoe not only can make weirdly specific blocks float, but it can also till dirt which totally justifies it's existence.");
        addItemLore(AetherItems.HEALING_STONE, "Obtained from enchanting Holystone, it can be used as a reliable healing source, providing Regeneration. It has a surprisingly juicy flavor.");
        addBlockLore(AetherBlocks.HELLFIRE_SLAB, "Crafted from Hellfire Stone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.HELLFIRE_STONE, "A warm, red stone that makes up 90%% of Gold Dungeon interior. It cannot be destroyed until the Sun Spirit is defeated.");
        addBlockLore(AetherBlocks.HELLFIRE_WALL, "Crafted from Hellfire Stone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addBlockLore(AetherBlocks.HELLFIRE_STAIRS, "Crafted from Hellfire Stone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlockLore(AetherBlocks.HOLIDAY_LEAVES, "Leaves on Christmas trees! It snows near them, and there is always presents nearby!");
        addItemLore(AetherItems.HOLY_SWORD, "An ancient sword which does heavy amounts of damage to undead foes. A perfect weapon for traversing the Overworld.");
        addBlockLore(AetherBlocks.HOLYSTONE, "The Aether's native rock. It can be used in various ways such as creating tools, construction, as well being able to be crafted into Holystone Bricks.");
        addItemLore(AetherItems.HOLYSTONE_AXE, "One of Aether's stone tools, it mines faster than Skyroot Tools, as well as dropping random Ambrosium Shards.");
        addBlockLore(AetherBlocks.HOLYSTONE_BRICKS, "Used as a building material native to the Aether. It is made from Holystone and is more sturdy than it too.");
        addBlockLore(AetherBlocks.HOLYSTONE_BRICK_SLAB, "Crafted from Holystone Bricks. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.HOLYSTONE_BRICK_STAIRS, "Crafted from Holystone Bricks. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlockLore(AetherBlocks.HOLYSTONE_BRICK_WALL, "Crafted from Holystone Bricks. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addBlockLore(AetherBlocks.HOLYSTONE_BUTTON, "Crafted from Holystone, a button used to activate mechanisms and redstone.");
        addItemLore(AetherItems.HOLYSTONE_HOE, "One of Aether's stone tools, It has more durability than the Skyroot hoe, as well as dropping random Ambrosium Shards. Which makes it more useful than most hoes.");
        addItemLore(AetherItems.HOLYSTONE_PICKAXE, "One of Aether's stone tools, it can mine Zanite, and will randomly drop Ambrosium Shards while mining.");
        addBlockLore(AetherBlocks.HOLYSTONE_PRESSURE_PLATE, "Crafted from Holystone, a pressure plate used to activate mechanisms and redstone.");
        addItemLore(AetherItems.HOLYSTONE_SHOVEL, "One of Aether's stone tools, it can mine Quicksoil, and all other blocks a Stone Shovel can mine, except it drops random Ambrosium Shards.");
        addBlockLore(AetherBlocks.HOLYSTONE_SLAB, "Crafted from Holystone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.HOLYSTONE_STAIRS, "Crafted from Holystone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addItemLore(AetherItems.HOLYSTONE_SWORD, "One of Aether's stone tools, it can drop Ambrosium Shards at random while attacking.");
        addBlockLore(AetherBlocks.HOLYSTONE_WALL, "Crafted from Holystone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addItemLore(AetherItems.ICE_PENDANT, "A pendant which allows you to freeze water, and lava when walked on.");
        addItemLore(AetherItems.ICE_RING, "A ring which allows you to freeze water, and lava when walked on.");
        addBlockLore(AetherBlocks.ICESTONE, "Icestone is a common ore that can be used as fuel for a Freezer. It can be used to freeze nearby liquids such as Lava and Water.");
        addBlockLore(AetherBlocks.ICESTONE_SLAB, "Crafted from Icestone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.ICESTONE_STAIRS, "Crafted from Icestone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlockLore(AetherBlocks.ICESTONE_WALL, "Crafted from Icestone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addBlockLore(AetherBlocks.INCUBATOR, "Used to incubate Moa's. You use Ambrosium Torches for the fuel. The process can take quite some time, but it is worth the wait once your Moa of choice hatches!");
        addItemLore(AetherItems.INVISIBILITY_CLOAK, "A cloak that makes the wearer completely invisible! Since mobs cannot see you, they cannot attack you. Sneak up on your enemies with it!");
        addItemLore(AetherItems.IRON_BUBBLE, "A common dungeon loot. It allows for the wearer to breathe underwater for a longer time than normal.");
        addItemLore(AetherItems.IRON_GLOVES, "Part of the Iron Armor set, it is needed to complete the Iron Armor set.");
        addItemLore(AetherItems.IRON_PENDANT, "An aesthetic accessory made of iron.");
        addItemLore(AetherItems.IRON_RING, "An aesthetic accessory made of iron.");
        addItemLore(AetherItems.LEATHER_GLOVES, "Dyeable gloves to match your leather tunic! This is needed to complete the Leather armor set.");
        addItemLore(AetherItems.LIFE_SHARD, "A very rare item found in Gold Dungeons. Using this will give you an extra permanent heart! The feeling of using it is very strange, and hard to describe.");
        addBlockLore(AetherBlocks.LIGHT_ANGELIC_STONE, "The Light version of Angelic Stone. It is less common than Angelic Stone, but it looks really nice as a decorative block.");
        addBlockLore(AetherBlocks.LIGHT_HELLFIRE_STONE, "The Light version of Hellfire Stone. It can be found in Gold Dungeons along with Hellfire stone, but like it's counterpart, it cannot be collected until the boss is defeated.");
        addItemLore(AetherItems.LIGHTNING_KNIFE, "Small knives that when thrown, summon lightning bolts where they land.");
        addItemLore(AetherItems.LIGHTNING_SWORD, "An ancient sword which summons lightning to its foes.");
        addBlockLore(AetherBlocks.MOSSY_HOLYSTONE, "A more aged Holystone, it is found near dungeons, and has pale colored vines growing on it, very decorative.");
        addBlockLore(AetherBlocks.MOSSY_HOLYSTONE_SLAB, "Crafted from Mossy Holystone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, "Crafted from Mossy Holystone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlockLore(AetherBlocks.MOSSY_HOLYSTONE_WALL, "Crafted from Mossy Holystone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addItemLore(AetherItems.NATURE_STAFF, "A staff that can allow for commanding tamed Moas. It can allow them to sit on the ground.");
        addItemLore(AetherItems.NEPTUNE_BOOTS, "Found in Silver Dungeons, this armor allows for water walking. The full set is a much better replacement for the Depth Strider enchantment.");
        addItemLore(AetherItems.NEPTUNE_CHESTPLATE, "Found in Silver Dungeons, this armor allows for water walking. Combined with an Iron Bubble, this armor set is super useful for defeating underwater temples.");
        addItemLore(AetherItems.NEPTUNE_GLOVES, "Found in Silver Dungeons, these gloves are requires to complete the Neptune Armor set, which allows for underwater walking.");
        addItemLore(AetherItems.NEPTUNE_HELMET, "Found in Silver Dungeons, this armor allows for water walking. Combined with an Iron Bubble, this armor set is super useful for defeating underwater temples.");
        addItemLore(AetherItems.NEPTUNE_LEGGINGS, "Found in Silver Dungeons, this armor allows for water walking. Combined with an Iron Bubble, this armor set is super useful for defeating underwater temples.");
        addItemLore(AetherItems.HAMMER_OF_NOTCH, "A mighty hammer which shoots heavy projectiles at mobs. It's said that Notch actually held this hammer.");
        addItemLore(AetherItems.OBSIDIAN_BOOTS, "A super powerful armor, more powerful than Diamond, this armor is only obtainable by standing in water while wearing Phoenix Armor, converting it to Obsidian.");
        addItemLore(AetherItems.OBSIDIAN_CHESTPLATE, "A super powerful armor, more powerful than Diamond, this armor is only obtainable by standing in water while wearing Phoenix Armor, converting it to Obsidian.");
        addItemLore(AetherItems.OBSIDIAN_GLOVES, "Needed to complete the Obsidian Armor set, you can convert Phoenix Gloves into Obsidian by standing in water while wearing them.");
        addItemLore(AetherItems.OBSIDIAN_HELMET, "A super powerful armor, more powerful than Diamond, this armor is only obtainable by standing in water while wearing Phoenix Armor, converting it to Obsidian.");
        addItemLore(AetherItems.OBSIDIAN_LEGGINGS, "A super powerful armor, more powerful than Diamond, this armor is only obtainable by standing in water while wearing Phoenix Armor, converting it to Obsidian.");
        addItemLore(AetherItems.ORANGE_MOA_EGG, "An Egg laid by an Orange Moa. Hatching this provides an Orange Moa with 2 mid-air jumps, but extreme speed. This Moa is quite common.");
        addItemLore(AetherItems.PHOENIX_BOOTS, "Found in Gold Dungeons, when fully worn, this armor set allows for Fire Resistance, you can swim in Lava as well. Be careful when standing in water, as it will turn into Obsidian armor.");
        addItemLore(AetherItems.PHOENIX_BOW, "Found as a dungeon loot, this bow is very heated, and can shoot flaming arrows! It's warm to the touch.");
        addItemLore(AetherItems.PHOENIX_CHESTPLATE, "Found in Gold Dungeons, when fully worn, this armor set allows for Fire Resistance, you can swim in Lava as well. Be careful when standing in water, as it will turn into Obsidian armor.");
        addItemLore(AetherItems.PHOENIX_GLOVES, "Found in Gold Dungeons, these gloves are required to finish the Phoenix Armor set, and are needed to convert the full set into Obsidian too.");
        addItemLore(AetherItems.PHOENIX_HELMET, "Found in Gold Dungeons, when fully worn, this armor set allows for Fire Resistance, you can swim in Lava as well. Be careful when standing in water, as it will turn into Obsidian armor.");
        addItemLore(AetherItems.PHOENIX_LEGGINGS, "Found in Gold Dungeons, when fully worn, this armor set allows for Fire Resistance, you can swim in Lava as well. Be careful when standing in water, as it will turn into Obsidian armor.");
        addItemLore(AetherItems.PIG_SLAYER, "Kills any Pig type mobs with nothing with a single blow. But why would you want to do that? Great for traversing the Nether.");
        addBlockLore(AetherBlocks.PILLAR, "The main part of the Silver Dungeon's decorative pillars, they spawn all around the dungeon, and are excellent for building.");
        addBlockLore(AetherBlocks.PILLAR_TOP, "The top of the Silver Dungeon's decorative pillars, they look excellent, and are great for building.");
        addBlockLore(AetherBlocks.PINK_AERCLOUD, "A very soft cloud that is pink. It has natural healing properties, healing anyone's wounds when stepped in.");
        addItemLore(AetherItems.POISON_DART, "The ammo for Poison Dart Shooters, these are made by infecting Golden Darts with poison!");
        addItemLore(AetherItems.POISON_DART_SHOOTER, "A Dart Shooter which shoots Poison Darts. Shooting this at something infects them with a deadly poison!");
        addBlockLore(AetherBlocks.PRESENT, "A wonderful holiday gift, open it for a surprise! Be careful, as there's a chance you could get ssssssurprised!");
        addBlockLore(AetherBlocks.PURPLE_FLOWER, "These pretty violet flowers can be found in large numbers around the Aether. They can be crafted into purple dye when placed into a Crafting Table.");
        addBlockLore(AetherBlocks.QUICKSOIL, "A silky sand with extremely slippery properties. It can be seen floating on the side of Aether islands. Be careful around it, or you'll fall off.");
        addBlockLore(AetherBlocks.QUICKSOIL_GLASS, "After enchanting Quicksoil you can get this tinted glass. It is slightly slippery and makes a for a great window.");
        addItemLore(AetherItems.RED_CAPE, "A rough feeling cape that is crafted using Red Wool.");
        addItemLore(AetherItems.REGENERATION_STONE, "A treasure found in Silver Dungeons. This stone makes the wearer feel healthy, and heals their wounds. The effects can stack with other Regeneration Stones.");
        addItemLore(AetherItems.REPULSION_SHIELD, "A shield that protects the user from most projectiles. It will reflect the projectile back at the thrower and cause damage to them. Each time a projectile is reflected the shield will be damaged.");
        addItemLore(AetherItems.SENTRY_BOOTS, "Found in Bronze Dungeons, these boots protect you from fall damage, allowing you to fall from great heights.");
        addBlockLore(AetherBlocks.SENTRY_STONE, "The Light version of Carved Stone, also found in Bronze Dungeons.");
        addItemLore(AetherItems.SILVER_DUNGEON_KEY, "A reflective key that is given to you by the Valkyrie Queen after defeating her. Use it to claim the treasure she left behind!");
        addItemLore(AetherItems.SKYROOT_AXE, "One of Aether's wooden tools, it can be used to double Skyroot Log drops. Simple but useful for beginners.");
        addBlockLore(AetherBlocks.SKYROOT_BED, "Crafted with cyan wool and skyroot planks. You can only sleep at night to skip night time, but during the day you can set your respawn point in the Aether. Cannot sleep while mobs are nearby.");
        addBlockLore(AetherBlocks.SKYROOT_BOOKSHELF, "Crafted from Skyroot Planks and Books. Bookshelves can be used to enhance the enchanting capabilities of an enchanting table.");
        addItemLore(AetherItems.SKYROOT_BUCKET, "A hand crafted bucket, used to contain Aechor poison, a nice Remedy or even just plain Water!");
        addBlockLore(AetherBlocks.SKYROOT_BUTTON, "Crafted from Skyroot Planks, a button used to activate mechanisms and redstone.");
        addBlockLore(AetherBlocks.SKYROOT_DOOR, "Crafted from Skyroot Planks, an ornate door helpful for keeping an enclosed and safe space without worry of monsters wandering in.");
        addBlockLore(AetherBlocks.SKYROOT_FENCE, "Crafted from Skyroot planks and Sticks. Great for keeping your livestock safe from wandering predators!");
        addBlockLore(AetherBlocks.SKYROOT_FENCE_GATE, "Crafted from Sticks and Skyroot planks. Gives a homely entrance and exit to your precious enclosures.");
        addItemLore(AetherItems.SKYROOT_HOE, "One of Aether's wooden tools, used to till dirt to allow for planting of crops. Famously underappreciated.");
        addBlockLore(AetherBlocks.SKYROOT_LEAVES, "These leaves generate with Skyroot Trees. They can drop Skyroot Saplings when decaying.");
        addBlockLore(AetherBlocks.SKYROOT_LOG, "These spawn with Skyroot Trees. They can be double dropped with Skyroot Axes. When put in a crafting table they will provide 4 Skyroot Planks.");
        addItemLore(AetherItems.SKYROOT_MILK_BUCKET, "A bucket full of fresh Milk, drink it to heal potion effects.");
        addItemLore(AetherItems.SKYROOT_PICKAXE, "One of Aether's wooden tools, when mining Holystone or Ambrosium ore, it will double the drops. This is even better when combined with an enchantment such as Fortune when mining Ambrosium Shards!");
        addBlockLore(AetherBlocks.SKYROOT_PLANKS, "Skyroot Planks can be made into various tools, blocks and items. They are crafted from Skyroot Logs, and make a great building material.");
        addBlockLore(AetherBlocks.SKYROOT_PRESSURE_PLATE, "Crafted from Skyroot Planks. A wooden pressure plate used to activate mechanisms and restone.");
        addItemLore(AetherItems.SKYROOT_POISON_BUCKET, "A Skyroot Bucket that has been filled with a deadly poison. Better not drink it! It can be used to craft Poison Darts and Dart Shooters. Enchant it to cure the poison in it.");
        addItemLore(AetherItems.SKYROOT_REMEDY_BUCKET, "A Skyroot Bucket containing a soothing remedy. It has a strong smell. Drinking this cures deadly poison, and prevents it for a short time.");
        addBlockLore(AetherBlocks.SKYROOT_SAPLING, "These small green saplings will grow into Skyroot Trees. They can be grown faster with Bone Meal.");
        addItemLore(AetherItems.SKYROOT_SHOVEL, "One of Aether's wooden tools, it doubles Aether dirt and Quicksoil drops.");
        addBlockLore(AetherBlocks.SKYROOT_SIGN, "Crafted from Skyroot Planks and Sticks. A helpful sign perfect for writing messages and directions on.");
        addBlockLore(AetherBlocks.SKYROOT_SLAB, "Crafted from Skyroot. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building’s roofing!");
        addBlockLore(AetherBlocks.SKYROOT_STAIRS, "Crafted from Skyroot Planks. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addItemLore(AetherItems.SKYROOT_STICK, "Crafted from Skyroot Planks. Used to create various aether tools and items. Nothing else too special about it.");
        addItemLore(AetherItems.SKYROOT_SWORD, "One of Aether's wooden tools, it has a low amount of durability, and doubles mob drops. It makes an excellent food collected when combined with enchantments such as Looting and Fire Aspect.");
        addBlockLore(AetherBlocks.SKYROOT_TRAPDOOR, "Crafted from Skyroot Planks. A Trapdoor useful for covering entryways one block wide. Often used to add extra protection to staircases.");
        addItemLore(AetherItems.SKYROOT_WATER_BUCKET, "A Skyroot Bucket that is filled to the brim with Water.");
        addBlockLore(AetherBlocks.STRIPPED_SKYROOT_LOG, "A Skyroot Log that has had its bark stripped away with an Axe. When put in a crafting table they will provide 4 Skyroot Planks.");
        addBlockLore(AetherBlocks.STRIPPED_SKYROOT_WOOD, "A Skyroot Log that has had its bark stripped away with an Axe. When put in a crafting table they will provide 4 Skyroot Planks. Crafted to be smooth on all sides.");
        addBlockLore(AetherBlocks.SUN_ALTAR, "An Altar containing the power to control the Sun itself! It is dropped by the Sun Spirit after you defeat him. Use it to control the time of day.");
        addItemLore(AetherItems.SWET_CAPE, "A common cape that is found in Bronze Dungeons. It allows for the wearer to ride Swets, as they become friendly when they see someone wearing it.");
        addItemLore(AetherItems.SWET_BALL, "A gooey orb that is dropped from Blue Swets. It can be used to fertilize soils. Another use is to put it alongside string to make a lead.");
        addBlockLore(AetherBlocks.TREASURE_CHEST, "A treasure chest, these are found after defeating a Bronze, Silver or Gold dungeon. Any of the keys can open these, and the specific dungeon's loot will pop out.");
        addItemLore(AetherItems.VALKYRIE_AXE, "A tool unique to the Silver Dungeon, This Axe has a very far reach, and very high attack power, you can use this to your advantage, but be warned, the attack cooldown is very high on this.");
        addItemLore(AetherItems.VALKYRIE_BOOTS, "An armor unique to the Silver Dungeon, when fully worn, you are granted temporary flight. Quite useful for getting to high places.");
        addItemLore(AetherItems.VALKYRIE_CAPE, "A rare cape that is found in Silver Dungeons. When worn, the wearer becomes lighter than air and can descend slowly.");
        addItemLore(AetherItems.VALKYRIE_CHESTPLATE, "An armor unique to the Silver Dungeon, when fully worn, you are granted temporary flight. Quite useful for getting to high places.");
        addItemLore(AetherItems.VALKYRIE_GLOVES, "An armor unique to the Silver Dungeon. The gloves are needed to complete the armor set, which grants temporary flight.");
        addItemLore(AetherItems.VALKYRIE_HELMET, "An armor unique to the Silver Dungeon, when fully worn, you are granted temporary flight. Quite useful for getting to high places.");
        addItemLore(AetherItems.VALKYRIE_HOE, "A tool unique to the Silver Dungeon, This hoe has incredibly far reach, allowing you to pointlessly till dirt from a safe distance.");
        addItemLore(AetherItems.VALKYRIE_LANCE, "A tool unique to the Silver Dungeon, this long range weapon is very good for defeating Zephyrs, and Valkyrie Queens.");
        addItemLore(AetherItems.VALKYRIE_LEGGINGS, "An armor unique to the Silver Dungeon, when fully worn, you are granted temporary flight. Quite useful for getting to high places.");
        addItemLore(AetherItems.VALKYRIE_PICKAXE, "A tool unique to the Silver Dungeon, this pickaxe is very useful when it comes to mining blocks from under islands, as it has a very far reach, almost double the normal reach! It is also quite useful for fighting the Slider.");
        addItemLore(AetherItems.VALKYRIE_SHOVEL, "A tool unique to the Silver Dungeon, this shovel can help you reach Quicksoil from a safer distance, quite useful wouldn't you say?");
        addItemLore(AetherItems.VAMPIRE_BLADE, "A mysterious sword that has life-stealing abilities. Holding it just makes you feel creepily empty...");
        addItemLore(AetherItems.VICTORY_MEDAL, "Proof of defeating a lesser Valkyrie. Use these to prove to the Queen you are worthy enough to fight her!");
        addItemLore(AetherItems.WHITE_APPLE, "One of the only known cures for the Aether's deadly poison. You can find them on Crystal Trees.");
        addItemLore(AetherItems.WHITE_CAPE, "A light and fluffy cape that is made from White Wool.");
        addBlockLore(AetherBlocks.WHITE_FLOWER, "These extremely good smelling roses can make great gifts to a friend or loved one. They spawn in large groups around the Aether.");
        addItemLore(AetherItems.WHITE_MOA_EGG, "An Egg laid by a White Moa. Hatching this provides a White Moa with 4 mid-air jumps. This Moa is decently rare.");
        addItemLore(AetherItems.YELLOW_CAPE, "A bright Yellow Cape that is crafted using Yellow Wool.");
        addItemLore(AetherItems.ZANITE_AXE, "One of Aether's mid-tier tools, you can mine wood faster than stone, and it will get even faster as the tool is worn down.");
        addBlockLore(AetherBlocks.ZANITE_BLOCK, "A block of compacted Zanite Gemstones. They can power beacons, and just have a sleek look to them.");
        addItemLore(AetherItems.ZANITE_BOOTS, "Part of the Zanite Armor set, the protection it grants increases as the durability decreases.");
        addItemLore(AetherItems.ZANITE_CHESTPLATE, "Part of the Zanite Armor set, the protection it grants increases as the durability decreases.");
        addItemLore(AetherItems.ZANITE_GEMSTONE, "Aether's version of Iron. These shiny purple gems can be made into tools and armor, that increase in strength when used.");
        addItemLore(AetherItems.ZANITE_GLOVES, "Part of the Zanite Armor set, it is needed to complete the Zanite Armor set.");
        addItemLore(AetherItems.ZANITE_HOE, "One of Aether's mid-tier tools, it has more durability than the Holystone hoe, and it will get even faster at mining very oddly specific blocks as the tool is worn down.");
        addItemLore(AetherItems.ZANITE_HELMET, "Part of the Zanite Armor set, the protection it grants increases as the durability decreases.");
        addItemLore(AetherItems.ZANITE_LEGGINGS, "Part of the Zanite Armor set, the protection it grants increases as the durability decreases.");
        addBlockLore(AetherBlocks.ZANITE_ORE, "A slightly rare ore that drops Zanite Gemstones when broken with Stone tools.");
        addItemLore(AetherItems.ZANITE_PENDANT, "A pendant which allows you to mine faster. It doesn't last for long, so use it wisely!");
        addItemLore(AetherItems.ZANITE_PICKAXE, "One of Aether's mid-tier tools, it mines what Iron can, except as it's durability decreases, it gets faster. Be careful though, as when it's repaired it will be as slow as before!");
        addItemLore(AetherItems.ZANITE_RING, "A ring which allows you to mine faster. It doesn't last for long, so use it wisely!");
        addItemLore(AetherItems.ZANITE_SHOVEL, "One of Aether's mid-tier tools, as you mine dirt and Quicksoil, the durability will decrease, and get faster. If you wait long enough it will start to break blocks instantly! Be careful repairing it, as it will be slower when there is more durability.");
        addItemLore(AetherItems.ZANITE_SWORD, "One of Aether's mid-tier tools, the attack damage on this sword starts at wood level, and will get to Diamond level when it's durability is low.");
        addItemLore(AetherItems.MUSIC_DISC_AETHER_TUNE, "A music disc that plays Aether Tune by Noisestorm.");
        addItemLore(AetherItems.MUSIC_DISC_ASCENDING_DAWN, "A music disc that plays Ascending Dawn by Emile van Krieken.");
        addItemLore(AetherItems.MUSIC_DISC_LEGACY, "A music disc that plays Legacy by Jon Lachney.");
        addItemLore(AetherItems.MUSIC_DISC_WELCOMING_SKIES, "A music disc that plays Welcoming Skies by Voyed.");
    }
}
