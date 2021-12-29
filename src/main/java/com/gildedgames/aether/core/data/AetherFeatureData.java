package com.gildedgames.aether.core.data;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherFeatures;
import com.gildedgames.aether.core.data.provider.AetherFeatureDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.OptionalInt;

public class AetherFeatureData {

    public static final ConfiguredFeature COLD_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(16, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState()));

    public static final ConfiguredFeature BLUE_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(8, AetherBlocks.BLUE_AERCLOUD.get().defaultBlockState()));

    public static final ConfiguredFeature GOLDEN_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(4, AetherBlocks.GOLDEN_AERCLOUD.get().defaultBlockState()));

    public static final ConfiguredFeature PINK_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(1, AetherBlocks.PINK_AERCLOUD.get().defaultBlockState()));

    public static final ConfiguredFeature SKYROOT_TREE_FEATURE_BASE = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(getDoubleDrops(AetherBlocks.SKYROOT_LOG.get())),
            new StraightTrunkPlacer(4, 2, 0),
            BlockStateProvider.simple(getDoubleDrops(AetherBlocks.SKYROOT_LEAVES.get())),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
            new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

    public static final ConfiguredFeature GOLDEN_OAK_FEATURE_BASE = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(getDoubleDrops(AetherBlocks.GOLDEN_OAK_LOG.get())),
            new FancyTrunkPlacer(3, 11, 0),
            BlockStateProvider.simple(getDoubleDrops(AetherBlocks.GOLDEN_OAK_LEAVES.get())),
            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).build());

    public static final ConfiguredFeature<RandomPatchConfiguration, ?> FLOWER_FEATURE_BASE = Feature.FLOWER.configured(AetherFeatureDataProvider.grassPatch(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(AetherBlocks.PURPLE_FLOWER.get().defaultBlockState(), 2)
                    .add(AetherBlocks.WHITE_FLOWER.get().defaultBlockState(), 2)
                    .add(getDoubleDrops(AetherBlocks.BERRY_BUSH.get()), 1)), 64));

    public static final PlacedFeature COLD_AERCLOUD_FEATURE = COLD_AERCLOUD_FEATURE_BASE.placed(
            AetherFeatureDataProvider.createAercloudPlacements(128, 5));

    public static final PlacedFeature BLUE_AERCLOUD_FEATURE = BLUE_AERCLOUD_FEATURE_BASE.placed(
            AetherFeatureDataProvider.createAercloudPlacements(96, 5));

    public static final PlacedFeature GOLDEN_AERCLOUD_FEATURE = GOLDEN_AERCLOUD_FEATURE_BASE.placed(
            AetherFeatureDataProvider.createAercloudPlacements(160, 5));

    public static final PlacedFeature PINK_AERCLOUD_FEATURE = PINK_AERCLOUD_FEATURE_BASE.placed(
            AetherFeatureDataProvider.createAercloudPlacements(160, 7));

    public static final PlacedFeature SKYROOT_TREE_FEATURE = SKYROOT_TREE_FEATURE_BASE.placed(VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1), AetherBlocks.SKYROOT_SAPLING.get()));

    public static final PlacedFeature GOLDEN_OAK_TREE_FEATURE = GOLDEN_OAK_FEATURE_BASE.placed(VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1), AetherBlocks.GOLDEN_OAK_SAPLING.get()));

    public static final PlacedFeature FLOWER_FEATURE = FLOWER_FEATURE_BASE.placed(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static BlockState getDoubleDrops(Block block) {
        return getDoubleDrops(block.defaultBlockState());
    }

    public static BlockState getDoubleDrops(BlockState blockState) {
        return blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
    }


}
