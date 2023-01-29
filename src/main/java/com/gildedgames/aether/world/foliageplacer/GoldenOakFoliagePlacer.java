package com.gildedgames.aether.world.foliageplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class GoldenOakFoliagePlacer extends FoliagePlacer {
    public static final Codec<GoldenOakFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return foliagePlacerParts(instance).and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter((placer) -> {
            return placer.trunkHeight;
        })).apply(instance, GoldenOakFoliagePlacer::new);
    });
    private final IntProvider trunkHeight;

    public GoldenOakFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset);
        this.trunkHeight = height;
    }

    protected FoliagePlacerType<?> type() {
        return AetherFoliagePlacerTypes.GOLDEN_OAK_FOLIAGE_PLACER.get();
    }

    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        for(int i = offset; i >= offset - 6; --i) {
            //int j = foliageRadius + (i >= offset - 1 /*at the top*/ || i <= offset - 5 /*or at the bottom*/ ? 0 : 1 /*middle*/);
            int j = foliageRadius + 1;
            if (i == offset || i == offset - 6) j = foliageRadius - 1;
            if (i == offset - 1 || i == offset - 5) j = foliageRadius;


            this.placeLeavesRow(level, blockSetter, random, config, attachment.pos(), j, i, attachment.doubleTrunk());
        }

    }

    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return Math.max(4, height - this.trunkHeight.sample(random));
    }

    /**
     * Skips certain positions based on the provided shape, such as rounding corners randomly.
     * The coordinates are passed in as absolute value, and should be within [0, {@code range}].
     */
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return (outside(localX + 0.5F, localZ + 0.5F, range)) || (outside(localX, localZ, range - 1.5F) && random.nextInt(4) == 0);
    }

    private boolean outside(float localX, float localZ, float range) {
        return Mth.square(localX) + Mth.square(localZ) > range*range;
    }
}
