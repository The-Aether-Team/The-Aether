package com.gildedgames.aether.core.data;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherFeatures;
import com.gildedgames.aether.core.data.provider.AetherFeatureDataProvider;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherFeatureData {

    public static final ConfiguredFeature COLD_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(16, AetherBlocks.COLD_AERCLOUD.get().defaultBlockState()));

    public static final ConfiguredFeature BLUE_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(8, AetherBlocks.BLUE_AERCLOUD.get().defaultBlockState()));

    public static final ConfiguredFeature GOLDEN_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(4, AetherBlocks.GOLDEN_AERCLOUD.get().defaultBlockState()));

    public static final ConfiguredFeature PINK_AERCLOUD_FEATURE_BASE = AetherFeatures.AERCLOUD.get()
            .configured(AetherFeatureDataProvider.createAercloudConfig(1, AetherBlocks.PINK_AERCLOUD.get().defaultBlockState()));

    public static final PlacedFeature COLD_AERCLOUD_FEATURE = COLD_AERCLOUD_FEATURE_BASE.placed(
            AetherFeatureDataProvider.createAercloudPlacements(128, 5));

    public static final PlacedFeature BLUE_AERCLOUD_FEATURE = BLUE_AERCLOUD_FEATURE_BASE.placed(
            AetherFeatureDataProvider.createAercloudPlacements(96, 5));

    public static final PlacedFeature GOLDEN_AERCLOUD_FEATURE = GOLDEN_AERCLOUD_FEATURE_BASE.placed(
            AetherFeatureDataProvider.createAercloudPlacements(160, 5));

    public static final PlacedFeature PINK_AERCLOUD_FEATURE = PINK_AERCLOUD_FEATURE_BASE.placed(
            AetherFeatureDataProvider.createAercloudPlacements(160, 7));


}
