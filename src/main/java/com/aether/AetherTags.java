package com.aether;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class AetherTags {

	public static class Blocks {
		
		@SuppressWarnings("unused")
		private static Tag<Block> tag(String name) {
			return BlockTags.getCollection().getOrCreate(new ResourceLocation(Aether.MODID, name));
		}
		
	}
	
	public static class Items {
		
		public static final Tag<Item> SKYROOT_TOOLS = tag("skyroot_tools");
		
		private static Tag<Item> tag(String name) {
			return ItemTags.getCollection().getOrCreate(new ResourceLocation(Aether.MODID, name));
		}
		
	}
	
}
