package com.gildedgames.aether.common.item.tools;

import com.gildedgames.aether.common.item.tools.abilities.IZaniteToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;

public class ZaniteShovelItem extends ShovelItem implements IZaniteToolItem
{
	public ZaniteShovelItem() {
		super(AetherItemTiers.ZANITE, 1.5F, -3.0F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return calculateIncrease(stack, super.getDestroySpeed(stack, state));
	}
}
