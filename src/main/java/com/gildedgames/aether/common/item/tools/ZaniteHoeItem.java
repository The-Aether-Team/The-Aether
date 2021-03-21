package com.gildedgames.aether.common.item.tools;

import com.gildedgames.aether.common.item.tools.abilities.IZaniteToolItem;
import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.HoeItem;

public class ZaniteHoeItem extends HoeItem implements IZaniteToolItem
{
    public ZaniteHoeItem() {
        super(AetherItemTiers.ZANITE, -2, -1.0F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return calculateIncrease(stack, super.getDestroySpeed(stack, state));
    }
}
