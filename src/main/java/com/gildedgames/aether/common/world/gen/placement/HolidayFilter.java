package com.gildedgames.aether.common.world.gen.placement;

import com.gildedgames.aether.core.AetherConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Calendar;
import java.util.Random;

public class HolidayFilter extends PlacementFilter {
    public static final Codec<HolidayFilter> CODEC = Codec.unit(HolidayFilter::new);

    @Override
    protected boolean shouldPlace(PlacementContext p_191835_, Random p_191836_, BlockPos p_191837_) {
        Calendar calendar = Calendar.getInstance();
        boolean isChristmas = calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26;
        return AetherConfig.COMMON.generate_holiday_tree_always.get() || (AetherConfig.COMMON.generate_holiday_tree_seasonally.get() && isChristmas);
    }

    @Override
    public PlacementModifierType<?> type() {
        return PlacementModifiers.HOLIDAY_FILTER;
    }
}
