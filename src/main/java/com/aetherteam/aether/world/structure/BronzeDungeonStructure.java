package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeDungeonBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Optional;

public class BronzeDungeonStructure extends Structure {
    public static final Codec<BronzeDungeonStructure> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            settingsCodec(builder),
            Codec.INT.fieldOf("maxrooms").forGetter(o -> o.maxRooms),
            Codec.INT.fieldOf("aboveBottom").forGetter(o -> o.aboveBottom),
            Codec.INT.fieldOf("belowTop").forGetter(o -> o.belowTop)
    ).apply(builder, BronzeDungeonStructure::new));

    private final int maxRooms;
    private final int aboveBottom;
    private final int belowTop;

    public BronzeDungeonStructure(StructureSettings settings, int maxRooms, int aboveBottom, int belowTop) {
        super(settings);
        this.maxRooms = maxRooms;
        this.aboveBottom = aboveBottom;
        this.belowTop = belowTop;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        RandomState randomState = context.randomState();
        StructureTemplateManager templateManager = context.structureTemplateManager();
        int height = findStartingHeight(chunkGenerator, heightAccessor, chunkPos, randomState, templateManager, this.aboveBottom, this.belowTop);
        // To make structure placement more reliable, we check the surrounding 8 chunks for suitable locations.
        if (height <= heightAccessor.getMinBuildHeight()) {
            MutableInt y = new MutableInt(height);
            chunkPos = searchNearbyChunks(chunkPos, y, chunkGenerator, heightAccessor, randomState, templateManager, this.aboveBottom, this.belowTop);
            height = y.getValue();
            if (height <= heightAccessor.getMinBuildHeight()) {
                return Optional.empty();
            }
        }
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), height, chunkPos.getMinBlockZ());
        return Optional.of(new GenerationStub(blockPos, builder -> this.generatePieces(builder, context, blockPos)));
    }

    private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context, BlockPos startPos) {
        BronzeDungeonBuilder graph = new BronzeDungeonBuilder(context, this.maxRooms);
        graph.initializeDungeon(startPos, context.chunkPos(), builder);
    }

    /**
     * Check the surrounding chunks for bronze dungeon placement.
     *
     * @param chunkPos        The {@link ChunkPos}.
     * @param height          The {@link MutableInt} for the height to check.
     * @param generator       The {@link ChunkGenerator} for generation.
     * @param heightAccessor  The {@link LevelHeightAccessor} to place in.
     * @param randomState     The {@link RandomState} for the structure.
     * @param templateManager The {@link StructureTemplateManager}.
     * @return A {@link ChunkPos} for placement.
     */
    private static ChunkPos searchNearbyChunks(ChunkPos chunkPos, MutableInt height, ChunkGenerator generator, LevelHeightAccessor heightAccessor, RandomState randomState, StructureTemplateManager templateManager, int aboveBottom, int belowTop) {
        int y;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x != 0 || z != 0) {
                    ChunkPos offset = new ChunkPos(chunkPos.x + x, chunkPos.z + z);
                    y = BronzeDungeonStructure.findStartingHeight(generator, heightAccessor, offset, randomState, templateManager, aboveBottom, belowTop);
                    if (y > heightAccessor.getMinBuildHeight()) {
                        height.setValue(y);
                        return offset;
                    }
                }
            }
        }
        return chunkPos;
    }

    /**
     * The bronze dungeon needs to generate as covered by land as possible.
     * Try to find a place where the land is taller than the boss room.
     *
     * @param generator       The {@link ChunkGenerator} for generation.
     * @param heightAccessor  The {@link LevelHeightAccessor} to place in.
     * @param chunkPos        The {@link ChunkPos}.
     * @param random          The {@link RandomSource} for the structure.
     * @param templateManager The {@link StructureTemplateManager}.
     * @return The starting height as an {@link Integer}.
     */
    private static int findStartingHeight(ChunkGenerator generator, LevelHeightAccessor heightAccessor, ChunkPos chunkPos, RandomState random, StructureTemplateManager templateManager, int aboveBottom, int belowTop) {
        int minX = chunkPos.getMinBlockX() - 1;
        int minZ = chunkPos.getMinBlockZ() - 1;
        int maxX = chunkPos.getMaxBlockX() + 1;
        int maxZ = chunkPos.getMaxBlockZ() + 1;
        NoiseColumn[] columns = {
                generator.getBaseColumn(minX, minZ, heightAccessor, random),
                generator.getBaseColumn(minX, maxZ, heightAccessor, random),
                generator.getBaseColumn(maxX, minZ, heightAccessor, random),
                generator.getBaseColumn(maxX, maxZ, heightAccessor, random)
        };
        int roomHeight = checkRoomHeight(templateManager, new ResourceLocation(Aether.MODID, "bronze_dungeon/boss_room"));
        int height = heightAccessor.getMinBuildHeight();
        int maxHeight = heightAccessor.getMaxBuildHeight() - belowTop;
        int thickness = roomHeight + 2;
        int currentThickness = 0;
        for (int y = height + aboveBottom; y <= maxHeight; y++) {
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
        int offset = (thickness + roomHeight) / 2;
        height -= offset;
        return height;
    }

    private static int checkRoomHeight(StructureTemplateManager manager, ResourceLocation roomName) {
        StructureTemplate template = manager.getOrCreate(roomName);
        return template.getSize().getY();
    }

    /**
     * Checks for no air in a column.
     *
     * @param columns The {@link NoiseColumn NoiseColumn[]} array to check.
     * @param y       The given y-level {@link Integer} to check.
     * @return A {@link Boolean} if there was no air found at the given y-level. Returns false if there was air found.
     */
    private static boolean checkEachCornerAtY(NoiseColumn[] columns, int y) {
        for (NoiseColumn column : columns) {
            if (column.getBlock(y).isAir() || column.getBlock(y).is(AetherTags.Blocks.NON_BRONZE_DUNGEON_SPAWNABLE)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Override to prevent beardifier bounding box adjustment.
     *
     * @param box The original {@link BoundingBox}.
     * @return The new {@link BoundingBox}.
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
