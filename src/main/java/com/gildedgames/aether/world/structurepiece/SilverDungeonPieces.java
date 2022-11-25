package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.gildedgames.aether.loot.AetherLoot;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.function.Function;

public class SilverDungeonPieces {
    public static final RuleProcessor LOCKED_ANGELIC_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_ANGELIC_STONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.TRAPPED_ANGELIC_STONE.get(), 0.2F), AlwaysTrueTest.INSTANCE, AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.HOLYSTONE.get(), 0.3F), AlwaysTrueTest.INSTANCE, AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState())
    ));
    public static final BlockRotProcessor CLOUD_DECAY = new BlockRotProcessor(0.2F);

    /**
     * For testing the silver dungeon grid.
     */
    public static void main(String[] args) {
        RandomSource randomsource = RandomSource.create();
        long i = randomsource.nextLong();
        System.out.println("Seed: " + i);
        randomsource.setSeed(i);
    }

    public static class TemplePiece extends SilverDungeonPiece {

        public TemplePiece(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.SILVER_DUNGEON_PIECE.get(), manager, name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(rotation.rotate(Direction.SOUTH));
        }

        public TemplePiece(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.SILVER_DUNGEON_PIECE.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(LOCKED_ANGELIC_STONE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            if (name.equals("Chest")) {
                if (random.nextBoolean()) {
                    level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 2);
                    if (level.getBlockEntity(pos) instanceof ChestBlockEntity chest) {
                        chest.setLootTable(AetherLoot.SILVER_DUNGEON, random.nextLong());
                    }
                } else {
                    level.setBlock(pos, AetherBlocks.CHEST_MIMIC.get().defaultBlockState(), 3);
                }
            }
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource random, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
            super.postProcess(level, pStructureManager, pGenerator, random, pBox, pChunkPos, pPos);
//            generateClouds(level, pBox, this.boundingBox.minY(), random);
        }
    }

    public static class BossRoom extends SilverDungeonPiece {

        public BossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.SILVER_BOSS_ROOM.get(), manager, name, makeSettings().setRotation(rotation), pos);
            this.setOrientation(rotation.rotate(Direction.SOUTH));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.SILVER_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(LOCKED_ANGELIC_STONE);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB) {
            if (name.equals("Valkyrie Queen")) {
                ValkyrieQueen queen = new ValkyrieQueen(AetherEntityTypes.VALKYRIE_QUEEN.get(), level.getLevel());
                queen.setPersistenceRequired();
                queen.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                queen.setDungeon(new DungeonTracker<>(queen,
                        queen.position(),
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ()),
                        new ArrayList<>()));
                queen.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.getLevel().addFreshEntity(queen);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            } else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.SILVER_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "silver"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        @Override
        public void postProcess(WorldGenLevel level, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource random, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
            super.postProcess(level, pStructureManager, pGenerator, random, pBox, pChunkPos, pPos);
//            generateClouds(level, pBox, this.boundingBox.minY(), random);
        }
    }

    public static class CloudBed extends SilverDungeonPiece {

        public CloudBed(StructureTemplateManager pStructureTemplateManager, String name, StructurePlaceSettings pPlaceSettings, BlockPos pTemplatePosition) {
            super(AetherStructurePieceTypes.CLOUD_BED.get(), pStructureTemplateManager, name, pPlaceSettings, pTemplatePosition);
        }

        public CloudBed(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.CLOUD_BED.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(CLOUD_DECAY);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }
    }

    public static class LegacyCloudBed extends StructurePiece {
        public LegacyCloudBed(StructurePieceType pType, BoundingBox pBox) {
            super(pType, 0, pBox);
        }

        public LegacyCloudBed(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.LEGACY_CLOUD_BED.get(), tag);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {

        }

        @Override
        public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {

        }
    }

    public static abstract class SilverDungeonPiece extends TemplateStructurePiece {

        public SilverDungeonPiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos) {
            super(type, 0, manager, new ResourceLocation(Aether.MODID, "silver_dungeon/" + name), name, settings, pos);
        }

        public SilverDungeonPiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
            super(type, tag, manager, settingsFactory.andThen(settings -> settings.setRotation(Rotation.valueOf(tag.getString("Rotation")))));
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rotation", this.placeSettings.getRotation().name());
        }
    }

    /**
     * Generates the cloud bed under the silver dungeon.
     */
    public static void generateClouds(WorldGenLevel level, BoundingBox chunk, int minY, RandomSource random) {
        int minX = chunk.minX();
        minY -= 1;
        int minZ = chunk.minZ();
        int xTendency = random.nextInt(3) - 2;
        int zTendency = random.nextInt(3) - 2;
        int xSpan = chunk.getXSpan();
        int zSpan = chunk.getZSpan();
        int xOffset = minX + random.nextInt(xSpan);
        int yOffset = minY;
        int zOffset = minZ + random.nextInt(zSpan);
        for (int tries = 0; tries < 16; tries++) {
            for (int n = 0; n < 10; ++n) {
                xOffset += random.nextInt(3) + xTendency;
                zOffset += random.nextInt(3) + zTendency;
                if (random.nextBoolean()) {
                    yOffset += random.nextInt(3) - 1;
                }
                for (int x = xOffset; x < xOffset + random.nextInt(4) + 3; ++x) {
                    for (int y = yOffset; y < yOffset + random.nextInt(1) + 2; ++y) {
                        for (int z = zOffset; z < zOffset + random.nextInt(4) + 3; ++z) {
                            BlockPos pos = new BlockPos(x, y, z);
                            if (chunk.isInside(pos)) {
                                if (Math.abs(x - xOffset) + Math.abs(y - yOffset) + Math.abs(z - zOffset) < 4 + random.nextInt(2)) {
                                    level.setBlock(pos, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
