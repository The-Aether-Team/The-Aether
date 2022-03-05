package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.Item;
import net.minecraft.tags.*;
import net.minecraft.resources.ResourceLocation;

public class AetherTags {
	public static class Blocks {
		public static final Tag.Named<Block> AETHER_PORTAL_BLOCKS = tag("aether_portal_blocks");
		public static final Tag.Named<Block> AETHER_ISLAND_BLOCKS = tag("aether_island_blocks");
		public static final Tag.Named<Block> ENCHANTABLE_GRASS_BLOCKS = tag("enchantable_grass_blocks");
		public static final Tag.Named<Block> AETHER_DIRT = tag("aether_dirt");
		public static final Tag.Named<Block> HOLYSTONE = tag("holystone");
		public static final Tag.Named<Block> AERCLOUDS = tag("aerclouds");
		public static final Tag.Named<Block> SKYROOT_LOGS = tag("skyroot_logs");
		public static final Tag.Named<Block> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
		public static final Tag.Named<Block> AEROGEL = tag("aerogel");
		public static final Tag.Named<Block> DUNGEON_BLOCKS = tag("dungeon_blocks");
		public static final Tag.Named<Block> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
		public static final Tag.Named<Block> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");
		public static final Tag.Named<Block> AETHER_ANIMALS_SPAWNABLE_ON = tag("aether_animals_spawnable_on");
		public static final Tag.Named<Block> AERWHALE_SPAWNABLE_ON = tag("aerwhale_spawnable_on");
		public static final Tag.Named<Block> SWET_SPAWNABLE_ON = tag("swet_spawnable_on");
		public static final Tag.Named<Block> AECHOR_PLANT_SPAWNABLE_ON = tag("aechor_plant_spawnable_on");
		public static final Tag.Named<Block> ZEPHYR_SPAWNABLE_ON = tag("zephyr_spawnable_on");
		public static final Tag.Named<Block> COCKATRICE_SPAWNABLE_BLACKLIST = tag("cockatrice_spawnable_blacklist");

		private static Tag.Named<Block> tag(String name) {
			return BlockTags.bind(new ResourceLocation(Aether.MODID, name).toString());
		}
	}
	
	public static class Items {
		public static final Tag.Named<Item> AETHER_DIRT = tag("aether_dirt");
		public static final Tag.Named<Item> HOLYSTONE = tag("holystone");
		public static final Tag.Named<Item> AERCLOUDS = tag("aerclouds");
		public static final Tag.Named<Item> SKYROOT_LOGS = tag("skyroot_logs");
		public static final Tag.Named<Item> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
		public static final Tag.Named<Item> AEROGEL = tag("aerogel");
		public static final Tag.Named<Item> DUNGEON_BLOCKS = tag("dungeon_blocks");
		public static final Tag.Named<Item> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
		public static final Tag.Named<Item> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");

		public static final Tag.Named<Item> PLANKS_CRAFTING = tag("planks_crafting");
		public static final Tag.Named<Item> STONE_CRAFTING = tag("stone_crafting");

		public static final Tag.Named<Item> BANNED_IN_AETHER = tag("banned_in_aether");
		public static final Tag.Named<Item> AETHER_PORTAL_ACTIVATION_ITEMS = tag("aether_portal_activation_items");
		public static final Tag.Named<Item> BOOK_OF_LORE_MATERIALS = tag("book_of_lore_materials");
		public static final Tag.Named<Item> SKYROOT_STICKS = tag("skyroot_stick");
		public static final Tag.Named<Item> SKYROOT_TOOLS = tag("skyroot_tools");
		public static final Tag.Named<Item> HOLYSTONE_TOOLS = tag("holystone_tools");
		public static final Tag.Named<Item> ZANITE_TOOLS = tag("zanite_tools");
		public static final Tag.Named<Item> GRAVITITE_TOOLS = tag("gravitite_tools");
		public static final Tag.Named<Item> VALKYRIE_TOOLS = tag("valkyrie_tools");
		public static final Tag.Named<Item> GOLDEN_AMBER_HARVESTERS = tag("golden_amber_harvesters");
		public static final Tag.Named<Item> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
		public static final Tag.Named<Item> PIG_DROPS = tag("pig_drops");
		public static final Tag.Named<Item> DARTS = tag("darts");
		public static final Tag.Named<Item> DART_SHOOTERS = tag("dart_shooters");
		public static final Tag.Named<Item> DEPLOYABLE_PARACHUTES = tag("deployable_parachutes");
		public static final Tag.Named<Item> DUNGEON_KEYS = tag("dungeon_keys");
		public static final Tag.Named<Item> ACCEPTED_MUSIC_DISCS = tag("accepted_music_discs");
		public static final Tag.Named<Item> SAVE_NBT_IN_RECIPE = tag("save_nbt_in_recipe");
		public static final Tag.Named<Item> MOA_EGGS = tag("moa_eggs");

		public static final Tag.Named<Item> PHYG_TEMPTATION_ITEMS = tag("phyg_temptation_items");
		public static final Tag.Named<Item> FLYING_COW_TEMPTATION_ITEMS = tag("flying_cow_temptation_items");
		public static final Tag.Named<Item> SHEEPUFF_TEMPTATION_ITEMS = tag("sheepuff_temptation_items");
		public static final Tag.Named<Item> AERBUNNY_TEMPTATION_ITEMS = tag("aerbunny_temptation_items");
		public static final Tag.Named<Item> MOA_TEMPTATION_ITEMS = tag("moa_temptation_items");
		public static final Tag.Named<Item> MOA_FOOD_ITEMS = tag("moa_food_items");

		private static Tag.Named<Item> tag(String name) {
			return ItemTags.bind(new ResourceLocation(Aether.MODID, name).toString());
		}
	}

	public static class Entities {
		public static final Tag.Named<EntityType<?>> PIGS = tag("pigs");
		public static final Tag.Named<EntityType<?>> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
		public static final Tag.Named<EntityType<?>> DEFLECTABLE_PROJECTILES = tag("deflectable_projectiles");
		public static final Tag.Named<EntityType<?>> SWET_TARGETS = tag("swet_targets");
		public static final Tag.Named<EntityType<?>> AECHOR_PLANT_TARGETS = tag("aechor_plant_targets");

		private static Tag.Named<EntityType<?>> tag(String name) {
			return EntityTypeTags.bind(new ResourceLocation(Aether.MODID, name).toString());
		}
	}

	public static class Fluids {
		public static final Tag.Named<Fluid> FREEZABLE_TO_AEROGEL = tag("freezable_to_aerogel");

		private static Tag.Named<Fluid> tag(String name) {
			return FluidTags.bind(new ResourceLocation(Aether.MODID, name).toString());
		}
	}
}
