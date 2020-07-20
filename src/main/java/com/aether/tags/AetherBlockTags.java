package com.aether.tags;

import com.aether.Aether;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class AetherBlockTags {
	
	public static final Tag<Block> SKYROOT_LOGS = tag("skyroot_logs");
	public static final Tag<Block> GOLDEN_OAK_LOGS = tag("golden_oak_logs");
	public static final Tag<Block> LOCKED_DUNGEON_BLOCKS = tag("locked_dungeon_blocks");
	public static final Tag<Block> TRAPPED_DUNGEON_BLOCKS = tag("trapped_dungeon_blocks");
	public static final Tag<Block> AETHER_DIRT = tag("aether_dirt");

	public static Tag<Block> tag(String name) {
		return BlockTags.getCollection().getOrCreate(new ResourceLocation(Aether.MODID, name));
	}
	
}
