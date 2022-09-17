package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.blockentity.TreasureChestBlockEntity;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.monster.dungeon.boss.Slider;
import com.gildedgames.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.world.processor.DungeonStoneProcessor;
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
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class BronzeDungeonPieces {

    public static abstract class BronzeDungeonPiece extends TemplateStructurePiece {
        public BronzeDungeonPiece(StructurePieceType pType, int pGenDepth, StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, String pTemplateName, StructurePlaceSettings pPlaceSettings, BlockPos pTemplatePosition) {
            super(pType, pGenDepth, pStructureTemplateManager, pLocation, pTemplateName, pPlaceSettings, pTemplatePosition);
        }

        public BronzeDungeonPiece(StructurePieceType type, StructurePieceSerializationContext context, CompoundTag tag) {
            super(type, tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(DungeonStoneProcessor.SENTRY);
        }
    }

    public static class TunnelPiece extends TemplateStructurePiece {

        public TunnelPiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation id, BlockPos pTemplatePosition, RandomSource random) {
            super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), 0, pStructureTemplateManager, id, id.toString(), makeSettings(), pTemplatePosition);
        }

        public TunnelPiece(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR);
        }

        @Override
        public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
            super.postProcess(pLevel, pStructureManager, pGenerator, pRandom, pBox, pChunkPos, pPos);
            pLevel.setBlock(pPos, Blocks.GLOWSTONE.defaultBlockState(), 3);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }
    }

    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, RandomSource random) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(Rotation.getRandom(random)), pos);
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(DungeonStoneProcessor.SENTRY);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
            if (name.equals("Slider")) {
                Slider slider = new Slider(AetherEntityTypes.SLIDER.get(), level.getLevel());
                slider.setPersistenceRequired();
                double xPos = pos.getX();
                double zPos = pos.getZ();
                switch (this.placeSettings.getRotation()) {
                    case NONE -> {
                        xPos += 1;
                        zPos += 1;
                    }
                    case CLOCKWISE_90 -> zPos += 1;
                    case COUNTERCLOCKWISE_90 -> xPos += 1;
                }
                slider.setPos(xPos, pos.getY(), zPos);
                slider.setDungeon(new DungeonTracker<>(slider,
                        slider.position(),
                        new AABB(this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ()),
                        new ArrayList<>()));
                slider.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.getLevel().addFreshEntity(slider);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
            else if (name.equals("Treasure Chest")) {
                BlockPos chest = pos.below();
                RandomizableContainerBlockEntity.setLootTable(level, random, chest, AetherLoot.BRONZE_DUNGEON_REWARD);
                TreasureChestBlockEntity.setDungeonType(level, chest, new ResourceLocation(Aether.MODID, "bronze"));
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
