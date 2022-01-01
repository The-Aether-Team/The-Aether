package com.gildedgames.aether.common.world.structure;

import com.gildedgames.aether.Aether;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public abstract class AetherTemplatePiece extends TemplateStructurePiece {
    public AetherTemplatePiece(StructurePieceType type, int depth, StructureManager structureManager, String location, StructurePlaceSettings placeSettings, BlockPos pos) {
        this(type, depth, structureManager, new ResourceLocation(Aether.MODID, location), placeSettings, pos);
    }

    private AetherTemplatePiece(StructurePieceType type, int depth, StructureManager structureManager, ResourceLocation location, StructurePlaceSettings placeSettings, BlockPos pos) {
        super(type, depth, structureManager, location, location.toString(), placeSettings, pos);
    }

    public AetherTemplatePiece(StructurePieceType type, StructurePieceSerializationContext context, CompoundTag tag) {
        super(type, tag, context.structureManager(), v -> new StructurePlaceSettings());
    }
}
