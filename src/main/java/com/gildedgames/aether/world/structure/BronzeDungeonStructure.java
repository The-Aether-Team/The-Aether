package com.gildedgames.aether.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class BronzeDungeonStructure extends Structure {
    public static final Codec<BronzeDungeonStructure> CODEC = simpleCodec(BronzeDungeonStructure::new);
    protected BronzeDungeonStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        RandomSource random = context.random();
        BlockPos blockPos = new BlockPos(context.chunkPos().getMiddleBlockX(), 60, context.chunkPos().getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockPos, (builder -> generatePieces(builder, context))));
    }

    private static void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {

    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.BRONZE_DUNGEON.get();
    }
}
