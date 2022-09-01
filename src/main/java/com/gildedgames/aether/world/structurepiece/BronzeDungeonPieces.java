package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.world.processor.DungeonStoneProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

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

    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, RandomSource random) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings(), pos);
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.BRONZE_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(DungeonStoneProcessor.SENTRY);
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {

        }
    }
}
