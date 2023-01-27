package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.structurepiece.BronzeDungeonGraph;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
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
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        int height = findStartingHeight(chunkGenerator, heightAccessor, chunkPos, context.randomState(), context.structureTemplateManager());
        if (height < heightAccessor.getMinBuildHeight() + 30) {
            return Optional.empty();
        }
        BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), height, context.chunkPos().getMinBlockZ());
        return Optional.of(new GenerationStub(blockPos, (builder -> this.generatePieces(builder, context, blockPos))));
    }

    private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context, BlockPos startPos) {
        BronzeDungeonGraph graph = new BronzeDungeonGraph(builder, context, this.maxRooms);
        graph.initializeDungeon(startPos);
        graph.populatePiecesBuilder();
    }

    /**
     * Check the density at all four corners of the chunk.
     */
    private static int findStartingHeight(ChunkGenerator chunkGenerator, LevelHeightAccessor heightAccessor, ChunkPos chunkPos, RandomState random, StructureTemplateManager manager) {
        int minX = chunkPos.getMinBlockX();
        int minZ = chunkPos.getMinBlockZ();
        int maxX = chunkPos.getMaxBlockX();
        int maxZ = chunkPos.getMaxBlockZ();
        NoiseColumn[] columns = {
                chunkGenerator.getBaseColumn(minX, minZ, heightAccessor, random),
                chunkGenerator.getBaseColumn(minX, maxZ, heightAccessor, random),
                chunkGenerator.getBaseColumn(maxX, minZ, heightAccessor, random),
                chunkGenerator.getBaseColumn(maxX, maxZ, heightAccessor, random)
        };

        int roomHeight = checkRoomHeight(manager, new ResourceLocation(Aether.MODID, "bronze_dungeon/boss_room"));

        int height = heightAccessor.getMinBuildHeight();
        int maxHeight = heightAccessor.getMaxBuildHeight() - 24;

        int thickness = roomHeight + 10;
        int currentThickness = 0;

        for (int y = height + 32; y <= maxHeight; y++) {
            if (checkEachCornerAtY(columns, y)) {
                ++currentThickness;
            } else {
                if (currentThickness > thickness) {
                    thickness = currentThickness;
                    height = y;
                }
                currentThickness = 0;
            }

        }

        height -= thickness * 0.8;
        return height;
    }

    private static int checkRoomHeight(StructureTemplateManager manager, ResourceLocation roomName) {
        StructureTemplate template = manager.getOrCreate(roomName);
        return template.getSize().getY();
    }

    /**
     * Returns false if there is air in one of the noise columns at the given y
     */
    private static boolean checkEachCornerAtY(NoiseColumn[] columns, int y) {
        for (NoiseColumn column : columns) {
            if (column.getBlock(y).isAir()) {
                return false;
            }
        }
        return true;
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
