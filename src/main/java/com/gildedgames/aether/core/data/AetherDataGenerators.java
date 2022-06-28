package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import com.gildedgames.aether.common.registry.worldgen.AetherNoiseGeneratorSettings;
import com.gildedgames.aether.common.world.builders.AetherBiomeBuilders;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.HashMap;
import java.util.Map;

public class AetherDataGenerators<T> {
    public static RegistryAccess DATA_REGISTRY = RegistryAccess.builtinCopy();

    public DataProvider create(DataGenerator generator, ExistingFileHelper helper, Map<ResourceLocation, T> map, ResourceKey<Registry<T>> registryKey) {
        return JsonCodecProvider.forDatapackRegistry(generator, helper, Aether.MODID, RegistryOps.create(JsonOps.INSTANCE, DATA_REGISTRY), registryKey, map);
    }

    public DataProvider create(RegistryAccess registryAccess, DataGenerator generator, ExistingFileHelper helper, Map<ResourceLocation, T> map, ResourceKey<Registry<T>> registryKey) {
        return JsonCodecProvider.forDatapackRegistry(generator, helper, Aether.MODID, RegistryOps.create(JsonOps.INSTANCE, registryAccess), registryKey, map);
    }

    public DataProvider levelStem(DataGenerator generator, ExistingFileHelper helper) {
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, BuiltinRegistries.ACCESS);
        Map<ResourceLocation, LevelStem> map = new HashMap<>();
        Registry<Biome> biomeRegistry = registryOps.registry(Registry.BIOME_REGISTRY).orElseThrow();
        Registry<DimensionType> dimensionTypeRegistry = registryOps.registry(Registry.DIMENSION_TYPE_REGISTRY).orElseThrow();
        Registry<StructureSet> structureSetRegistry = registryOps.registry(Registry.STRUCTURE_SET_REGISTRY).orElseThrow();
        Registry<NormalNoise.NoiseParameters> noiseParametersRegistry = registryOps.registry(Registry.NOISE_REGISTRY).orElseThrow();
        Registry<NoiseGeneratorSettings> noiseGeneratorSettingsRegistry = registryOps.registry(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY).orElseThrow();
        BiomeSource source = AetherBiomeBuilders.buildAetherBiomeSource(biomeRegistry);
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(structureSetRegistry, noiseParametersRegistry, source, AetherNoiseGeneratorSettings.getHolder(AetherNoiseGeneratorSettings.SKYLANDS, noiseGeneratorSettingsRegistry));
        LevelStem levelStem = new LevelStem(AetherDimensions.getHolder(AetherDimensions.AETHER_DIMENSION_TYPE, dimensionTypeRegistry), aetherChunkGen);
        map.put(AetherDimensions.AETHER_LEVEL_STEM.location(), levelStem);
        final ResourceLocation registryId = Registry.LEVEL_STEM_REGISTRY.location();
        final String registryFolder = registryId.getPath();
        return new JsonCodecProvider<>(generator, helper, Aether.MODID, registryOps, PackType.SERVER_DATA, registryFolder, LevelStem.CODEC, map);
    }
}
