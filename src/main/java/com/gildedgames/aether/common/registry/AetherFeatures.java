package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import com.gildedgames.aether.common.world.gen.configuration.SimpleDiskConfiguration;
import com.gildedgames.aether.common.world.gen.feature.*;
import com.gildedgames.aether.common.world.gen.placement.ElevationAdjustment;
import com.gildedgames.aether.common.world.gen.placement.ElevationFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.*;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

public class AetherFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Aether.MODID);
    //
//    public static final RegistryObject<Feature<RandomPatchConfiguration>> GRASS_PATCH = FEATURES.register("grass_patch", () -> new AetherGrassFeature(RandomPatchConfiguration.CODEC));
//
    public static final RegistryObject<Feature<SimpleDiskConfiguration>> SIMPLE_DISK = FEATURES.register("simple_disk", () -> new SimpleDiskFeature(SimpleDiskConfiguration.CODEC));
//
//    public static final RegistryObject<Feature<BlockStateConfiguration>> LAKE = FEATURES.register("lake", () -> new AetherLakeFeature(BlockStateConfiguration.CODEC));

    public static final RegistryObject<Feature<AercloudConfiguration>> AERCLOUD = FEATURES.register("aercloud", () -> new AercloudFeature(AercloudConfiguration.CODEC));
    //
//    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CRYSTAL_TREE = FEATURES.register("crystal_tree", () -> new CrystalTreeFeature(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HOLIDAY_TREE = FEATURES.register("holiday_tree", () -> new HolidayTreeFeature(NoneFeatureConfiguration.CODEC));
//
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HOLYSTONE_SPHERE = FEATURES.register("holystone_sphere", () -> new HolystoneSphereFeature(NoneFeatureConfiguration.CODEC)); // This is for Gold Dungeons
    
    public static class Features {
        public static final RuleTest HOLYSTONE = new TagMatchTest(AetherTags.Blocks.HOLYSTONE);
        
        public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> COLD_AERCLOUD_CONFIGURED_FEATURE = FeatureUtils.register("cold_aercloud", AetherFeatures.AERCLOUD.get(),
                createAercloudConfig(16, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState()));
        public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> BLUE_AERCLOUD_CONFIGURED_FEATURE = FeatureUtils.register("blue_aercloud", AetherFeatures.AERCLOUD.get(),
                createAercloudConfig(8, AetherBlocks.BLUE_AERCLOUD.get().defaultBlockState()));
        public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> GOLDEN_AERCLOUD_CONFIGURED_FEATURE = FeatureUtils.register("golden_aercloud", AetherFeatures.AERCLOUD.get(),
                createAercloudConfig(4, AetherBlocks.GOLDEN_AERCLOUD.get().defaultBlockState()));
        public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> PINK_AERCLOUD_CONFIGURED_FEATURE = FeatureUtils.register("pink_aercloud", AetherFeatures.AERCLOUD.get(),
                createAercloudConfig(1, AetherBlocks.PINK_AERCLOUD.get().defaultBlockState()));

        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SKYROOT_TREE_CONFIGURED_FEATURE = FeatureUtils.register("skyroot_tree", Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(getDoubleDrops(AetherBlocks.SKYROOT_LOG)),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(getDoubleDrops(AetherBlocks.SKYROOT_LEAVES)),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> GOLDEN_OAK_TREE_CONFIGURED_FEATURE = FeatureUtils.register("golden_oak_tree", Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(getDoubleDrops(AetherBlocks.GOLDEN_OAK_LOG)),
                        new FancyTrunkPlacer(3, 11, 0),
                        BlockStateProvider.simple(getDoubleDrops(AetherBlocks.GOLDEN_OAK_LEAVES.get())),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());

        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PATCH_CONFIGURED_FEATURE = FeatureUtils.register("flower_patch", Feature.FLOWER,
                grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(AetherBlocks.PURPLE_FLOWER.get().defaultBlockState(), 2)
                        .add(AetherBlocks.WHITE_FLOWER.get().defaultBlockState(), 2)
                        .add(getDoubleDrops(AetherBlocks.BERRY_BUSH), 1)), 64));

        public static final Holder<ConfiguredFeature<SimpleDiskConfiguration, ?>> QUICKSOIL_SHELF_CONFIGURED_FEATURE = FeatureUtils.register("quicksoil_shelf", AetherFeatures.SIMPLE_DISK.get(),
                new SimpleDiskConfiguration(
                        UniformFloat.of(Mth.sqrt(12), 5), // sqrt(12) is old static value
                        BlockStateProvider.simple(getDoubleDrops(AetherBlocks.QUICKSOIL)),
                        3));

        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_AETHER_DIRT_CONFIGURED_FEATURE = FeatureUtils.register("aether_dirt_ore", Feature.ORE,
                new OreConfiguration(HOLYSTONE, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), 33));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_ICESTONE_CONFIGURED_FEATURE = FeatureUtils.register("icestone_ore", Feature.ORE,
                new OreConfiguration(HOLYSTONE, AetherBlocks.ICESTONE.get().defaultBlockState(), 16));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_AMBROSIUM_CONFIGURED_FEATURE = FeatureUtils.register("ambrosium_ore", Feature.ORE,
                new OreConfiguration(HOLYSTONE, getDoubleDrops(AetherBlocks.AMBROSIUM_ORE), 16));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_ZANITE_CONFIGURED_FEATURE = FeatureUtils.register("zanite_ore", Feature.ORE,
                new OreConfiguration(HOLYSTONE, AetherBlocks.ZANITE_ORE.get().defaultBlockState(), 8));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_GRAVITITE_COMMON_CONFIGURED_FEATURE = FeatureUtils.register("gravitite_ore_common", Feature.ORE,
                new OreConfiguration(HOLYSTONE, AetherBlocks.GRAVITITE_ORE.get().defaultBlockState(), 6, 0.9F));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_GRAVITITE_DENSE_CONFIGURED_FEATURE = FeatureUtils.register("gravitite_ore_dense", Feature.ORE,
                new OreConfiguration(HOLYSTONE, AetherBlocks.GRAVITITE_ORE.get().defaultBlockState(), 3, 0.5F));
    }

    public static class Placements {
        public static final Holder<PlacedFeature> COLD_AERCLOUD_PLACED_FEATURE = PlacementUtils.register("cold_aercloud", Features.COLD_AERCLOUD_CONFIGURED_FEATURE, createAercloudPlacements(128, 5));
        public static final Holder<PlacedFeature> BLUE_AERCLOUD_PLACED_FEATURE = PlacementUtils.register("blue_aercloud", Features.BLUE_AERCLOUD_CONFIGURED_FEATURE, createAercloudPlacements(96, 5));
        public static final Holder<PlacedFeature> GOLDEN_AERCLOUD_PLACED_FEATURE = PlacementUtils.register("golden_aercloud", Features.GOLDEN_AERCLOUD_CONFIGURED_FEATURE, createAercloudPlacements(160, 5));
        public static final Holder<PlacedFeature> PINK_AERCLOUD_PLACED_FEATURE = PlacementUtils.register("pink_aercloud", Features.PINK_AERCLOUD_CONFIGURED_FEATURE, createAercloudPlacements(160, 7));

        public static final Holder<PlacedFeature> SKYROOT_TREE_PLACED_FEATURE = PlacementUtils.register("skyroot_tree", Features.SKYROOT_TREE_CONFIGURED_FEATURE,
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1), AetherBlocks.SKYROOT_SAPLING.get()));
        public static final Holder<PlacedFeature> GOLDEN_OAK_TREE_PLACED_FEATURE = PlacementUtils.register("golden_oak_tree", Features.GOLDEN_OAK_TREE_CONFIGURED_FEATURE,
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1), AetherBlocks.GOLDEN_OAK_SAPLING.get()));

        public static final Holder<PlacedFeature> FLOWER_PATCH_PLACED_FEATURE = PlacementUtils.register("flower_patch", Features.FLOWER_PATCH_CONFIGURED_FEATURE,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

        public static final Holder<PlacedFeature> QUICKSOIL_SHELF_PLACED_FEATURE = PlacementUtils.register("quicksoil_shelf", Features.QUICKSOIL_SHELF_CONFIGURED_FEATURE,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, new ElevationAdjustment(UniformInt.of(-4, -2)), new ElevationFilter(47, 70), BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(BlockPredicate.matchesBlock(AetherBlocks.AETHER_DIRT.get(), BlockPos.ZERO), BlockPredicate.matchesTag(AetherTags.Blocks.HOLYSTONE))));
        // FIXME once Terrain can go above 63 again, change 47 -> 63

        public static final Holder<PlacedFeature> ORE_AETHER_DIRT_PLACED_FEATURE = PlacementUtils.register("aether_dirt_ore", Features.ORE_AETHER_DIRT_CONFIGURED_FEATURE,
                commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_ICESTONE_PLACED_FEATURE = PlacementUtils.register("icestone_ore", Features.ORE_ICESTONE_CONFIGURED_FEATURE,
                commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_AMBROSIUM_PLACED_FEATURE = PlacementUtils.register("ambrosium_ore", Features.ORE_AMBROSIUM_CONFIGURED_FEATURE,
                commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_ZANITE_PLACED_FEATURE = PlacementUtils.register("zanite_ore", Features.ORE_ZANITE_CONFIGURED_FEATURE,
                commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_GRAVITITE_COMMON_PLACED_FEATURE = PlacementUtils.register("gravitite_ore_common", Features.ORE_GRAVITITE_COMMON_CONFIGURED_FEATURE,
                commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_GRAVITITE_DENSE_PLACED_FEATURE = PlacementUtils.register("gravitite_ore_dense", Features.ORE_GRAVITITE_DENSE_CONFIGURED_FEATURE,
                commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    }

    public static AercloudConfiguration createAercloudConfig(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds,
                BlockStateProvider.simple(blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)));
    }

    // TODO investigate changing this to triangle
    public static List<PlacementModifier> createAercloudPlacements(int height, int chance) {
        return List.of(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(height)),
                InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(chance));
    }

    public static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
    }

    public static BlockState getDoubleDrops(Supplier<? extends Block> block) {
        return getDoubleDrops(block.get());
    }

    public static BlockState getDoubleDrops(Block block) {
        return getDoubleDrops(block.defaultBlockState());
    }

    public static BlockState getDoubleDrops(BlockState blockState) {
        return blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
}  

//TODO: Old code.

//    public static final class Configs
//    {
////        public static final TreeConfiguration SKYROOT_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
////                new SimpleStateProvider(States.SKYROOT_LOG),
////                new SimpleStateProvider(States.SKYROOT_LEAVES),
////                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
////                new StraightTrunkPlacer(4, 2, 0),
////                new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build();
////        public static final TreeConfiguration GOLDEN_OAK_TREE_CONFIG = (new TreeConfiguration.TreeConfigurationBuilder(
////                new SimpleStateProvider(States.GOLDEN_OAK_LOG),
////                new SimpleStateProvider(States.GOLDEN_OAK_LEAVES),
////                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
////                new FancyTrunkPlacer(3, 11, 0),
////                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Types.MOTION_BLOCKING).build();
////
////        public static final RandomPatchConfiguration FLOWER_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder((new WeightedStateProvider())
////                        .add(States.PURPLE_FLOWER, 1)
////                        .add(States.WHITE_FLOWER, 1)
////                        .add(States.BERRY_BUSH, 1), SimpleBlockPlacer.INSTANCE))
////                .tries(64).whitelist(ImmutableSet.of(AetherBlocks.AETHER_GRASS_BLOCK.get())).build();
//    }
//
//    public static void registerConfiguredFeatures() {
////        register("grass_patch", AetherFeatures.GRASS_PATCH.get().configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).decorated(FeatureDecorator.COUNT_NOISE.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 5, 10))));
////        register("tall_grass_patch", AetherFeatures.GRASS_PATCH.get().configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP).squared().decorated(FeatureDecorator.COUNT_NOISE.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 0, 7))));
////
////        register("quicksoil", AetherFeatures.QUICKSOIL.get().configured(FeatureConfiguration.NONE).decorated(FeatureDecorator.RANGE_VERY_BIASED.configured(new RangeDecoratorConfiguration(63, 0, 70))).squared().count(10));
////
////        register("water_lake", AetherFeatures.LAKE.get().configured(new BlockStateConfiguration(Blocks.WATER.defaultBlockState())).decorated(FeatureDecorator.WATER_LAKE.configured(new ChanceDecoratorConfiguration(4))));
////        register("spring_water", Feature.SPRING.configured(new SpringConfiguration(Fluids.WATER.defaultFluidState(), true, 4, 1, ImmutableSet.of(AetherBlocks.HOLYSTONE.get(), AetherBlocks.AETHER_DIRT.get()))).decorated(FeatureDecorator.RANGE_BIASED.configured(new RangeDecoratorConfiguration(8, 8, 256))).squared().count(50));
////
////        register("cold_aercloud", AetherFeatures.COLD_AERCLOUD.get().configured(FeatureConfiguration.NONE).range(128).squared().chance(5));
////        register("blue_aercloud", AetherFeatures.BLUE_AERCLOUD.get().configured(FeatureConfiguration.NONE).range(96).squared().chance(5));
////        register("gold_aercloud", AetherFeatures.GOLD_AERCLOUD.get().configured(FeatureConfiguration.NONE).range(160).squared().chance(5));
////        register("pink_aercloud", AetherFeatures.PINK_AERCLOUD.get().configured(FeatureConfiguration.NONE).range(160).squared().chance(7));
////
////
////        register("tree_skyroot", Feature.TREE.configured(Configs.SKYROOT_TREE_CONFIG).decorated(Features.Decorators.HEIGHTMAP_SQUARE));
////        register("tree_golden_oak", Feature.TREE.configured(Configs.GOLDEN_OAK_TREE_CONFIG).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.03F, 1))));
////        register("crystal_tree", AetherFeatures.CRYSTAL_TREE.get().configured(FeatureConfiguration.NONE).chance(30));
////        register("holiday_tree", AetherFeatures.HOLIDAY_TREE.get().configured(FeatureConfiguration.NONE).chance(60));
//// see: OrePlacements
////        register("ore_aether_dirt", Feature.ORE.configured(new OreConfiguration(Tests.HOLYSTONE, States.AETHER_DIRT, 33)).range(256).squared().count(10));
////        register("ore_icestone", Feature.ORE.configured(new OreConfiguration(Tests.HOLYSTONE, States.ICESTONE, 16)).range(256).squared().count(10));
////        register("ore_ambrosium", Feature.ORE.configured(new OreConfiguration(Tests.HOLYSTONE, States.AMBROSIUM_ORE, 16)).range(256).squared().count(10));
////        register("ore_zanite", Feature.ORE.configured(new OreConfiguration(Tests.HOLYSTONE, States.ZANITE_ORE, 8)).range(256).squared().count(10));
////        register("ore_gravitite", Feature.ORE.configured(new OreConfiguration(Tests.HOLYSTONE, States.GRAVITITE_ORE, 6)).range(256).squared().count(10));
////
////        register("aether_skylands_flowers", Feature.FLOWER.configured(Configs.FLOWER_PATCH_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(2));
//    }
//
//    private static <FC extends FeatureConfiguration> void register(String name, ConfiguredFeature<FC, ?> feature) {
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name), feature);
//    }
//
//    public static final class Configured
//    {
////        public static final ConfiguredFeature<?, ?> GRASS_PATCH = register("grass_patch", AetherFeatures.GRASS_PATCH.get().configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
////        public static final ConfiguredFeature<?, ?> TALL_GRASS_PATCH = register("tall_grass_patch", AetherFeatures.GRASS_PATCH.get().configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP).squared().decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 0, 7))));
////
////        public static final ConfiguredFeature<?, ?> QUICKSOIL = register("quicksoil", AetherFeatures.QUICKSOIL.get().configured(IFeatureConfig.NONE).decorated(Placement.RANGE_VERY_BIASED.configured(new TopSolidRangeConfig(63, 0, 70))).squared().count(10));
////
////        public static final ConfiguredFeature<?, ?> LAKE = register("water_lake", AetherFeatures.LAKE.get().configured(new BlockStateFeatureConfig(Blocks.WATER.defaultBlockState())).decorated(Placement.WATER_LAKE.configured(new ChanceConfig(4))));
////        public static final ConfiguredFeature<?, ?> SPRING = register("spring_water", Feature.SPRING.configured(new LiquidsConfig(Fluids.WATER.defaultFluidState(), true, 4, 1, ImmutableSet.of(AetherBlocks.HOLYSTONE.get(), AetherBlocks.AETHER_DIRT.get()))).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(8, 8, 256))).squared().count(50));
////
////        public static final ConfiguredFeature<?, ?> COLD_AERCLOUD = register("cold_aercloud", AetherFeatures.COLD_AERCLOUD.get().configured(IFeatureConfig.NONE).range(128).squared().chance(5));
////        public static final ConfiguredFeature<?, ?> BLUE_AERCLOUD = register("blue_aercloud", AetherFeatures.BLUE_AERCLOUD.get().configured(IFeatureConfig.NONE).range(96).squared().chance(5));
////        public static final ConfiguredFeature<?, ?> GOLD_AERCLOUD = register("gold_aercloud", AetherFeatures.GOLD_AERCLOUD.get().configured(IFeatureConfig.NONE).range(160).squared().chance(5));
////        public static final ConfiguredFeature<?, ?> PINK_AERCLOUD = register("pink_aercloud", AetherFeatures.PINK_AERCLOUD.get().configured(IFeatureConfig.NONE).range(160).squared().chance(7));
////
////
////        public static final ConfiguredFeature<?, ?> CRYSTAL_TREE = register("crystal_tree", AetherFeatures.CRYSTAL_TREE.get().configured(IFeatureConfig.NONE).chance(30));
////        public static final ConfiguredFeature<?, ?> SKYROOT_TREE = register("tree_skyroot", Feature.TREE.configured(Configs.SKYROOT_TREE_CONFIG).decorated(Features.Placements.HEIGHTMAP_SQUARE));
////        public static final ConfiguredFeature<?, ?> GOLDEN_OAK_TREE = register("tree_golden_oak", Feature.TREE.configured(Configs.GOLDEN_OAK_TREE_CONFIG).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.03F, 1))));
////
////        public static final ConfiguredFeature<?, ?> AETHER_DIRT_CLUSTER = register("ore_aether_dirt", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.AETHER_DIRT, 33)).range(256).squared().count(10));
////        public static final ConfiguredFeature<?, ?> ICESTONE_CLUSTER = register("ore_icestone", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.ICESTONE, 16)).range(256).squared().count(10));
////        public static final ConfiguredFeature<?, ?> AMBROSIUM_ORE_CLUSTER = register("ore_ambrosium", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.AMBROSIUM_ORE, 16)).range(256).squared().count(10));
////        public static final ConfiguredFeature<?, ?> ZANITE_ORE_CLUSTER = register("ore_zanite", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.ZANITE_ORE, 8)).range(256).squared().count(10));
////        public static final ConfiguredFeature<?, ?> GRAVITITE_ORE_CLUSTER = register("ore_gravitite", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.GRAVITITE_ORE, 6)).range(256).squared().count(10));
////
////        public static final ConfiguredFeature<?, ?> FLOWER_PATCH = register("aether_skylands_flowers", Feature.FLOWER.configured(Configs.FLOWER_PATCH_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
////
//        private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> feature) {
//            return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name), feature);
//        }
//    }
//}