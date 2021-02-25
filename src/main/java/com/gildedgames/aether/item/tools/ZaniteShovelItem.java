package com.gildedgames.aether.item.tools;

import com.gildedgames.aether.item.tools.abilities.IZaniteToolItem;
import com.gildedgames.aether.registry.AetherItemGroups;
import com.gildedgames.aether.registry.AetherItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;

public class ZaniteShovelItem extends ShovelItem implements IZaniteToolItem
{
	public ZaniteShovelItem() {
		super(AetherItemTier.ZANITE, 1.5F, -3.0F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return calculateIncrease(stack, super.getDestroySpeed(stack, state));
	}
}
