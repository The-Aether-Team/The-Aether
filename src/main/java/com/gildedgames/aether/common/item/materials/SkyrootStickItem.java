package com.gildedgames.aether.common.item.materials;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SkyrootStickItem extends Item
{
    public SkyrootStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 100;
    }
}
