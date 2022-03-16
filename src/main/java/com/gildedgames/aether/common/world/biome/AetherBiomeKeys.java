package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class AetherBiomeKeys {
    public static final ResourceKey<Biome> SKYROOT_GROVE = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, "skyroot_grove"));
    public static final ResourceKey<Biome> SKYROOT_FOREST = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, "skyroot_forest"));
    public static final ResourceKey<Biome> SKYROOT_THICKET = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, "skyroot_thicket"));
    public static final ResourceKey<Biome> GOLDEN_FOREST = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, "golden_forest"));
}
