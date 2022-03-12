package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.Item;
import net.minecraft.tags.*;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraftforge.common.Tags;

public class AetherTags {
	public static class Blocks {
		public static final Tags.IOptionalNamedTag<Block> AETHER_PORTAL_BLOCKS = tag("aether_portal_blocks");
		public static final Tags.IOptionalNamedTag<Block> AETHER_ISLAND_BLOCKS = tag("aether_island_blocks");
		public static final Tags.IOptionalNamedTag<Block> ENCHANTABLE_GRASS_BLOCKS = tag("enchantable_grass_blocks");
		public static final Tags.IOptionalNamedTag<Block> AETHER_DIRT = tag("aether_dirt");
		public static final Tags.IOptionalNamedTag<Block> HOLYSTONE = tag("holystone");
		public static final Tags.IOptionalNamedTag<Block> AERCLOUDS = tag("aerclouds");
		public static final Tags.IOptionalNamedTag<Block> SKYROOT_LOGS = tag("skyroot_logs");
		public static final Tags.IOptionalNamedTag<Block> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
		public static final Tags.IOptionalNamedTag<Block> AEROGEL = tag("aerogel");
		public static final Tags.IOptionalNamedTag<Block> DUNGEON_BLOCKS = tag("dungeon_blocks");
		public static final Tags.IOptionalNamedTag<Block> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
		public static final Tags.IOptionalNamedTag<Block> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");
		public static final Tags.IOptionalNamedTag<Block> AETHER_ANIMALS_SPAWNABLE_ON = tag("aether_animals_spawnable_on");
		public static final Tags.IOptionalNamedTag<Block> AERWHALE_SPAWNABLE_ON = tag("aerwhale_spawnable_on");
		public static final Tags.IOptionalNamedTag<Block> SWET_SPAWNABLE_ON = tag("swet_spawnable_on");
		public static final Tags.IOptionalNamedTag<Block> AECHOR_PLANT_SPAWNABLE_ON = tag("aechor_plant_spawnable_on");
		public static final Tags.IOptionalNamedTag<Block> ZEPHYR_SPAWNABLE_ON = tag("zephyr_spawnable_on");
		public static final Tags.IOptionalNamedTag<Block> COCKATRICE_SPAWNABLE_BLACKLIST = tag("cockatrice_spawnable_blacklist");

		private static Tags.IOptionalNamedTag<Block> tag(String name) {
			return BlockTags.createOptional(new ResourceLocation(Aether.MODID, name));
		}

		public static void init() {}
	}
	
	public static class Items {
		public static final Tags.IOptionalNamedTag<Item> AETHER_DIRT = tag("aether_dirt");
		public static final Tags.IOptionalNamedTag<Item> HOLYSTONE = tag("holystone");
		public static final Tags.IOptionalNamedTag<Item> AERCLOUDS = tag("aerclouds");
		public static final Tags.IOptionalNamedTag<Item> SKYROOT_LOGS = tag("skyroot_logs");
		public static final Tags.IOptionalNamedTag<Item> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
		public static final Tags.IOptionalNamedTag<Item> AEROGEL = tag("aerogel");
		public static final Tags.IOptionalNamedTag<Item> DUNGEON_BLOCKS = tag("dungeon_blocks");
		public static final Tags.IOptionalNamedTag<Item> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
		public static final Tags.IOptionalNamedTag<Item> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");

		public static final Tags.IOptionalNamedTag<Item> PLANKS_CRAFTING = tag("planks_crafting");
		public static final Tags.IOptionalNamedTag<Item> STONE_CRAFTING = tag("stone_crafting");

		public static final Tags.IOptionalNamedTag<Item> BANNED_IN_AETHER = tag("banned_in_aether");
		public static final Tags.IOptionalNamedTag<Item> AETHER_PORTAL_ACTIVATION_ITEMS = tag("aether_portal_activation_items");
		public static final Tags.IOptionalNamedTag<Item> BOOK_OF_LORE_MATERIALS = tag("book_of_lore_materials");
		public static final Tags.IOptionalNamedTag<Item> SKYROOT_STICKS = tag("skyroot_stick");
		public static final Tags.IOptionalNamedTag<Item> SKYROOT_TOOLS = tag("skyroot_tools");
		public static final Tags.IOptionalNamedTag<Item> HOLYSTONE_TOOLS = tag("holystone_tools");
		public static final Tags.IOptionalNamedTag<Item> ZANITE_TOOLS = tag("zanite_tools");
		public static final Tags.IOptionalNamedTag<Item> GRAVITITE_TOOLS = tag("gravitite_tools");
		public static final Tags.IOptionalNamedTag<Item> VALKYRIE_TOOLS = tag("valkyrie_tools");
		public static final Tags.IOptionalNamedTag<Item> GOLDEN_AMBER_HARVESTERS = tag("golden_amber_harvesters");
		public static final Tags.IOptionalNamedTag<Item> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
		public static final Tags.IOptionalNamedTag<Item> PIG_DROPS = tag("pig_drops");
		public static final Tags.IOptionalNamedTag<Item> DARTS = tag("darts");
		public static final Tags.IOptionalNamedTag<Item> DART_SHOOTERS = tag("dart_shooters");
		public static final Tags.IOptionalNamedTag<Item> DEPLOYABLE_PARACHUTES = tag("deployable_parachutes");
		public static final Tags.IOptionalNamedTag<Item> DUNGEON_KEYS = tag("dungeon_keys");
		public static final Tags.IOptionalNamedTag<Item> ACCEPTED_MUSIC_DISCS = tag("accepted_music_discs");
		public static final Tags.IOptionalNamedTag<Item> SAVE_NBT_IN_RECIPE = tag("save_nbt_in_recipe");
		public static final Tags.IOptionalNamedTag<Item> MOA_EGGS = tag("moa_eggs");

		public static final Tags.IOptionalNamedTag<Item> PHYG_TEMPTATION_ITEMS = tag("phyg_temptation_items");
		public static final Tags.IOptionalNamedTag<Item> FLYING_COW_TEMPTATION_ITEMS = tag("flying_cow_temptation_items");
		public static final Tags.IOptionalNamedTag<Item> SHEEPUFF_TEMPTATION_ITEMS = tag("sheepuff_temptation_items");
		public static final Tags.IOptionalNamedTag<Item> AERBUNNY_TEMPTATION_ITEMS = tag("aerbunny_temptation_items");
		public static final Tags.IOptionalNamedTag<Item> MOA_TEMPTATION_ITEMS = tag("moa_temptation_items");
		public static final Tags.IOptionalNamedTag<Item> MOA_FOOD_ITEMS = tag("moa_food_items");

		public static final Tags.IOptionalNamedTag<Item> AETHER_RING = curio("aether_ring");
		public static final Tags.IOptionalNamedTag<Item> AETHER_PENDANT = curio("aether_pendant");
		public static final Tags.IOptionalNamedTag<Item> AETHER_GLOVES = curio("aether_gloves");
		public static final Tags.IOptionalNamedTag<Item> AETHER_CAPE = curio("aether_cape");
		public static final Tags.IOptionalNamedTag<Item> AETHER_ACCESSORY = curio("aether_accessory");
		public static final Tags.IOptionalNamedTag<Item> AETHER_SHIELD = curio("aether_shield");

		private static Tags.IOptionalNamedTag<Item> tag(String name) {
			return ItemTags.createOptional(new ResourceLocation(Aether.MODID, name));
		}

		private static Tags.IOptionalNamedTag<Item> curio(String name) {
			return ItemTags.createOptional(new ResourceLocation(CuriosApi.MODID, name));
		}

		public static void init() {}
	}

	public static class Entities {
		public static final Tags.IOptionalNamedTag<EntityType<?>> PIGS = tag("pigs");
		public static final Tags.IOptionalNamedTag<EntityType<?>> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
		public static final Tags.IOptionalNamedTag<EntityType<?>> DEFLECTABLE_PROJECTILES = tag("deflectable_projectiles");
		public static final Tags.IOptionalNamedTag<EntityType<?>> SWET_TARGETS = tag("swet_targets");
		public static final Tags.IOptionalNamedTag<EntityType<?>> AECHOR_PLANT_TARGETS = tag("aechor_plant_targets");

		private static Tags.IOptionalNamedTag<EntityType<?>> tag(String name) {
			return EntityTypeTags.createOptional(new ResourceLocation(Aether.MODID, name));
		}

		public static void init() {}
	}

	public static class Fluids {
		public static final Tags.IOptionalNamedTag<Fluid> FREEZABLE_TO_AEROGEL = tag("freezable_to_aerogel");

		private static Tags.IOptionalNamedTag<Fluid> tag(String name) {
			return FluidTags.createOptional(new ResourceLocation(Aether.MODID, name));
		}

		public static void init() {}
	}

	// Classloads all of the tags.
	public static void init() {
		Blocks.init();
		Items.init();
		Entities.init();
		Fluids.init();
	}
}
