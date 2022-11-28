package com.gildedgames.aether.data.resources;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.data.resources.builders.AetherFeatureBuilders;
import com.gildedgames.aether.world.placementmodifier.*;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.data.generators.AetherDataGenerators;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AetherPlacedFeatures {
    public static final Map<ResourceLocation, PlacedFeature> PLACED_FEATURES = new HashMap<>();

    public static final ResourceKey<PlacedFeature> COLD_AERCLOUD_PLACEMENT = register("cold_aercloud", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.COLD_AERCLOUD_CONFIGURATION), AetherFeatureBuilders.createAercloudPlacements(128, 5));
    public static final ResourceKey<PlacedFeature> BLUE_AERCLOUD_PLACEMENT = register("blue_aercloud", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION), AetherFeatureBuilders.createAercloudPlacements(96, 5));
    public static final ResourceKey<PlacedFeature> GOLDEN_AERCLOUD_PLACEMENT = register("golden_aercloud", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.GOLDEN_AERCLOUD_CONFIGURATION), AetherFeatureBuilders.createAercloudPlacements(160, 5));
    public static final ResourceKey<PlacedFeature> PINK_AERCLOUD_PLACEMENT = register("pink_aercloud", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.PINK_AERCLOUD_CONFIGURATION), AetherFeatureBuilders.createPinkAercloudPlacements(160, 7));

    public static final ResourceKey<PlacedFeature> SKYROOT_GROVE_TREES_PLACEMENT = register("skyroot_grove_trees", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
            AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));

    public static final ResourceKey<PlacedFeature> SKYROOT_FOREST_TREES_PLACEMENT = register("skyroot_forest_trees", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
            AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(7, 0.1F, 1)));

    public static final ResourceKey<PlacedFeature> SKYROOT_THICKET_TREES_PLACEMENT = register("skyroot_thicket_trees", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
            AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(15, 0.1F, 1)));

    public static final ResourceKey<PlacedFeature> GOLDEN_FOREST_TREES_PLACEMENT = register("golden_forest_trees", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.TREES_GOLDEN_OAK_AND_SKYROOT_CONFIGURATION),
            AetherFeatureBuilders.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final ResourceKey<PlacedFeature> CRYSTAL_ISLAND_PLACEMENT = register("crystal_island", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.CRYSTAL_ISLAND_CONFIGURATION),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(80), VerticalAnchor.absolute(120)),
            new DungeonBlacklistFilter(),
            RarityFilter.onAverageOnceEvery(16));

    public static final ResourceKey<PlacedFeature> HOLIDAY_TREE_PLACEMENT = register("holiday_tree", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.HOLIDAY_TREE_CONFIGURATION),
            new HolidayFilter(),
            ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
            RarityFilter.onAverageOnceEvery(48),
            PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()));

    public static final ResourceKey<PlacedFeature> FLOWER_PATCH_PLACEMENT = register("flower_patch", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.FLOWER_PATCH_CONFIGURATION),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final ResourceKey<PlacedFeature> GRASS_PATCH_PLACEMENT = register("grass_patch", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION),
            new ConfigFilter(AetherConfig.COMMON.generate_tall_grass),
            CountPlacement.of(2),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome());

    public static final ResourceKey<PlacedFeature> TALL_GRASS_PATCH_PLACEMENT = register("tall_grass_patch", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION),
            new ConfigFilter(AetherConfig.COMMON.generate_tall_grass),
            NoiseThresholdCountPlacement.of(-0.8D, 0, 7),
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome());

    public static final ResourceKey<PlacedFeature> QUICKSOIL_SHELF_PLACEMENT = register("quicksoil_shelf", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.QUICKSOIL_SHELF_CONFIGURATION),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            new ElevationAdjustmentModifier(UniformInt.of(-4, -2)),
            new ElevationFilter(47, 70),
            new DungeonBlacklistFilter(),
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(AetherTags.Blocks.QUICKSOIL_CAN_GENERATE)));
    // FIXME once Terrain can go above 63 again, change 47 -> 63

    public static final ResourceKey<PlacedFeature> WATER_LAKE_PLACEMENT = register("water_lake", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.WATER_LAKE_CONFIGURATION),
            RarityFilter.onAverageOnceEvery(40),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome());

    public static final ResourceKey<PlacedFeature> WATER_SPRING_PLACEMENT = register("water_spring", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.WATER_SPRING_CONFIGURATION),
            CountPlacement.of(25),
            InSquarePlacement.spread(),
            new DungeonBlacklistFilter(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(192)), BiomeFilter.biome());

    public static final ResourceKey<PlacedFeature> ORE_AETHER_DIRT_PLACEMENT = register("aether_dirt_ore", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.ORE_AETHER_DIRT_CONFIGURATION),
            AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE_PLACEMENT = register("icestone_ore", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.ORE_ICESTONE_CONFIGURATION),
            AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final ResourceKey<PlacedFeature> ORE_AMBROSIUM_PLACEMENT = register("ambrosium_ore", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
            AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final ResourceKey<PlacedFeature> ORE_ZANITE_PLACEMENT = register("zanite_ore", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
            AetherFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_COMMON_PLACEMENT = register("gravitite_ore_common", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.ORE_GRAVITITE_COMMON_CONFIGURATION),
            AetherFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_DENSE_PLACEMENT = register("gravitite_ore_dense", AetherConfiguredFeatures.dataHolder(AetherConfiguredFeatures.ORE_GRAVITITE_DENSE_CONFIGURATION),
            AetherFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));

    public static ResourceKey<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> placementModifiers) {
        ResourceLocation location = new ResourceLocation(Aether.MODID, name);
        PLACED_FEATURES.put(location, new PlacedFeature(Holder.hackyErase(configuredFeature), List.copyOf(placementModifiers)));
        return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, location);
    }

    public static ResourceKey<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placementModifiers) {
        return register(name, configuredFeature, List.of(placementModifiers));
    }

    public static Holder<PlacedFeature> dataHolder(ResourceKey<PlacedFeature> resourceKey) {
        return AetherDataGenerators.DATA_REGISTRY.registryOrThrow(Registry.PLACED_FEATURE_REGISTRY).getOrCreateHolderOrThrow(resourceKey);
    }
}
