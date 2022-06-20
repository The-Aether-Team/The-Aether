package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.world.builders.AetherFeatureBuilders;
import com.gildedgames.aether.common.world.foliageplacer.CrystalFoliagePlacer;
import com.gildedgames.aether.common.world.foliageplacer.HolidayFoliagePlacer;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import com.gildedgames.aether.common.world.gen.configuration.AetherLakeConfiguration;
import com.gildedgames.aether.common.world.gen.configuration.SimpleDiskConfiguration;
import com.gildedgames.aether.common.world.gen.feature.AercloudFeature;
import com.gildedgames.aether.common.world.gen.feature.AetherLakeFeature;
import com.gildedgames.aether.common.world.gen.feature.CrystalIslandFeature;
import com.gildedgames.aether.common.world.gen.feature.SimpleDiskFeature;
import com.gildedgames.aether.common.world.gen.placement.*;
import com.gildedgames.aether.common.world.treedecorator.HolidayTreeDecorator;
import com.gildedgames.aether.core.AetherConfig;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.OptionalInt;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Aether.MODID);

    public static RegistryObject<Feature<SimpleDiskConfiguration>> SIMPLE_DISK = FEATURES.register("simple_disk", () -> new SimpleDiskFeature(SimpleDiskConfiguration.CODEC));
    public static RegistryObject<Feature<AercloudConfiguration>> AERCLOUD = FEATURES.register("aercloud", () -> new AercloudFeature(AercloudConfiguration.CODEC));
    public static RegistryObject<Feature<NoneFeatureConfiguration>> CRYSTAL_ISLAND = FEATURES.register("crystal_island", () -> new CrystalIslandFeature(NoneFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<AetherLakeConfiguration>> LAKE = FEATURES.register("lake", () -> new AetherLakeFeature(AetherLakeConfiguration.CODEC));

    //public static Feature<NoneFeatureConfiguration> HOLYSTONE_SPHERE = new HolystoneSphereFeature(NoneFeatureConfiguration.CODEC); // This is for Gold Dungeons

    public static class ConfiguredFeatures {
        public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Aether.MODID);

        public static final RegistryObject<ConfiguredFeature<?, ?>> COLD_AERCLOUD_CONFIGURATION = CONFIGURED_FEATURES.register("cold_aercloud", () -> new ConfiguredFeature<>(AERCLOUD.get(), AetherFeatureBuilders.createAercloudConfig(16, States.COLD_AERCLOUD)));
        public static final RegistryObject<ConfiguredFeature<?, ?>> BLUE_AERCLOUD_CONFIGURATION = CONFIGURED_FEATURES.register("blue_aercloud", () -> new ConfiguredFeature<>(AERCLOUD.get(), AetherFeatureBuilders.createAercloudConfig(8, States.BLUE_AERCLOUD)));
        public static final RegistryObject<ConfiguredFeature<?, ?>> GOLDEN_AERCLOUD_CONFIGURATION = CONFIGURED_FEATURES.register("golden_aercloud", () -> new ConfiguredFeature<>(AERCLOUD.get(), AetherFeatureBuilders.createAercloudConfig(4, States.GOLDEN_AERCLOUD)));
        public static final RegistryObject<ConfiguredFeature<?, ?>> PINK_AERCLOUD_CONFIGURATION = CONFIGURED_FEATURES.register("pink_aercloud", () -> new ConfiguredFeature<>(AERCLOUD.get(), AetherFeatureBuilders.createAercloudConfig(1, States.PINK_AERCLOUD)));

        public static final RegistryObject<ConfiguredFeature<?, ?>> CRYSTAL_ISLAND_CONFIGURATION = CONFIGURED_FEATURES.register("crystal_island", () -> new ConfiguredFeature<>(CRYSTAL_ISLAND.get(), NoneFeatureConfiguration.INSTANCE));

        public static final RegistryObject<ConfiguredFeature<?, ?>> SKYROOT_TREE_CONFIGURATION = CONFIGURED_FEATURES.register("skyroot_tree", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(States.SKYROOT_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(States.SKYROOT_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().build()));

        public static final RegistryObject<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE_CONFIGURATION = CONFIGURED_FEATURES.register("golden_oak_tree", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(States.GOLDEN_OAK_LOG),
                new FancyTrunkPlacer(9, 5, 0),
                BlockStateProvider.simple(States.GOLDEN_OAK_LEAVES),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
        ).ignoreVines().build()));

        public static final RegistryObject<ConfiguredFeature<?, ?>> CRYSTAL_TREE_CONFIGURATION = CONFIGURED_FEATURES.register("crystal_tree", () -> new ConfiguredFeature<>(Feature.TREE,  new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(States.SKYROOT_LOG),
                new StraightTrunkPlacer(7, 0, 0),
                new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(States.CRYSTAL_LEAVES, 4).add(States.CRYSTAL_FRUIT_LEAVES, 1).build()),
                new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().build()));

        public static final RegistryObject<ConfiguredFeature<?, ?>> HOLIDAY_TREE_CONFIGURATION = CONFIGURED_FEATURES.register("holiday_tree", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(States.SKYROOT_LOG),
                new StraightTrunkPlacer(9, 0, 0),
                new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(States.HOLIDAY_LEAVES, 4).add(States.DECORATED_HOLIDAY_LEAVES, 1).build()),
                new HolidayFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(8)),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines()
                .decorators(ImmutableList.of(new HolidayTreeDecorator(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(States.SNOW, 10).add(States.PRESENT, 1).build()))))
                .build()));

        public static final RegistryObject<ConfiguredFeature<?, ?>> FLOWER_PATCH_CONFIGURATION = CONFIGURED_FEATURES.register("flower_patch", () -> new ConfiguredFeature<>(Feature.FLOWER, AetherFeatureBuilders.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(States.PURPLE_FLOWER, 2)
                .add(States.WHITE_FLOWER, 2)
                .add(States.BERRY_BUSH, 1)), 64)));

        public static final RegistryObject<ConfiguredFeature<?, ?>> GRASS_PATCH_CONFIGURATION = CONFIGURED_FEATURES.register("grass_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, AetherFeatureBuilders.grassPatch(BlockStateProvider.simple(Blocks.GRASS), 32)));

        public static final RegistryObject<ConfiguredFeature<?, ?>> TALL_GRASS_PATCH_CONFIGURATION = CONFIGURED_FEATURES.register("tall_grass_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, AetherFeatureBuilders.tallGrassPatch(BlockStateProvider.simple(Blocks.TALL_GRASS))));

        public static final RegistryObject<ConfiguredFeature<?, ?>> QUICKSOIL_SHELF_CONFIGURATION = CONFIGURED_FEATURES.register("quicksoil_shelf", () -> new ConfiguredFeature<>(SIMPLE_DISK.get(), new SimpleDiskConfiguration(
                UniformFloat.of(Mth.sqrt(12), 5), // sqrt(12) is old static value
                BlockStateProvider.simple(States.QUICKSOIL),
                3
        )));

        public static final RegistryObject<ConfiguredFeature<?, ?>> WATER_LAKE_CONFIGURATION = CONFIGURED_FEATURES.register("water_lake", () -> new ConfiguredFeature<>(LAKE.get(), AetherFeatureBuilders.lake(BlockStateProvider.simple(Blocks.WATER), BlockStateProvider.simple(AetherBlocks.AETHER_GRASS_BLOCK.get()))));

        public static final RegistryObject<ConfiguredFeature<?, ?>> WATER_SPRING_CONFIGURATION = CONFIGURED_FEATURES.register("water_spring", () -> new ConfiguredFeature<>(Feature.SPRING, AetherFeatureBuilders.spring(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, AetherBlocks.HOLYSTONE.get(), AetherBlocks.AETHER_DIRT.get()))));

        public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_AETHER_DIRT_CONFIGURATION = CONFIGURED_FEATURES.register("aether_dirt_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(Tests.HOLYSTONE, States.AETHER_DIRT, 33)));
        public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_ICESTONE_CONFIGURATION = CONFIGURED_FEATURES.register("icestone_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(Tests.HOLYSTONE, States.ICESTONE, 16)));
        public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_AMBROSIUM_CONFIGURATION = CONFIGURED_FEATURES.register("ambrosium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(Tests.HOLYSTONE, States.AMBROSIUM_ORE, 16)));
        public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_ZANITE_CONFIGURATION = CONFIGURED_FEATURES.register("zanite_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(Tests.HOLYSTONE, States.ZANITE_ORE, 8)));
        public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_GRAVITITE_COMMON_CONFIGURATION = CONFIGURED_FEATURES.register("gravitite_ore_common", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(Tests.HOLYSTONE, States.GRAVITITE_ORE, 6, 0.9F)));
        public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_GRAVITITE_DENSE_CONFIGURATION = CONFIGURED_FEATURES.register("gravitite_ore_dense", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(Tests.HOLYSTONE, States.GRAVITITE_ORE, 3, 0.5F)));

        public static final RegistryObject<ConfiguredFeature<?, ?>> TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION = CONFIGURED_FEATURES.register("trees_skyroot_and_golden_oak", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                List.of(new WeightedPlacedFeature(
                    PlacementUtils.inlinePlaced(Holder.direct(GOLDEN_OAK_TREE_CONFIGURATION.get()), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.01F)),
                    PlacementUtils.inlinePlaced(Holder.direct(SKYROOT_TREE_CONFIGURATION.get()), PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())))));

        public static final RegistryObject<ConfiguredFeature<?, ?>> TREES_GOLDEN_OAK_AND_SKYROOT_CONFIGURATION = CONFIGURED_FEATURES.register("trees_golden_oak_and_skyroot", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                List.of(new WeightedPlacedFeature(
                    PlacementUtils.inlinePlaced(Holder.direct(SKYROOT_TREE_CONFIGURATION.get()), PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())), 0.1F)),
                    PlacementUtils.inlinePlaced(Holder.direct(GOLDEN_OAK_TREE_CONFIGURATION.get()), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())))));
    }

    public static class PlacedFeatures { //TODO: STOP USING HOLDER.DIRECT() FOR ALL OF THESE ONCE THEYRE MOVED OFF OF DEFERRED.
        public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Aether.MODID);

        public static final RegistryObject<PlacedFeature> COLD_AERCLOUD_PLACEMENT = PLACED_FEATURES.register("cold_aercloud", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.COLD_AERCLOUD_CONFIGURATION.get()), AetherFeatureBuilders.createAercloudPlacements(128, 5)));
        public static final RegistryObject<PlacedFeature> BLUE_AERCLOUD_PLACEMENT = PLACED_FEATURES.register("blue_aercloud", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION.get()), AetherFeatureBuilders.createAercloudPlacements(96, 5)));
        public static final RegistryObject<PlacedFeature> GOLDEN_AERCLOUD_PLACEMENT = PLACED_FEATURES.register("golden_aercloud", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.GOLDEN_AERCLOUD_CONFIGURATION.get()), AetherFeatureBuilders.createAercloudPlacements(160, 5)));
        public static final RegistryObject<PlacedFeature> PINK_AERCLOUD_PLACEMENT = PLACED_FEATURES.register("pink_aercloud", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.PINK_AERCLOUD_CONFIGURATION.get()), AetherFeatureBuilders.createPinkAercloudPlacements(160, 7)));

        public static final RegistryObject<PlacedFeature> SKYROOT_GROVE_TREES_PLACEMENT = PLACED_FEATURES.register("skyroot_grove_trees_placed_feature", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION.get()), AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))));
        public static final RegistryObject<PlacedFeature> SKYROOT_FOREST_TREES_PLACEMENT = PLACED_FEATURES.register("skyroot_forest_trees_placed_feature", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION.get()), AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(7, 0.1F, 1))));
        public static final RegistryObject<PlacedFeature> SKYROOT_THICKET_TREES_PLACEMEMT = PLACED_FEATURES.register("skyroot_thicket_trees_placed_feature", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION.get()), AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(15, 0.1F, 1))));
        public static final RegistryObject<PlacedFeature> GOLDEN_FOREST_TREES_PLACEMENT = PLACED_FEATURES.register("golden_forest_trees_placed_feature", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.TREES_GOLDEN_OAK_AND_SKYROOT_CONFIGURATION.get()), AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))));

        public static final RegistryObject<PlacedFeature> CRYSTAL_ISLAND_PLACEMENT = PLACED_FEATURES.register("crystal_island", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.CRYSTAL_ISLAND_CONFIGURATION.get()), List.of(
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(80), VerticalAnchor.absolute(120)),
                RarityFilter.onAverageOnceEvery(16)
        )));

        public static final RegistryObject<PlacedFeature> HOLIDAY_TREE_PLACEMENT = PLACED_FEATURES.register("holiday_tree", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.HOLIDAY_TREE_CONFIGURATION.get()), List.of(
                new HolidayFilter(),
                CountOnEveryLayerPlacement.of(1),
                RarityFilter.onAverageOnceEvery(48),
                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())
        )));

        public static final RegistryObject<PlacedFeature> FLOWER_PATCH_PLACEMENT = PLACED_FEATURES.register("flower_patch", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.FLOWER_PATCH_CONFIGURATION.get()), List.of(
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        )));

        public static final RegistryObject<PlacedFeature> GRASS_PATCH_PLACEMENT = PLACED_FEATURES.register("grass_patch", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.GRASS_PATCH_CONFIGURATION.get()), List.of(
                new ConfigFilter(AetherConfig.COMMON.generate_tall_grass),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        )));

        public static final RegistryObject<PlacedFeature> TALL_GRASS_PATCH_PLACEMENT = PLACED_FEATURES.register("tall_grass_patch", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION.get()), List.of(
                new ConfigFilter(AetherConfig.COMMON.generate_tall_grass),
                NoiseThresholdCountPlacement.of(-0.8D, 0, 7),
                RarityFilter.onAverageOnceEvery(32),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        )));

        public static final RegistryObject<PlacedFeature> QUICKSOIL_SHELF_PLACEMENT = PLACED_FEATURES.register("quicksoil_shelf", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.QUICKSOIL_SHELF_CONFIGURATION.get()), List.of(
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                new ElevationAdjustment(UniformInt.of(-4, -2)),
                new ElevationFilter(47, 70),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(AetherTags.Blocks.QUICKSOIL_CAN_GENERATE))
        )));
        // FIXME once Terrain can go above 63 again, change 47 -> 63

        public static final RegistryObject<PlacedFeature> WATER_LAKE_PLACEMENT = PLACED_FEATURES.register("water_lake", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.WATER_LAKE_CONFIGURATION.get()), List.of(
                RarityFilter.onAverageOnceEvery(40),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        )));

        public static final RegistryObject<PlacedFeature> WATER_SPRING_PLACEMENT = PLACED_FEATURES.register("water_spring", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.WATER_SPRING_CONFIGURATION.get()), List.of(
                CountPlacement.of(25),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(192)),
                BiomeFilter.biome()
        )));

        public static final RegistryObject<PlacedFeature> ORE_AETHER_DIRT_PLACEMENT = PLACED_FEATURES.register("aether_dirt_ore", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.ORE_AETHER_DIRT_CONFIGURATION.get()), AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0)))));
        public static final RegistryObject<PlacedFeature> ORE_ICESTONE_PLACEMENT = PLACED_FEATURES.register("icestone_ore", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.ORE_ICESTONE_CONFIGURATION.get()), AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0)))));
        public static final RegistryObject<PlacedFeature> ORE_AMBROSIUM_PLACEMENT = PLACED_FEATURES.register("ambrosium_ore", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION.get()), AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0)))));
        public static final RegistryObject<PlacedFeature> ORE_ZANITE_PLACEMENT = PLACED_FEATURES.register("zanite_ore", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.ORE_ZANITE_CONFIGURATION.get()), AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0)))));
        public static final RegistryObject<PlacedFeature> ORE_GRAVITITE_COMMON_PLACEMENT = PLACED_FEATURES.register("gravitite_ore_common", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.ORE_GRAVITITE_COMMON_CONFIGURATION.get()), AetherFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0)))));
        public static final RegistryObject<PlacedFeature> ORE_GRAVITITE_DENSE_PLACEMENT = PLACED_FEATURES.register("gravitite_ore_dense", () -> new PlacedFeature(Holder.direct(ConfiguredFeatures.ORE_GRAVITITE_DENSE_CONFIGURATION.get()), AetherFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0)))));
    }

    public static class States {
        public static final BlockState COLD_AERCLOUD = AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState BLUE_AERCLOUD = AetherBlocks.BLUE_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState GOLDEN_AERCLOUD = AetherBlocks.GOLDEN_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState PINK_AERCLOUD = AetherBlocks.PINK_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState SKYROOT_LOG = AetherBlocks.SKYROOT_LOG.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState SKYROOT_LEAVES = AetherBlocks.SKYROOT_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState GOLDEN_OAK_LOG = AetherBlocks.GOLDEN_OAK_LOG.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState GOLDEN_OAK_LEAVES = AetherBlocks.GOLDEN_OAK_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState CRYSTAL_LEAVES = AetherBlocks.CRYSTAL_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState CRYSTAL_FRUIT_LEAVES = AetherBlocks.CRYSTAL_FRUIT_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState HOLIDAY_LEAVES = AetherBlocks.HOLIDAY_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState DECORATED_HOLIDAY_LEAVES = AetherBlocks.DECORATED_HOLIDAY_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState SNOW = Blocks.SNOW.defaultBlockState();
        public static final BlockState PRESENT = AetherBlocks.PRESENT.get().defaultBlockState();
        public static final BlockState AIR = Blocks.AIR.defaultBlockState();

        public static final BlockState PURPLE_FLOWER = AetherBlocks.PURPLE_FLOWER.get().defaultBlockState();
        public static final BlockState WHITE_FLOWER = AetherBlocks.WHITE_FLOWER.get().defaultBlockState();
        public static final BlockState BERRY_BUSH = AetherBlocks.BERRY_BUSH.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState QUICKSOIL = AetherBlocks.QUICKSOIL.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState AETHER_GRASS_BLOCK = AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState AETHER_DIRT = AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState HOLYSTONE = AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState ICESTONE = AetherBlocks.ICESTONE.get().defaultBlockState();
        public static final BlockState AMBROSIUM_ORE = AetherBlocks.AMBROSIUM_ORE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState ZANITE_ORE = AetherBlocks.ZANITE_ORE.get().defaultBlockState();
        public static final BlockState GRAVITITE_ORE = AetherBlocks.GRAVITITE_ORE.get().defaultBlockState();
    }

    public static class Tests {
        public static final RuleTest HOLYSTONE = new TagMatchTest(AetherTags.Blocks.HOLYSTONE);
    }
}
