package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class AetherWorldData extends AetherWorldProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create(); // If desired, custom formatting rules can be set up here

    public AetherWorldData(DataGenerator generator) {
        super(generator, JsonOps.INSTANCE, GSON::toJson);
    }

    @Override
    public String getName() {
        return "Aether World Data";
    }

    @Override
    public void generate(RegistryAccess registryAccess) {
        // It if crashes on .get(), then you've got bigger problems than removing Optional here
        DimensionType dimensionType = registryAccess.registry(Registry.DIMENSION_TYPE_REGISTRY).map(reg -> Registry.register(reg, new ResourceLocation(Aether.MODID, "aether_type"), this.aetherDimensionType())).get();
        NoiseGeneratorSettings worldNoiseSettings = registryAccess.registry(BuiltinRegistries.NOISE_GENERATOR_SETTINGS.key()).map(reg -> Registry.register(reg, new ResourceLocation(Aether.MODID, "skyland_generation"), this.aetherNoiseSettings())).get();

        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(RegistryAccess.builtin().registryOrThrow(Registry.NOISE_REGISTRY), new FixedBiomeSource(AetherBiomeData.FLOATING_FOREST), 0L, () -> worldNoiseSettings);

        this.serialize(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"), new LevelStem(() -> dimensionType, aetherChunkGen), LevelStem.CODEC);
    }
}