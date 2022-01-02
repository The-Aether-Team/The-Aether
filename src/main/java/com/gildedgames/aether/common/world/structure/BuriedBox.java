package com.gildedgames.aether.common.world.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

public abstract class BuriedBox extends StructurePiece {
    public BuriedBox(StructurePieceType type, BoundingBox boundingBox, int recursionDepth) {
        super(type, recursionDepth, boundingBox);
    }

    public BuriedBox(StructurePieceType type, StructurePieceSerializationContext context, CompoundTag tag) {
        super(type, tag);
    }

    @Override
    public NoiseEffect getNoiseEffect() {
        return NoiseEffect.BURY;
    }
}
