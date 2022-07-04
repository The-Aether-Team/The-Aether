package com.gildedgames.aether.world.placementmodifier;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class ElevationAdjustmentModifier extends PlacementModifier {
    public static final Codec<ElevationAdjustmentModifier> CODEC = IntProvider.CODEC.xmap(ElevationAdjustmentModifier::new, v -> v.adjustment);

    private final IntProvider adjustment;

    public ElevationAdjustmentModifier(IntProvider adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        return Stream.of(pos.above(this.adjustment.sample(random)));
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherPlacementModifiers.ELEVATION_ADJUSTMENT;
    }
}
