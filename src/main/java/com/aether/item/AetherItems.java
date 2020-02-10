package com.aether.item;

import com.aether.Aether;
import com.aether.block.AetherBlocks;
import com.aether.block.TintedAercloudBlock;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherItems {
	
	// Items for blocks
	public static final Item
		AETHER_GRASS_BLOCK = null,
		ENCHANTED_AETHER_GRASS_BLOCK = null,
		AETHER_DIRT = null,
		HOLYSTONE = null,
		MOSSY_HOLYSTONE = null,
		HOLYSTONE_BRICKS = null,
		COLD_AERCLOUD = null,
		BLUE_AERCLOUD = null,
		GOLDEN_AERCLOUD = null,
		PINK_AERCLOUD = null,
		QUICKSOIL = null,
		ICESTONE = null,
		AMBROSIUM_ORE = null,
		ZANITE_ORE = null,
		GRAVITITE_ORE = null,
		SKYROOT_LEAVES = null,
		GOLDEN_OAK_LEAVES = null,
		CRYSTAL_LEAVES = null,
		HOLIDAY_LEAVES = null,
		SKYROOT_LOG = null,
		GOLDEN_OAK_LOG = null,
		SKYROOT_PLANKS = null,
		QUICKSOIL_GLASS = null,
		AEROGEL = null,
		ENCHANTED_GRAVITITE = null,
		ZANITE_BLOCK = null,
		BERRY_BUSH = null,
		ENCHANTER = null,
		FREEZER = null,
		INCUBATOR = null,
		AMBROSIUM_TORCH = null,
		CHEST_MIMIC = null,
		TREASURE_CHEST = null,
		CARVED_STONE = null,
		SENTRY_STONE = null,
		ANGELIC_STONE = null,
		LIGHT_ANGELIC_STONE = null,
		HELLFIRE_STONE = null,
		LIGHT_HELLFIRE_STONE = null,
		LOCKED_CARVED_STONE = null,
		LOCKED_SENTRY_STONE = null,
		LOCKED_ANGELIC_STONE = null,
		LOCKED_LIGHT_ANGELIC_STONE = null,
		LOCKED_HELLFIRE_STONE = null,
		LOCKED_LIGHT_HELLFIRE_STONE = null,
		TRAPPED_CARVED_STONE = null,
		TRAPPED_SENTRY_STONE = null,
		TRAPPED_ANGELIC_STONE = null,
		TRAPPED_LIGHT_ANGELIC_STONE = null,
		TRAPPED_HELLFIRE_STONE = null,
		TRAPPED_LIGHT_HELLFIRE_STONE = null,
		PURPLE_FLOWER = null,
		WHITE_FLOWER = null,
		SKYROOT_SAPLING = null,
		GOLDEN_OAK_SAPLING = null,
		CRYSTAL_SAPLING = null,
		PILLAR = null,
		PILLAR_TOP = null,
		SKYROOT_FENCE = null,
		SKYROOT_FENCE_GATE = null,
		CARVED_STAIRS = null,
		ANGELIC_STAIRS = null,
		HELLFIRE_STAIRS = null,
		SKYROOT_STAIRS = null,
		HOLYSTONE_STAIRS = null,
		MOSSY_HOLYSTONE_STAIRS = null,
		HOLYSTONE_BRICK_STAIRS = null,
		AEROGEL_STAIRS = null,
		CARVED_SLAB = null,
		ANGELIC_SLAB = null,
		HELLFIRE_SLAB = null,
		SKYROOT_SLAB = null,
		HOLYSTONE_SLAB = null,
		MOSSY_HOLYSTONE_SLAB = null,
		HOLYSTONE_BRICK_SLAB = null,
		AEROGEL_SLAB = null,
		CARVED_WALL = null,
		ANGELIC_WALL = null,
		HELLFIRE_WALL = null,
		HOLYSTONE_WALL = null,
		MOSSY_HOLYSTONE_WALL = null,
		HOLYSTONE_BRICK_WALL = null,
		AEROGEL_WALL = null,
		PRESENT = null,
		SUN_ALTAR = null,
		SKYROOT_BOOKSHELF = null;
	
	// Items
	public static final Item
		ZANITE_GEMSTONE = null,
		AMBROSIUM_SHARD = null,
		GOLDEN_AMBER = null,
		AECHOR_PETAL = null,
		SWETTY_BALL = null,
		SKYROOT_PICKAXE = null,
		SKYROOT_AXE = null,
		SKYROOT_SHOVEL = null,
		SKYROOT_SWORD = null,
		HOLYSTONE_PICKAXE = null,
		HOLYSTONE_AXE = null,
		HOLYSTONE_SHOVEL = null,
		HOLYSTONE_SWORD = null,
		ZANITE_PICKAXE = null,
		ZANITE_AXE = null,
		ZANITE_SHOVEL = null,
		ZANITE_SWORD = null,
		GRAVITITE_PICKAXE = null,
		GRAVITITE_AXE = null,
		GRAVITITE_SHOVEL = null,
		GRAVITITE_SWORD = null,
		VALKYRIE_PICKAXE = null,
		VALKYRIE_AXE = null,
		VALKYRIE_SHOVEL = null,
		VALKYRIE_SWORD = null,
		ZANITE_HELMET = null,
		ZANITE_CHESTPLATE = null,
		ZANITE_LEGGINGS = null,
		ZANITE_BOOTS = null,
		GRAVITITE_HELMET = null,
		GRAVITITE_CHESTPLATE = null,
		GRAVITITE_LEGGINGS = null,
		GRAVITITE_BOOTS = null,
		NEPTUNE_HELMET = null,
		NEPTUNE_CHESTPLATE = null,
		NEPTUNE_LEGGINGS = null,
		NEPTUNE_BOOTS = null,
		PHOENIX_HELMET = null,
		PHOENIX_CHESTPLATE = null,
		PHOENIX_LEGGINGS = null,
		PHOENIX_BOOTS = null,
		OBSIDIAN_HELMET = null,
		OBSIDIAN_CHESTPLATE = null,
		OBSIDIAN_LEGGINGS = null,
		OBSIDIAN_BOOTS = null,
		VALKYRIE_HELMET = null,
		VALKYRIE_CHESTPLATE = null,
		VALKYRIE_LEGGINGS = null,
		VALKYRIE_BOOTS = null,
		BLUEBERRY = null,
		BLUE_GUMMY_SWET = null,
		GOLDEN_GUMMY_SWET = null,
		HEALING_STONE = null,
		WHITE_APPLE = null,
		GINGERBREAD_MAN = null,
		CANDY_CANE = null,
		ENCHANTED_BLUEBERRY = null,
		SKYROOT_STICK = null,
		VICTORY_MEDAL = null,
		BRONZE_DUNGEON_KEY = null,
		SILVER_DUNGEON_KEY = null,
		GOLD_DUNGEON_KEY = null,
		SKYROOT_BUCKET = null,
		SKYROOT_WATER_BUCKET = null,
		SKYROOT_POISON_BUCKET = null,
		SKYROOT_REMEDY_BUCKET = null,
		SKYROOT_MILK_BUCKET = null,
		CLOUD_PARACHUTE = null,
		GOLDEN_PARACHUTE = null,
		NATURE_STAFF = null,
		CLOUD_STAFF = null,
		MOA_EGG = null,
		DART_SHOOTER = null,
		PHOENIX_BOX = null,
		GOLDEN_DART = null,
		POISON_DART = null,
		ENCHANTED_DART = null,
		FLAMING_SWORD = null,
		LIGHTNING_SWORD = null,
		HOLY_SWORD = null,
		VAMPIRE_BLADE = null,
		PIG_SLAYER = null,
		CANDY_CANE_SWORD = null,
		NOTCH_HAMMER = null,
		VALKYRIE_LANCE = null,
		LEATHER_GLOVES = null,
		IRON_GLOVES = null,
		GOLDEN_GLOVES = null,
		CHAINMAIL_GLOVES = null,
		DIAMOND_GLOVES = null,
		ZANITE_GLOVES = null,
		GRAVITITE_GLOVES = null,
		NEPTUNE_GLOVES = null,
		PHOENIX_GLOVES = null,
		OBSIDIAN_GLOVES = null,
		VALKYRIE_GLOVES = null,
		IRON_RING = null,
		GOLDEN_RING = null,
		ZANITE_RING = null,
		ICE_RING = null,
		IRON_PENDANT = null,
		GOLDEN_PENDANT = null,
		ZANITE_PENDANT = null,
		ICE_PENDANT = null,
		RED_CAPE = null,
		BLUE_CAPE = null,
		YELLOW_CAPE = null,
		WHITE_CAPE = null,
		SWET_CAPE = null,
		INVISIBILITY_CAPE = null,
		AGILITY_CAPE = null,
		VALKYRIE_CAPE = null,
		GOLDEN_FEATHER = null,
		REGENERATION_STONE = null,
		IRON_BUBBLE = null,
		LIFE_SHARD = null,
		SENTRY_BOOTS = null,
		LIGHTNING_KNIFE = null,
		MUSIC_DISC_AETHER_TUNE = null,
		MUSIC_DISC_ASCENDING_DAWN = null,
		MUSIC_DISC_WELCOMING_SKIES = null,
		MUSIC_DISC_LEGACY = null,
		REPULSION_SHIELD = null,
		LORE_BOOK = null,
		DEVELOPER_STICK = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static final class Registration {
		
		private static IForgeRegistry<Item> registry;
		
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			registry = event.getRegistry();
			
			register(AetherBlocks.AETHER_GRASS_BLOCK, AetherItemGroups.AETHER_BLOCKS);
			register(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, AetherItemGroups.AETHER_BLOCKS);
			register(AetherBlocks.AETHER_DIRT, AetherItemGroups.AETHER_BLOCKS);
			register(AetherBlocks.HOLYSTONE, AetherItemGroups.AETHER_BLOCKS);
			register(AetherBlocks.MOSSY_HOLYSTONE, AetherItemGroups.AETHER_BLOCKS);
			register(AetherBlocks.HOLYSTONE_BRICKS, AetherItemGroups.AETHER_BLOCKS);
			register(AetherBlocks.COLD_AERCLOUD, AetherItemGroups.AETHER_BLOCKS);
			registerTintedAercloud(AetherBlocks.BLUE_AERCLOUD, AetherItemGroups.AETHER_BLOCKS);
			registerTintedAercloud(AetherBlocks.GOLDEN_AERCLOUD, AetherItemGroups.AETHER_BLOCKS);
			register(AetherBlocks.PINK_AERCLOUD, AetherItemGroups.AETHER_BLOCKS);
			
			registry = null;
		}
		
		private static TintedBlockItem registerTintedAercloud(Block block, ItemGroup itemGroup) {
			return registerTintedAercloud(block, new Item.Properties().group(itemGroup));
		}
		
		private static TintedBlockItem registerTintedAercloud(Block block, Item.Properties properties) {
			TintedAercloudBlock aercloud = (TintedAercloudBlock) block;
			return (TintedBlockItem) register(block.getRegistryName().getPath(), new TintedBlockItem(aercloud.getColor(false), aercloud.getColor(true), block, properties));
		}
		
		private static BlockItem register(Block block) {
			return register(block, new Item.Properties());
		}
		
		private static BlockItem register(Block block, ItemGroup itemGroup) {
			return register(block, new Item.Properties().group(itemGroup));
		}
		
		private static BlockItem register(Block block, Item.Properties properties) {
			return (BlockItem) register(block.getRegistryName().getPath(), new BlockItem(block, properties));
		}
		
		private static Item register(String name, Item item) {
			item.setRegistryName(Aether.MODID, name);
			
			registry.register(item);
			
			return item;
		}
		
	}
	
}
