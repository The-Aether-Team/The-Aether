package com.aether;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class AetherTags {

	public static class Blocks {
		
		@SuppressWarnings("unused")
		private static ITag.INamedTag<Block> tag(String name) {
			return BlockTags.makeWrapperTag(new ResourceLocation(Aether.MODID, name).toString());
		}
		
	}
	
	public static class Items {
		
		public static final ITag.INamedTag<Item> SKYROOT_TOOLS = tag("skyroot_tools");

		private static ITag.INamedTag<Item> tag(String name) {
			return ItemTags.makeWrapperTag(new ResourceLocation(Aether.MODID, name).toString());
		}
		
	}
	
}
