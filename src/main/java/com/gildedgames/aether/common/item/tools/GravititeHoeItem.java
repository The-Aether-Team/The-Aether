package com.gildedgames.aether.common.item.tools;

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
        return IGravititeToolItem.super.onItemUse(context);
    }

    @Override
    public ActionResultType defaultItemUse(ItemUseContext context) {
        return super.useOn(context);
    }
}
