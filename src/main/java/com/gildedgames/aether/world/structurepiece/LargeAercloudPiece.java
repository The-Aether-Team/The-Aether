package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class LargeAercloudPiece extends StructurePiece {
    private final Set<BlockPos> positions = new HashSet<>();
    private final BlockStateProvider blocks;

    public LargeAercloudPiece(Set<BlockPos> positions, BlockStateProvider blocks, BoundingBox bounds, Direction direction) {
        super(AetherStructurePieceTypes.LARGE_AERCLOUD.get(), 0, bounds);
        this.setOrientation(direction);
        this.positions.addAll(positions);
        this.blocks = blocks;
    }

    public LargeAercloudPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.LARGE_AERCLOUD.get(), tag);
        ListTag positions = tag.getList("Positions", Tag.TAG_COMPOUND);
        for (Tag position : positions) {
            this.positions.add(NbtUtils.readBlockPos((CompoundTag) position));
        }
        this.blocks = BlockStateProvider.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Blocks"))).getOrThrow(true, Aether.LOGGER::error);
    }

    protected void addAdditionalSaveData(@Nonnull StructurePieceSerializationContext context, @Nonnull CompoundTag tag) {
        ListTag positions = new ListTag();
        for (BlockPos position : this.positions) {
            positions.add(NbtUtils.writeBlockPos(position));
        }
        tag.put("Positions", positions);
        BlockStateProvider.CODEC.encodeStart(NbtOps.INSTANCE, this.blocks).resultOrPartial(Aether.LOGGER::error).ifPresent(value -> tag.put("Blocks", value));
    }

    @Override
    public void postProcess(@Nonnull WorldGenLevel level, @Nonnull StructureManager manager, @Nonnull ChunkGenerator generator, @Nonnull RandomSource random, @Nonnull BoundingBox bounds, @Nonnull ChunkPos chunkPos, @Nonnull BlockPos blockPos) {
        if (!this.positions.isEmpty()) {
            this.positions.forEach(pos -> this.placeBlock(level, this.blocks.getState(random, pos), pos.getX(), pos.getY(), pos.getZ(), bounds));
        }
    }

    protected void placeBlock(@Nonnull WorldGenLevel level, @Nonnull BlockState state, int x, int y, int z, BoundingBox bounds) {
        BlockPos pos = new BlockPos(x, y, z);
        if (bounds.isInside(pos)) {
            if (this.canBeReplaced(level, x, y, z, bounds)) {
                level.setBlock(pos, state, 2);
            }
        }
    }

    @Override
    protected boolean canBeReplaced(LevelReader level, int x, int y, int z, @Nonnull BoundingBox bounds) {
        return level.getBlockState(new BlockPos(x, y, z)).isAir();
    }
}
