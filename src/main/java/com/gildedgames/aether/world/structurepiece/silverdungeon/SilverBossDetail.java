package com.gildedgames.aether.world.structurepiece.silverdungeon;

import com.gildedgames.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class SilverBossDetail extends SilverDungeonPiece {
    public SilverBossDetail(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation) {
        super(AetherStructurePieceTypes.SILVER_BOSS_DETAIL.get(), manager, name, new StructurePlaceSettings().setRotation(rotation), pos);
    }

    public SilverBossDetail(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieceTypes.SILVER_BOSS_DETAIL.get(), tag, context.structureTemplateManager(), (id) -> new StructurePlaceSettings());
    }
}