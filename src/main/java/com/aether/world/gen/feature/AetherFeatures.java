package com.aether.world.gen.feature;

import com.aether.Aether;
import com.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.registries.ObjectHolder;

import java.util.OptionalInt;

@ObjectHolder(Aether.MODID)
public class AetherFeatures {

    public static void registerConfiguredFeatures() {
        RuleTest HOLYSTONE = new BlockMatchRuleTest(AetherBlocks.HOLYSTONE);

        register("tree_skyroot", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(AetherBlocks.SKYROOT_LOG.getDefaultState()), new SimpleBlockStateProvider(AetherBlocks.SKYROOT_LEAVES.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT));
        register("tree_golden_oak", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(AetherBlocks.GOLDEN_OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(AetherBlocks.GOLDEN_OAK_LEAVES.getDefaultState()), new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build()).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.03F, 1))));

        register("ore_aether_dirt", Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE, AetherBlocks.AETHER_DIRT.getDefaultState(), 33)).range(256).square().func_242731_b(10));
        register("ore_icestone", Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE, AetherBlocks.ICESTONE.getDefaultState(), 16)).range(256).square().func_242731_b(10));
        register("ore_ambrosium", Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE, AetherBlocks.AMBROSIUM_ORE.getDefaultState(), 16)).range(256).square().func_242731_b(10));
        register("ore_zanite", Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE, AetherBlocks.ZANITE_ORE.getDefaultState(), 8)).range(256).square().func_242731_b(10));
        register("ore_gravitite", Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE, AetherBlocks.ICESTONE.getDefaultState(), 6)).range(256).square().func_242731_b(10));

        register("spring_water", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.WATER.getDefaultState(), true, 4, 1, ImmutableSet.of(AetherBlocks.HOLYSTONE, AetherBlocks.AETHER_DIRT))).withPlacement(Placement.RANGE_BIASED.configure(new TopSolidRangeConfig(8, 8, 256))).square().func_242731_b(50));

    }

    private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> feature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name), feature);
    }
}
