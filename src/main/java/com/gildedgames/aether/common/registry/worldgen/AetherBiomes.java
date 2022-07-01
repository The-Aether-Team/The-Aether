package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.builders.AetherBiomeBuilders;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class AetherBiomes {
    public static final Map<ResourceLocation, Biome> BIOMES = new HashMap<>();

    public static final ResourceKey<Biome> SKYROOT_GROVE = register("skyroot_grove", AetherBiomeBuilders.skyrootGroveBiome());
    public static final ResourceKey<Biome> SKYROOT_FOREST = register("skyroot_forest", AetherBiomeBuilders.skyrootForestBiome());
    public static final ResourceKey<Biome> SKYROOT_THICKET = register("skyroot_thicket", AetherBiomeBuilders.skyrootThicketBiome());
    public static final ResourceKey<Biome> GOLDEN_FOREST = register("golden_forest", AetherBiomeBuilders.goldenForestBiome());

    public static ResourceKey<Biome> register(String name, Biome biome) {
        ResourceLocation location = new ResourceLocation(Aether.MODID, name);
        BIOMES.putIfAbsent(location, biome);
        return ResourceKey.create(Registry.BIOME_REGISTRY, location);
    }

    public static Holder<Biome> getHolder(ResourceKey<Biome> key, Registry<Biome> registry) {
        Biome biome = BIOMES.get(key.location());
        Holder.Reference<Biome> biomeHolder = (Holder.Reference<Biome>) registry.getOrCreateHolderOrThrow(key);
        biomeHolder.bind(key, biome);
        return biomeHolder;
    }
}
