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

public class HolidayFoliagePlacer extends FoliagePlacer {
    public static final Codec<HolidayFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter((placer) -> placer.trunkHeight))
            .apply(instance, HolidayFoliagePlacer::new));
    private final IntProvider trunkHeight;

    public HolidayFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset);
        this.trunkHeight = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return AetherFoliagePlacerTypes.HOLIDAY_FOLIAGE_PLACER.get();
    }

    /**
     * Places circular leaf rows around the center trunk in a Christmas tree shape.
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
        for (int l = offset; l >= offset - 7; --l) {
            switch (i) {
                default -> this.placeLeavesRow(level, foliageSetter, random, config, blockPos, i, l, attachment.doubleTrunk());
                case 1 -> this.placeLeavesRow(level, foliageSetter, random, config, blockPos, 1, l, attachment.doubleTrunk());
                case 2 -> {
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos, 1, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos.east().north(), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos.west().north(), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos.east().south(), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos.west().south(), 0, l, attachment.doubleTrunk());
                }
                case 3 -> disk360(level, foliageSetter, random, config, attachment.doubleTrunk(), blockPos, l, 1, 1);
                case 4 -> {
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos, 2, l, attachment.doubleTrunk());
                    disk360(level, foliageSetter, random, config, attachment.doubleTrunk(), blockPos, l, 3, 0);
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos.east(2).north(2), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos.west(2).north(2), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos.east(2).south(2), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos.west(2).south(2), 0, l, attachment.doubleTrunk());
                }
                case 5 -> disk360(level, foliageSetter, random, config, attachment.doubleTrunk(), blockPos, l, 1, 2);
                case 6 -> {
                    this.placeLeavesRow(level, foliageSetter, random, config, blockPos, 3, l, attachment.doubleTrunk());
                    disk360(level, foliageSetter, random, config, attachment.doubleTrunk(), blockPos, l, 4, 0);
                }
                case 7 -> disk360(level, foliageSetter, random, config, attachment.doubleTrunk(), blockPos, l, 2, 2);
            }
            ++i;
        }
    }

    /**
     * Places leaves outwards from a position at a certain distance.
     * @param level The {@link LevelSimulatedReader}.
     * @param foliageSetter The {@link BiConsumer} of a {@link BlockPos} and {@link BlockState} used for block placement.
     * @param random The {@link RandomSource}.
     * @param config The {@link TreeConfiguration}.
     * @param doubleTrunk Whether the tree has a 2x2 trunk, as a {@link Boolean}.
     * @param blockPos The initial {@link BlockPos}.
     * @param height The {@link Integer} height for placement.
     * @param distance The {@link Integer} distance for offsetting the position.
     * @param range The {@link Integer} range for placement.
     */
    private void disk360(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> foliageSetter, RandomSource random, TreeConfiguration config, boolean doubleTrunk, BlockPos blockPos, int height, int distance, int range) {
        this.placeLeavesRow(level, foliageSetter, random, config, blockPos.east(distance), range, height, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, blockPos.south(distance), range, height, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, blockPos.west(distance), range, height, doubleTrunk);
        this.placeLeavesRow(level, foliageSetter, random, config, blockPos.north(distance), range, height, doubleTrunk);
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
