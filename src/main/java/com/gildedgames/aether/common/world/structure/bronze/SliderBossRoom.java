package com.gildedgames.aether.common.world.structure.bronze;

import com.gildedgames.aether.common.world.structure.AetherStructurePieces;
import com.gildedgames.aether.common.world.structure.AetherTemplatePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Random;

public class SliderBossRoom extends AetherTemplatePiece {
    public SliderBossRoom(StructureManager structureManager, int depth, StructurePlaceSettings placeSettings, BlockPos pos) {
        super(AetherStructurePieces.SLIDER_BOSS_ROOM, depth, structureManager, "bronze_dungeon/boss_room", placeSettings.addProcessor(BronzeStructureFeature.SENTRY_PROCESSOR), pos);
    }

    public SliderBossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieces.SLIDER_BOSS_ROOM, context, tag);
    }

    @Override
    public NoiseEffect getNoiseEffect() {
        return NoiseEffect.BURY;
    }

    @Override
    protected void handleDataMarker(String marker, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox box) {

    }
}
