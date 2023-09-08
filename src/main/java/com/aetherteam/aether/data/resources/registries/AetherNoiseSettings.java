package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.data.resources.builders.AetherNoiseBuilders;
//import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
//import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class AetherNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> SKYLANDS = createKey("skylands");

    private static ResourceKey<NoiseGeneratorSettings> createKey(String name) {
        return ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation(Aether.MODID, name));
    }

//    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
//        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
//        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
//        context.register(SKYLANDS, AetherNoiseBuilders.skylandsNoiseSettings(densityFunctions, noise));
//    }
}
