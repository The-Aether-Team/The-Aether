package com.aether.item.tools;

import com.aether.item.tools.abilities.IZaniteToolItem;
import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItemTier;
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
