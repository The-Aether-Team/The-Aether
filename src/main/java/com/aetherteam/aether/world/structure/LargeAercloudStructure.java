package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.world.structurepiece.LegacyCloudBed;
import com.mojang.serialization.Codec;
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

import javax.annotation.Nonnull;
import java.util.*;

public class LargeAercloudStructure extends Structure {
    public static final Codec<LargeAercloudStructure> CODEC = RecordCodecBuilder.create((p_229075_) -> p_229075_.group(settingsCodec(p_229075_),
            BlockStateProvider.CODEC.fieldOf("blocks").forGetter(structure -> structure.blocks),
            Codec.INT.fieldOf("size").forGetter(structure -> structure.size)
    ).apply(p_229075_, LargeAercloudStructure::new));
    private final BlockStateProvider blocks;
    private final int size;

    public LargeAercloudStructure(Structure.StructureSettings settings, BlockStateProvider blocks, int size) {
        super(settings);
        this.blocks = blocks;
        this.size = size;
    }

    @Nonnull
    @Override
    public Optional<GenerationStub> findGenerationPoint(@Nonnull Structure.GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (builder) -> generatePieces(builder, context, this.blocks, this.size));
    }

    private static void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context, BlockStateProvider blocks, int size) {
        Map<ChunkPos, Set<BlockPos>> chunks = new LinkedHashMap<>();
        Set<BlockPos> positions = new LinkedHashSet<>();

        WorldgenRandom random = context.random();
        boolean direction = random.nextBoolean();
        int initialY = context.heightAccessor().getMinBuildHeight() + context.random().nextInt(32);
        int x = context.chunkPos().getMinBlockX();
        int y = initialY;
        int z = context.chunkPos().getMinBlockZ();
        int xTendency = random.nextInt(3) - 1;
        int zTendency = random.nextInt(3) - 1;

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

        chunks.forEach(((chunkPos, blockPosSet) -> {
            Set<BlockPos> withinChunk = new LinkedHashSet<>(positions);
            withinChunk.removeIf(pos -> !(new ChunkPos(pos).equals(chunkPos)));
            blockPosSet.addAll(withinChunk);
        }));

        int finalY = y;
        Direction orientation = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
        chunks.forEach((chunkPos, blockPosSet) -> {
            BoundingBox boundingBox = new BoundingBox(chunkPos.getMinBlockX(), Math.max(initialY - 16, 0), chunkPos.getMinBlockZ(), chunkPos.getMaxBlockX(), finalY + 16, chunkPos.getMaxBlockZ());
            builder.addPiece(new LegacyCloudBed(blockPosSet, blocks, boundingBox, orientation));
        });
    }

    @Nonnull
    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.LARGE_AERCLOUD.get();
    }
}
