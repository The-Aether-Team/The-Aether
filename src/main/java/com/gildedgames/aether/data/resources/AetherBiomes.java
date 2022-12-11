package com.gildedgames.aether.data.resources;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.data.resources.builders.AetherBiomeBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherBiomes {
    public static final ResourceKey<Biome> SKYROOT_GROVE = createKey("skyroot_grove");
    public static final ResourceKey<Biome> SKYROOT_FOREST = createKey("skyroot_forest");
    public static final ResourceKey<Biome> SKYROOT_THICKET = createKey("skyroot_thicket");
    public static final ResourceKey<Biome> GOLDEN_FOREST = createKey("golden_forest");

    public static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Aether.MODID, name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(SKYROOT_GROVE, AetherBiomeBuilders.skyrootGroveBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(SKYROOT_FOREST, AetherBiomeBuilders.skyrootForestBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(SKYROOT_THICKET, AetherBiomeBuilders.skyrootThicketBiome(placedFeatures, vanillaConfiguredCarvers));
        context.register(GOLDEN_FOREST, AetherBiomeBuilders.goldenForestBiome(placedFeatures, vanillaConfiguredCarvers));
    }
}
