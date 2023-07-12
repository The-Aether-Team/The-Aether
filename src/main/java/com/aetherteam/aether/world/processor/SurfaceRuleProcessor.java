package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.common.accessor.ChunkAccessAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SurfaceRuleProcessor extends StructureProcessor {
    public static final Codec<SurfaceRuleProcessor> CODEC = Codec.unit(() -> SurfaceRuleProcessor.INSTANCE);

    public static final SurfaceRuleProcessor INSTANCE = new SurfaceRuleProcessor();

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader reader, BlockPos templatePos, BlockPos pPos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (reader instanceof WorldGenLevel level) {
            // If the processor is running outside of the center chunk, return immediately.
            if (level instanceof WorldGenRegion region && isOutOfBounds(relativeBlockInfo.pos, region.getCenter())) {
                return relativeBlockInfo;
            }
            if (level.getChunkSource() instanceof ServerChunkCache serverChunkCache) {
                if (serverChunkCache.getGenerator() instanceof NoiseBasedChunkGenerator noiseBasedChunkGenerator) {
                    NoiseGeneratorSettings settingsHolder = noiseBasedChunkGenerator.generatorSettings().get();
                    SurfaceRules.RuleSource surfaceRule = settingsHolder.surfaceRule();
                    ChunkAccess chunkAccess = level.getChunk(relativeBlockInfo.pos);
                    NoiseChunk noisechunk = ((ChunkAccessAccessor) chunkAccess).aether$getNoiseChunk();
                    if (noisechunk != null) {
                        CarvingContext carvingcontext = new CarvingContext(noiseBasedChunkGenerator, reader.registryAccess(), chunkAccess.getHeightAccessorForGeneration(), noisechunk, serverChunkCache.randomState(), surfaceRule);
                        Optional<BlockState> state = carvingcontext.topMaterial(reader.getBiomeManager()::getBiome, chunkAccess, relativeBlockInfo.pos, false);
                        if (state.isPresent()) {
                            if (relativeBlockInfo.state.is(AetherTags.Blocks.AETHER_DIRT) && !relativeBlockInfo.state.is(AetherBlocks.AETHER_DIRT.get()) && state.get().is(AetherTags.Blocks.AETHER_DIRT)) {
                                return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos, state.get(), null);
                            }
                        }
                    }
                }
            }
        }
        return relativeBlockInfo;
    }

    private boolean isOutOfBounds(BlockPos relativePos, ChunkPos centerChunk) {
        int x = SectionPos.blockToSectionCoord(relativePos.getX());
        int z = SectionPos.blockToSectionCoord(relativePos.getZ());
        int xDistance = Math.abs(x - centerChunk.x);
        int zDistance = Math.abs(z - centerChunk.z);
        return xDistance > 1 || zDistance > 1;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.SURFACE_RULE.get();
    }
}
