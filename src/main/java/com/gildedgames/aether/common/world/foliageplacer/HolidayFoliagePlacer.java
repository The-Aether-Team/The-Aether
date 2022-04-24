package com.gildedgames.aether.common.world.foliageplacer;

import com.gildedgames.aether.common.registry.worldgen.AetherFoliagePlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Random;
import java.util.function.BiConsumer;

public class HolidayFoliagePlacer extends FoliagePlacer {
    public static final Codec<HolidayFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return foliagePlacerParts(instance).and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter((placer) -> {
            return placer.trunkHeight;
        })).apply(instance, HolidayFoliagePlacer::new);
    });
    private final IntProvider trunkHeight;

    public HolidayFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset);
        this.trunkHeight = height;
    }

    protected FoliagePlacerType<?> type() {
        return AetherFoliagePlacerTypes.HOLIDAY_FOLIAGE_PLACER.get();
    }

    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, TreeConfiguration config, int maxFreeTreeHeight, FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos blockpos = attachment.pos();

        int i = 0;
        for(int l = offset; l >= offset - 7; --l) {
            switch (i) {
                default -> this.placeLeavesRow(level, blockSetter, random, config, blockpos, i, l, attachment.doubleTrunk());
                case 1 -> this.placeLeavesRow(level, blockSetter, random, config, blockpos, 1, l, attachment.doubleTrunk());
                case 2 -> {
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos, 1, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos.east().north(), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos.west().north(), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos.east().south(), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos.west().south(), 0, l, attachment.doubleTrunk());
                }
                case 3 -> disk360(level, blockSetter, random, config, attachment.doubleTrunk(), blockpos, l, 1, 1);
                case 4 -> {
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos, 2, l, attachment.doubleTrunk());
                    disk360(level, blockSetter, random, config, attachment.doubleTrunk(), blockpos, l, 3, 0);
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos.east(2).north(2), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos.west(2).north(2), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos.east(2).south(2), 0, l, attachment.doubleTrunk());
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos.west(2).south(2), 0, l, attachment.doubleTrunk());
                }
                case 5 -> disk360(level, blockSetter, random, config, attachment.doubleTrunk(), blockpos, l, 1, 2);
                case 6 -> {
                    this.placeLeavesRow(level, blockSetter, random, config, blockpos, 3, l, attachment.doubleTrunk());
                    disk360(level, blockSetter, random, config, attachment.doubleTrunk(), blockpos, l, 4, 0);
                }
                case 7 -> disk360(level, blockSetter, random, config, attachment.doubleTrunk(), blockpos, l, 2, 2);
            }
            ++i;
        }
    }

    private void disk360(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, TreeConfiguration config, boolean doubleTrunk, BlockPos blockpos, int height, int distance, int range) {
        this.placeLeavesRow(level, blockSetter, random, config, blockpos.east(distance), range, height, doubleTrunk);
        this.placeLeavesRow(level, blockSetter, random, config, blockpos.south(distance), range, height, doubleTrunk);
        this.placeLeavesRow(level, blockSetter, random, config, blockpos.west(distance), range, height, doubleTrunk);
        this.placeLeavesRow(level, blockSetter, random, config, blockpos.north(distance), range, height, doubleTrunk);
    }

    public int foliageHeight(Random random, int height, TreeConfiguration config) {
        return Math.max(4, height - this.trunkHeight.sample(random));
    }

    /**
     * Skips certain positions based on the provided shape, such as rounding corners randomly.
     * The coordinates are passed in as absolute value, and should be within [0, {@code range}].
     */
    protected boolean shouldSkipLocation(Random random, int localX, int localY, int localZ, int range, boolean large) {
        return localX == range && localZ == range && range > 0;
    }
}
