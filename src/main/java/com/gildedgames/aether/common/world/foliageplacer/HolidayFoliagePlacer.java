package com.gildedgames.aether.common.world.foliageplacer;

import com.gildedgames.aether.common.registry.worldgen.AetherFoliagePlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
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
        for(int l = offset; l >= offset - 5; --l) {
            switch (i) {
                default -> this.placeLeavesRow(level, blockSetter, random, config, blockpos, 0, l, attachment.doubleTrunk());
                case 1 -> this.placeLeavesRow(level, blockSetter, random, config, blockpos, 1, l, attachment.doubleTrunk());
                case 2 -> this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 1, 1, l);
                case 3 -> {
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 1, 1, l);
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 2, 0, l);
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 0, 2, l);
                }
                case 4 -> {
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 2, 2, l);
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 3, 0, l);
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 0, 3, l);
                }
                case 5 -> {
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 2, 2, l);
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 3, 1, l);
                    this.placeLeavesRectangle(level, blockSetter, random, config, blockpos, 1, 3, l);
                }
            }
            ++i;
        }
    }

    private void placeLeavesRectangle(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, TreeConfiguration config, BlockPos blockpos, int radiusX, int radiusZ, int offset) {
        for (int i = -radiusX; i <= radiusX; i++) {
            for (int j = -radiusZ; j <= radiusZ; j++) {
                this.placeLeavesRow(level, blockSetter, random, config, blockpos.offset(i, 0, j), 0, offset, false);
            }
        }
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
