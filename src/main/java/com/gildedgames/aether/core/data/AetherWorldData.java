package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.worldgen.AetherBiomes;
import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import com.gildedgames.aether.common.world.biome.AetherBiomeBuilder;
import com.gildedgames.aether.common.world.biome.AetherBiomeKeys;
import com.gildedgames.aether.common.world.feature.FeatureBuilders;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import com.gildedgames.aether.core.util.RegistryUtil;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.registries.RegistryObject;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

// Register this class to the data generator to generate world generation pieces
public class AetherWorldData extends AetherWorldProvider {
    public AetherWorldData(DataGenerator generator) {
        super(generator);
    }

    @Override
    public <E> boolean shouldSerialize(ResourceKey<E> resourceKey, E resource) {
        return Aether.MODID.equals(resourceKey.location().getNamespace());
    }

    //TODO: Override path so this isnt generated in the reports/ folder.

    @Override
    public String getName() {
        return "Aether World Data";
    }

    @Override
    public void run(HashCache cache) {
        Path path = this.generator.getOutputFolder();
        RegistryAccess registryAccess = RegistryAccess.BUILTIN.get();

        Registry<LevelStem> levelStemRegistry = getLevel(registryAccess);
        //Registry<Biome> biomeRegistry = getBiomes(registryAccess);

        DynamicOps<JsonElement> dynamicOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);
        RegistryAccess.knownRegistries().forEach(registryData -> this.dumpRegistryCap(cache, path, registryAccess, dynamicOps, registryData));
        //this.dumpRegistry(path, cache, dynamicOps, Registry.BIOME_REGISTRY, biomeRegistry, Biome.DIRECT_CODEC);
        this.dumpRegistry(path, cache, dynamicOps, Registry.LEVEL_STEM_REGISTRY, levelStemRegistry, LevelStem.CODEC);
    }

    public Registry<LevelStem> getLevel(RegistryAccess registryAccess) {
        WritableRegistry<LevelStem> writableRegistry = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental(), null);
        Registry<DimensionType> dimensionTypeRegistry = registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
        Registry<StructureSet> structureRegistry = registryAccess.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY);
        Registry<NoiseGeneratorSettings> noiseGeneratorRegistry = registryAccess.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
        Registry<NormalNoise.NoiseParameters> noiseParametersRegistry = registryAccess.registryOrThrow(Registry.NOISE_REGISTRY);

        Holder<DimensionType> dimensionType = RegistryUtil.register(dimensionTypeRegistry, "hostile_paradise", aetherDimensionType());
        BiomeSource source = AetherWorldProvider.buildAetherBiomeSource(biomeRegistry);
        Holder<NoiseGeneratorSettings> worldNoiseSettings = RegistryUtil.register(noiseGeneratorRegistry, "skyland_generation", aetherNoiseSettings());
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(structureRegistry, noiseParametersRegistry, source, 0L, worldNoiseSettings);

        writableRegistry.register(AetherDimensions.AETHER_WORLD_STEM, new LevelStem(dimensionType, aetherChunkGen), Lifecycle.stable());
        return writableRegistry;

    }

//    public Registry<Biome> getBiomes(RegistryAccess registryAccess) {
//        WritableRegistry<Biome> writableRegistry = new MappedRegistry<>(Registry.BIOME_REGISTRY, Lifecycle.experimental(), null);
//        writableRegistry.register(AetherBiomeKeys.SKYROOT_GROVE, AetherBiomeBuilder.skyrootGrove(), Lifecycle.stable());
//        writableRegistry.register(AetherBiomeKeys.SKYROOT_FOREST, AetherBiomeBuilder.skyrootForest(), Lifecycle.stable());
//        writableRegistry.register(AetherBiomeKeys.SKYROOT_THICKET, AetherBiomeBuilder.skyrootThicket(), Lifecycle.stable());
//        writableRegistry.register(AetherBiomeKeys.GOLDEN_FOREST, AetherBiomeBuilder.goldenForest(), Lifecycle.stable());
//
////        Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
////        Holder<Biome> skyrootGrove = RegistryUtil.register(biomeRegistry, "skyroot_grove", AetherBiomeBuilder.skyrootGrove());
////        Holder<Biome> skyrootForest = RegistryUtil.register(biomeRegistry, "skyroot_forest", AetherBiomeBuilder.skyrootForest());
////        Holder<Biome> skyrootThicket = RegistryUtil.register(biomeRegistry, "skyroot_thicket", AetherBiomeBuilder.skyrootThicket());
////        Holder<Biome> goldenForest = RegistryUtil.register(biomeRegistry, "golden_forest", AetherBiomeBuilder.goldenForest());
//
//        return writableRegistry;
//    }
}