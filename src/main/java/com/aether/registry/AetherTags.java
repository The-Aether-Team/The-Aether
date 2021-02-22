package com.aether.registry;

import com.aether.Aether;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class AetherTags
{
	public static class Blocks
	{
		public static final ITag.INamedTag<Block> AETHER_DIRT = tag("aether_dirt");
		public static final ITag.INamedTag<Block> SKYROOT_LOGS = tag("skyroot_logs");
		public static final ITag.INamedTag<Block> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
		public static final ITag.INamedTag<Block> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
		public static final ITag.INamedTag<Block> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");

		private static ITag.INamedTag<Block> tag(String name) {
			return BlockTags.makeWrapperTag(new ResourceLocation(Aether.MODID, name).toString());
		}
	}
	
	public static class Items
	{
		public static final ITag.INamedTag<Item> SKYROOT_STICK = tag("skyroot_stick");
		public static final ITag.INamedTag<Item> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
		public static final ITag.INamedTag<Item> SKYROOT_TOOLS = tag("skyroot_tools");
		public static final ITag.INamedTag<Item> ZANITE_TOOLS = tag("zanite_tools");
		public static final ITag.INamedTag<Item> VALKYRIE_TOOLS = tag("valkyrie_tools");
		public static final ITag.INamedTag<Item> GRAVITITE_TOOLS = tag("gravitite_tools");
		public static final ITag.INamedTag<Item> GOLDEN_AMBER_HARVESTERS = tag("golden_amber_harvesters");
		public static final ITag.INamedTag<Item> BANNED_IN_AETHER = tag("banned_in_aether");
		public static final ITag.INamedTag<Item> DUNGEON_KEYS = tag("dungeon_keys");

		private static ITag.INamedTag<Item> tag(String name) {
			return ItemTags.makeWrapperTag(new ResourceLocation(Aether.MODID, name).toString());
		}
	}

	public static class Entities
	{
		public static final ITag.INamedTag<EntityType<?>> NO_SKYROOT_DOUBLE_DROPS = tag("no_skyroot_double_drops");
		public static final ITag.INamedTag<EntityType<?>> PIGS = tag("pigs");

		private static ITag.INamedTag<EntityType<?>> tag(String name) {
			return EntityTypeTags.getTagById(new ResourceLocation(Aether.MODID, name).toString());
		}
	}
}
