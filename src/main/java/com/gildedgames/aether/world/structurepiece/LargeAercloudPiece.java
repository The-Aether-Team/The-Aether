package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class LargeAercloudPiece extends ScatteredFeaturePiece { //todo: https://misode.github.io/guides/adding-custom-structures/#the-structure-set
    private final Set<BlockPos> positions = new HashSet<>();

    public LargeAercloudPiece(RandomSource source, int x, int z) {
        super(AetherStructurePieceTypes.LARGE_AERCLOUD.get(), x, 0, z, 128, 16, 128, getRandomHorizontalDirection(source));
    }

    public LargeAercloudPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.LARGE_AERCLOUD.get(), tag);
        ListTag positions = tag.getList("positions", Tag.TAG_COMPOUND);
        for (Tag position : positions) {
            this.positions.add(NbtUtils.readBlockPos((CompoundTag) position));
        }
    }

    protected void addAdditionalSaveData(@Nonnull StructurePieceSerializationContext context, @Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        ListTag positions = new ListTag();
        for (BlockPos position : this.positions) {
            positions.add(NbtUtils.writeBlockPos(position));
        }
        tag.put("positions", positions);
    }

    @Override
    public void postProcess(@Nonnull WorldGenLevel level, @Nonnull StructureManager manager, @Nonnull ChunkGenerator generator, @Nonnull RandomSource random, @Nonnull BoundingBox bounds, @Nonnull ChunkPos chunkPos, @Nonnull BlockPos blockPos) {
        if (this.positions.isEmpty()) { //todo copy some of this logic over to the normal aerclouds.
            Set<BlockPos> positions1 = new HashSet<>();

            int minX = 0;
            int maxX = 0;
            int minZ = 0;
            int maxZ = 0;

            boolean direction = random.nextBoolean();
            int x = 0;
            int y = 0;
            int z = 0;
            int xTendency = random.nextInt(3) - 1;
            int zTendency = random.nextInt(3) - 1;

            for (int amount = 0; amount < 64; ++amount) {
                x += random.nextInt(3) - 1 + xTendency;
                y += random.nextInt(10) == 0 ? random.nextInt(3) - 1 : 0;
                z += direction ? random.nextInt(3) - 1 + zTendency : -(random.nextInt(3) - 1 + zTendency);

                for (int x1 = x; x1 < x + random.nextInt(4) + 3 * 3; ++x1) {
                    for (int y1 = y; y1 < y + random.nextInt(1) + 2; ++y1) {
                        for (int z1 = z; z1 < z + random.nextInt(4) + 3 * 3; ++z1) {
                            BlockPos newPosition = new BlockPos(x1, y1, z1);
                            if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * 3 + random.nextInt(2)) {
                                //this.positions.add(newPosition);
                                positions1.add(newPosition);
                                if (x1 > maxX) maxX = x1;
                                if (x1 < minX) minX = x1;
                                if (z1 > maxZ) maxZ = z1;
                                if (z1 < minZ) minZ = z1;
                            }
                        }
                    }
                }
            }

            //todo probably need a check to determine which is the larger offset, the min or the max. will need absolutes here.


            //todo: maybe i should just check whether the position is within the bounds and getting the difference between the border position and the overboard position and add that to get it back in the border idk.

            //TODO: honestly i *could* just make it so the code is centered and not edge based or something if i can. make like x and z start at the center and then idk like figure out how to generate the cloud forwards and opposite backwards.

            Set<BlockPos> positions2 = new HashSet<>();

            if (minX < 0) {
                int finalMinX = minX;
                positions1.forEach(pos -> positions2.add(pos.offset(-finalMinX, 0, 0)));
            } else if (maxX > this.boundingBox.getXSpan()) {
                int finalMaxX = maxX - this.boundingBox.getXSpan();
                positions1.forEach(pos -> positions2.add(pos.offset(-finalMaxX, 0, 0)));
            }

            Set<BlockPos> positions3 = new HashSet<>();

            if (minZ < 0) {
                int finalMinZ = minZ;
                positions2.forEach(pos -> positions3.add(pos.offset(0, 0, -finalMinZ)));
            } else if (maxZ > this.boundingBox.getZSpan()) {
                int finalMaxZ = maxZ - this.boundingBox.getZSpan();
                positions2.forEach(pos -> positions3.add(pos.offset(0, 0, -finalMaxZ)));
            }

            this.positions.addAll(positions3);



            //todo: need to find the highest and lowest values for x and z from this.positions.
            //  then i need to somehow offset based on those values to get it within the structure bounds
            //  also need to account for both positives and negatives.
        }

        if (!this.positions.isEmpty()) {
            //Aether.LOGGER.info(this.positions);
            //this.positions.forEach(pos -> level.setBlock(this.getWorldPos(pos.getX(), pos.getY(), pos.getZ()), AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), 3));
            this.positions.forEach(pos -> this.placeBlock(level, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), pos.getX(), pos.getY(), pos.getZ(), bounds));
        }





//        if (this.positions.isEmpty()) {
//            boolean direction = random.nextBoolean();
//            BlockPos position = blockPos.offset(-random.nextInt(8), 0, (direction ? 8 : 0) - random.nextInt(8));
//
//            for (int amount = 0; amount < 64; ++amount) {
//                int xOffset = random.nextInt(2);
//                int yOffset = random.nextInt(10) == 0 ? random.nextInt(2) : 0;
//                int zOffset = random.nextInt(2);
//
//                if (direction) {
//                    position = position.offset(xOffset, yOffset, -zOffset);
//                } else {
//                    position = position.offset(xOffset, yOffset, zOffset);
//                }
//
//                for (int x = position.getX(); x < position.getX() + random.nextInt(2) + 3 * 3; ++x) {
//                    for (int y = position.getY(); y < position.getY() + random.nextInt(1) + 2; ++y) {
//                        for (int z = position.getZ(); z < position.getZ() + random.nextInt(2) + 3 * 3; ++z) {
//                            BlockPos newPosition = new BlockPos(x, y, z);
//
//                            //if (reader.isEmptyBlock(newPosition)) {
//                            if (Math.abs(x - position.getX()) + Math.abs(y - position.getY()) + Math.abs(z - position.getZ()) < 4 * 3 + random.nextInt(2)) {
//                                this.positions.add(newPosition);
//                            }
//                            //}
//                        }
//                    }
//                }
//            }
//        }
//
//        if (!this.positions.isEmpty()) {
//            this.positions.forEach(pos -> level.setBlock(pos, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), 3));
//        }




    }

    @Override
    protected boolean canBeReplaced(LevelReader pLevel, int pX, int pY, int pZ, BoundingBox pBox) {
        return pLevel.getBlockState(this.getWorldPos(pX, pY, pZ)).isAir();
    }
}
