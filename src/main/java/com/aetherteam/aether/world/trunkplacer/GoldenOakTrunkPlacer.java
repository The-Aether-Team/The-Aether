package com.aetherteam.aether.world.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class GoldenOakTrunkPlacer extends TrunkPlacer {
    public static final Codec<GoldenOakTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).apply(instance, GoldenOakTrunkPlacer::new));

    public GoldenOakTrunkPlacer(int height, int heightRandA, int heightRandB) {
        super(height, heightRandA, heightRandB);
    }

    protected TrunkPlacerType<?> type() {
        return AetherTrunkPlacerTypes.GOLDEN_OAK_TRUNK_PLACER.get();
    }

    /**
     * Randomly places logs in Golden Oak Trees branching out from the center until they reach the edge of the leaves.
     *
     * @param level       The {@link LevelSimulatedReader}.
     * @param blockSetter The {@link BiConsumer} of a {@link BlockPos} and {@link BlockState} used for block placement.
     * @param random      The {@link RandomSource}.
     * @param height      The {@link Integer} height of the tree.
     * @param pos         The initial {@link BlockPos} for placement.
     * @param config      The {@link TreeConfiguration}.
     * @return A {@link List} of {@link net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment}s for the tree.
     */
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int height, BlockPos pos, TreeConfiguration config) {
        TrunkPlacer.setDirtAt(level, blockSetter, random, pos.below(), config);
        for (int i = 0; i < height; ++i) {
            if (i > 4 && random.nextInt(3) > 0 && i < 9) {
                this.branch(level, random, blockSetter, pos.getX(), pos.getY() + i, pos.getZ(), i / 4 - 1, config);
            }
            this.placeLog(level, blockSetter, random, pos.above(i), config);
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(height), 0, false));
    }

    /**
     * Places a branch.
     *
     * @param level       The {@link LevelSimulatedReader}.
     * @param random      The {@link RandomSource}.
     * @param blockSetter The {@link BiConsumer} of a {@link BlockPos} and {@link BlockState} used for block placement.
     * @param i           The x {@link Integer} position.
     * @param j           The y {@link Integer} position.
     * @param k           The z {@link Integer} position.
     * @param slant       The {@link Integer} value for the branch slant.
     * @param config      The {@link TreeConfiguration}.
     */
    public void branch(LevelSimulatedReader level, RandomSource random, BiConsumer<BlockPos, BlockState> blockSetter, int i, int j, int k, int slant, TreeConfiguration config) {
        int directionX = random.nextInt(3) - 1;
        int directionZ = random.nextInt(3) - 1;

        for (int n = 0; n < random.nextInt(2) + 1; ++n) {
            i += directionX;
            j += slant;
            k += directionZ;
            this.placeLog(level, blockSetter, random, new BlockPos(i, j, k), config);
        }
    }
}
