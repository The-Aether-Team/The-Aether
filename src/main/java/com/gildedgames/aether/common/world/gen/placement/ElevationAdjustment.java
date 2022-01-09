package com.gildedgames.aether.common.world.gen.placement;

import com.gildedgames.aether.common.registry.AetherWorldComponents;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Random;
import java.util.stream.Stream;

public class ElevationAdjustment extends PlacementModifier {
    public static final Codec<ElevationAdjustment> CODEC = IntProvider.CODEC.xmap(ElevationAdjustment::new, v -> v.adjustment);

    private final IntProvider adjustment;

    public ElevationAdjustment(IntProvider adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, Random random, BlockPos pos) {
        return Stream.of(pos.above(this.adjustment.sample(random)));
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherWorldComponents.ELEVATION_ADJUSTMENT;
    }
}
