package com.gildedgames.aether.data.resources.registries;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.data.resources.builders.AetherPlacedFeatureBuilders;
import com.gildedgames.aether.world.placementmodifier.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class AetherPlacedFeatures {
    public static final ResourceKey<PlacedFeature> COLD_AERCLOUD_PLACEMENT = createKey("cold_aercloud");
    public static final ResourceKey<PlacedFeature> BLUE_AERCLOUD_PLACEMENT = createKey("blue_aercloud");
    public static final ResourceKey<PlacedFeature> GOLDEN_AERCLOUD_PLACEMENT = createKey("golden_aercloud");
    public static final ResourceKey<PlacedFeature> PINK_AERCLOUD_PLACEMENT = createKey("pink_aercloud");
    public static final ResourceKey<PlacedFeature> SKYROOT_GROVE_TREES_PLACEMENT = createKey("skyroot_grove_trees");
    public static final ResourceKey<PlacedFeature> SKYROOT_FOREST_TREES_PLACEMENT = createKey("skyroot_forest_trees");
    public static final ResourceKey<PlacedFeature> SKYROOT_THICKET_TREES_PLACEMENT = createKey("skyroot_thicket_trees");
    public static final ResourceKey<PlacedFeature> GOLDEN_FOREST_TREES_PLACEMENT = createKey("golden_forest_trees");
    public static final ResourceKey<PlacedFeature> CRYSTAL_ISLAND_PLACEMENT = createKey("crystal_island");
    public static final ResourceKey<PlacedFeature> HOLIDAY_TREE_PLACEMENT = createKey("holiday_tree");
    public static final ResourceKey<PlacedFeature> FLOWER_PATCH_PLACEMENT = createKey("flower_patch");
    public static final ResourceKey<PlacedFeature> GRASS_PATCH_PLACEMENT = createKey("grass_patch");
    public static final ResourceKey<PlacedFeature> TALL_GRASS_PATCH_PLACEMENT = createKey("tall_grass_patch");
    public static final ResourceKey<PlacedFeature> QUICKSOIL_SHELF_PLACEMENT = createKey("quicksoil_shelf");
    public static final ResourceKey<PlacedFeature> WATER_LAKE_PLACEMENT = createKey("water_lake");
    public static final ResourceKey<PlacedFeature> WATER_SPRING_PLACEMENT = createKey("water_spring");
    public static final ResourceKey<PlacedFeature> ORE_AETHER_DIRT_PLACEMENT = createKey("aether_dirt_ore");
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE_PLACEMENT = createKey("icestone_ore");
    public static final ResourceKey<PlacedFeature> ORE_AMBROSIUM_PLACEMENT = createKey("ambrosium_ore");
    public static final ResourceKey<PlacedFeature> ORE_ZANITE_PLACEMENT = createKey("zanite_ore");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_PLACEMENT = createKey("gravitite_ore");

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, COLD_AERCLOUD_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.COLD_AERCLOUD_CONFIGURATION), AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 7));
        register(context, BLUE_AERCLOUD_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION), AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 24));
        register(context, GOLDEN_AERCLOUD_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.GOLDEN_AERCLOUD_CONFIGURATION), AetherPlacedFeatureBuilders.aercloudPlacement(96, 32, 75));
        register(context, PINK_AERCLOUD_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.PINK_AERCLOUD_CONFIGURATION), AetherPlacedFeatureBuilders.pinkAercloudPlacement(96, 32, 75));
        register(context, CRYSTAL_ISLAND_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.CRYSTAL_ISLAND_CONFIGURATION),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(96)),
                RarityFilter.onAverageOnceEvery(32),
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());
        register(context, SKYROOT_GROVE_TREES_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
                AetherPlacedFeatureBuilders.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
        register(context, SKYROOT_FOREST_TREES_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
                AetherPlacedFeatureBuilders.treePlacement(PlacementUtils.countExtra(7, 0.1F, 1)));
        register(context, SKYROOT_THICKET_TREES_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TREES_SKYROOT_AND_GOLDEN_OAK_CONFIGURATION),
                AetherPlacedFeatureBuilders.treePlacement(PlacementUtils.countExtra(15, 0.1F, 1)));
        register(context, GOLDEN_FOREST_TREES_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TREES_GOLDEN_OAK_AND_SKYROOT_CONFIGURATION),
                AetherPlacedFeatureBuilders.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
        register(context, HOLIDAY_TREE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.HOLIDAY_TREE_CONFIGURATION),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()),
                RarityFilter.onAverageOnceEvery(48),
                BiomeFilter.biome(),
                new HolidayFilter());
        register(context, FLOWER_PATCH_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.FLOWER_PATCH_CONFIGURATION), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        register(context, GRASS_PATCH_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                CountPlacement.of(2),
                BiomeFilter.biome(),
                new ConfigFilter(AetherConfig.COMMON.generate_tall_grass));
        register(context, TALL_GRASS_PATCH_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                NoiseThresholdCountPlacement.of(-0.8D, 0, 7),
                RarityFilter.onAverageOnceEvery(32),
                BiomeFilter.biome(),
                new ConfigFilter(AetherConfig.COMMON.generate_tall_grass));
        register(context, QUICKSOIL_SHELF_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.QUICKSOIL_SHELF_CONFIGURATION),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(AetherTags.Blocks.QUICKSOIL_CAN_GENERATE)),
                new ElevationAdjustmentModifier(UniformInt.of(-4, -2)),
                new ElevationFilter(63, 70),
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());
        register(context, WATER_LAKE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.WATER_LAKE_CONFIGURATION),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                RarityFilter.onAverageOnceEvery(40),
                BiomeFilter.biome());
        register(context, WATER_SPRING_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.WATER_SPRING_CONFIGURATION),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(192)),
                CountPlacement.of(25),
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());
        register(context, ORE_AETHER_DIRT_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_AETHER_DIRT_CONFIGURATION),
                AetherPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        register(context, ORE_ICESTONE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_ICESTONE_CONFIGURATION),
                AetherPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        register(context, ORE_AMBROSIUM_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
                AetherPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        register(context, ORE_ZANITE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
                AetherPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
        register(context, ORE_GRAVITITE_PLACEMENT, configuredFeatures.getOrThrow(AetherConfiguredFeatures.ORE_GRAVITITE_CONFIGURATION),
                AetherPlacedFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
