package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.world.builders.AetherFeatureBuilders;
import com.gildedgames.aether.common.world.foliageplacer.CrystalFoliagePlacer;
import com.gildedgames.aether.common.world.foliageplacer.HolidayFoliagePlacer;
import com.gildedgames.aether.common.world.gen.configuration.SimpleDiskConfiguration;
import com.gildedgames.aether.common.world.treedecorator.HolidayTreeDecorator;
import com.gildedgames.aether.core.data.AetherDataGenerators;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.Fluids;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

public class AetherConfiguredFeatures {
    public static final Map<ResourceLocation, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new HashMap<>();

    public static final Holder<ConfiguredFeature<?, ?>> COLD_AERCLOUD_CONFIGURATION = register("cold_aercloud", AetherFeatures.AERCLOUD.get(),
            AetherFeatureBuilders.createAercloudConfig(16, AetherFeatureStates.COLD_AERCLOUD));
    public static final Holder<ConfiguredFeature<?, ?>> BLUE_AERCLOUD_CONFIGURATION = register("blue_aercloud", AetherFeatures.AERCLOUD.get(),
            AetherFeatureBuilders.createAercloudConfig(8, AetherFeatureStates.BLUE_AERCLOUD));
    public static final Holder<ConfiguredFeature<?, ?>> GOLDEN_AERCLOUD_CONFIGURATION = register("golden_aercloud", AetherFeatures.AERCLOUD.get(),
            AetherFeatureBuilders.createAercloudConfig(4, AetherFeatureStates.GOLDEN_AERCLOUD));
    public static final Holder<ConfiguredFeature<?, ?>> PINK_AERCLOUD_CONFIGURATION = register("pink_aercloud", AetherFeatures.AERCLOUD.get(),
            AetherFeatureBuilders.createAercloudConfig(1, AetherFeatureStates.PINK_AERCLOUD));

    public static final Holder<ConfiguredFeature<?, ?>> CRYSTAL_ISLAND_CONFIGURATION = register("crystal_island", AetherFeatures.CRYSTAL_ISLAND.get(), NoneFeatureConfiguration.INSTANCE);

    public static final Holder<ConfiguredFeature<?, ?>> SKYROOT_TREE_CONFIGURATION = register("skyroot_tree", Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)
            ).ignoreVines().build());

    public static final Holder<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE_CONFIGURATION = register("golden_oak_tree", Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LOG),
                    new FancyTrunkPlacer(9, 5, 0),
                    BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
            ).ignoreVines().build());

    public static final Holder<ConfiguredFeature<?, ?>> CRYSTAL_TREE_CONFIGURATION = register("crystal_tree", Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                    new StraightTrunkPlacer(7, 0, 0),
                    new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.CRYSTAL_LEAVES, 4).add(AetherFeatureStates.CRYSTAL_FRUIT_LEAVES, 1).build()),
                    new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                    new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

    public static final Holder<ConfiguredFeature<?, ?>> HOLIDAY_TREE_CONFIGURATION = register("holiday_tree", Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                    new StraightTrunkPlacer(9, 0, 0),
                    new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.HOLIDAY_LEAVES, 4).add(AetherFeatureStates.DECORATED_HOLIDAY_LEAVES, 1).build()),
                    new HolidayFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(8)),
                    new TwoLayersFeatureSize(1, 0, 1)).ignoreVines()
                    .decorators(ImmutableList.of(new HolidayTreeDecorator(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.SNOW, 10).add(AetherFeatureStates.PRESENT, 1).build()))))
                    .build());

    public static final Holder<ConfiguredFeature<?, ?>> FLOWER_PATCH_CONFIGURATION = register("flower_patch", Feature.FLOWER,
            AetherFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(AetherFeatureStates.PURPLE_FLOWER, 2)
                    .add(AetherFeatureStates.WHITE_FLOWER, 2)
                    .add(AetherFeatureStates.BERRY_BUSH, 1)), 64
            ));

    public static final Holder<ConfiguredFeature<?, ?>> GRASS_PATCH_CONFIGURATION = register("grass_patch", Feature.RANDOM_PATCH,
            AetherFeatureBuilders.grassPatch(BlockStateProvider.simple(Blocks.GRASS), 32));

    public static final Holder<ConfiguredFeature<?, ?>> TALL_GRASS_PATCH_CONFIGURATION = register("tall_grass_patch", Feature.RANDOM_PATCH,
            AetherFeatureBuilders.tallGrassPatch(BlockStateProvider.simple(Blocks.TALL_GRASS)));

    public static final Holder<ConfiguredFeature<?, ?>> QUICKSOIL_SHELF_CONFIGURATION = register("quicksoil_shelf", AetherFeatures.SIMPLE_DISK.get(),
            new SimpleDiskConfiguration(
                    UniformFloat.of(Mth.sqrt(12), 5), // sqrt(12) is old static value
                    BlockStateProvider.simple(AetherFeatureStates.QUICKSOIL),
                    3
            ));

    public static final Holder<ConfiguredFeature<?, ?>> WATER_LAKE_CONFIGURATION = register("water_lake", AetherFeatures.LAKE.get(),
            AetherFeatureBuilders.lake(BlockStateProvider.simple(Blocks.WATER), BlockStateProvider.simple(AetherBlocks.AETHER_GRASS_BLOCK.get())));

    public static final Holder<ConfiguredFeature<?, ?>> WATER_SPRING_CONFIGURATION = register("water_spring", Feature.SPRING,
            AetherFeatureBuilders.spring(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, AetherBlocks.HOLYSTONE.get(), AetherBlocks.AETHER_DIRT.get())));

    public static final Holder<ConfiguredFeature<?, ?>> ORE_AETHER_DIRT_CONFIGURATION = register("aether_dirt_ore", Feature.ORE,
            new OreConfiguration(AetherFeatureTests.HOLYSTONE, AetherFeatureStates.AETHER_DIRT, 33));
    public static final Holder<ConfiguredFeature<?, ?>> ORE_ICESTONE_CONFIGURATION = register("icestone_ore", Feature.ORE,
            new OreConfiguration(AetherFeatureTests.HOLYSTONE, AetherFeatureStates.ICESTONE, 16));
    public static final Holder<ConfiguredFeature<?, ?>> ORE_AMBROSIUM_CONFIGURATION = register("ambrosium_ore", Feature.ORE,
            new OreConfiguration(AetherFeatureTests.HOLYSTONE, AetherFeatureStates.AMBROSIUM_ORE, 16));
    public static final Holder<ConfiguredFeature<?, ?>> ORE_ZANITE_CONFIGURATION = register("zanite_ore", Feature.ORE,
            new OreConfiguration(AetherFeatureTests.HOLYSTONE, AetherFeatureStates.ZANITE_ORE, 8));
    public static final Holder<ConfiguredFeature<?, ?>> ORE_GRAVITITE_COMMON_CONFIGURATION = register("gravitite_ore_common", Feature.ORE,
            new OreConfiguration(AetherFeatureTests.HOLYSTONE, AetherFeatureStates.GRAVITITE_ORE, 6, 0.9F));
    public static final Holder<ConfiguredFeature<?, ?>> ORE_GRAVITITE_DENSE_CONFIGURATION = register("gravitite_ore_dense", Feature.ORE,
            new OreConfiguration(AetherFeatureTests.HOLYSTONE, AetherFeatureStates.GRAVITITE_ORE, 3, 0.5F));

    public static final Holder<ConfiguredFeature<?, ?>> TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION = register("trees_skyroot_and_golden_oak", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                    PlacementUtils.inlinePlaced(GOLDEN_OAK_TREE_CONFIGURATION, PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.01F)),
                    PlacementUtils.inlinePlaced(SKYROOT_TREE_CONFIGURATION, PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()))));

    public static final Holder<ConfiguredFeature<?, ?>> TREES_GOLDEN_OAK_AND_SKYROOT_CONFIGURATION = register("trees_golden_oak_and_skyroot", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                    PlacementUtils.inlinePlaced(SKYROOT_TREE_CONFIGURATION, PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())), 0.1F)),
                    PlacementUtils.inlinePlaced(GOLDEN_OAK_TREE_CONFIGURATION, PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(String name, F feature, FC configuration) {
        ResourceKey<Registry<ConfiguredFeature<?, ?>>> registry = Registry.CONFIGURED_FEATURE_REGISTRY;
        ResourceLocation location = new ResourceLocation(Aether.MODID, name);
        CONFIGURED_FEATURES.put(location, new ConfiguredFeature<>(feature, configuration));
        return AetherDataGenerators.DATA_REGISTRY.registryOrThrow(registry).getOrCreateHolderOrThrow(ResourceKey.create(registry, location));
    }

    public static Holder<ConfiguredFeature<?, ?>> copy(Holder<ConfiguredFeature<?, ?>> oldHolder, Registry<ConfiguredFeature<?, ?>> registry) {
        ResourceKey<ConfiguredFeature<?, ?>> key = oldHolder.unwrapKey().orElseThrow();
        ConfiguredFeature<?, ?> configuredFeature = CONFIGURED_FEATURES.get(key.location());
        Holder.Reference<ConfiguredFeature<?, ?>> configuredFeatureHolder = (Holder.Reference<ConfiguredFeature<?, ?>>) registry.getOrCreateHolderOrThrow(key);
        configuredFeatureHolder.bind(key, configuredFeature);
        return configuredFeatureHolder;
    }
}
