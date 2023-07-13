package com.aetherteam.aether.world;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BlockPlacementUtil {
    /**
     * Places a disc for generation purposes.
     * @param level The {@link WorldGenLevel} for generation.
     * @param blockProvider The {@link BlockStateProvider} for the block to be placed.
     * @param center The center {@link BlockPos} to generate the disc from.
     * @param radius The radius of the disc, as a {@link Float}.
     * @param random The {@link RandomSource} used for generation.
     */
    public static void placeDisk(WorldGenLevel level, BlockStateProvider blockProvider, BlockPos center, float radius, RandomSource random) {
        float radiusSq = radius * radius;
        placeProvidedBlock(level, blockProvider, center, random);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                placeProvidedBlock(level, blockProvider, center.offset(x, 0, z), random);
                placeProvidedBlock(level, blockProvider, center.offset(-x, 0, -z), random);
                placeProvidedBlock(level, blockProvider, center.offset(-z, 0, x), random);
                placeProvidedBlock(level, blockProvider, center.offset(z, 0, -x), random);
            }
        }
    }

    /**
     * Places a block if there is not already one at the position.<br><br>
     * Warning for "UnusedReturnValue" is suppressed because the boolean from {@link WorldGenLevel#setBlock(BlockPos, BlockState, int)} needs to be retained.
     * @param level The {@link WorldGenLevel} for generation.
     * @param provider The {@link BlockStateProvider} for the block to be placed.
     * @param pos The {@link BlockPos} for the block.
     * @param random The {@link RandomSource} used for generation.
     * @return A {@link Boolean} for whether the block was placed successfully.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static boolean placeProvidedBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos).isAir()) {
            return level.setBlock(pos, provider.getState(random, pos), 2);
        } else {
            return false;
        }
    }
}
