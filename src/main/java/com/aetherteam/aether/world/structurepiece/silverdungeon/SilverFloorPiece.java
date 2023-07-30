package com.aetherteam.aether.world.structurepiece.silverdungeon;

import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * A floor in the Silver Dungeon.
 */
public class SilverFloorPiece extends SilverDungeonPiece {
    public SilverFloorPiece(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.SILVER_FLOOR_PIECE.get(), manager, name, SilverFloorPiece.makeSettings().setRotation(rotation), pos);
        this.setOrientation(rotation.rotate(Direction.SOUTH));
    }

    public SilverFloorPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.SILVER_FLOOR_PIECE.get(), tag, context.structureTemplateManager(), resourceLocation -> SilverFloorPiece.makeSettings());
    }

    private static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .addProcessor(SilverDungeonPiece.LOCKED_ANGELIC_STONE)
                .addProcessor(SilverDungeonPiece.TRAPPED_ANGELIC_STONE)
                .addProcessor(DoubleDropsProcessor.INSTANCE);
    }
}