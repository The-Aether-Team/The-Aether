package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.builders.AetherNoiseBuilders;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class AetherNoiseGeneratorSettings {
    public static final ResourceKey<NoiseGeneratorSettings> SKYLANDS = ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation(Aether.MODID, "skylands"));

    public static void register(ResourceKey<NoiseGeneratorSettings> pKey, NoiseGeneratorSettings pSettings) {
        BuiltinRegistries.register(BuiltinRegistries.NOISE_GENERATOR_SETTINGS, pKey.location(), pSettings);
    }

    public static void init() {
        register(SKYLANDS, AetherNoiseBuilders.skylandsNoiseSettings());
    }
}
