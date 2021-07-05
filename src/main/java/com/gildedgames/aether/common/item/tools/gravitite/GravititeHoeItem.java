package com.gildedgames.aether.common.item.tools.gravitite;

import com.gildedgames.aether.common.item.tools.abilities.IGravititeToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.HoeItem;
import net.minecraft.util.ActionResultType;

public class GravititeHoeItem extends HoeItem implements IGravititeToolItem
{
    public GravititeHoeItem() {
        super(AetherItemTiers.GRAVITITE, -3, 0, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
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
