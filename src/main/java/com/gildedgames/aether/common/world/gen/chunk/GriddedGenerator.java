package com.gildedgames.aether.common.world.gen.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GriddedGenerator extends DelegatedChunkGenerator {
    public static final Codec<GriddedGenerator> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ChunkGenerator.CODEC.fieldOf("delegate").forGetter(o -> o.delegate),
            Codec.INT.fieldOf("vertical_unit_span").forGetter(o -> o.verticalUnitSpan),
            Codec.INT.fieldOf("blocks_to_unit_span").forGetter(o -> o.blocksToUnitScale),
            Codec.INT.fieldOf("vertical_scale").forGetter(o -> o.verticalScale),
            SurfaceRules.RuleSource.CODEC.fieldOf("surface_rule").forGetter(o -> o.ruleSource),
            BlockState.CODEC.fieldOf("blockstate").forGetter(o -> o.defaultBlock)
    ).apply(inst, GriddedGenerator::new));

    private final int verticalUnitSpan;
    private final int blocksToUnitScale;
    private final int blockHalfScale;
    private final int verticalScale;
    private final int verticalHalfScale;
    private final SurfaceRules.RuleSource ruleSource;
    private final BlockState defaultBlock;

    public GriddedGenerator(ChunkGenerator delegate, int verticalUnitSpan, int blocksToUnitScale, int verticalScale, SurfaceRules.RuleSource ruleSource, BlockState defaultBlock) {
        super(delegate, delegate.getBiomeSource(), delegate.getSettings(), delegate.strongholdSeed);
        // Can't have 0 thickness or less
        this.verticalUnitSpan = Math.max(1, verticalUnitSpan);
        this.blocksToUnitScale = blocksToUnitScale | 1; // Must be even number
        this.blockHalfScale = this.blocksToUnitScale >> 1;
        this.verticalScale = verticalScale | 1; // Must be even number
        this.verticalHalfScale = this.verticalScale >> 1;
        this.ruleSource = ruleSource;
        this.defaultBlock = defaultBlock;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager structureFeatureManager, ChunkAccess chunkAccess) {
        return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("aether_grid_fill", () -> this.doFill(blender, structureFeatureManager, chunkAccess)), executor);
    }

    private ChunkAccess doFill(Blender blender, StructureFeatureManager structureFeatureManager, ChunkAccess chunk) {
        //Beardifier beardifier = new Beardifier(structureFeatureManager, chunk); TODO
        Heightmap oceanHeightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap worldHeightmap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);

        ChunkPos chunkPos = chunk.getPos();
        int xPos = chunkPos.getMinBlockX();
        int zPos = chunkPos.getMinBlockZ();

        for (int z = 0; z < 16; z++)
            for (int x = 0; x < 16; x++)
                for (int y = chunk.getMinBuildHeight(); y < chunk.getMaxBuildHeight(); y++)
                    if (this.test(xPos + x, y, zPos + z))
                        this.place(x, y, z, this.defaultBlock, oceanHeightmap, worldHeightmap, chunk);

        return chunk;
    }

    protected boolean test(int x, int y, int z) {
        float rX = Math.abs(this.relativeXZ(x));
        float rZ = Math.abs(this.relativeXZ(z));
        float rY = Math.abs(this.relativeY(y));

        float v = rX * rY + rX * rZ + rY * rZ;

        return v <= 0.2f;
    }

    protected void place(int x, int y, int z, BlockState state, Heightmap oceanHeightmap, Heightmap worldHeightmap, ChunkAccess chunk) {
        chunk.setBlockState(new BlockPos(x, y, z), state, false);
        oceanHeightmap.update(x, y, z, state);
        worldHeightmap.update(x, y, z, state);
    }

    protected float relativeXZ(float xzPos) {
        return (Mth.positiveModulo(xzPos, this.blocksToUnitScale) - this.blockHalfScale) / (float) this.blockHalfScale;
    }

    protected float relativeY(float yPos) {
        return (Mth.positiveModulo(yPos, this.verticalScale) - this.verticalHalfScale) / (float) this.verticalHalfScale;
    }

    protected int nearestXZ(int xzPos) {
        return xzPos / this.blocksToUnitScale;
    }

    protected int nearestY(int yPos) {
        return Mth.clamp(0, yPos / this.verticalScale, this.verticalUnitSpan - 1);
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }
}
