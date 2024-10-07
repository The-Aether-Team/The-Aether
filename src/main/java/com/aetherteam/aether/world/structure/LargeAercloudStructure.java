package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.world.structurepiece.LargeAercloudChunk;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.*;

public class LargeAercloudStructure extends Structure {
    public static final MapCodec<LargeAercloudStructure> CODEC = RecordCodecBuilder.mapCodec((p_229075_) -> p_229075_.group(settingsCodec(p_229075_),
            BlockStateProvider.CODEC.fieldOf("blocks").forGetter(structure -> structure.blocks),
            Codec.INT.fieldOf("size").forGetter(structure -> structure.size),
            Codec.INT.fieldOf("rangeY").forGetter(o -> o.rangeY)
    ).apply(p_229075_, LargeAercloudStructure::new));

    private final BlockStateProvider blocks;
    private final int size;
    private final int rangeY;

    public LargeAercloudStructure(StructureSettings settings, BlockStateProvider blocks, int size, int rangeY) {
        super(settings);
        this.blocks = blocks;
        this.size = size;
        this.rangeY = rangeY;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return Structure.onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (builder) -> generatePieces(builder, context, this.blocks, this.size, this.rangeY));
    }

    private static void generatePieces(StructurePiecesBuilder builder, GenerationContext context, BlockStateProvider blocks, int size, int rangeY) {
        Map<ChunkPos, Set<BlockPos>> chunks = new LinkedHashMap<>();
        Set<BlockPos> positions = new LinkedHashSet<>();

        WorldgenRandom random = context.random();
        boolean direction = random.nextBoolean();
        int initialY = context.heightAccessor().getMinBuildHeight() + context.random().nextInt(rangeY);
        int x = context.chunkPos().getMinBlockX();
        int y = initialY;
        int z = context.chunkPos().getMinBlockZ();
        int xTendency = random.nextInt(3) - 1;
        int zTendency = random.nextInt(3) - 1;

        // Sets up what positions cloud blocks should be able to be placed. The code is taken from older versions.
        for (int amount = 0; amount < 64; ++amount) {
            x += random.nextInt(3) - 1 + xTendency;
            y += random.nextInt(10) == 0 ? random.nextInt(3) - 1 : 0;
            z += direction ? random.nextInt(3) - 1 + zTendency : -(random.nextInt(3) - 1 + zTendency);

            for (int x1 = x; x1 < x + random.nextInt(4) + 3 * size; ++x1) {
                for (int y1 = y; y1 < y + random.nextInt(1) + 2; ++y1) {
                    for (int z1 = z; z1 < z + random.nextInt(4) + 3 * size; ++z1) {
                        BlockPos newPosition = new BlockPos(x1, y1, z1);
                        if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * size + random.nextInt(2)) {
                            positions.add(newPosition);
                            chunks.putIfAbsent(new ChunkPos(newPosition), new HashSet<>());
                        }
                    }
                }
            }
        }

        // Checks if positions are within their chunk.
        chunks.forEach(((chunkPos, blockPosSet) -> {
            Set<BlockPos> withinChunk = new LinkedHashSet<>(positions);
            withinChunk.removeIf(pos -> !(new ChunkPos(pos).equals(chunkPos)));
            blockPosSet.addAll(withinChunk);
        }));

        // Creates a LargeAercloudChunk piece for each chunk that there are blocks in. The pieces handle the actual Aercloud block placement.
        int finalY = y;
        Direction orientation = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
        chunks.forEach((chunkPos, blockPosSet) -> {
            BoundingBox boundingBox = new BoundingBox(chunkPos.getMinBlockX(), Math.max(initialY - 16, 0), chunkPos.getMinBlockZ(), chunkPos.getMaxBlockX(), finalY + 16, chunkPos.getMaxBlockZ());
            builder.addPiece(new LargeAercloudChunk(blockPosSet, blocks, boundingBox, orientation));
        });
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.LARGE_AERCLOUD.get();
    }
}
