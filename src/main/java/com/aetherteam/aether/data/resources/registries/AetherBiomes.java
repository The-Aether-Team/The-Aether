package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.resources.builders.AetherBiomeBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherBiomes {
    public static final ResourceKey<Biome> SKYROOT_MEADOW = createKey("skyroot_meadow");
    public static final ResourceKey<Biome> SKYROOT_GROVE = createKey("skyroot_grove");
    public static final ResourceKey<Biome> SKYROOT_WOODLAND = createKey("skyroot_woodland");
    public static final ResourceKey<Biome> SKYROOT_FOREST = createKey("skyroot_forest");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(SKYROOT_MEADOW, AetherBiomeBuilders.skyrootMeadowBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(SKYROOT_GROVE, AetherBiomeBuilders.skyrootGroveBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(SKYROOT_WOODLAND, AetherBiomeBuilders.skyrootWoodlandBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(SKYROOT_FOREST, AetherBiomeBuilders.skyrootForestBiome(placedFeatures, vanillaConfiguredCarvers));
    }
}
