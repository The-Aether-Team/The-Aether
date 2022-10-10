package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.world.structurepiece.BronzeDungeonPieces;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BronzeDungeonStructure extends Structure {
    public static final Codec<BronzeDungeonStructure> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            settingsCodec(builder),
            Codec.INT.fieldOf("maxrooms").forGetter(o -> o.maxRooms)
    ).apply(builder, BronzeDungeonStructure::new));

    private final int maxRooms;
    public BronzeDungeonStructure(StructureSettings settings, int maxRooms) {
        super(settings);
        this.maxRooms = maxRooms;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getMiddleBlockX(), 60, context.chunkPos().getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockPos, (builder -> generatePieces(builder, context, blockPos))));
    }

    private static void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context, BlockPos pos) {
        List<StructurePiece> pieces = new ArrayList<>();
        BronzeDungeonPieces.startBronzeDungeon(pieces, context.structureTemplateManager(), pos, context.random());
        pieces.forEach(builder::addPiece);
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.BRONZE_DUNGEON.get();
    }
}
