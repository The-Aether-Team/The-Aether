package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.core.api.registers.MoaType;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class MoaEggItem extends Item
{
    private final Supplier<MoaType> moaType;
    private final int color;

    public MoaEggItem(Supplier<MoaType> moaType, int shellColor, Properties properties) {
        super(properties);
        this.moaType = moaType;
        this.color = shellColor;
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int color) {
        return this.color;
    }

    public Supplier<MoaType> getMoaType() {
        return this.moaType;
    }
}
