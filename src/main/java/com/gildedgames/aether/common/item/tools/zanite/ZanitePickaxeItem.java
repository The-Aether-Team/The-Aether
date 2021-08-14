package com.gildedgames.aether.common.item.tools.zanite;

import com.gildedgames.aether.common.item.tools.abilities.IZaniteToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class ZanitePickaxeItem extends PickaxeItem implements IZaniteToolItem
{
	public ZanitePickaxeItem() {
		super(AetherItemTiers.ZANITE, 1, -2.8F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return calculateIncrease(stack, super.getDestroySpeed(stack, state));
	}
}
