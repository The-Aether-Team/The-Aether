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
 * Different pieces used for rooms in the Silver Dungeons.
 */
public class SilverTemplePiece extends SilverDungeonPiece {
    public SilverTemplePiece(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.SILVER_TEMPLE_PIECE.get(), manager, name, SilverTemplePiece.makeSettings().setRotation(rotation), pos);
        this.setOrientation(rotation.rotate(Direction.SOUTH));
    }

    public SilverTemplePiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.SILVER_TEMPLE_PIECE.get(), tag, context.structureTemplateManager(), resourceLocation -> SilverTemplePiece.makeSettings());
    }

    private static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .addProcessor(SilverDungeonPiece.LOCKED_ANGELIC_STONE)
                .addProcessor(DoubleDropsProcessor.INSTANCE);
    }
}