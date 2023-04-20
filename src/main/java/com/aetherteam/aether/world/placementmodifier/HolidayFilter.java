package com.aetherteam.aether.world.placementmodifier;

import com.aetherteam.aether.AetherConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Calendar;

public class HolidayFilter extends PlacementFilter {
    public static final Codec<HolidayFilter> CODEC = Codec.unit(HolidayFilter::new);

    @Override
    protected boolean shouldPlace(PlacementContext p_191835_, RandomSource p_191836_, BlockPos p_191837_) {
        Calendar calendar = Calendar.getInstance();
        boolean isChristmas = calendar.get(Calendar.MONTH) == Calendar.DECEMBER || calendar.get(Calendar.MONTH) == Calendar.JANUARY;
        return AetherConfig.COMMON.generate_holiday_tree_always.get() || (AetherConfig.COMMON.generate_holiday_tree_seasonally.get() && isChristmas);
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherPlacementModifiers.HOLIDAY_FILTER;
    }
}
