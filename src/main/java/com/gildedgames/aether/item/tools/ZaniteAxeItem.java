package com.gildedgames.aether.item.tools;

import com.gildedgames.aether.item.tools.abilities.IZaniteToolItem;
import com.gildedgames.aether.registry.AetherItemGroups;
import com.gildedgames.aether.registry.AetherItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ZaniteAxeItem extends AxeItem implements IZaniteToolItem
{
	public ZaniteAxeItem() {
		super(AetherItemTier.ZANITE, 8.0F, -3.1F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return calculateIncrease(stack, super.getDestroySpeed(stack, state));
	}
}
