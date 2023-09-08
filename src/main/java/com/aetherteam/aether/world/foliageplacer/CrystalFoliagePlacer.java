package com.aetherteam.aether.world.foliageplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class CrystalFoliagePlacer extends FoliagePlacer {
    public static final Codec<CrystalFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter((placer) -> placer.trunkHeight))
            .apply(instance, CrystalFoliagePlacer::new));
    private final IntProvider trunkHeight;

    public CrystalFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset);
        this.trunkHeight = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherFoliagePlacerTypes.CRYSTAL_FOLIAGE_PLACER.get();
    }

    /**
     * Places leaves in a diamond shape around the trunk with different radii at different heights.
     * @param level The {@link LevelSimulatedReader}.
     * @param foliageSetter The {@link BiConsumer} of a {@link BlockPos} and {@link BlockState} used for block placement.
     * @param random The {@link RandomSource}.
     * @param config The {@link TreeConfiguration}.
     * @param maxFreeTreeHeight The {@link Integer} for the maximum tree height.
     * @param attachment A {@link net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment} to add foliage to.
     * @param foliageHeight The {@link Integer} for the foliage height.
     * @param foliageRadius The {@link Integer} for the foliage radius.
     * @param offset The {@link Integer} for the foliage offset.
     */
    @Override
    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> foliageSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos blockPos = attachment.pos();
        int i = 0;
        int j;
        for (int l = offset; l >= offset - 5; --l) {
            switch (i) {
                case 1, 3 -> j = 1;
                case 2, 4 -> {
                    this.placeLeavesDiamond(level, foliageSetter, random, config, attachment, 1, l);
                    j = 1;
                }
                case 5 -> j = 2;
                default -> j = 0;
            }
            this.placeLeavesRow(level, foliageSetter, random, config, blockPos, j, l, attachment.doubleTrunk());
            ++i;
        }
    }

    /**
     * Places leaves in a diamond shape around a trunk piece.
     * @param level The {@link LevelSimulatedReader}.
     * @param foliageSetter The {@link BiConsumer} of a {@link BlockPos} and {@link BlockState} used for block placement.
     * @param random The {@link RandomSource}.
     * @param config The {@link TreeConfiguration}.
     * @param attachment A {@link net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment} to add foliage to.
     * @param radius The {@link Integer} for the placement radius.
     * @param offset The {@link Integer} for the placement offset.
     */
    private void placeLeavesDiamond(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> foliageSetter, RandomSource random, TreeConfiguration config, FoliagePlacer.FoliageAttachment attachment, int radius, int offset) {
        this.placeLeavesRow(level, foliageSetter, random, config, attachment.pos().north(), radius, offset, attachment.doubleTrunk());
        this.placeLeavesRow(level, foliageSetter, random, config, attachment.pos().south(), radius, offset, attachment.doubleTrunk());
        this.placeLeavesRow(level, foliageSetter, random, config, attachment.pos().west(), radius, offset, attachment.doubleTrunk());
        this.placeLeavesRow(level, foliageSetter, random, config, attachment.pos().east(), radius, offset, attachment.doubleTrunk());
    }

    /**
     * Determines the foliage height with some randomization.
     * @param random The {@link RandomSource}.
     * @param height The {@link Integer} for the foliage height.
     * @param config The {@link TreeConfiguration}.
     * @return The {@link Integer} for the foliage height.
     */
    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return Math.max(4, height - this.trunkHeight.sample(random));
    }

    /**
     * Skips placing a foliage block at an edge location.
     * @param random The {@link RandomSource}.
     * @param localX The local {@link Integer} x-position.
     * @param localY The local {@link Integer} y-position.
     * @param localZ The local {@link Integer} z-position.
     * @param range The {@link Integer} for the placement range.
     * @param large The {@link Boolean} for whether the tree is large.
     * @return Whether the location should be skipped, as a {@link Boolean}.
     */
    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return localX == range && localZ == range && range > 0;
    }
}