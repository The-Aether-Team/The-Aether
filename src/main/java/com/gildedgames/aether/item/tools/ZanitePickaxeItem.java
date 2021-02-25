package com.gildedgames.aether.item.tools;

import com.gildedgames.aether.item.tools.abilities.IZaniteToolItem;
import com.gildedgames.aether.registry.AetherItemGroups;
import com.gildedgames.aether.registry.AetherItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class ZanitePickaxeItem extends PickaxeItem implements IZaniteToolItem
{
	public ZanitePickaxeItem() {
		super(AetherItemTier.ZANITE, 1, -2.8F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return calculateIncrease(stack, super.getDestroySpeed(stack, state));
	}
}
