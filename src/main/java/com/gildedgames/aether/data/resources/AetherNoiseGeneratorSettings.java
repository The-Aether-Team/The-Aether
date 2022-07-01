package com.gildedgames.aether.data.resources;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.data.resources.builders.AetherNoiseBuilders;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.HashMap;
import java.util.Map;

public class AetherNoiseGeneratorSettings {
    public static final Map<ResourceLocation, NoiseGeneratorSettings> NOISE_GENERATOR_SETTINGS = new HashMap<>();

    public static final ResourceKey<NoiseGeneratorSettings> SKYLANDS = register("skylands", AetherNoiseBuilders.skylandsNoiseSettings());

    public static ResourceKey<NoiseGeneratorSettings> register(String name, NoiseGeneratorSettings noiseGeneratorSettings) {
        ResourceLocation location = new ResourceLocation(Aether.MODID, name);
        NOISE_GENERATOR_SETTINGS.putIfAbsent(location, noiseGeneratorSettings);
        return ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, location);
    }
}
