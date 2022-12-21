package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.world.structurepiece.BronzeDungeonGraph;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

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
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        int height = chunkGenerator.getFirstOccupiedHeight(chunkPos.getMiddleBlockX(), chunkPos.getMiddleBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, heightAccessor, context.randomState());
        if (height < heightAccessor.getMinBuildHeight() + 30) {
            return Optional.empty();
        }
        BlockPos blockPos = new BlockPos(context.chunkPos().getMiddleBlockX(), height - 10, context.chunkPos().getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockPos, (builder -> this.generatePieces(builder, context, blockPos))));
    }

    private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context, BlockPos startPos) {
        BronzeDungeonGraph graph = new BronzeDungeonGraph(builder, context, this.maxRooms);
        graph.initializeDungeon(startPos);
        graph.populatePiecesBuilder();
    }

    /**
     * Override to prevent beardifier bounding box adjustment
     */
    @Override
    public BoundingBox adjustBoundingBox(BoundingBox box) {
        return box;
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.BRONZE_DUNGEON.get();
    }
}
