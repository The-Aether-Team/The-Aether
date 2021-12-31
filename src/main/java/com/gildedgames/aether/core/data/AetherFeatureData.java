package com.gildedgames.aether.core.data;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherFeatures;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import com.gildedgames.aether.common.world.gen.configuration.SimpleDiskConfiguration;
import com.gildedgames.aether.common.world.gen.placement.ElevationAdjustment;
import com.gildedgames.aether.common.world.gen.placement.ElevationFilter;
import com.gildedgames.aether.core.data.provider.AetherFeatureDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.OptionalInt;

public class AetherFeatureData
{
    public static final ConfiguredFeature<AercloudConfiguration, ?> COLD_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(16, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState()));
    public static final ConfiguredFeature<AercloudConfiguration, ?> BLUE_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(8, AetherBlocks.BLUE_AERCLOUD.get().defaultBlockState()));
    public static final ConfiguredFeature<AercloudConfiguration, ?> GOLDEN_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(4, AetherBlocks.GOLDEN_AERCLOUD.get().defaultBlockState()));
    public static final ConfiguredFeature<AercloudConfiguration, ?> PINK_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(1, AetherBlocks.PINK_AERCLOUD.get().defaultBlockState()));

    public static final ConfiguredFeature<TreeConfiguration, ?> SKYROOT_TREE_FEATURE_BASE = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(AetherFeatureDataProvider.getDoubleDrops(AetherBlocks.SKYROOT_LOG)),
            new StraightTrunkPlacer(4, 2, 0),
            BlockStateProvider.simple(AetherFeatureDataProvider.getDoubleDrops(AetherBlocks.SKYROOT_LEAVES)),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
            new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

    public static final ConfiguredFeature<TreeConfiguration, ?> GOLDEN_OAK_FEATURE_BASE = Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(AetherFeatureDataProvider.getDoubleDrops(AetherBlocks.GOLDEN_OAK_LOG)),
            new FancyTrunkPlacer(3, 11, 0),
            BlockStateProvider.simple(AetherFeatureDataProvider.getDoubleDrops(AetherBlocks.GOLDEN_OAK_LEAVES.get())),
            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).build());

    public static final ConfiguredFeature<RandomPatchConfiguration, ?> FLOWER_FEATURE_BASE = Feature.FLOWER.configured(AetherFeatureDataProvider.grassPatch(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(AetherBlocks.PURPLE_FLOWER.get().defaultBlockState(), 2)
                    .add(AetherBlocks.WHITE_FLOWER.get().defaultBlockState(), 2)
                    .add(AetherFeatureDataProvider.getDoubleDrops(AetherBlocks.BERRY_BUSH), 1)), 64));

    public static final ConfiguredFeature<SimpleDiskConfiguration, ?> QUICKSOIL_BASE = AetherFeatures.SIMPLE_DISK.get().configured(new SimpleDiskConfiguration(
            UniformFloat.of(Mth.sqrt(12), 5), // sqrt(12) is old static value
            BlockStateProvider.simple(AetherFeatureDataProvider.getDoubleDrops(AetherBlocks.QUICKSOIL)),
            3
    ));

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

    public static final PlacedFeature QUICKSOIL_SHELF_FEATURE = QUICKSOIL_BASE.placed(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, new ElevationAdjustment(UniformInt.of(-4, -2)), new ElevationFilter(47, 70), BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(BlockPredicate.matchesBlock(AetherBlocks.AETHER_DIRT.get(), BlockPos.ZERO), BlockPredicate.matchesTag(AetherTags.Blocks.HOLYSTONE)))); // FIXME once Terrain can go above 63 again, change 47 -> 63

    public static final RuleTest HOLYSTONE = new TagMatchTest(AetherTags.Blocks.HOLYSTONE);

    public static final ConfiguredFeature<OreConfiguration, ?> ORE_DIRT_FEATURE_BASE = Feature.ORE.configured(new OreConfiguration(HOLYSTONE, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), 33));
    public static final ConfiguredFeature<OreConfiguration, ?> ORE_ICESTONE_FEATURE_BASE = Feature.ORE.configured(new OreConfiguration(HOLYSTONE, AetherBlocks.ICESTONE.get().defaultBlockState(), 16));
    public static final ConfiguredFeature<OreConfiguration, ?> ORE_AMBROSIUM_FEATURE_BASE = Feature.ORE.configured(new OreConfiguration(HOLYSTONE, AetherFeatureDataProvider.getDoubleDrops(AetherBlocks.AMBROSIUM_ORE), 16));
    public static final ConfiguredFeature<OreConfiguration, ?> ORE_ZANITE_FEATURE_BASE = Feature.ORE.configured(new OreConfiguration(HOLYSTONE, AetherBlocks.ZANITE_ORE.get().defaultBlockState(), 8));
    public static final ConfiguredFeature<OreConfiguration, ?> ORE_GRAVITITE_DENSE_FEATURE_BASE = Feature.ORE.configured(new OreConfiguration(HOLYSTONE, AetherBlocks.GRAVITITE_ORE.get().defaultBlockState(), 3, 0.5f));
    public static final ConfiguredFeature<OreConfiguration, ?> ORE_GRAVITITE_COMMON_FEATURE_BASE = Feature.ORE.configured(new OreConfiguration(HOLYSTONE, AetherBlocks.GRAVITITE_ORE.get().defaultBlockState(), 6, 0.9f));

    public static final PlacedFeature ORE_DIRT_FEATURE = ORE_DIRT_FEATURE_BASE.placed(
            AetherFeatureDataProvider.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final PlacedFeature ORE_ICESTONE_FEATURE = ORE_ICESTONE_FEATURE_BASE.placed(
            AetherFeatureDataProvider.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final PlacedFeature ORE_AMBROSIUM_FEATURE = ORE_AMBROSIUM_FEATURE_BASE.placed(
            AetherFeatureDataProvider.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final PlacedFeature ORE_ZANITE_FEATURE = ORE_ZANITE_FEATURE_BASE.placed(
            AetherFeatureDataProvider.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final PlacedFeature ORE_GRAVITITE_DENSE_FEATURE = ORE_GRAVITITE_DENSE_FEATURE_BASE.placed(
            AetherFeatureDataProvider.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final PlacedFeature ORE_GRAVITITE_COMMON_FEATURE = ORE_GRAVITITE_COMMON_FEATURE_BASE.placed(
            AetherFeatureDataProvider.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
}