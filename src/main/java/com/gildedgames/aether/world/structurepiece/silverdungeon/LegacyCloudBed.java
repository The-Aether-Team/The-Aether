package com.gildedgames.aether.world.structurepiece.silverdungeon;

import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class LegacyCloudBed extends StructurePiece {
    private final Set<BlockPos> positions;

    public LegacyCloudBed(Set<BlockPos> positions, BoundingBox pBox, Direction direction) {
        super(AetherStructurePieceTypes.LEGACY_CLOUD_BED.get(), 0, pBox);
        this.positions = positions;
        this.setOrientation(direction);
    }

    public LegacyCloudBed(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.LEGACY_CLOUD_BED.get(), tag);
        ListTag positions = tag.getList("Positions", Tag.TAG_COMPOUND);
        this.positions = new HashSet<>();
        for (Tag position : positions) {
            this.positions.add(NbtUtils.readBlockPos((CompoundTag) position));
        }
    }

    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        ListTag positions = new ListTag();
        for (BlockPos position : this.positions) {
            positions.add(NbtUtils.writeBlockPos(position));
        }
        tag.put("Positions", positions);
    }

    private final static BlockState COLD_CLOUD = AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
    @Override
    public void postProcess(@Nonnull WorldGenLevel level, @Nonnull StructureManager manager, @Nonnull ChunkGenerator generator, @Nonnull RandomSource random, @Nonnull BoundingBox bounds, @Nonnull ChunkPos chunkPos, @Nonnull BlockPos blockPos) {
        if (!this.positions.isEmpty()) {
            this.positions.removeIf(pos -> this.placeBlock(level, COLD_CLOUD, pos.offset(0, blockPos.getY(), 0), bounds));
        }
    }

    protected boolean placeBlock(WorldGenLevel level, BlockState state, BlockPos pos, BoundingBox bounds) {
        if (bounds.isInside(pos)) {
            if (this.canBeReplaced(level, pos)) {
                level.setBlock(pos, state, 2);
            }
            return true;
        }
        return false;
    }

    protected boolean canBeReplaced(LevelReader level, BlockPos pos) {
        return level.isEmptyBlock(pos);
    }
}