package com.gildedgames.aether.item.food;

import net.minecraft.item.Item;

public class GummySwetItem extends Item
{
	public GummySwetItem(Item.Properties properties) {
		super(properties);
	}
	
//	@Override
//	public ActionResultType onItemUse(ItemUseContext context)
//	{
//		ItemStack heldItem = context.getItem();
//		context.getPlayer().heal(context.getPlayer().getMaxHealth());
//		if (!context.getPlayer().isCreative()) {
//			heldItem.shrink(1);
//		}
//		return ActionResultType.SUCCESS;
//	}
}
