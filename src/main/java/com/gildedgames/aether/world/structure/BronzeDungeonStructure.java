package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.world.structurepiece.BronzeDungeonGraph;
import com.gildedgames.aether.world.structurepiece.BronzeDungeonPieces;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

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
        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        int height = chunkGenerator.getBaseHeight(chunkPos.getMiddleBlockX(), chunkPos.getMiddleBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockPos blockPos = new BlockPos(context.chunkPos().getMiddleBlockX(), 50, context.chunkPos().getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockPos, (builder -> this.generatePieces(builder, context, blockPos))));
    }

    private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context, BlockPos startPos) {
        BronzeDungeonGraph graph = new BronzeDungeonGraph(builder, context, this.maxRooms);
        graph.initializeDungeon(startPos);
        graph.populatePiecesBuilder();
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.BRONZE_DUNGEON.get();
    }
}
