package com.gildedgames.aether.world.placementmodifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ElevationFilter extends PlacementFilter {
    public static final Codec<ElevationFilter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("minimum").forGetter(o -> o.minimum),
            Codec.INT.fieldOf("maximum").forGetter(o -> o.maximum)
    ).apply(instance, ElevationFilter::new));

    private final int minimum;
    private final int maximum;

    public ElevationFilter(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return this.minimum <= pos.getY() && pos.getY() <= this.maximum;
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherPlacementModifiers.ELEVATION_FILTER;
    }
}
