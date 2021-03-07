package com.gildedgames.aether.common.item.tools;

import com.gildedgames.aether.common.item.tools.abilities.IGravititeToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResultType;

public class GravititeShovelItem extends ShovelItem implements IGravititeToolItem
{
	public GravititeShovelItem() {
		super(AetherItemTiers.GRAVITITE, 1.5F, -3.0F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
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
