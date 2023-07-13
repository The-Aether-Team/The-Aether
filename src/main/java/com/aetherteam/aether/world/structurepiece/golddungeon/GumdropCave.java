package com.aetherteam.aether.world.structurepiece.golddungeon;


import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

/**
 * Little caves that are placed around the gold dungeon island.
 */
public class GumdropCave extends StructurePiece {

    public GumdropCave(BoundingBox box) {
        super(AetherStructurePieceTypes.GUMDROP_CAVE.get(), 0, box);
    }

    public GumdropCave(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.GUMDROP_CAVE.get(), tag);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {

    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos startPos) {
        BlockPos center = this.boundingBox.getCenter();
        float f = random.nextFloat() * Mth.PI;
        int i = center.getX();
        int j = center.getY();
        int k = center.getZ();

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        double offset = 30.0 / 8.0;

        double lowerX = (i) + Mth.sin(f) * offset;
        double upperX = (i) - Mth.sin(f) * offset;
        double lowerZ = (k) + Mth.cos(f) * offset;
        double upperZ = (k) - Mth.cos(f) * offset;
        double lowerY = j + random.nextInt(3) + 2;
        double upperY = j + random.nextInt(3) + 2;

        for(int length = 0; length <= 30; ++length) {
            double radius = length / 30.0;

            double x = lowerX + (upperX - lowerX) * radius;
            double y = lowerY + (upperY - lowerY) * radius;
            double z = lowerZ + (upperZ - lowerZ) * radius;

            double magnitude = random.nextDouble() * 30 / 16.0;
            double width = (Math.sin(radius * Mth.PI) + 1.0F) * magnitude + 1.0;
            width /= 2;

            int minX = Mth.floor(x - width);
            int minY = Mth.floor(y - width);
            int minZ = Mth.floor(z - width);

            int maxX = Mth.floor(x + width);
            int maxY = Mth.floor(y + width);
            int maxZ = Mth.floor(z + width);

            for(int xOffset = minX; xOffset <= maxX; ++xOffset) {
                double xDistance = Mth.square((xOffset + 0.5 - x) / (width));
                if(xDistance < 1.0) {
                    for(int yOffset = minY; yOffset <= maxY; ++yOffset) {
                        double yDistance = Mth.square((yOffset + 0.5 - y) / (width));
                        if(xDistance + yDistance < 1.0) {
                            for(int zOffset = minZ; zOffset <= maxZ; ++zOffset) {
                                double zDistance = Mth.square((zOffset + 0.5 - z) / (width));
                                if(xDistance + yDistance + zDistance < 1.0) {
                                    BlockState state = level.getBlockState(mutable.set(xOffset, yOffset, zOffset));
                                    if (state.is(AetherTags.Blocks.AETHER_DIRT) || state.is(AetherTags.Blocks.HOLYSTONE)) {
                                        level.setBlock(mutable, Blocks.AIR.defaultBlockState(), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}