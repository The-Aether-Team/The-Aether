package com.gildedgames.aether.common.item.tools.gravitite;

import com.gildedgames.aether.common.item.tools.abilities.IGravititeToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class GravititeAxeItem extends AxeItem implements IGravititeToolItem
{
	public GravititeAxeItem() {
		super(AetherItemTiers.GRAVITITE, 5.0F, -3.0F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
			return this.startFloatBlock(context);
		} else {
			ActionResultType result = super.useOn(context);
			if (result == ActionResultType.PASS || result == ActionResultType.FAIL) {
				return this.startFloatBlock(context);
			}
			return result;
		}
	}

	private ActionResultType startFloatBlock(ItemUseContext context) {
		float destroySpeed = this.getDestroySpeed(context.getItemInHand(), context.getLevel().getBlockState(context.getClickedPos()));
		float efficiency = this.getTier().getSpeed();
		return floatBlock(context, destroySpeed, efficiency);
	}
}
