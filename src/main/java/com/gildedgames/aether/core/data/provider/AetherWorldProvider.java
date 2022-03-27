package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import com.gildedgames.aether.common.registry.worldgen.AetherNoiseGeneratorSettings;
import com.gildedgames.aether.common.world.builders.AetherBiomeBuilders;
import com.gildedgames.aether.common.world.builders.AetherDimensionBuilders;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.nio.file.Path;

public abstract class AetherWorldProvider extends WorldProvider {
    public AetherWorldProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void run(HashCache cache) {
        Path path = this.generator.getOutputFolder();
        RegistryAccess registryAccess = RegistryAccess.BUILTIN.get();
        DynamicOps<JsonElement> dynamicOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        RegistryAccess.knownRegistries().forEach(registryData -> this.dumpRegistryCap(cache, path, registryAccess, dynamicOps, registryData));
        this.dumpRegistries(registryAccess, cache, path, dynamicOps);
    }

    protected abstract void dumpRegistries(RegistryAccess registryAccess, HashCache cache, Path path, DynamicOps<JsonElement> dynamicOps);

    protected void registerDimensionType(HashCache cache, Path path, DynamicOps<JsonElement> dynamicOps) {
        WritableRegistry<DimensionType> writableRegistry = new MappedRegistry<>(Registry.DIMENSION_TYPE_REGISTRY, Lifecycle.experimental(), null);
        writableRegistry.register(AetherDimensions.AETHER_DIMENSION_TYPE, AetherDimensionBuilders.aetherDimensionType(), Lifecycle.stable());
        this.dumpRegistry(path, cache, dynamicOps, Registry.DIMENSION_TYPE_REGISTRY, writableRegistry, DimensionType.DIRECT_CODEC);
    }

    protected void registerLevelStem(RegistryAccess registryAccess, HashCache cache, Path path, DynamicOps<JsonElement> dynamicOps) {
        WritableRegistry<LevelStem> writableRegistry = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental(), null);
        Registry<DimensionType> dimensionTypeRegistry = registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
        Registry<StructureSet> structureSetRegistry = registryAccess.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY);
        Registry<NoiseGeneratorSettings> noiseGeneratorSettingsRegistry = registryAccess.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
        Registry<NormalNoise.NoiseParameters> noiseParametersRegistry = registryAccess.registryOrThrow(Registry.NOISE_REGISTRY);

        Holder.Reference<DimensionType> dimensionType = Holder.Reference.createStandAlone(dimensionTypeRegistry, AetherDimensions.AETHER_DIMENSION_TYPE);
        dimensionType.bind(AetherDimensions.AETHER_DIMENSION_TYPE, AetherDimensionBuilders.aetherDimensionType());
        Holder<NoiseGeneratorSettings> worldNoiseSettings = noiseGeneratorSettingsRegistry.getHolderOrThrow(AetherNoiseGeneratorSettings.SKYLANDS);

        BiomeSource source = AetherBiomeBuilders.buildAetherBiomeSource(biomeRegistry);
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(structureSetRegistry, noiseParametersRegistry, source, 0L, worldNoiseSettings);

        writableRegistry.register(AetherDimensions.AETHER_LEVEL_STEM, new LevelStem(dimensionType, aetherChunkGen, true), Lifecycle.stable());
        this.dumpRegistry(path, cache, dynamicOps, Registry.LEVEL_STEM_REGISTRY, writableRegistry, LevelStem.CODEC);
    }
}
