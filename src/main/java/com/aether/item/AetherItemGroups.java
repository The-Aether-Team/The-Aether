package com.aether.item;

import com.aether.block.AetherBlocks;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherItemGroups {

	public static final ItemGroup AETHER_BLOCKS = new ItemGroup("aether_blocks") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(AetherBlocks.AETHER_GRASS_BLOCK);
		}
	};
	public static final ItemGroup AETHER_TOOLS = new ItemGroup("aether_tools") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(AetherItems.GRAVITITE_PICKAXE);
		}
	};
	public static final ItemGroup AETHER_COMBAT = new ItemGroup("aether_combat") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(AetherItems.GRAVITITE_SWORD);
		}
	};
	public static final ItemGroup AETHER_FOOD = new ItemGroup("aether_food") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(AetherItems.BLUEBERRY);
		}
	};
	public static final ItemGroup AETHER_ACCESSORIES = new ItemGroup("aether_accessories") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(AetherItems.GRAVITITE_GLOVES);
		}
	};
	public static final ItemGroup AETHER_MATERIALS = new ItemGroup("aether_materials") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(AetherItems.AMBROSIUM_SHARD);
		}
	};
	public static final ItemGroup AETHER_MISC = new ItemGroup("aether_misc") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(AetherItems.BRONZE_DUNGEON_KEY);
		}
	};
	
}
