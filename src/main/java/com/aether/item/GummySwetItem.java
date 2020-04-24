package com.aether.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class GummySwetItem extends Item {

	public GummySwetItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		ItemStack heldItem = context.getItem();
		context.getPlayer().heal(context.getPlayer().getMaxHealth());
		if (!context.getPlayer().isCreative()) {
			heldItem.shrink(1);
		}
		return ActionResultType.SUCCESS;
	}
	
}
