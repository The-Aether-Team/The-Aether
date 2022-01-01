package com.gildedgames.aether.common.world.structure;

import com.gildedgames.aether.core.util.BlockPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import java.util.Random;

public class BeardedPiece extends StructurePiece {
    public BeardedPiece(BoundingBox boundingBox) {
        super(AetherStructurePieces.EMPTY_BEARDED_PIECE, 0, boundingBox);
    }

    public BeardedPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieces.EMPTY_BEARDED_PIECE, tag);
    }

    @Override
    public NoiseEffect getNoiseEffect() {
        return NoiseEffect.BEARD;
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureFeatureManager structureFeatureManager, ChunkGenerator chunkGenerator, Random random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        BlockPlacers.fill(level, Blocks.AIR.defaultBlockState(), this.getBoundingBox(), box);
    }
}
