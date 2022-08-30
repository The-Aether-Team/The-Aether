package com.gildedgames.aether.util;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BlockPlacementUtil {
    public static void placeDisk(BlockPos center, float radius, WorldGenLevel level, BlockStateProvider blockProvider, RandomSource random) {
        float radiusSq = radius * radius;

        placeProvidedBlock(level, blockProvider, center, random);

        for (int z = 1; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;

                placeProvidedBlock(level, blockProvider, center.offset(x, 0, z), random);
                placeProvidedBlock(level, blockProvider, center.offset(-x, 0, -z), random);
                placeProvidedBlock(level, blockProvider, center.offset(-z, 0, x), random);
                placeProvidedBlock(level, blockProvider, center.offset(z, 0, -x), random);
            }
        }
    }

    public static void placeSphere(BlockPos center, float radius, WorldGenLevel level, BlockStateProvider blockProvider, RandomSource random) {
        float radiusSq = radius * radius;

        placeProvidedBlock(level, blockProvider, center, random);

        for (int z = 1; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                int xzLengthSq = x * x + z * z;

                if (xzLengthSq > radiusSq) continue;

                placeProvidedBlock(level, blockProvider, center.offset(x, 0, z), random);
                placeProvidedBlock(level, blockProvider, center.offset(-x, 0, -z), random);
                placeProvidedBlock(level, blockProvider, center.offset(-z, 0, x), random);
                placeProvidedBlock(level, blockProvider, center.offset(z, 0, -x), random);

                for (int y = 1; y < radius; y++) {
                    if (xzLengthSq + y * y > radiusSq) continue;

                    placeProvidedBlock(level, blockProvider, center.offset(x, -y, z), random);
                    placeProvidedBlock(level, blockProvider, center.offset(-x, -y, -z), random);
                    placeProvidedBlock(level, blockProvider, center.offset(-z, -y, x), random);
                    placeProvidedBlock(level, blockProvider, center.offset(z, -y, -x), random);

                    placeProvidedBlock(level, blockProvider, center.offset(x, y, z), random);
                    placeProvidedBlock(level, blockProvider, center.offset(-x, y, -z), random);
                    placeProvidedBlock(level, blockProvider, center.offset(-z, y, x), random);
                    placeProvidedBlock(level, blockProvider, center.offset(z, y, -x), random);
                }
            }
        }
    }

    @SuppressWarnings("UnusedReturnValue") // Retain the boolean feedback from setting block
    public static boolean placeProvidedBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random) {
        if (!level.getBlockState(pos).is(AetherTags.Blocks.QUICKSOIL_CAN_GENERATE)) return level.setBlock(pos, provider.getState(random, pos), 2);
        return false;
    }
}
