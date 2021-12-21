package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.core.api.registers.MoaType;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

public class MoaEggItem extends Item
{
    private static final Map<Supplier<MoaType>, MoaEggItem> BY_ID = Maps.newIdentityHashMap();
    private final Supplier<MoaType> moaType;
    private final int color;

    public MoaEggItem(Supplier<MoaType> moaType, int shellColor, Properties properties) {
        super(properties);
        this.moaType = moaType;
        this.color = shellColor;
        BY_ID.put(moaType, this);
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int color) {
        return this.color;
    }

    public Supplier<MoaType> getMoaType() {
        return this.moaType;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public static MoaEggItem byId(@Nullable Supplier<MoaType> moaType) {
        return BY_ID.get(moaType);
    }

    public static Iterable<MoaEggItem> moaEggs() {
        return Iterables.unmodifiableIterable(BY_ID.values());
    }
}
