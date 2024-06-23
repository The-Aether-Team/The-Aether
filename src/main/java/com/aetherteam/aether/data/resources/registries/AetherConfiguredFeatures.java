package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureRules;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.builders.AetherConfiguredFeatureBuilders;
import com.aetherteam.aether.world.configuration.ShelfConfiguration;
import com.aetherteam.aether.world.feature.AetherFeatures;
import com.aetherteam.aether.world.foliageplacer.CrystalFoliagePlacer;
import com.aetherteam.aether.world.foliageplacer.GoldenOakFoliagePlacer;
import com.aetherteam.aether.world.foliageplacer.HolidayFoliagePlacer;
import com.aetherteam.aether.world.treedecorator.HolidayTreeDecorator;
import com.aetherteam.aether.world.trunkplacer.CrystalTreeTrunkPlacer;
import com.aetherteam.aether.world.trunkplacer.GoldenOakTrunkPlacer;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenConfiguredFeatureBuilders;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.OptionalInt;

public class AetherConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> COLD_AERCLOUD_CONFIGURATION = createKey("cold_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_AERCLOUD_CONFIGURATION = createKey("blue_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_AERCLOUD_CONFIGURATION = createKey("golden_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_ISLAND_CONFIGURATION = createKey("crystal_island");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TREE_CONFIGURATION = createKey("skyroot_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE_CONFIGURATION = createKey("golden_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_TREE_CONFIGURATION = createKey("crystal_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLIDAY_TREE_CONFIGURATION = createKey("holiday_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH_CONFIGURATION = createKey("grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_GRASS_PATCH_CONFIGURATION = createKey("tall_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_FLOWER_PATCH_CONFIGURATION = createKey("white_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_FLOWER_PATCH_CONFIGURATION = createKey("purple_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BERRY_BUSH_PATCH_CONFIGURATION = createKey("berry_bush_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUICKSOIL_SHELF_CONFIGURATION = createKey("quicksoil_shelf");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_LAKE_CONFIGURATION = createKey("water_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_SPRING_CONFIGURATION = createKey("water_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AETHER_DIRT_CONFIGURATION = createKey("aether_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ICESTONE_CONFIGURATION = createKey("icestone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AMBROSIUM_CONFIGURATION = createKey("ambrosium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ZANITE_CONFIGURATION = createKey("zanite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GRAVITITE_BURIED_CONFIGURATION = createKey("gravitite_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GRAVITITE_CONFIGURATION = createKey("gravitite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION = createKey("trees_skyroot_and_golden_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SINGLE_GOLD_DUNGEON_FLOWER_CONFIGURATION = createKey("single_gold_dungeon_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_DUNGEON_ISLAND_FOLIAGE_CONFIGURATION = createKey("gold_dungeon_island_foliage");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name));
    }

    /**
     * Warning for "deprecation" is suppressed because {@link Block#builtInRegistryHolder()} is fine to use.
     */
    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, COLD_AERCLOUD_CONFIGURATION, AetherFeatures.AERCLOUD.get(), AetherConfiguredFeatureBuilders.aercloud(16, AetherFeatureStates.COLD_AERCLOUD));
        register(context, BLUE_AERCLOUD_CONFIGURATION, AetherFeatures.AERCLOUD.get(), AetherConfiguredFeatureBuilders.aercloud(8, AetherFeatureStates.BLUE_AERCLOUD));
        register(context, GOLDEN_AERCLOUD_CONFIGURATION, AetherFeatures.AERCLOUD.get(), AetherConfiguredFeatureBuilders.aercloud(4, AetherFeatureStates.GOLDEN_AERCLOUD));
        register(context, CRYSTAL_ISLAND_CONFIGURATION, AetherFeatures.CRYSTAL_ISLAND.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, SKYROOT_TREE_CONFIGURATION, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());
        register(context, GOLDEN_OAK_TREE_CONFIGURATION, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LOG),
                        new GoldenOakTrunkPlacer(10, 0, 0),
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
                        new GoldenOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(7)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines().build());
        register(context, CRYSTAL_TREE_CONFIGURATION, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new CrystalTreeTrunkPlacer(7, 0, 0),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.CRYSTAL_LEAVES, 4).add(AetherFeatureStates.CRYSTAL_FRUIT_LEAVES, 1).build()),
                        new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        register(context, HOLIDAY_TREE_CONFIGURATION, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(9, 0, 0),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.HOLIDAY_LEAVES, 4).add(AetherFeatureStates.DECORATED_HOLIDAY_LEAVES, 1).build()),
                        new HolidayFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(8)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines()
                        .decorators(ImmutableList.of(new HolidayTreeDecorator(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.SNOW, 10).add(AetherFeatureStates.PRESENT, 1).build()))))
                        .build());
        register(context, GRASS_PATCH_CONFIGURATION, Feature.RANDOM_PATCH, NitrogenConfiguredFeatureBuilders.grassPatch(BlockStateProvider.simple(Blocks.GRASS), 32));
        register(context, TALL_GRASS_PATCH_CONFIGURATION, Feature.RANDOM_PATCH, NitrogenConfiguredFeatureBuilders.tallGrassPatch(BlockStateProvider.simple(Blocks.TALL_GRASS)));
        register(context, WHITE_FLOWER_PATCH_CONFIGURATION, Feature.FLOWER,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AetherFeatureStates.WHITE_FLOWER, 1)), 64));
        register(context, PURPLE_FLOWER_PATCH_CONFIGURATION, Feature.FLOWER,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AetherFeatureStates.PURPLE_FLOWER, 1)), 64));
        register(context, BERRY_BUSH_PATCH_CONFIGURATION, Feature.RANDOM_PATCH,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AetherFeatureStates.BERRY_BUSH, 1)), 32));
        register(context, QUICKSOIL_SHELF_CONFIGURATION, AetherFeatures.SHELF.get(),
                new ShelfConfiguration(
                        BlockStateProvider.simple(AetherFeatureStates.QUICKSOIL),
                        ConstantFloat.of(Mth.sqrt(12)),
                        UniformInt.of(0, 48),
                        HolderSet.direct(Block::builtInRegistryHolder, AetherBlocks.AETHER_GRASS_BLOCK.get())));
        register(context, WATER_LAKE_CONFIGURATION, AetherFeatures.LAKE.get(),
                AetherConfiguredFeatureBuilders.lake(BlockStateProvider.simple(Blocks.WATER), BlockStateProvider.simple(AetherFeatureStates.AETHER_GRASS_BLOCK)));
        register(context, WATER_SPRING_CONFIGURATION, Feature.SPRING,
                AetherConfiguredFeatureBuilders.spring(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, AetherBlocks.HOLYSTONE.get(), AetherBlocks.AETHER_DIRT.get())));
        register(context, ORE_AETHER_DIRT_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, AetherFeatureStates.AETHER_DIRT, 33));
        register(context, ORE_ICESTONE_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, AetherFeatureStates.ICESTONE, 32));
        register(context, ORE_AMBROSIUM_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, AetherFeatureStates.AMBROSIUM_ORE, 16));
        register(context, ORE_ZANITE_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, AetherFeatureStates.ZANITE_ORE, 5, 0.5F));
        register(context, ORE_GRAVITITE_BURIED_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, AetherFeatureStates.GRAVITITE_ORE, 3, 0.5F));
        register(context, ORE_GRAVITITE_CONFIGURATION, Feature.ORE, new OreConfiguration(AetherFeatureRules.HOLYSTONE, AetherFeatureStates.GRAVITITE_ORE, 4));
        register(context, TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GOLDEN_OAK_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.01F)),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()))));
        register(context, SINGLE_GOLD_DUNGEON_FLOWER_CONFIGURATION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(
                        new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(Blocks.DANDELION.defaultBlockState(), 1)
                                .add(Blocks.POPPY.defaultBlockState(), 1))));
        register(context, GOLD_DUNGEON_ISLAND_FOLIAGE_CONFIGURATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GOLDEN_OAK_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.66F)),
                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SINGLE_GOLD_DUNGEON_FLOWER_CONFIGURATION), PlacementUtils.isEmpty())));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
