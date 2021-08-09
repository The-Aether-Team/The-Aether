package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.world.gen.feature.*;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.OptionalInt;

public class AetherFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Aether.MODID);

    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> GRASS_PATCH = FEATURES.register("grass_patch", () -> new AetherGrassFeature(BlockClusterFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> QUICKSOIL = FEATURES.register("quicksoil", () -> new QuicksoilFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<BlockStateFeatureConfig>> LAKE = FEATURES.register("lake", () -> new AetherLakeFeature(BlockStateFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> COLD_AERCLOUD = FEATURES.register("cold_aercloud", () -> new ColdAercloudFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> BLUE_AERCLOUD = FEATURES.register("blue_aercloud", () -> new BlueAercloudFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> GOLD_AERCLOUD = FEATURES.register("gold_aercloud", () -> new GoldAercloudFeature(NoFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> PINK_AERCLOUD = FEATURES.register("pink_aercloud", () -> new PinkAercloudFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> CRYSTAL_TREE = FEATURES.register("crystal_tree", () -> new CrystalTreeFeature(NoFeatureConfig.CODEC));

    public static final RegistryObject<Feature<NoFeatureConfig>> HOLYSTONE_SPHERE = FEATURES.register("holystone_sphere", () -> new HolystoneSphereFeature(NoFeatureConfig.CODEC));

    public static final class States
    {
        public static final BlockState SKYROOT_LOG = AetherBlocks.SKYROOT_LOG.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState SKYROOT_LEAVES = AetherBlocks.SKYROOT_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState GOLDEN_OAK_LOG = AetherBlocks.GOLDEN_OAK_LOG.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState GOLDEN_OAK_LEAVES = AetherBlocks.GOLDEN_OAK_LEAVES.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState AETHER_DIRT = AetherBlocks.AETHER_DIRT.get().defaultBlockState();
        public static final BlockState ICESTONE = AetherBlocks.ICESTONE.get().defaultBlockState();
        public static final BlockState AMBROSIUM_ORE = AetherBlocks.AMBROSIUM_ORE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState ZANITE_ORE = AetherBlocks.ZANITE_ORE.get().defaultBlockState();
        public static final BlockState GRAVITITE_ORE = AetherBlocks.GRAVITITE_ORE.get().defaultBlockState();

        public static final BlockState PURPLE_FLOWER = AetherBlocks.PURPLE_FLOWER.get().defaultBlockState();
        public static final BlockState WHITE_FLOWER = AetherBlocks.WHITE_FLOWER.get().defaultBlockState();
        public static final BlockState BERRY_BUSH = AetherBlocks.BERRY_BUSH.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
    }

    public static final class Configs
    {
        public static final BaseTreeFeatureConfig SKYROOT_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(States.SKYROOT_LOG),
                new SimpleBlockStateProvider(States.SKYROOT_LEAVES),
                new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
                new StraightTrunkPlacer(4, 2, 0),
                new TwoLayerFeature(1, 0, 1))).ignoreVines().build();
        public static final BaseTreeFeatureConfig GOLDEN_OAK_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(States.GOLDEN_OAK_LOG),
                new SimpleBlockStateProvider(States.GOLDEN_OAK_LEAVES),
                new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 4),
                new FancyTrunkPlacer(3, 11, 0),
                new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build();

        public static final BlockClusterFeatureConfig FLOWER_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider())
                        .add(States.PURPLE_FLOWER, 1)
                        .add(States.WHITE_FLOWER, 1)
                        .add(States.BERRY_BUSH, 1), SimpleBlockPlacer.INSTANCE))
                .tries(64).whitelist(ImmutableSet.of(AetherBlocks.AETHER_GRASS_BLOCK.get())).build();
    }

    public static final class Tests
    {
        public static final RuleTest HOLYSTONE = new BlockMatchRuleTest(AetherBlocks.HOLYSTONE.get());
    }

    public static void registerConfiguredFeatures() {
        register("grass_patch", AetherFeatures.GRASS_PATCH.get().configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
        register("tall_grass_patch", AetherFeatures.GRASS_PATCH.get().configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP).squared().decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 0, 7))));

        register("quicksoil", AetherFeatures.QUICKSOIL.get().configured(IFeatureConfig.NONE).decorated(Placement.RANGE_VERY_BIASED.configured(new TopSolidRangeConfig(63, 0, 70))).squared().count(10));

        register("water_lake", AetherFeatures.LAKE.get().configured(new BlockStateFeatureConfig(Blocks.WATER.defaultBlockState())).decorated(Placement.WATER_LAKE.configured(new ChanceConfig(4))));
        register("spring_water", Feature.SPRING.configured(new LiquidsConfig(Fluids.WATER.defaultFluidState(), true, 4, 1, ImmutableSet.of(AetherBlocks.HOLYSTONE.get(), AetherBlocks.AETHER_DIRT.get()))).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(8, 8, 256))).squared().count(50));

        register("cold_aercloud", AetherFeatures.COLD_AERCLOUD.get().configured(IFeatureConfig.NONE).range(128).squared().chance(5));
        register("blue_aercloud", AetherFeatures.BLUE_AERCLOUD.get().configured(IFeatureConfig.NONE).range(96).squared().chance(5));
        register("gold_aercloud", AetherFeatures.GOLD_AERCLOUD.get().configured(IFeatureConfig.NONE).range(160).squared().chance(5));
        register("pink_aercloud", AetherFeatures.PINK_AERCLOUD.get().configured(IFeatureConfig.NONE).range(160).squared().chance(7));


        register("crystal_tree", AetherFeatures.CRYSTAL_TREE.get().configured(IFeatureConfig.NONE).chance(30));
        register("tree_skyroot", Feature.TREE.configured(Configs.SKYROOT_TREE_CONFIG).decorated(Features.Placements.HEIGHTMAP_SQUARE));
        register("tree_golden_oak", Feature.TREE.configured(Configs.GOLDEN_OAK_TREE_CONFIG).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.03F, 1))));

        register("ore_aether_dirt", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.AETHER_DIRT, 33)).range(256).squared().count(10));
        register("ore_icestone", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.ICESTONE, 16)).range(256).squared().count(10));
        register("ore_ambrosium", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.AMBROSIUM_ORE, 16)).range(256).squared().count(10));
        register("ore_zanite", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.ZANITE_ORE, 8)).range(256).squared().count(10));
        register("ore_gravitite", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.GRAVITITE_ORE, 6)).range(256).squared().count(10));

        register("aether_skylands_flowers", Feature.FLOWER.configured(Configs.FLOWER_PATCH_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
    }

    private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> feature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name), feature);
    }

//    public static final class Configured
//    {
//        public static final ConfiguredFeature<?, ?> GRASS_PATCH = register("grass_patch", AetherFeatures.GRASS_PATCH.get().configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10))));
//        public static final ConfiguredFeature<?, ?> TALL_GRASS_PATCH = register("tall_grass_patch", AetherFeatures.GRASS_PATCH.get().configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP).squared().decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 0, 7))));
//
//        public static final ConfiguredFeature<?, ?> QUICKSOIL = register("quicksoil", AetherFeatures.QUICKSOIL.get().configured(IFeatureConfig.NONE).decorated(Placement.RANGE_VERY_BIASED.configured(new TopSolidRangeConfig(63, 0, 70))).squared().count(10));
//
//        public static final ConfiguredFeature<?, ?> LAKE = register("water_lake", AetherFeatures.LAKE.get().configured(new BlockStateFeatureConfig(Blocks.WATER.defaultBlockState())).decorated(Placement.WATER_LAKE.configured(new ChanceConfig(4))));
//        public static final ConfiguredFeature<?, ?> SPRING = register("spring_water", Feature.SPRING.configured(new LiquidsConfig(Fluids.WATER.defaultFluidState(), true, 4, 1, ImmutableSet.of(AetherBlocks.HOLYSTONE.get(), AetherBlocks.AETHER_DIRT.get()))).decorated(Placement.RANGE_BIASED.configured(new TopSolidRangeConfig(8, 8, 256))).squared().count(50));
//
//        public static final ConfiguredFeature<?, ?> COLD_AERCLOUD = register("cold_aercloud", AetherFeatures.COLD_AERCLOUD.get().configured(IFeatureConfig.NONE).range(128).squared().chance(5));
//        public static final ConfiguredFeature<?, ?> BLUE_AERCLOUD = register("blue_aercloud", AetherFeatures.BLUE_AERCLOUD.get().configured(IFeatureConfig.NONE).range(96).squared().chance(5));
//        public static final ConfiguredFeature<?, ?> GOLD_AERCLOUD = register("gold_aercloud", AetherFeatures.GOLD_AERCLOUD.get().configured(IFeatureConfig.NONE).range(160).squared().chance(5));
//        public static final ConfiguredFeature<?, ?> PINK_AERCLOUD = register("pink_aercloud", AetherFeatures.PINK_AERCLOUD.get().configured(IFeatureConfig.NONE).range(160).squared().chance(7));
//
//
//        public static final ConfiguredFeature<?, ?> CRYSTAL_TREE = register("crystal_tree", AetherFeatures.CRYSTAL_TREE.get().configured(IFeatureConfig.NONE).chance(30));
//        public static final ConfiguredFeature<?, ?> SKYROOT_TREE = register("tree_skyroot", Feature.TREE.configured(Configs.SKYROOT_TREE_CONFIG).decorated(Features.Placements.HEIGHTMAP_SQUARE));
//        public static final ConfiguredFeature<?, ?> GOLDEN_OAK_TREE = register("tree_golden_oak", Feature.TREE.configured(Configs.GOLDEN_OAK_TREE_CONFIG).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, 0.03F, 1))));
//
//        public static final ConfiguredFeature<?, ?> AETHER_DIRT_CLUSTER = register("ore_aether_dirt", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.AETHER_DIRT, 33)).range(256).squared().count(10));
//        public static final ConfiguredFeature<?, ?> ICESTONE_CLUSTER = register("ore_icestone", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.ICESTONE, 16)).range(256).squared().count(10));
//        public static final ConfiguredFeature<?, ?> AMBROSIUM_ORE_CLUSTER = register("ore_ambrosium", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.AMBROSIUM_ORE, 16)).range(256).squared().count(10));
//        public static final ConfiguredFeature<?, ?> ZANITE_ORE_CLUSTER = register("ore_zanite", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.ZANITE_ORE, 8)).range(256).squared().count(10));
//        public static final ConfiguredFeature<?, ?> GRAVITITE_ORE_CLUSTER = register("ore_gravitite", Feature.ORE.configured(new OreFeatureConfig(Tests.HOLYSTONE, States.GRAVITITE_ORE, 6)).range(256).squared().count(10));
//
//        public static final ConfiguredFeature<?, ?> FLOWER_PATCH = register("aether_skylands_flowers", Feature.FLOWER.configured(Configs.FLOWER_PATCH_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(2));
//
//        private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> feature) {
//            return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name), feature);
//        }
//    }
}