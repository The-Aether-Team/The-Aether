package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.world.gen.placement.ElevationAdjustment;
import com.gildedgames.aether.common.world.gen.placement.ElevationFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class AetherPlacements {
    public static final Holder<PlacedFeature> COLD_AERCLOUD_PLACED_FEATURE = register("cold_aercloud", AetherConfiguredFeatures.COLD_AERCLOUD, createAercloudPlacements(128, 5));
    public static final Holder<PlacedFeature> BLUE_AERCLOUD_PLACED_FEATURE = register("blue_aercloud", AetherConfiguredFeatures.BLUE_AERCLOUD, createAercloudPlacements(96, 5));
    public static final Holder<PlacedFeature> GOLDEN_AERCLOUD_PLACED_FEATURE = register("golden_aercloud", AetherConfiguredFeatures.GOLDEN_AERCLOUD, createAercloudPlacements(160, 5));
    public static final Holder<PlacedFeature> PINK_AERCLOUD_PLACED_FEATURE = register("pink_aercloud", AetherConfiguredFeatures.PINK_AERCLOUD, createAercloudPlacements(160, 7));

    public static final Holder<PlacedFeature> SKYROOT_TREE_PLACED_FEATURE = register("skyroot_tree", AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE,
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1), AetherBlocks.SKYROOT_SAPLING.get()));
    public static final Holder<PlacedFeature> GOLDEN_OAK_TREE_PLACED_FEATURE = register("golden_oak_tree", AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE,
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1), AetherBlocks.GOLDEN_OAK_SAPLING.get()));

    public static final Holder<PlacedFeature> FLOWER_PATCH_PLACED_FEATURE = register("flower_patch", AetherConfiguredFeatures.FLOWER_PATCH_CONFIGURED_FEATURE,
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> QUICKSOIL_SHELF_PLACED_FEATURE = register("quicksoil_shelf", AetherConfiguredFeatures.QUICKSOIL_SHELF_CONFIGURED_FEATURE,
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, new ElevationAdjustment(UniformInt.of(-4, -2)), new ElevationFilter(47, 70), BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(BlockPredicate.matchesBlock(AetherBlocks.AETHER_DIRT.get(), BlockPos.ZERO), BlockPredicate.matchesTag(AetherTags.Blocks.HOLYSTONE))));
    // FIXME once Terrain can go above 63 again, change 47 -> 63

    public static final Holder<PlacedFeature> ORE_AETHER_DIRT_PLACED_FEATURE = register("aether_dirt_ore", AetherConfiguredFeatures.ORE_AETHER_DIRT_CONFIGURED_FEATURE,
            commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_ICESTONE_PLACED_FEATURE = register("icestone_ore", AetherConfiguredFeatures.ORE_ICESTONE_CONFIGURED_FEATURE,
            commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_AMBROSIUM_PLACED_FEATURE = register("ambrosium_ore", AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURED_FEATURE,
            commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_ZANITE_PLACED_FEATURE = register("zanite_ore", AetherConfiguredFeatures.ORE_ZANITE_CONFIGURED_FEATURE,
            commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_GRAVITITE_COMMON_PLACED_FEATURE = register("gravitite_ore_common", AetherConfiguredFeatures.ORE_GRAVITITE_COMMON_CONFIGURED_FEATURE,
            commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));
    public static final Holder<PlacedFeature> ORE_GRAVITITE_DENSE_PLACED_FEATURE = register("gravitite_ore_dense", AetherConfiguredFeatures.ORE_GRAVITITE_DENSE_CONFIGURED_FEATURE,
            commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0))));

    // TODO investigate changing this to triangle
    public static List<PlacementModifier> createAercloudPlacements(int height, int chance) {
        return List.of(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(height)),
                InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(chance));
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> placementModifiers) {
        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Aether.MODID, name).toString(), new PlacedFeature(Holder.hackyErase(configuredFeature), List.copyOf(placementModifiers)));
    }

    public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placementModifiers) {
        return register(name, configuredFeature, List.of(placementModifiers));
    }
}
