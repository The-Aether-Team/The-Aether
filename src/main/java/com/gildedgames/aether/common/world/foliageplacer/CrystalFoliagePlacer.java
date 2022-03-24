package com.gildedgames.aether.common.world.foliageplacer;

import com.gildedgames.aether.common.registry.AetherFoliagePlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Random;
import java.util.function.BiConsumer;

public class CrystalFoliagePlacer extends FoliagePlacer {
    public static final Codec<CrystalFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return foliagePlacerParts(instance).and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter((placer) -> {
            return placer.trunkHeight;
        })).apply(instance, CrystalFoliagePlacer::new);
    });
    private final IntProvider trunkHeight;

    public CrystalFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height) {
        super(radius, offset);
        this.trunkHeight = height;
    }

    protected FoliagePlacerType<?> type() {
        return AetherFoliagePlacerTypes.CRYSTAL_FOLIAGE_PLACER;
    }

    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, TreeConfiguration config, int maxFreeTreeHeight, FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos blockpos = attachment.pos();

        int i = 0;
        int j;
        for(int l = offset; l >= offset - 5; --l) {
            switch (i) {
                case 1, 3 -> j = 1;
                case 2, 4 -> {
                    placeLeavesDiamond(level, blockSetter, random, config, attachment, 1, l);
                    j = 1;
                }
                case 5 -> j = 2;
                default -> j = 0;
            }
            this.placeLeavesRow(level, blockSetter, random, config, blockpos, j, l, attachment.doubleTrunk());
            ++i;
        }
    }

    private void placeLeavesDiamond(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, TreeConfiguration config, FoliagePlacer.FoliageAttachment attachment, int radius, int offset) {
        this.placeLeavesRow(level, blockSetter, random, config, attachment.pos().north(), radius, offset, attachment.doubleTrunk());
        this.placeLeavesRow(level, blockSetter, random, config, attachment.pos().south(), radius, offset, attachment.doubleTrunk());
        this.placeLeavesRow(level, blockSetter, random, config, attachment.pos().west(), radius, offset, attachment.doubleTrunk());
        this.placeLeavesRow(level, blockSetter, random, config, attachment.pos().east(), radius, offset, attachment.doubleTrunk());
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