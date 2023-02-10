package com.gildedgames.aether.world.structurepiece.golddungeon;


import com.gildedgames.aether.world.processor.VerticalGradientProcessor;
import com.gildedgames.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * The chunks of land surrounding the boss room to form an island.
 */
public class GoldIsland extends GoldDungeonPiece {

    public GoldIsland(StructureTemplateManager manager, String name, BlockPos pos) {
        super(AetherStructurePieceTypes.GOLD_ISLAND.get(), manager, name, makeSettings(), pos);
    }

    public GoldIsland(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.GOLD_ISLAND.get(), tag, context.structureTemplateManager(), resourceLocation -> makeSettings());
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
    }

    private static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings().addProcessor(VerticalGradientProcessor.INSTANCE);
    }
}