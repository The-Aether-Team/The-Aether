package com.aetherteam.aether.world.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class CrystalTreeTrunkPlacer extends StraightTrunkPlacer {
    public static final Codec<CrystalTreeTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).apply(instance, CrystalTreeTrunkPlacer::new));

    public CrystalTreeTrunkPlacer(int height, int heightRandA, int heightRandB) {
        super(height, heightRandA, heightRandB);
    }

    protected TrunkPlacerType<?> type() {
        return AetherTrunkPlacerTypes.CRYSTAL_TREE_TRUNK_PLACER.get();
    }

    /**
     * Places a log on each side of the Crystal Tree's trunk every 3 blocks,
     * starting at 2 blocks up from the initial position and then 5 blocks up (3 and 6 blocks up from the ground).
     * @param level The {@link LevelSimulatedReader}.
     * @param blockSetter The {@link BiConsumer} of a {@link BlockPos} and {@link BlockState} used for block placement.
     * @param random The {@link RandomSource}.
     * @param height The {@link Integer} height of the tree.
     * @param pos The initial {@link BlockPos} for placement.
     * @param config The {@link TreeConfiguration}.
     * @return A {@link List} of {@link net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment}s for the tree.
     */
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int height, BlockPos pos, TreeConfiguration config) {
        TrunkPlacer.setDirtAt(level, blockSetter, random, pos.below(), config);
        super.placeTrunk(level, blockSetter, random, height, pos, config);
        float f = 0;
        for (int i = 2; i < 7; i += 3) {
            for (int l = 0; l < 4; l++) {
                int j = (int) Mth.cos(f);
                int k = (int) Mth.sin(f);
                BlockPos blockPos = pos.offset(j, i, k);
                this.placeLog(level, blockSetter, random, blockPos, config);
                f += 0.25F * Mth.TWO_PI;
            }
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(height), 0, false));
    }
}
