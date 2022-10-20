package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.SunSpirit;
import com.gildedgames.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.processor.DungeonStoneProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class SilverDungeonPieces {
    public static RuleProcessor LOCKED_ANGELIC_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.LOCKED_ANGELIC_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get().defaultBlockState())
    ));
    /**
     * For testing the silver dungeon grid.
     */
    public static void main(String[] args) {
        RandomSource randomsource = RandomSource.create();
        long i = randomsource.nextLong();
        System.out.println("Seed: " + i);
        randomsource.setSeed(i);
    }

    static class FloorGrid {
        private final int[][] grid;
        private final int width;
        private final int length;

        public FloorGrid(int width, int length) {
            this.grid = new int[width][length];
            this.width = width;
            this.length = length;
        }
    }

    public static class SilverDungeonPiece extends TemplateStructurePiece {

        public SilverDungeonPiece(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.SILVER_DUNGEON_PIECE.get(), 0, manager, id, id.toString(), makeSettings().setRotation(rotation), pos);
            this.setOrientation(rotation.rotate(Direction.SOUTH));
        }

        public SilverDungeonPiece(StructurePieceSerializationContext context, CompoundTag tag) {
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
    }

    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.SILVER_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(rotation), pos);
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
            }
            else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.SILVER_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "silver"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
