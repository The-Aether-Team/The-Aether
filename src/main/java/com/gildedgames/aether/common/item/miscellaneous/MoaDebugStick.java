package com.gildedgames.aether.common.item.miscellaneous;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class MoaDebugStick extends Item
{
    public MoaDebugStick(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        return true;
    }
}
