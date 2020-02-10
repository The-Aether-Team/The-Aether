package com.aether.block;

import com.aether.Aether;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherBlocks {

	public static final Block //
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
		AMBROSIUM_WALL_TORCH = null,
		AETHER_PORTAL = null,
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
	
	public static final class Decorations {
		
		private static ChestBlock dungeon_chest = (ChestBlock) Blocks.CHEST;
		
		public static ChestBlock getDungeonChest() {
			return dungeon_chest == null? (ChestBlock) Blocks.CHEST : dungeon_chest;
		}
		
		public static void setDungeonChest(ChestBlock block) {
			dungeon_chest = block;
		}
		
		private static LogBlock crystal_log = (LogBlock) SKYROOT_LOG;
		
		public static LogBlock getCrystalLog() {
			return crystal_log == null? (LogBlock) SKYROOT_LOG : crystal_log;
		}
		
		public static void setCrystalLog(LogBlock block) {
			crystal_log = block;
		}
		
		private static Block tall_grass = Blocks.GRASS;
		
		public static Block getTallGrass() {
			return tall_grass == null? Blocks.GRASS : tall_grass;
		}
		
		public static void setTallGrass(Block block) {
			tall_grass = block;
		}
		
		private Decorations() {}
		
	}
	
	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static final class Registration {
		
		private static IForgeRegistry<Block> registry; 
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			registry = event.getRegistry();
			
			register("aether_grass_block", new AetherGrassBlock(Block.Properties.from(Blocks.GRASS_BLOCK)));
			register("enchanted_aether_grass_block", new EnchantedAetherGrassBlock(Block.Properties.from(Blocks.GRASS_BLOCK)));
			register("aether_dirt", new DefaultAetherDoubleDropBlock(Block.Properties.from(Blocks.DIRT)));
			register("holystone", new DefaultAetherDoubleDropBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(0.5f)));
			register("mossy_holystone", new DefaultAetherDoubleDropBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(0.5f)));
			register("holystone_bricks", new Block(Block.Properties.from(Blocks.STONE_BRICKS).hardnessAndResistance(0.5f, 10.0f)));
			register("cold_aercloud", new AercloudBlock(Block.Properties.create(Material.ICE).hardnessAndResistance(0.2f).sound(SoundType.CLOTH)));
			register("blue_aercloud", new BouncyAercloudBlock(TintedAercloudBlock.COLOR_BLUE_OLD, TintedAercloudBlock.COLOR_BLUE_NEW, Block.Properties.create(Material.ICE).hardnessAndResistance(0.2f).sound(SoundType.CLOTH)));
			register("golden_aercloud", new TintedAercloudBlock(TintedAercloudBlock.COLOR_GOLDEN_OLD, TintedAercloudBlock.COLOR_GOLDEN_NEW, Block.Properties.create(Material.ICE).hardnessAndResistance(0.2f).sound(SoundType.CLOTH)));
			register("pink_aercloud", new HealingAercloudBlock(Block.Properties.create(Material.ICE).hardnessAndResistance(0.2f).sound(SoundType.CLOTH)));
			
			registry = null;
		}
	
		private static Block register(String name, Block block) {
			block.setRegistryName(Aether.MODID, name);
			
			registry.register(block);
			
			return block;
		}
		
	}

}
