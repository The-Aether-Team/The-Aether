package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.world.processor.HellfireStoneProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * Class for all the pieces of the gold dungeon.
 */
public class GoldDungeonPieces {

    /**
     * The room inside the island. This should contain the sun spirit and the dungeon's chest.
     */
    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings(), pos);
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());

        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(HellfireStoneProcessor.INSTANCE);
        }

        @Override
        protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) {
            // TODO
        }
    }

    /**
     * The chunks of land surrounding the boss room to form an island.
     */
    public static class Island extends TemplateStructurePiece {

        public Island(StructureTemplateManager manager, ResourceLocation id, String templateName, StructurePlaceSettings settings, BlockPos pos) {
            super(AetherStructurePieceTypes.GOLD_ISLAND.get(), 0, manager, id, templateName, settings, pos);
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
}
