package com.gildedgames.aether.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BlockPlacementUtil {
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

    @SuppressWarnings("UnusedReturnValue") // Retain the boolean feedback from setting block
    public static boolean placeProvidedBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos).isAir()) {
            return level.setBlock(pos, provider.getState(random, pos), 2);
        } else {
            return false;
        }
    }
}
