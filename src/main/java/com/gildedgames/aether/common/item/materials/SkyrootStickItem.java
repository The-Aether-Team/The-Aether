package com.gildedgames.aether.common.item.materials;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

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
