package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.worldgen.AetherBiomes;
import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import com.gildedgames.aether.common.registry.worldgen.AetherFeatures;
import com.gildedgames.aether.common.world.biome.AetherBiomeBuilders;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import com.gildedgames.aether.core.util.RegistryUtil;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

// Register this class to the data generator to generate world generation pieces
public class AetherWorldData extends AetherWorldProvider {
    public AetherWorldData(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Aether World Data";
    }

    @Override
    protected void dumpRegistries(RegistryAccess registryAccess, HashCache cache, Path path, DynamicOps<JsonElement> dynamicOps) {
        this.dumpRegistry(path, cache, dynamicOps, Registry.LEVEL_STEM_REGISTRY, registerLevelStem(registryAccess), LevelStem.CODEC);
    }

    @Override
    public <E> boolean shouldSerialize(ResourceKey<E> resourceKey, E resource) {
        return Aether.MODID.equals(resourceKey.location().getNamespace());
    }

    @Override
    protected Path resolveTopPath(Path path) {
        return path.resolve("data").resolve(Aether.MODID).resolve("worldgen");
    }
}