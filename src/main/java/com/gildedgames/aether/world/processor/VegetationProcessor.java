package com.gildedgames.aether.world.processor;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.data.resources.registries.AetherConfiguredFeatures;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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

public class VegetationProcessor extends StructureProcessor {
    public static final Codec<VegetationProcessor> CODEC = Codec.unit(() -> VegetationProcessor.INSTANCE);

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
        if (template != null) {
            BoundingBox box = template.getBoundingBox(settings, templatePos);
            int y = Mth.floor(box.maxY() - box.minY() * 0.75) + 1;
            if (relativeBlockInfo.pos.getY() < y) {
                return relativeBlockInfo.state.isAir() ? null : relativeBlockInfo;
            }
        }

        if (reader instanceof WorldGenLevel level && level.getChunkSource() instanceof ServerChunkCache chunkSource) {
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
