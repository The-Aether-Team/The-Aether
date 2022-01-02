package com.gildedgames.aether.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.Random;
import java.util.function.BiPredicate;

public final class BlockPlacers {
    public static void placeDisk(BlockPos center, float radius, WorldGenLevel level, BlockStateProvider blockProvider, Random random) {
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

    public static void placeSphere(BlockPos center, float radius, WorldGenLevel level, BlockStateProvider blockProvider, Random random) {
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
    public static boolean placeProvidedBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, Random random) {
        return level.setBlock(pos, provider.getState(random, pos), 2);
    }

    public static void fillBox(WorldGenLevel level, BlockState state, BoundingBox structureBox, BoundingBox chunkBox) {
        if (!structureBox.intersects(chunkBox)) return;

        int xStart = Math.max(structureBox.minX(), chunkBox.minX());
        int yStart = Math.max(structureBox.minY(), chunkBox.minY());
        int zStart = Math.max(structureBox.minZ(), chunkBox.minZ());

        int xEnd = Math.min(structureBox.maxX(), chunkBox.maxX());
        int yEnd = Math.min(structureBox.maxY(), chunkBox.maxY());
        int zEnd = Math.min(structureBox.maxZ(), chunkBox.maxZ());

        for (int z = zStart; z <= zEnd; z++)
            for (int y = yStart; y <= yEnd; y++)
                for (int x = xStart; x <= xEnd; x++)
                    level.setBlock(new BlockPos(x, y, z), state, 2);
    }

    public static void fillBoxWithShell(WorldGenLevel level, BlockStateProvider shell, BlockState interior, BiPredicate<BoundingBox, BlockPos> isInterior, BoundingBox structureBox, BoundingBox chunkBox, Random random) {
        if (!structureBox.intersects(chunkBox)) return;

        int xStart = Math.max(structureBox.minX(), chunkBox.minX());
        int yStart = Math.max(structureBox.minY(), chunkBox.minY());
        int zStart = Math.max(structureBox.minZ(), chunkBox.minZ());

        int xEnd = Math.min(structureBox.maxX(), chunkBox.maxX());
        int yEnd = Math.min(structureBox.maxY(), chunkBox.maxY());
        int zEnd = Math.min(structureBox.maxZ(), chunkBox.maxZ());

        for (int z = zStart; z <= zEnd; z++)
            for (int y = yStart; y <= yEnd; y++)
                for (int x = xStart; x <= xEnd; x++) {
                    BlockPos pos = new BlockPos(x, y, z);

                    level.setBlock(pos, isInterior.test(structureBox, pos) ? interior : shell.getState(random, pos), 2);
                }
    }

    public static void shelledTunnel(WorldGenLevel level, BlockStateProvider shell, Direction.Axis axis, BoundingBox structureBox, BoundingBox chunkBox, Random random) {
        fillBoxWithShell(level, shell, Blocks.AIR.defaultBlockState(), BlockLogic.getShellingDirection(axis), structureBox, chunkBox, random);
    }

    private BlockPlacers() {
    }
}
