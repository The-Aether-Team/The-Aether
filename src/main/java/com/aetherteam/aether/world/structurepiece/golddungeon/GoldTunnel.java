package com.aetherteam.aether.world.structurepiece.golddungeon;


import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import com.aetherteam.aether.world.processor.NoReplaceProcessor;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/**
 * The tunnel leading from the side of the island to the boss room.
 */
public class GoldTunnel extends GoldDungeonPiece {
    public GoldTunnel(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.GOLD_TUNNEL.get(), manager, name, GoldTunnel.makeSettings().setRotation(rotation), pos);
    }

    public GoldTunnel(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.GOLD_TUNNEL.get(), tag, context.structureTemplateManager(), resourceLocation -> GoldTunnel.makeSettings());
    }

    private static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .addProcessor(MOSSY_HOLYSTONE)
                .addProcessor(NoReplaceProcessor.AIR)
                .addProcessor(DoubleDropsProcessor.INSTANCE);
    }
}