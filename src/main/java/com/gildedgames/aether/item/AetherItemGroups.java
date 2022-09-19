package com.gildedgames.aether.item;

import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class AetherItemGroups {
	public static final CreativeModeTab AETHER_BLOCKS = new CreativeModeTab("aether_blocks") {
		@Nonnull
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherBlocks.AETHER_GRASS_BLOCK.get());
		}
	};
	public static final CreativeModeTab AETHER_TOOLS = new CreativeModeTab("aether_tools") {
		@Nonnull
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.GRAVITITE_PICKAXE.get());
		}
	};
	public static final CreativeModeTab AETHER_WEAPONS = new CreativeModeTab("aether_weapons") {
		@Nonnull
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.GRAVITITE_SWORD.get());
		}
	};
	public static final CreativeModeTab AETHER_ARMOR = new CreativeModeTab("aether_armor") {
		@Nonnull
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.GRAVITITE_HELMET.get());
		}
	};
	public static final CreativeModeTab AETHER_FOOD = new CreativeModeTab("aether_food") {
		@Nonnull
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.BLUE_BERRY.get());
		}
	};
	public static final CreativeModeTab AETHER_ACCESSORIES = new CreativeModeTab("aether_accessories") {
		@Nonnull
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.GRAVITITE_GLOVES.get());
		}
	};
	public static final CreativeModeTab AETHER_MATERIALS = new CreativeModeTab("aether_materials") {
		@Nonnull
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.AMBROSIUM_SHARD.get());
		}
	};
	public static final CreativeModeTab AETHER_MISC = new CreativeModeTab("aether_misc") {
		@Nonnull
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(AetherItems.BRONZE_DUNGEON_KEY.get());
		}
	};
}