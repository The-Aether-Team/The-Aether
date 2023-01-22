package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.SunSpirit;
import com.gildedgames.aether.loot.AetherLoot;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

/**
 * Class for all the pieces of the gold dungeon.
 */
public class GoldDungeonPieces {

    public static final RuleProcessor LOCKED_HELLFIRE_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_HELLFIRE_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get().defaultBlockState())
    ));

    /**
     * A room inside the island. This should contain the sun spirit.
     */
    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, RandomSource random) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(Rotation.getRandom(random)), pos);
            this.setOrientation(this.getRotation().rotate(Direction.NORTH));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(LOCKED_HELLFIRE_STONE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB) {
            if (name.equals("Sun Spirit")) {
                SunSpirit sunSpirit = new SunSpirit(AetherEntityTypes.SUN_SPIRIT.get(), level.getLevel());
                sunSpirit.setPersistenceRequired();
                sunSpirit.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                sunSpirit.setDungeon(new DungeonTracker<>(sunSpirit,
                        sunSpirit.position(),
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() + 1, this.boundingBox.maxY(), this.boundingBox.maxZ() + 1),
                        new ArrayList<>()));
                sunSpirit.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.getLevel().addFreshEntity(sunSpirit);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
            else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.GOLD_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "gold"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    /**
     * The chunks of land surrounding the boss room to form an island.
     */
    public static class Island extends TemplateStructurePiece { // TODO: This might be unnecessary.

        public Island(StructureTemplateManager manager, ResourceLocation id, BlockPos pos) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), 0, manager, id, id.toString(), makeSettings(), pos);
        }

        public Island(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings();
        }

        @Override
        protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) {}
    }

    public static class LegacyIslandPiece extends StructurePiece {
        public final int radius = 24;

        public LegacyIslandPiece(BoundingBox pBox) {
            super(AetherStructurePieceTypes.GOLD_LEGACY_ISLAND.get(), 0, pBox);
            this.setOrientation(Direction.NORTH);
            Aether.LOGGER.info(this.boundingBox.toString());
        }

        public LegacyIslandPiece(StructurePieceSerializationContext context, CompoundTag pTag) {
            super(AetherStructurePieceTypes.GOLD_LEGACY_ISLAND.get(), pTag);
            this.setOrientation(Direction.NORTH);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {

        }

        @Override
        public void postProcess(WorldGenLevel world, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource random, BoundingBox chunkBox, ChunkPos pChunkPos, BlockPos startPos) {
            for(int x = -24; x <= 24; x++) {
                for(int y = 24; y >= -24; y--) {
                    for(int z = -24; z <= 24; z++) {
                        int width = Mth.floor(x * (1 + y / 240.0) / 0.8);
                        int height = y;

                        if(y > 15)
                        {
                            height = Mth.floor(height * 1.375);
                            height -= 6;
                        }
                        else if(y < -15)
                        {
                            height = Mth.floor(height * 1.3500000238418579);
                            height += 6;
                        }

                        int length = Mth.floor(z * (1 + y / 240.0) / 0.8);

                        if(Math.sqrt(width * width + height * height + length * length) <= 24) {
                            int xOffset = x + 24;
                            int yOffset = y + 24;
                            int zOffset = z + 24;
                            if(this.getBlock(world, xOffset, yOffset + 1, zOffset, chunkBox).isAir() && y > 4) {
                                this.placeBlock(world, AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState(), xOffset, yOffset, zOffset, chunkBox);
                                this.placeBlock(world, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), xOffset, yOffset - 1, zOffset, chunkBox);
                                this.placeBlock(world, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), xOffset, yOffset - (1 + random.nextInt(2)), zOffset, chunkBox);

                                /*if(l2 >= 24 / 2)
                                {
                                    int j5 = this.random.nextInt(48);

                                    if(j5 < 2)
                                    {
                                        //AetherGenUtils.generateGoldenOakTree(this, i2, l2 + 1, i3);
                                    }
                                    else if(j5 == 3)
                                    {
                                        if(this.random.nextInt(2) == 0)
                                        {
                                            //new WorldGenLakes(Blocks.FLOWING_WATER).generate(world, random, new BlockPos.MutableBlockPos((i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j, (i3 + k + random.nextInt(3)) - random.nextInt(3)));
                                        }
                                    }
                                    else if(j5 == 4)
                                    {
                                        if(this.random.nextInt(2) == 0)
                                        {
                                            AetherGenUtils.generateFlower(this, Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), EnumFlowerType.POPPY), (i2 + this.random.nextInt(3)) - this.random.nextInt(3), l2 + 1, (i3 + this.random.nextInt(3)) - this.random.nextInt(3));
                                        }
                                        else
                                        {
                                            AetherGenUtils.generateFlower(this, Blocks.YELLOW_FLOWER.getDefaultState().withProperty(Blocks.YELLOW_FLOWER.getTypeProperty(), EnumFlowerType.DANDELION), (i2 + this.random.nextInt(3)) - this.random.nextInt(3), l2 + 1, (i3 + this.random.nextInt(3)) - this.random.nextInt(3));
                                        }
                                    }
                                }*/
                            } else if(this.getBlock(world, xOffset, yOffset, zOffset, chunkBox).isAir()) {
                                this.placeBlock(world, AetherBlocks.HOLYSTONE.get().defaultBlockState(), xOffset, yOffset, zOffset, chunkBox);
                            }
                        }
                    }

                }

            }

            int l3 = 18;

            for(int j4 = 0; j4 < l3; j4++) {
                int i5 = random.nextInt(24) - random.nextInt(24);
                int l5 = random.nextInt(24) - random.nextInt(24);
                int j6 = random.nextInt(24) - random.nextInt(24);

//                this.generateCaves(i5, l5, j6, 24 + l3 / 3);
            }
        }

        public boolean smallSphere(WorldGenLevel world, BoundingBox chunkBox, RandomSource random, int xFactor, int yFactor, int zFactor, int radius, boolean flag) {
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            if(yFactor - radius <= 0) {
                yFactor = radius + 1;
            }

            if(yFactor + radius >= 127) {
                yFactor = 127 - radius - 1;
            }

            float f = 1.0F;

            int x;
            int y;
            int z;
            int l2;
            int j3;
            for(x = -radius; x <= radius; ++x) {
                for(y = radius; y >= -radius; --y) {
                    for(z = -radius; z <= radius; ++z) {
                        l2 = Mth.floor((double) x / (double)f);
                        j3 = y;
                        if(y > radius * 0.625D) {
                            j3 = Mth.floor(y * 1.375D);
                            j3 -= Mth.floor(radius * 0.25D);
                        } else if(y < radius * -0.625D) {
                            j3 = Mth.floor(y * 1.350000023841858D);
                            j3 += Mth.floor(radius * 0.25D);
                        }

                        int k3 = Mth.floor(z / f);
                        if(Math.sqrt((l2 * l2 + j3 * j3 + k3 * k3)) <= radius) {
                            if(world.isEmptyBlock(mutable.set(x + xFactor, y + yFactor + 1, z + zFactor)) && y > Mth.floor(radius / 5.0D)) {
                                this.placeBlock(world, AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState(), x + xFactor, y + yFactor, z + zFactor, chunkBox);
                                this.placeBlock(world, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), x + xFactor, y + yFactor - 1, z + zFactor, chunkBox);
                                this.placeBlock(world, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), x + xFactor, y + yFactor - (1 + random.nextInt(2)), z + zFactor, chunkBox);

                                /*if(y >= radius / 2) {
                                    int l3 = random.nextInt(64);
                                    if(l3 == 0) {
                                        (new AetherGenGoldenOak()).generate(world, random, x + xFactor, y + yFactor + 1, z + zFactor);
                                    } else if(l3 == 5 && random.nextInt(3) == 0) {
                                        (new WorldGenLakes(Block.waterStill.blockID)).generate(world, random, x + xFactor + random.nextInt(3) - random.nextInt(3), y + yFactor, z + zFactor + random.nextInt(3) - random.nextInt(3));
                                    }
                                }*/
                            } else if (world.isEmptyBlock(mutable.set(x + xFactor, y + yFactor, z + zFactor))) {
                                this.placeBlock(world, AetherBlocks.HOLYSTONE.get().defaultBlockState(), x + xFactor, y + yFactor, z + zFactor, chunkBox);
                            }
                        }
                    }
                }
            }

            if(!flag) {
                x = Mth.floor(radius * 1.25D);

                /*for(y = 0; y < x; ++y) {
                    z = xFactor + random.nextInt(radius) - random.nextInt(radius);
                    l2 = yFactor + random.nextInt(radius) - random.nextInt(radius);
                    j3 = zFactor + random.nextInt(radius) - random.nextInt(radius);
                    (new AetherGenGumdropCaves(0, 16 + x / 3)).generate(world, random, z, l2, j3);
                }*/
            }

            return true;
        }
    }

    public static class LegacyTunnelPiece extends StructurePiece {

        public LegacyTunnelPiece(BoundingBox pBox) {
            super(AetherStructurePieceTypes.GOLD_LEGACY_TUNNEL.get(), 0, pBox);
            this.setOrientation(Direction.NORTH);
        }

        public LegacyTunnelPiece(StructurePieceSerializationContext context, CompoundTag pTag) {
            super(AetherStructurePieceTypes.GOLD_LEGACY_TUNNEL.get(), pTag);
            this.setOrientation(Direction.NORTH);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {

        }

        @Override
        public void postProcess(WorldGenLevel world, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource random, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {

            int radius = 13;
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for(int z = radius; z < radius + 32; ++z) { // loop: 13 - 44 [z offset]
                boolean var18 = false;

                for (int y = -3; y < 2; ++y) { // y offset
                    for (int x = -3; x < 4; ++x) { // x offset

                        if (world.isEmptyBlock(mutable.set(x, y, pPos.getZ() + z))) {
                            var18 = true;
                            if (y == -3) {
                                this.placeBlock(world, AetherBlocks.HOLYSTONE.get().defaultBlockState(), x, y, z, pBox);
                            } else if (y < 1) {
                                if (z == radius) {
                                    if (x < 2 && x > -2 && y < 0) {
                                        this.placeBlock(world, Blocks.AIR.defaultBlockState(), x, y, z, pBox);
                                    } else {
                                        this.placeBlock(world, AetherBlocks.HELLFIRE_STONE.get().defaultBlockState(), x, y, z, pBox);
                                    }
                                } else if (x != 3 && x != -3) {
                                    this.placeBlock(world, Blocks.AIR.defaultBlockState(), x, y, z, pBox);
                                    if (y == -1 && (x == 2 || x == -2) && (z - radius - 2) % 3 == 0) {
                                        this.placeBlock(world, Blocks.AIR.defaultBlockState(), x, y, z, pBox);
                                    }
                                } else {
                                    this.placeBlock(world, AetherBlocks.HOLYSTONE.get().defaultBlockState(), x, y, z, pBox);
                                }
                            } else if (z == radius) {
                                this.placeBlock(world, AetherBlocks.HELLFIRE_STONE.get().defaultBlockState(), x, y, z, pBox);
                            } else {
                                this.placeBlock(world, AetherBlocks.HOLYSTONE.get().defaultBlockState(), x, y, z, pBox);
                            }
                        }
                    }
                }
            }
        }
    }
}
