package com.gildedgames.aether.common.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherItemGroups
{
	public static final ItemGroup AETHER_BLOCKS = new ItemGroup("aether_blocks") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherBlocks.AETHER_GRASS_BLOCK.get());
		}
	};
	public static final ItemGroup AETHER_TOOLS = new ItemGroup("aether_tools") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.GRAVITITE_PICKAXE.get());
		}
	};
	public static final ItemGroup AETHER_WEAPONS = new ItemGroup("aether_weapons") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.GRAVITITE_SWORD.get());
		}
	};
	public static final ItemGroup AETHER_ARMOR = new ItemGroup("aether_armor") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.GRAVITITE_HELMET.get());
		}
	};
	public static final ItemGroup AETHER_FOOD = new ItemGroup("aether_food") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.BLUE_BERRY.get());
		}
	};
	public static final ItemGroup AETHER_ACCESSORIES = new ItemGroup("aether_accessories") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.GRAVITITE_GLOVES.get());
		}
	};
	public static final ItemGroup AETHER_MATERIALS = new ItemGroup("aether_materials") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.AMBROSIUM_SHARD.get());
		}
	};
	public static final ItemGroup AETHER_MISC = new ItemGroup("aether_misc") {
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.BRONZE_DUNGEON_KEY.get());
		}
	};
}