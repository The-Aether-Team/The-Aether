package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

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
        // It if crashes on .get(), then you've got bigger problems than removing Optional here
        DimensionType dimensionType = registryAccess.registry(Registry.DIMENSION_TYPE_REGISTRY).map(reg -> Registry.register(reg, new ResourceLocation(Aether.MODID, "aether_type"), this.aetherDimensionType())).get();
        NoiseGeneratorSettings worldNoiseSettings = registryAccess.registry(BuiltinRegistries.NOISE_GENERATOR_SETTINGS.key()).map(reg -> Registry.register(reg, new ResourceLocation(Aether.MODID, "skyland_generation"), aetherNoiseSettings())).get();

        //NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(RegistryAccess.builtin().registryOrThrow(Registry.NOISE_REGISTRY), new FixedBiomeSource(AetherBiomeData.FLOATING_FOREST), 0L, () -> worldNoiseSettings);

        //this.serialize(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"), new LevelStem(() -> dimensionType, aetherChunkGen), LevelStem.CODEC);
    }
}