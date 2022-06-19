package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.builders.AetherNoiseBuilders;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherNoiseGeneratorSettings {
    public static final DeferredRegister<NoiseGeneratorSettings> NOISE_GENERATOR_SETTINGS = DeferredRegister.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, Aether.MODID);

    public static final RegistryObject<NoiseGeneratorSettings> SKYLANDS = NOISE_GENERATOR_SETTINGS.register("skylands", AetherNoiseBuilders::skylandsNoiseSettings);
}
