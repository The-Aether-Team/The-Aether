package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.Item;
import net.minecraft.tags.*;
import net.minecraft.resources.ResourceLocation;

public class AetherTags
{
	public static class Blocks
	{
		public static final TagKey<Block> AETHER_PORTAL_BLOCKS = tag("aether_portal_blocks");
		public static final TagKey<Block> AETHER_ISLAND_BLOCKS = tag("aether_island_blocks");
		public static final TagKey<Block> ENCHANTABLE_GRASS_BLOCKS = tag("enchantable_grass_blocks");
		public static final TagKey<Block> AETHER_DIRT = tag("aether_dirt");
		public static final TagKey<Block> HOLYSTONE = tag("holystone");
		public static final TagKey<Block> AERCLOUDS = tag("aerclouds");
		public static final TagKey<Block> SKYROOT_LOGS = tag("skyroot_logs");
		public static final TagKey<Block> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
		public static final TagKey<Block> AEROGEL = tag("aerogel");
		public static final TagKey<Block> DUNGEON_BLOCKS = tag("dungeon_blocks");
		public static final TagKey<Block> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
		public static final TagKey<Block> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");
		public static final TagKey<Block> AETHER_ANIMALS_SPAWNABLE_ON = tag("aether_animals_spawnable_on");
		public static final TagKey<Block> AERWHALE_SPAWNABLE_ON = tag("aerwhale_spawnable_on");
		public static final TagKey<Block> SWET_SPAWNABLE_ON = tag("swet_spawnable_on");
		public static final TagKey<Block> AECHOR_PLANT_SPAWNABLE_ON = tag("aechor_plant_spawnable_on");
		public static final TagKey<Block> ZEPHYR_SPAWNABLE_ON = tag("zephyr_spawnable_on");
		public static final TagKey<Block> COCKATRICE_SPAWNABLE_BLACKLIST = tag("cockatrice_spawnable_blacklist");

		public static void init() { }

		private static TagKey<Block> tag(String name) {
			return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Aether.MODID, name));
		}
	}

	public static class Items
	{
		public static final TagKey<Item> AETHER_DIRT = tag("aether_dirt");
		public static final TagKey<Item> HOLYSTONE = tag("holystone");
		public static final TagKey<Item> AERCLOUDS = tag("aerclouds");
		public static final TagKey<Item> SKYROOT_LOGS = tag("skyroot_logs");
		public static final TagKey<Item> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
		public static final TagKey<Item> AEROGEL = tag("aerogel");
		public static final TagKey<Item> DUNGEON_BLOCKS = tag("dungeon_blocks");
		public static final TagKey<Item> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
		public static final TagKey<Item> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");

		public static final TagKey<Item> PLANKS_CRAFTING = tag("planks_crafting");
		public static final TagKey<Item> STONE_CRAFTING = tag("stone_crafting");

		public static final TagKey<Item> BANNED_IN_AETHER = tag("banned_in_aether");
		public static final TagKey<Item> AETHER_PORTAL_ACTIVATION_ITEMS = tag("aether_portal_activation_items");
		public static final TagKey<Item> BOOK_OF_LORE_MATERIALS = tag("book_of_lore_materials");
		public static final TagKey<Item> SKYROOT_STICKS = tag("skyroot_stick");
		public static final TagKey<Item> SKYROOT_TOOLS = tag("skyroot_tools");
		public static final TagKey<Item> HOLYSTONE_TOOLS = tag("holystone_tools");
		public static final TagKey<Item> ZANITE_TOOLS = tag("zanite_tools");
		public static final TagKey<Item> GRAVITITE_TOOLS = tag("gravitite_tools");
		public static final TagKey<Item> VALKYRIE_TOOLS = tag("valkyrie_tools");
		public static final TagKey<Item> GOLDEN_AMBER_HARVESTERS = tag("golden_amber_harvesters");
		public static final TagKey<Item> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
		public static final TagKey<Item> PIG_DROPS = tag("pig_drops");
		public static final TagKey<Item> DARTS = tag("darts");
		public static final TagKey<Item> DART_SHOOTERS = tag("dart_shooters");
		public static final TagKey<Item> DEPLOYABLE_PARACHUTES = tag("deployable_parachutes");
		public static final TagKey<Item> DUNGEON_KEYS = tag("dungeon_keys");
		public static final TagKey<Item> ACCEPTED_MUSIC_DISCS = tag("accepted_music_discs");
		public static final TagKey<Item> SAVE_NBT_IN_RECIPE = tag("save_nbt_in_recipe");
		public static final TagKey<Item> MOA_EGGS = tag("moa_eggs");

		public static final TagKey<Item> PHYG_TEMPTATION_ITEMS = tag("phyg_temptation_items");
		public static final TagKey<Item> FLYING_COW_TEMPTATION_ITEMS = tag("flying_cow_temptation_items");
		public static final TagKey<Item> SHEEPUFF_TEMPTATION_ITEMS = tag("sheepuff_temptation_items");
		public static final TagKey<Item> AERBUNNY_TEMPTATION_ITEMS = tag("aerbunny_temptation_items");
		public static final TagKey<Item> MOA_TEMPTATION_ITEMS = tag("moa_temptation_items");
		public static final TagKey<Item> MOA_FOOD_ITEMS = tag("moa_food_items");

		public static void init() { }

		private static TagKey<Item> tag(String name) {
			return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Aether.MODID, name));
		}
	}

	public static class Entities
	{
		public static final TagKey<EntityType<?>> PIGS = tag("pigs");
		public static final TagKey<EntityType<?>> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
		public static final TagKey<EntityType<?>> DEFLECTABLE_PROJECTILES = tag("deflectable_projectiles");
		public static final TagKey<EntityType<?>> SWET_TARGETS = tag("swet_target");
		public static final TagKey<EntityType<?>> AECHOR_PLANT_TARGETS = tag("aechor_plant_targets");

		public static void init() { }

		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Aether.MODID, name));
		}
	}

	public static class Fluids
	{
		public static final TagKey<Fluid> FREEZABLE_TO_AEROGEL = tag("freezable_to_aerogel");

		public static void init() { }

		private static TagKey<Fluid> tag(String name) {
			return TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(Aether.MODID, name));
		}
	}

	// Classloads all of the tags.
	public static void init() {
		Blocks.init();
		Items.init();
		Entities.init();
		Fluids.init();
	}
}