package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class AetherNoiseGeneratorSettings {
    public static final ResourceKey<NoiseGeneratorSettings> SKYLAND_GENERATION = register("skyland_generation");

    private static ResourceKey<NoiseGeneratorSettings> register(String key) {
        return ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation(Aether.MODID, key));
    }

    public static void register(ResourceKey<NoiseGeneratorSettings> key, NoiseGeneratorSettings settings) {
        BuiltinRegistries.register(BuiltinRegistries.NOISE_GENERATOR_SETTINGS, key.location(), settings);
    }

    static {
        register(SKYLAND_GENERATION, AetherWorldProvider.aetherNoiseSettings());
    }
}
