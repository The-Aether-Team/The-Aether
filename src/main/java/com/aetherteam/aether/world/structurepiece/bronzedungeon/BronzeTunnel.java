package com.aetherteam.aether.world.structurepiece.bronzedungeon;

import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.common.world.PieceBeardifierModifier;

/**
 * The entrance to the bronze dungeon. It shouldn't replace air so that it matches the landscape.
 */
public class BronzeTunnel extends BronzeDungeonPiece implements PieceBeardifierModifier {

    public BronzeTunnel(StructureTemplateManager pStructureTemplateManager, String name, BlockPos pTemplatePosition, Rotation rotation) {
        super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), pStructureTemplateManager, name, makeSettings().setRotation(rotation), pTemplatePosition);
    }

    public BronzeTunnel(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.BRONZE_TUNNEL.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
    }

    static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings().addProcessor(AVOID_DUNGEONS).addProcessor(BRONZE_DUNGEON_STONE).addProcessor(DoubleDropsProcessor.INSTANCE);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {

    }

    @Override
    public BoundingBox getBeardifierBox() {
        return this.boundingBox;
    }

    @Override
    public TerrainAdjustment getTerrainAdjustment() {
        return TerrainAdjustment.NONE;
    }

    @Override
    public int getGroundLevelDelta() {
        return 0;
    }
}