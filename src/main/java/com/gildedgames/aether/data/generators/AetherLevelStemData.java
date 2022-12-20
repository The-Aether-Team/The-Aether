package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.data.resources.registries.AetherDimensions;
import com.gildedgames.aether.data.resources.registries.AetherNoiseSettings;
import com.gildedgames.aether.data.resources.builders.AetherBiomeBuilders;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.Map;

public class AetherLevelStemData {
    public static DataProvider create(PackOutput output, ExistingFileHelper helper) {
        HolderLookup.Provider aetherRegistry = AetherWorldGenData.createLookup();
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, aetherRegistry);
        HolderGetter<Biome> biomes = aetherRegistry.lookupOrThrow(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = aetherRegistry.lookupOrThrow(Registries.NOISE_SETTINGS);
        HolderGetter<DimensionType> dimensionTypes = aetherRegistry.lookupOrThrow(Registries.DIMENSION_TYPE);
        BiomeSource source = AetherBiomeBuilders.buildAetherBiomeSource(biomes);
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(source, noiseSettings.getOrThrow(AetherNoiseSettings.SKYLANDS));
        LevelStem levelStem = new LevelStem(dimensionTypes.getOrThrow(AetherDimensions.AETHER_DIMENSION_TYPE), aetherChunkGen);
        Map<ResourceLocation, LevelStem> map = Map.of(AetherDimensions.AETHER_LEVEL_STEM.location(), levelStem);
        ResourceLocation registryId = Registries.LEVEL_STEM.location();
        String registryFolder = registryId.getPath();
        return new JsonCodecProvider<>(output, helper, Aether.MODID, registryOps, PackType.SERVER_DATA, registryFolder, LevelStem.CODEC, map);
    }
}
