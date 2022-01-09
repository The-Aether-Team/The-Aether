package com.gildedgames.aether.common.world.gen.chunk;

import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class DelegatedChunkGenerator extends ChunkGenerator {
    protected final ChunkGenerator delegate;

    public DelegatedChunkGenerator(ChunkGenerator delegate, BiomeSource biomeSource, StructureSettings structureSettings) {
        super(biomeSource, structureSettings);

        this.delegate = delegate;
    }

    public DelegatedChunkGenerator(ChunkGenerator delegate, BiomeSource biomeSource, StructureSettings settings, long seed) {
        this(delegate, biomeSource, biomeSource, settings, seed);
    }

    public DelegatedChunkGenerator(ChunkGenerator delegate, BiomeSource biomeSource, BiomeSource runtimeBiomeSource, StructureSettings settings, long seed) {
        super(biomeSource, runtimeBiomeSource, settings, seed);

        this.delegate = delegate;
    }

    @Override
    public ChunkGenerator withSeed(long pSeed) {
        return this.delegate.withSeed(pSeed);
    }

    @Override
    public Climate.Sampler climateSampler() {
        return this.delegate.climateSampler();
    }

    @Override
    public void applyCarvers(WorldGenRegion pLevel, long pSeed, BiomeManager pBiomeManager, StructureFeatureManager pStructureFeatureManager, ChunkAccess pChunk, GenerationStep.Carving pStep) {
        this.delegate.applyCarvers(pLevel, pSeed, pBiomeManager, pStructureFeatureManager, pChunk, pStep);
    }

    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureFeatureManager pStructureFeatureManager, ChunkAccess pChunk) {
        this.delegate.buildSurface(pLevel, pStructureFeatureManager, pChunk);
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion pLevel) {
        this.delegate.spawnOriginalMobs(pLevel);
    }

    @Override
    public int getGenDepth() {
        return this.delegate.getGenDepth();
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor p_187748_, Blender p_187749_, StructureFeatureManager p_187750_, ChunkAccess p_187751_) {
        return this.delegate.fillFromNoise(p_187748_, p_187749_, p_187750_, p_187751_);
    }

    @Override
    public int getSeaLevel() {
        return this.delegate.getSeaLevel();
    }

    @Override
    public int getMinY() {
        return this.delegate.getMinY();
    }

    @Override
    public int getBaseHeight(int pX, int pZ, Heightmap.Types pType, LevelHeightAccessor pLevel) {
        return this.delegate.getBaseHeight(pX, pZ, pType, pLevel);
    }

    @Override
    public NoiseColumn getBaseColumn(int pX, int pZ, LevelHeightAccessor pLevel) {
        return this.delegate.getBaseColumn(pX, pZ, pLevel);
    }
}
