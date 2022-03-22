package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.world.feature.AetherFeatureBuilders;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import com.gildedgames.aether.common.world.gen.configuration.SimpleDiskConfiguration;
import com.gildedgames.aether.common.world.gen.feature.AercloudFeature;
import com.gildedgames.aether.common.world.gen.feature.SimpleDiskFeature;
import com.gildedgames.aether.common.world.gen.placement.ElevationAdjustment;
import com.gildedgames.aether.common.world.gen.placement.ElevationFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherFeatures {
    public static Feature<SimpleDiskConfiguration> SIMPLE_DISK = new SimpleDiskFeature(SimpleDiskConfiguration.CODEC);
    public static Feature<AercloudConfiguration> AERCLOUD = new AercloudFeature(AercloudConfiguration.CODEC);
    //public static Feature<NoneFeatureConfiguration> HOLYSTONE_SPHERE = new HolystoneSphereFeature(NoneFeatureConfiguration.CODEC); // This is for Gold Dungeons

    @SubscribeEvent //This cannot be moved to DeferredRegister or the features won't be able to be added to the biomes at registry time.
    public static void register(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        register(registry, "simple_disk", SIMPLE_DISK);
        register(registry, "aercloud", AERCLOUD);
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> void register(IForgeRegistry<Feature<?>> registry, String name, F value) {
        value.setRegistryName(new ResourceLocation(Aether.MODID, name).toString());
        registry.register(value);
    }

    public static class ConfiguredFeatures {
        public static final HashMap<ResourceKey<ConfiguredFeature<?, ?>>, Supplier<ConfiguredFeature<?, ?>>> CONFIGURED_FEATURES = new HashMap<>();

        public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> COLD_AERCLOUD = register("cold_aercloud", AERCLOUD,
                AetherFeatureBuilders.createAercloudConfig(16, States.COLD_AERCLOUD));
        public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> BLUE_AERCLOUD = register("blue_aercloud", AERCLOUD,
                AetherFeatureBuilders.createAercloudConfig(8, States.BLUE_AERCLOUD));
        public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> GOLDEN_AERCLOUD = register("golden_aercloud", AERCLOUD,
                AetherFeatureBuilders.createAercloudConfig(4, States.GOLDEN_AERCLOUD));
        public static final Holder<ConfiguredFeature<AercloudConfiguration, ?>> PINK_AERCLOUD = register("pink_aercloud", AERCLOUD,
                AetherFeatureBuilders.createAercloudConfig(1, States.PINK_AERCLOUD));

        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SKYROOT_TREE_CONFIGURED_FEATURE = register("skyroot_tree", Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(States.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(States.SKYROOT_LEAVES),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());

        public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> GOLDEN_OAK_TREE_CONFIGURED_FEATURE = register("golden_oak_tree", Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(States.GOLDEN_OAK_LOG),
                        new FancyTrunkPlacer(3, 11, 0),
                        BlockStateProvider.simple(States.GOLDEN_OAK_LEAVES),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                ).ignoreVines().build());

        public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PATCH_CONFIGURED_FEATURE = register("flower_patch", Feature.FLOWER,
                AetherFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(States.PURPLE_FLOWER, 2)
                        .add(States.WHITE_FLOWER, 2)
                        .add(States.BERRY_BUSH, 1)), 64
                ));

        public static final Holder<ConfiguredFeature<SimpleDiskConfiguration, ?>> QUICKSOIL_SHELF_CONFIGURED_FEATURE = register("quicksoil_shelf", SIMPLE_DISK,
                new SimpleDiskConfiguration(
                        UniformFloat.of(Mth.sqrt(12), 5), // sqrt(12) is old static value
                        BlockStateProvider.simple(States.QUICKSOIL),
                        3
                ));

        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_AETHER_DIRT_CONFIGURED_FEATURE = register("aether_dirt_ore", Feature.ORE,
                new OreConfiguration(Tests.HOLYSTONE, States.AETHER_DIRT, 33));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_ICESTONE_CONFIGURED_FEATURE = register("icestone_ore", Feature.ORE,
                new OreConfiguration(Tests.HOLYSTONE, States.ICESTONE, 16));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_AMBROSIUM_CONFIGURED_FEATURE = register("ambrosium_ore", Feature.ORE,
                new OreConfiguration(Tests.HOLYSTONE, States.AMBROSIUM_ORE, 16));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_ZANITE_CONFIGURED_FEATURE = register("zanite_ore", Feature.ORE,
                new OreConfiguration(Tests.HOLYSTONE, States.ZANITE_ORE, 8));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_GRAVITITE_COMMON_CONFIGURED_FEATURE = register("gravitite_ore_common", Feature.ORE,
                new OreConfiguration(Tests.HOLYSTONE, States.GRAVITITE_ORE, 6, 0.9F));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_GRAVITITE_DENSE_CONFIGURED_FEATURE = register("gravitite_ore_dense", Feature.ORE,
                new OreConfiguration(Tests.HOLYSTONE, States.GRAVITITE_ORE, 3, 0.5F));

        public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> TREE_BLEND = register("aether_tree_mix", Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(new WeightedPlacedFeature(
                                Holder.direct(new PlacedFeature(Holder.hackyErase(ConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE), List.of(AetherFeatureBuilders.copyBlockSurvivability(AetherBlocks.GOLDEN_OAK_SAPLING.get())))), 0.01F)),
                        Holder.direct(new PlacedFeature(
                                Holder.hackyErase(ConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE), List.of(AetherFeatureBuilders.copyBlockSurvivability(AetherBlocks.SKYROOT_SAPLING.get()))))
                ));

        public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC configuration) {
            ResourceKey<ConfiguredFeature<?, ?>> resourceKey = ResourceKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Aether.MODID, name));
            ConfiguredFeature<FC, ?> configuredFeature =  new ConfiguredFeature<>(feature, configuration);
            CONFIGURED_FEATURES.put(resourceKey, () -> configuredFeature);
            return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name).toString(), configuredFeature);
        }
    }

    public static class PlacedFeatures {
        public static final HashMap<ResourceKey<PlacedFeature>, Supplier<PlacedFeature>> PLACED_FEATURES = new HashMap<>();

        public static final Holder<PlacedFeature> COLD_AERCLOUD_PLACED_FEATURE = register("cold_aercloud", ConfiguredFeatures.COLD_AERCLOUD, AetherFeatureBuilders.createAercloudPlacements(128, 5));
        public static final Holder<PlacedFeature> BLUE_AERCLOUD_PLACED_FEATURE = register("blue_aercloud", ConfiguredFeatures.BLUE_AERCLOUD, AetherFeatureBuilders.createAercloudPlacements(96, 5));
        public static final Holder<PlacedFeature> GOLDEN_AERCLOUD_PLACED_FEATURE = register("golden_aercloud", ConfiguredFeatures.GOLDEN_AERCLOUD, AetherFeatureBuilders.createAercloudPlacements(160, 5));
        public static final Holder<PlacedFeature> PINK_AERCLOUD_PLACED_FEATURE = register("pink_aercloud", ConfiguredFeatures.PINK_AERCLOUD, AetherFeatureBuilders.createAercloudPlacements(160, 7));

        public static final Holder<PlacedFeature> SKYROOT_TREE_PLACED_FEATURE = register("skyroot_tree", ConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE,
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1), AetherBlocks.SKYROOT_SAPLING.get()));
        public static final Holder<PlacedFeature> GOLDEN_OAK_TREE_PLACED_FEATURE = register("golden_oak_tree", ConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE,
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1), AetherBlocks.GOLDEN_OAK_SAPLING.get()));

        public static final Holder<PlacedFeature> FLOWER_PATCH_PLACED_FEATURE = register("flower_patch", ConfiguredFeatures.FLOWER_PATCH_CONFIGURED_FEATURE,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

        public static final Holder<PlacedFeature> QUICKSOIL_SHELF_PLACED_FEATURE = register("quicksoil_shelf", ConfiguredFeatures.QUICKSOIL_SHELF_CONFIGURED_FEATURE,
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                new ElevationAdjustment(UniformInt.of(-4, -2)),
                new ElevationFilter(47, 70),
                BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(BlockPredicate.matchesBlock(AetherBlocks.AETHER_DIRT.get(), BlockPos.ZERO), BlockPredicate.matchesTag(AetherTags.Blocks.HOLYSTONE))));
        // FIXME once Terrain can go above 63 again, change 47 -> 63

        public static final Holder<PlacedFeature> ORE_AETHER_DIRT_PLACED_FEATURE = register("aether_dirt_ore", ConfiguredFeatures.ORE_AETHER_DIRT_CONFIGURED_FEATURE,
                AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_ICESTONE_PLACED_FEATURE = register("icestone_ore", ConfiguredFeatures.ORE_ICESTONE_CONFIGURED_FEATURE,
                AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_AMBROSIUM_PLACED_FEATURE = register("ambrosium_ore", ConfiguredFeatures.ORE_AMBROSIUM_CONFIGURED_FEATURE,
                AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_ZANITE_PLACED_FEATURE = register("zanite_ore", ConfiguredFeatures.ORE_ZANITE_CONFIGURED_FEATURE,
                AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_GRAVITITE_COMMON_PLACED_FEATURE = register("gravitite_ore_common", ConfiguredFeatures.ORE_GRAVITITE_COMMON_CONFIGURED_FEATURE,
                AetherFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        public static final Holder<PlacedFeature> ORE_GRAVITITE_DENSE_PLACED_FEATURE = register("gravitite_ore_dense", ConfiguredFeatures.ORE_GRAVITITE_DENSE_CONFIGURED_FEATURE,
                AetherFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));

        public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> placementModifiers) {
            ResourceKey<PlacedFeature> resourceKey = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Aether.MODID, name));
            PlacedFeature placedFeature = new PlacedFeature(Holder.hackyErase(configuredFeature), List.copyOf(placementModifiers));
            PLACED_FEATURES.put(resourceKey, () -> placedFeature);
            return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Aether.MODID, name).toString(), placedFeature);
        }

        public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placementModifiers) {
            return register(name, configuredFeature, List.of(placementModifiers));
        }
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

        public static final BlockState PURPLE_FLOWER = AetherBlocks.PURPLE_FLOWER.get().defaultBlockState();
        public static final BlockState WHITE_FLOWER = AetherBlocks.WHITE_FLOWER.get().defaultBlockState();
        public static final BlockState BERRY_BUSH = AetherBlocks.BERRY_BUSH.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState QUICKSOIL = AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);

        public static final BlockState AETHER_DIRT = AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState ICESTONE = AetherBlocks.ICESTONE.get().defaultBlockState();
        public static final BlockState AMBROSIUM_ORE = AetherBlocks.AMBROSIUM_ORE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        public static final BlockState ZANITE_ORE = AetherBlocks.ZANITE_ORE.get().defaultBlockState();
        public static final BlockState GRAVITITE_ORE = AetherBlocks.GRAVITITE_ORE.get().defaultBlockState();
    }

    public static class Tests {
        public static final RuleTest HOLYSTONE = new TagMatchTest(AetherTags.Blocks.HOLYSTONE);
    }
}
