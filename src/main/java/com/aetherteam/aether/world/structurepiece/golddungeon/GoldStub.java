package com.aetherteam.aether.world.structurepiece.golddungeon;


import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.processor.SurfaceRuleProcessor;
import com.aetherteam.aether.world.processor.VerticalGradientProcessor;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * The chunks of land surrounding the boss room to form an island.
 * This is separate from GoldIsland to make vegetation placement more accurate
 * in {@link com.aetherteam.aether.world.structure.GoldDungeonStructure#afterPlace(WorldGenLevel, StructureManager, ChunkGenerator, RandomSource, BoundingBox, ChunkPos, PiecesContainer)}.
 */
public class GoldStub extends GoldDungeonPiece {
    public GoldStub(StructureTemplateManager manager, String name, BlockPos pos) {
        super(AetherStructurePieceTypes.GOLD_STUB.get(), manager, name, GoldStub.makeSettings(), pos);
    }

    public GoldStub(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.GOLD_STUB.get(), tag, context.structureTemplateManager(), resourceLocation -> GoldStub.makeSettings());
    }

    private static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .addProcessor(SurfaceRuleProcessor.INSTANCE)
                .addProcessor(VerticalGradientProcessor.INSTANCE)
                .addProcessor(DoubleDropsProcessor.INSTANCE);
    }
}