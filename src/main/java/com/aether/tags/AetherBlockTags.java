package com.aether.tags;

import com.aether.Aether;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class AetherBlockTags {
	
	public static final ITag.INamedTag<Block> SKYROOT_LOGS = tag("skyroot_logs");
	public static final ITag.INamedTag<Block> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
	public static final ITag.INamedTag<Block> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
	public static final ITag.INamedTag<Block> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");
	public static final ITag.INamedTag<Block> AETHER_DIRT = tag("aether_dirt");

	private static ITag.INamedTag<Block> tag(String name) {
		return BlockTags.makeWrapperTag(new ResourceLocation(Aether.MODID, name).toString());
	}
	
}
