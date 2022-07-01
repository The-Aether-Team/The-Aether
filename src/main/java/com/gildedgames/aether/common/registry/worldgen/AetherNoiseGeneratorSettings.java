package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.builders.AetherNoiseBuilders;
import net.minecraft.core.Holder;
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

    public static Holder<NoiseGeneratorSettings> getHolder(ResourceKey<NoiseGeneratorSettings> key, Registry<NoiseGeneratorSettings> registry) {
        NoiseGeneratorSettings noiseGeneratorSettings = NOISE_GENERATOR_SETTINGS.get(key.location());
        Holder.Reference<NoiseGeneratorSettings> noiseGeneratorSettingsHolder = (Holder.Reference<NoiseGeneratorSettings>) registry.getOrCreateHolderOrThrow(key);
        noiseGeneratorSettingsHolder.bind(key, noiseGeneratorSettings);
        return noiseGeneratorSettingsHolder;
    }
}
