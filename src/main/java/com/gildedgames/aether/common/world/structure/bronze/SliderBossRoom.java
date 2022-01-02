package com.gildedgames.aether.common.world.structure.bronze;

import com.gildedgames.aether.common.world.structure.AetherStructurePieces;
import com.gildedgames.aether.common.world.structure.AetherTemplatePiece;
import com.gildedgames.aether.core.util.BlockLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Random;

public class SliderBossRoom extends AetherTemplatePiece {
    public SliderBossRoom(StructureManager structureManager, int depth, StructurePlaceSettings placeSettings, BlockPos pos) {
        super(AetherStructurePieces.SLIDER_BOSS_ROOM, depth, structureManager, "bronze_dungeon/boss_room", placeSettings.addProcessor(BronzeStructureFeature.SENTRY_PROCESSOR), pos);

        this.setOrientation(Direction.NORTH);
    }

    public SliderBossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieces.SLIDER_BOSS_ROOM, context, tag, new StructurePlaceSettings().addProcessor(BronzeStructureFeature.SENTRY_PROCESSOR));
    }

    @Override
    public NoiseEffect getNoiseEffect() {
        return NoiseEffect.BURY;
    }

    @Override
    public void addChildren(StructurePiece parent, StructurePieceAccessor pieceAccessor, Random random) {
        Direction tunnelDirection = this.getRotation().rotate(Direction.NORTH);

        var tunnel = new HolystoneTunnel(BlockLogic.tunnelFromEvenSquareRoom(this.getBoundingBox(), tunnelDirection, 30, 5, 5), this.getGenDepth() - 1, tunnelDirection);
        pieceAccessor.addPiece(tunnel);
        tunnel.addChildren(parent, pieceAccessor, random);
    }

    @Override
    protected void handleDataMarker(String marker, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox box) {

    }
}
