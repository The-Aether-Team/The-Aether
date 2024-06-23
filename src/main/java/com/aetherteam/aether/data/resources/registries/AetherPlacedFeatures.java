package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.builders.AetherPlacedFeatureBuilders;
import com.aetherteam.aether.world.placementmodifier.ConfigFilter;
import com.aetherteam.aether.world.placementmodifier.DungeonBlacklistFilter;
import com.aetherteam.aether.world.placementmodifier.HolidayFilter;
import com.aetherteam.aether.world.placementmodifier.ImprovedLayerPlacementModifier;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenPlacedFeatureBuilders;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class AetherPlacedFeatures {
    public static final ResourceKey<PlacedFeature> COLD_AERCLOUD_PLACEMENT = createKey("cold_aercloud");
    public static final ResourceKey<PlacedFeature> BLUE_AERCLOUD_PLACEMENT = createKey("blue_aercloud");
    public static final ResourceKey<PlacedFeature> GOLDEN_AERCLOUD_PLACEMENT = createKey("golden_aercloud");
    public static final ResourceKey<PlacedFeature> SKYROOT_MEADOW_TREES_PLACEMENT = createKey("skyroot_meadow_trees");
    public static final ResourceKey<PlacedFeature> SKYROOT_GROVE_TREES_PLACEMENT = createKey("skyroot_grove_trees");
    public static final ResourceKey<PlacedFeature> SKYROOT_WOODLAND_TREES_PLACEMENT = createKey("skyroot_woodland_trees");
    public static final ResourceKey<PlacedFeature> SKYROOT_FOREST_TREES_PLACEMENT = createKey("skyroot_forest_trees");
    public static final ResourceKey<PlacedFeature> CRYSTAL_ISLAND_PLACEMENT = createKey("crystal_island");
    public static final ResourceKey<PlacedFeature> HOLIDAY_TREE_PLACEMENT = createKey("holiday_tree");
    public static final ResourceKey<PlacedFeature> GRASS_PATCH_PLACEMENT = createKey("grass_patch");
    public static final ResourceKey<PlacedFeature> TALL_GRASS_PATCH_PLACEMENT = createKey("tall_grass_patch");
    public static final ResourceKey<PlacedFeature> AETHER_GRASS_BONEMEAL = createKey("aether_grass_bonemeal");
    public static final ResourceKey<PlacedFeature> ENCHANTED_AETHER_GRASS_BONEMEAL = createKey("enchanted_aether_grass_bonemeal");
    public static final ResourceKey<PlacedFeature> WHITE_FLOWER_PATCH_PLACEMENT = createKey("white_flower_patch");
    public static final ResourceKey<PlacedFeature> PURPLE_FLOWER_PATCH_PLACEMENT = createKey("purple_flower_patch");
    public static final ResourceKey<PlacedFeature> BERRY_BUSH_PATCH_PLACEMENT = createKey("berry_bush_patch");
    public static final ResourceKey<PlacedFeature> QUICKSOIL_SHELF_PLACEMENT = createKey("quicksoil_shelf");
    public static final ResourceKey<PlacedFeature> WATER_LAKE_PLACEMENT = createKey("water_lake");
    public static final ResourceKey<PlacedFeature> WATER_SPRING_PLACEMENT = createKey("water_spring");
    public static final ResourceKey<PlacedFeature> ORE_AETHER_DIRT_PLACEMENT = createKey("aether_dirt_ore");
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE_PLACEMENT = createKey("icestone_ore");
    public static final ResourceKey<PlacedFeature> ORE_AMBROSIUM_PLACEMENT = createKey("ambrosium_ore");
    public static final ResourceKey<PlacedFeature> ORE_ZANITE_PLACEMENT = createKey("zanite_ore");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_BURIED_PLACEMENT = createKey("gravitite_ore_buried");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_PLACEMENT = createKey("gravitite_ore");
    public static final ResourceKey<PlacedFeature> GOLD_DUNGEON_ISLAND_FOLIAGE = createKey("gold_dungeon_island_foliage");

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, COLD_AERCLOUD_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.COLD_AERCLOUD_CONFIGURATION), AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 7));
        register(context, BLUE_AERCLOUD_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION), AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 24));
        register(context, GOLDEN_AERCLOUD_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.GOLDEN_AERCLOUD_CONFIGURATION), AetherPlacedFeatureBuilders.aercloudPlacement(96, 32, 75));
        register(context, CRYSTAL_ISLAND_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.CRYSTAL_ISLAND_CONFIGURATION),
                RarityFilter.onAverageOnceEvery(50),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(96)),
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());
        register(context, SKYROOT_MEADOW_TREES_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
                AetherPlacedFeatureBuilders.treePlacement(RarityFilter.onAverageOnceEvery(1)));
        register(context, SKYROOT_GROVE_TREES_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
                AetherPlacedFeatureBuilders.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
        register(context, SKYROOT_WOODLAND_TREES_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
                AetherPlacedFeatureBuilders.treePlacement(PlacementUtils.countExtra(5, 0.1F, 1)));
        register(context, SKYROOT_FOREST_TREES_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
                AetherPlacedFeatureBuilders.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1)));
        register(context, HOLIDAY_TREE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.HOLIDAY_TREE_CONFIGURATION),
                RarityFilter.onAverageOnceEvery(75),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                new HolidayFilter(),
                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()));
        register(context, GRASS_PATCH_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                new ConfigFilter(AetherConfig.SERVER.generate_tall_grass));
        register(context, TALL_GRASS_PATCH_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION),
                NoiseThresholdCountPlacement.of(-0.8, 0, 7),
                RarityFilter.onAverageOnceEvery(32),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                new ConfigFilter(AetherConfig.SERVER.generate_tall_grass));
        register(context, AETHER_GRASS_BONEMEAL, configuredFeatures.getOrThrow(VegetationFeatures.SINGLE_PIECE_OF_GRASS),
                PlacementUtils.isEmpty());
        register(context, ENCHANTED_AETHER_GRASS_BONEMEAL, configuredFeatures.getOrThrow(VegetationFeatures.SINGLE_PIECE_OF_GRASS),
                PlacementUtils.isEmpty());
        register(context, WHITE_FLOWER_PATCH_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.WHITE_FLOWER_PATCH_CONFIGURATION),
                RarityFilter.onAverageOnceEvery(8),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome());
        register(context, PURPLE_FLOWER_PATCH_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.PURPLE_FLOWER_PATCH_CONFIGURATION),
                RarityFilter.onAverageOnceEvery(16),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome());
        register(context, BERRY_BUSH_PATCH_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.BERRY_BUSH_PATCH_CONFIGURATION),
                RarityFilter.onAverageOnceEvery(8),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome());
        register(context, QUICKSOIL_SHELF_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.QUICKSOIL_SHELF_CONFIGURATION),
                RarityFilter.onAverageOnceEvery(5),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());
        register(context, WATER_LAKE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.WATER_LAKE_CONFIGURATION),
                RarityFilter.onAverageOnceEvery(15),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome());
        register(context, WATER_SPRING_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.WATER_SPRING_CONFIGURATION),
                CountPlacement.of(30),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(8), VerticalAnchor.aboveBottom(128)),
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());
        register(context, ORE_AETHER_DIRT_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_AETHER_DIRT_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));
        register(context, ORE_ICESTONE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_ICESTONE_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));
        register(context, ORE_AMBROSIUM_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(20, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));
        register(context, ORE_ZANITE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(14, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(75))));
        register(context, ORE_GRAVITITE_BURIED_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_GRAVITITE_BURIED_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(74))));
        register(context, ORE_GRAVITITE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_GRAVITITE_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-58), VerticalAnchor.aboveBottom(74))));
        register(context, GOLD_DUNGEON_ISLAND_FOLIAGE, configuredFeatures.getOrThrow(AetherConfiguredFeatures.GOLD_DUNGEON_ISLAND_FOLIAGE_CONFIGURATION),
                PlacementUtils.isEmpty(), RarityFilter.onAverageOnceEvery(16));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
