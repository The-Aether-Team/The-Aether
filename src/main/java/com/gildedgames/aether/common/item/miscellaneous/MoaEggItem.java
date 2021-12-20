package com.gildedgames.aether.common.item.miscellaneous;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.item.Item.Properties;

public class MoaEggItem extends Item
{
    private final int color;

    public MoaEggItem(int shellColor, Properties properties) {
        super(properties);
        this.color = shellColor;
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int color) {
        return this.color;
    }
}
