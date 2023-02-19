package com.gildedgames.aether.world.structurepiece.silverdungeon;

import com.gildedgames.aether.world.processor.DoubleDropsProcessor;
import com.gildedgames.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class SilverTemplePiece extends SilverDungeonPiece {

    public SilverTemplePiece(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.SILVER_TEMPLE_PIECE.get(), manager, name, makeSettings().setRotation(rotation), pos);
        this.setOrientation(rotation.rotate(Direction.SOUTH));
    }

    public SilverTemplePiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.SILVER_TEMPLE_PIECE.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
    }

    private static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings().addProcessor(LOCKED_ANGELIC_STONE).addProcessor(DoubleDropsProcessor.INSTANCE);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {

    }
}