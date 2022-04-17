package com.gildedgames.aether.common.world.gen.placement;

import com.gildedgames.aether.core.AetherConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * A PlacementFilter to prevent tall grass from generating when generate_tall_grass is set to false.
 * The codec is blank, since I don't know of a way to select a config option using a codec.
 */
public class TallGrassFilter extends PlacementFilter {
    private static final TallGrassFilter INSTANCE = new TallGrassFilter();
    public static final Codec<TallGrassFilter> CODEC = Codec.unit(() -> INSTANCE);

    public static TallGrassFilter getInstance() {
        return INSTANCE;
    }

    @Override
    protected boolean shouldPlace(@Nonnull PlacementContext context, @Nonnull Random random, @Nonnull BlockPos pos) {
        return AetherConfig.COMMON.generate_tall_grass.get();
    }

    @Override
    @Nonnull
    public PlacementModifierType<?> type() {
        return PlacementModifiers.CONFIG_FILTER;
    }
}
