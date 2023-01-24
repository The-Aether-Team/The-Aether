package com.gildedgames.aether.world.processor;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.data.resources.registries.AetherConfiguredFeatures;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * This structure processor plants trees and flowers on top of the island.
 * It's only used for the gold dungeon island currently.
 */
@Deprecated(forRemoval = true)
public class VegetationProcessor extends StructureProcessor {
    public static final Codec<VegetationProcessor> CODEC = Codec.unit(() -> VegetationProcessor.INSTANCE); //TODO: Make a proper codec

    public static final VegetationProcessor INSTANCE = new VegetationProcessor(48, 2, true);
    public static final VegetationProcessor STUB_PROCESSOR = new VegetationProcessor(64, 1, false);

    private final int randomBounds;
    private final int treeWeight;
    private final boolean spawnFlowers;

    public VegetationProcessor(int randomBounds, int treeWeight, boolean spawnFlowers) {
        this.randomBounds = randomBounds;
        this.treeWeight = treeWeight;
        this.spawnFlowers = spawnFlowers;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader reader, BlockPos templatePos, BlockPos pPos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (reader instanceof WorldGenLevel level && level.getChunkSource() instanceof ServerChunkCache chunkSource) { // During worldgen, this should always be true.
            // If the processor is running outside of the center chunk, return immediately.
            if (level instanceof WorldGenRegion region) {
                int x = SectionPos.blockToSectionCoord(relativeBlockInfo.pos.getX());
                int z = SectionPos.blockToSectionCoord(relativeBlockInfo.pos.getZ());
                ChunkPos chunk = region.getCenter();

                int xDistance = Math.abs(x - chunk.x);
                int zDistance = Math.abs(z - chunk.z);
                if (xDistance > 1 || zDistance > 1) {
                    return relativeBlockInfo;
                }
            }
            if (template != null) {
                BoundingBox box = template.getBoundingBox(settings, templatePos);
                int y = Mth.floor((box.maxY() - box.minY()) * 0.75) + 1;
                if (relativeBlockInfo.pos.getY() < y) {
                    return relativeBlockInfo.state.isAir() ? null : relativeBlockInfo;
                }
            }
            if (relativeBlockInfo.state.isAir()) {
                if (level.getBlockState(relativeBlockInfo.pos.below()).is(AetherTags.Blocks.AETHER_DIRT)) {
                    RandomSource random = settings.getRandom(relativeBlockInfo.pos);
                    int featureType = random.nextInt(this.randomBounds);
                    if (featureType < this.treeWeight) {
                        PlacedFeature tree = PlacementUtils.inlinePlaced(level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION)).get();
                        tree.place(level, chunkSource.getGenerator(), random, relativeBlockInfo.pos);
                    } else {
                        if (this.spawnFlowers && featureType == this.treeWeight) {
                            Block flower = random.nextBoolean() ? Blocks.DANDELION : Blocks.POPPY;
                            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos, flower.defaultBlockState(), null);
                        }
                    }
                }
                return null;
            }
        }

        return relativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.VEGETATION.get();
    }
}
