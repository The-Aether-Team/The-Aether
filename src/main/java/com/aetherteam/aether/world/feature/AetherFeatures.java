package com.aetherteam.aether.world.feature;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.world.configuration.AercloudConfiguration;
import com.aetherteam.aether.world.configuration.AetherLakeConfiguration;
import com.aetherteam.aether.world.configuration.ShelfConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Aether.MODID);

    public static final Supplier<Feature<ShelfConfiguration>> SHELF = FEATURES.register("shelf", () -> new ShelfFeature(ShelfConfiguration.CODEC));
    public static final Supplier<Feature<AercloudConfiguration>> AERCLOUD = FEATURES.register("aercloud", () -> new AercloudFeature(AercloudConfiguration.CODEC));
    public static final Supplier<Feature<NoneFeatureConfiguration>> CRYSTAL_ISLAND = FEATURES.register("crystal_island", () -> new CrystalIslandFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<AetherLakeConfiguration>> LAKE = FEATURES.register("lake", () -> new AetherLakeFeature(AetherLakeConfiguration.CODEC));
}