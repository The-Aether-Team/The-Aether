package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import javax.annotation.Nonnull;

public class LargeAercloudPiece extends ScatteredFeaturePiece {
    public LargeAercloudPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.LARGE_AERCLOUD.get(), tag);
    }

    public LargeAercloudPiece(RandomSource source, int x, int z) {
        super(StructurePieceType.SWAMPLAND_HUT, x, 0, z, 64, 32, 64, getRandomHorizontalDirection(source));
    }

    @Override
    public void postProcess(@Nonnull WorldGenLevel level, @Nonnull StructureManager manager, @Nonnull ChunkGenerator generator, @Nonnull RandomSource random, @Nonnull BoundingBox bounds, @Nonnull ChunkPos chunkPos, @Nonnull BlockPos blockPos) {
        if (this.updateAverageGroundHeight(level, bounds, 0)) { //todo
            int x = 0;
            int y = 0;
            int z = 0;
            int xTendency = random.nextInt(3) - 1;
            int zTendency = random.nextInt(3) - 1;
            for (int n = 0; n < 64; ++n) {
                x += random.nextInt(3) - 1 + xTendency;
                random.nextBoolean();
                if (random.nextInt(10) == 0) {
                    y += random.nextInt(3) - 1;
                }
                z += random.nextInt(3) - 1 + zTendency;
                for (int x1 = x; x1 < x + random.nextInt(4) + 3 * 3; ++x1) {
                    for (int y1 = y; y1 < y + random.nextInt(1) + 2; ++y1) {
                        for (int z1 = z; z1 < z + random.nextInt(4) + 3 * 3; ++z1) {
                            BlockPos newPosition = this.getWorldPos(x, y, z);
                            if (level.isEmptyBlock(newPosition)) {
                                if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * 3 + random.nextInt(2)) {
                                    BlockState blockState = AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
                                    this.placeBlock(level, blockState, x, y, z, bounds);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
