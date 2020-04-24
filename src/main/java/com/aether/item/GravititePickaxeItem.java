package com.aether.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResultType;

public class GravititePickaxeItem extends PickaxeItem implements IGravititeToolItem {

	public GravititePickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		return IGravititeToolItem.super.onItemUse(context);
	}
	
	@Override
	public ActionResultType defaultItemUse(ItemUseContext context) {
		return super.onItemUse(context);
	}
	
}
