package com.gildedgames.aether.world.structurepiece;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.processor.HellfireStoneProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
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
     * A room inside the island. This should contain the sun spirit.
     */
    public static class BossRoom extends TemplateStructurePiece {

        public BossRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, RandomSource random) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(Rotation.getRandom(random)), pos);
            this.setOrientation(getRandomHorizontalDirection(random));
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_BOSS_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
        }

        /**
         * Adds a treasure room directly to the back of the boss room.
         */
        public TreasureRoom addTreasureRoom(StructureTemplateManager templateManager) {
            BlockPos pos;
            Rotation rotation = this.getRotation();
            switch (rotation) {
                case COUNTERCLOCKWISE_90 -> pos = new BlockPos(this.boundingBox.maxX(), this.boundingBox.minY() + 1, this.boundingBox.minZ() + (this.boundingBox.getZSpan() / 2));
                case CLOCKWISE_90 -> pos = new BlockPos(this.boundingBox.minX(), this.boundingBox.minY() + 1, this.boundingBox.minZ() + (this.boundingBox.getZSpan() / 2));
                case CLOCKWISE_180 -> pos = new BlockPos(this.boundingBox.minX() + (this.boundingBox.getXSpan() / 2), this.boundingBox.minY() + 1, this.boundingBox.minZ());
                default -> pos = new BlockPos(this.boundingBox.minX() + (this.boundingBox.getXSpan() / 2), this.boundingBox.minY() + 1, this.boundingBox.maxZ());
            }
            return new TreasureRoom(templateManager,
                    new ResourceLocation(Aether.MODID, "gold_dungeon/treasure_room"),
                    pos,
                    rotation);
        }

        private static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().addProcessor(HellfireStoneProcessor.INSTANCE);
        }

        @Override
        protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) {
            // TODO
        }
    }

    public static class TreasureRoom extends TemplateStructurePiece {

        public TreasureRoom(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
            super(AetherStructurePieceTypes.GOLD_TREASURE_ROOM.get(), 0, manager, id, id.toString(), makeSettings().setRotation(rotation), pos);
        }

        public TreasureRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(AetherStructurePieceTypes.GOLD_TREASURE_ROOM.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
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
}
