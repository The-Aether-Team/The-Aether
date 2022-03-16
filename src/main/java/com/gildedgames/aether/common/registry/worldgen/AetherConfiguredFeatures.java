package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.world.feature.FeatureBuilders;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import com.gildedgames.aether.common.world.gen.configuration.SimpleDiskConfiguration;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

public class AetherConfiguredFeatures {
    public static final RuleTest HOLYSTONE = new TagMatchTest(AetherTags.Blocks.HOLYSTONE);

    public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> COLD_AERCLOUD = FeatureUtils.register("cold_aercloud", AetherFeaturesForge.AERCLOUD.get(),
            createAercloudConfig(16, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> BLUE_AERCLOUD = FeatureUtils.register("blue_aercloud", AetherFeaturesForge.AERCLOUD.get(),
            createAercloudConfig(8, AetherBlocks.BLUE_AERCLOUD.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> GOLDEN_AERCLOUD = FeatureUtils.register("golden_aercloud", AetherFeaturesForge.AERCLOUD.get(),
            createAercloudConfig(4, AetherBlocks.GOLDEN_AERCLOUD.get().defaultBlockState()));
    public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> PINK_AERCLOUD = FeatureUtils.register("pink_aercloud", AetherFeaturesForge.AERCLOUD.get(),
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

    public static final Holder<ConfiguredFeature<SimpleDiskConfiguration, ?>> QUICKSOIL_SHELF_CONFIGURED_FEATURE = FeatureUtils.register("quicksoil_shelf", AetherFeaturesForge.SIMPLE_DISK.get(),
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

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREE_BLEND = FeatureUtils.register("aether_tree_mix", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(
                    List.of(new WeightedPlacedFeature(Holder.direct(new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE), List.of(FeatureBuilders.copyBlockSurvivability(AetherBlocks.GOLDEN_OAK_SAPLING.get())))), 0.01f)),
                    Holder.direct(new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE), List.of(FeatureBuilders.copyBlockSurvivability(AetherBlocks.SKYROOT_SAPLING.get()))))
            ));

    public static AercloudConfiguration createAercloudConfig(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds,
                BlockStateProvider.simple(blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)));
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
}
