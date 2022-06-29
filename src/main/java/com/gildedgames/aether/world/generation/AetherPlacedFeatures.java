package com.gildedgames.aether.world.generation;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.api.AetherTags;
import com.gildedgames.aether.world.builders.AetherFeatureBuilders;
import com.gildedgames.aether.world.generation.AetherConfiguredFeatures;
import com.gildedgames.aether.world.generation.placement.ConfigFilter;
import com.gildedgames.aether.world.generation.placement.ElevationAdjustment;
import com.gildedgames.aether.world.generation.placement.ElevationFilter;
import com.gildedgames.aether.world.generation.placement.HolidayFilter;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.data.AetherDataGenerators;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AetherPlacedFeatures {
    public static final Map<ResourceLocation, PlacedFeature> PLACED_FEATURES = new HashMap<>();

    public static final Holder<PlacedFeature> COLD_AERCLOUD_PLACEMENT = register("cold_aercloud", AetherConfiguredFeatures.COLD_AERCLOUD_CONFIGURATION, AetherFeatureBuilders.createAercloudPlacements(128, 5));
    public static final Holder<PlacedFeature> BLUE_AERCLOUD_PLACEMENT = register("blue_aercloud", AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION, AetherFeatureBuilders.createAercloudPlacements(96, 5));
    public static final Holder<PlacedFeature> GOLDEN_AERCLOUD_PLACEMENT = register("golden_aercloud", AetherConfiguredFeatures.GOLDEN_AERCLOUD_CONFIGURATION, AetherFeatureBuilders.createAercloudPlacements(160, 5));
    public static final Holder<PlacedFeature> PINK_AERCLOUD_PLACEMENT = register("pink_aercloud", AetherConfiguredFeatures.PINK_AERCLOUD_CONFIGURATION, AetherFeatureBuilders.createPinkAercloudPlacements(160, 7));

    public static final Holder<PlacedFeature> SKYROOT_GROVE_TREES_PLACEMENT = register("skyroot_grove_trees", AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION,
            AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));

    public static final Holder<PlacedFeature> SKYROOT_FOREST_TREES_PLACEMENT = register("skyroot_forest_trees", AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION,
            AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(7, 0.1F, 1)));

    public static final Holder<PlacedFeature> SKYROOT_THICKET_TREES_PLACEMENT = register("skyroot_thicket_trees", AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION,
            AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(15, 0.1F, 1)));

    public static final Holder<PlacedFeature> GOLDEN_FOREST_TREES_PLACEMENT = register("golden_forest_trees", AetherConfiguredFeatures.TREES_GOLDEN_OAK_AND_SKYROOT_CONFIGURATION,
            AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> CRYSTAL_ISLAND_PLACEMENT = register("crystal_island", AetherConfiguredFeatures.CRYSTAL_ISLAND_CONFIGURATION,
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(80), VerticalAnchor.absolute(120)),
            RarityFilter.onAverageOnceEvery(16));

    public static final Holder<PlacedFeature> HOLIDAY_TREE_PLACEMENT = register("holiday_tree", AetherConfiguredFeatures.HOLIDAY_TREE_CONFIGURATION,
            new HolidayFilter(),
            CountOnEveryLayerPlacement.of(1), //todo replace
            RarityFilter.onAverageOnceEvery(48),
            PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()));

    public static final Holder<PlacedFeature> FLOWER_PATCH_PLACEMENT = register("flower_patch", AetherConfiguredFeatures.FLOWER_PATCH_CONFIGURATION,
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> GRASS_PATCH_PLACEMENT = register("grass_patch", AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION,
            new ConfigFilter(AetherConfig.COMMON.generate_tall_grass),
            CountPlacement.of(2),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome());

    public static final Holder<PlacedFeature> TALL_GRASS_PATCH_PLACEMENT = register("tall_grass_patch", AetherConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION,
            new ConfigFilter(AetherConfig.COMMON.generate_tall_grass),
            NoiseThresholdCountPlacement.of(-0.8D, 0, 7),
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome());

    public static final Holder<PlacedFeature> QUICKSOIL_SHELF_PLACEMENT = register("quicksoil_shelf", AetherConfiguredFeatures.QUICKSOIL_SHELF_CONFIGURATION,
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            new ElevationAdjustment(UniformInt.of(-4, -2)),
            new ElevationFilter(47, 70),
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(AetherTags.Blocks.QUICKSOIL_CAN_GENERATE)));
    // FIXME once Terrain can go above 63 again, change 47 -> 63

    public static final Holder<PlacedFeature> WATER_LAKE_PLACEMENT = register("water_lake", AetherConfiguredFeatures.WATER_LAKE_CONFIGURATION,
            RarityFilter.onAverageOnceEvery(40),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome());

    public static final Holder<PlacedFeature> WATER_SPRING_PLACEMENT = register("water_spring", AetherConfiguredFeatures.WATER_SPRING_CONFIGURATION,
            CountPlacement.of(25),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(192)), BiomeFilter.biome());

    public static final Holder<PlacedFeature> ORE_AETHER_DIRT_PLACEMENT = register("aether_dirt_ore", AetherConfiguredFeatures.ORE_AETHER_DIRT_CONFIGURATION,
            AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_ICESTONE_PLACEMENT = register("icestone_ore", AetherConfiguredFeatures.ORE_ICESTONE_CONFIGURATION,
            AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_AMBROSIUM_PLACEMENT = register("ambrosium_ore", AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION,
            AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_ZANITE_PLACEMENT = register("zanite_ore", AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION,
            AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_GRAVITITE_COMMON_PLACEMENT = register("gravitite_ore_common", AetherConfiguredFeatures.ORE_GRAVITITE_COMMON_CONFIGURATION,
            AetherFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_GRAVITITE_DENSE_PLACEMENT = register("gravitite_ore_dense", AetherConfiguredFeatures.ORE_GRAVITITE_DENSE_CONFIGURATION,
            AetherFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));

    public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> placementModifiers) {
        ResourceKey<Registry<PlacedFeature>> registry = Registry.PLACED_FEATURE_REGISTRY;
        ResourceLocation location = new ResourceLocation(Aether.MODID, name);
        PLACED_FEATURES.put(location, new PlacedFeature(Holder.hackyErase(configuredFeature), List.copyOf(placementModifiers)));
        return AetherDataGenerators.DATA_REGISTRY.registryOrThrow(registry).getOrCreateHolderOrThrow(ResourceKey.create(registry, location));
    }

    public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placementModifiers) {
        return register(name, configuredFeature, List.of(placementModifiers));
    }

    public static Holder<PlacedFeature> copy(Holder<PlacedFeature> oldHolder, Registry<PlacedFeature> registry) {
        ResourceKey<PlacedFeature> key = oldHolder.unwrapKey().orElseThrow();
        PlacedFeature placedFeature = PLACED_FEATURES.get(key.location());
        Holder.Reference<PlacedFeature> configuredFeatureHolder = (Holder.Reference<PlacedFeature>) registry.getOrCreateHolderOrThrow(key);
        configuredFeatureHolder.bind(key, placedFeature);
        return configuredFeatureHolder;
    }
}
