package com.aetherteam.aether.world.structurepiece.silverdungeon;

import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * A floor in the Silver Dungeon.
 */
public class SilverFloorPiece extends SilverDungeonPiece {
    public SilverFloorPiece(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        super(AetherStructurePieceTypes.SILVER_FLOOR_PIECE.get(), manager, name, new StructurePlaceSettings().setRotation(rotation), pos, processors);
        this.setOrientation(rotation.rotate(Direction.SOUTH));
    }

    public SilverFloorPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.SILVER_FLOOR_PIECE.get(), context.registryAccess(), tag, context.structureTemplateManager(), resourceLocation -> new StructurePlaceSettings());
    }
}
