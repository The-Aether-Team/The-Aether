package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import com.gildedgames.aether.core.util.RegistryUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Optional;

// Register this class to the data generator to generate world generation pieces
public class AetherWorldData extends AetherWorldProvider {
    public AetherWorldData(DataGenerator generator) {
        super(generator);
    }

    @Override
    public <E> boolean shouldSerialize(ResourceKey<E> resourceKey, E resource) {
        return Aether.MODID.equals(resourceKey.location().getNamespace());
    }

    @Override
    public String getName() {
        return "Aether World Data";
    }

    public void preloadRegistries(RegistryAccess registryAccess) {
        Optional<? extends Registry<Biome>> biomeRegistry = registryAccess.registry(Registry.BIOME_REGISTRY);
        Optional<? extends Registry<DimensionType>> dimensionTypeRegistry = registryAccess.registry(Registry.DIMENSION_TYPE_REGISTRY);
        Optional<? extends Registry<NoiseGeneratorSettings>> noiseGenerator = registryAccess.registry(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
        Optional<? extends Registry<StructureSet>> structureSet = registryAccess.registry(Registry.STRUCTURE_SET_REGISTRY);
        Optional<? extends Registry<NormalNoise.NoiseParameters>> noiseParameters = registryAccess.registry(Registry.NOISE_REGISTRY);

        // It if crashes on .get(), then you've got bigger problems than removing Optional here
        Holder<DimensionType> dimensionType = RegistryUtil.register(dimensionTypeRegistry.get(), "hostile_paradise", aetherDimensionType());
        Holder<NoiseGeneratorSettings> worldNoiseSettings = RegistryUtil.register(noiseGenerator.get(), "skyland_generation", aetherNoiseSettings());

        BiomeSource source = AetherWorldProvider.buildAetherBiomeSource(biomeRegistry.get());

        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(structureSet.get(), noiseParameters.get(), source, 0L, worldNoiseSettings);
    }
}