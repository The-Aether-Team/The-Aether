package com.gildedgames.aether.common.item.tools.gravitite;

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
		super(AetherItemTiers.GRAVITITE, 1.5F, -3.0F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		ActionResultType result = super.useOn(context);
		if (result == ActionResultType.PASS || result == ActionResultType.FAIL) {
			float destroySpeed = this.getDestroySpeed(context.getItemInHand(), context.getLevel().getBlockState(context.getClickedPos()));
			float efficiency = this.getTier().getSpeed();
			return floatBlock(context, destroySpeed, efficiency);
		}
		return result;
	}
}
