package com.aether.item.tools;

import com.aether.item.tools.abilities.IGravititeToolItem;
import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItemTier;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class GravititeAxeItem extends AxeItem implements IGravititeToolItem
{
	public GravititeAxeItem() {
		super(AetherItemTier.GRAVITITE, 5.0F, -3.0F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
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
