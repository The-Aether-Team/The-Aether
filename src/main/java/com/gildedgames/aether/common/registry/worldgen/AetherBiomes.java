package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.builders.AetherBiomeBuilders;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AetherBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Aether.MODID);

    public static final RegistryObject<Biome> SKYROOT_GROVE = register(AetherBiomes.Keys.SKYROOT_GROVE, AetherBiomeBuilders::skyrootGroveBiome);
    public static final RegistryObject<Biome> SKYROOT_FOREST = register(AetherBiomes.Keys.SKYROOT_FOREST, AetherBiomeBuilders::skyrootForestBiome);
    public static final RegistryObject<Biome> SKYROOT_THICKET = register(AetherBiomes.Keys.SKYROOT_THICKET, AetherBiomeBuilders::skyrootThicketBiome);
    public static final RegistryObject<Biome> GOLDEN_FOREST = register(AetherBiomes.Keys.GOLDEN_FOREST, AetherBiomeBuilders::goldenForestBiome);

    private static RegistryObject<Biome> register(ResourceKey<Biome> biomeResourceKey, Supplier<Biome> biome) {
        return BIOMES.register(biomeResourceKey.location().getPath(), biome);
    }

    public static class Keys {
        public static final ResourceKey<Biome> SKYROOT_GROVE = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, "skyroot_grove"));
        public static final ResourceKey<Biome> SKYROOT_FOREST = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, "skyroot_forest"));
        public static final ResourceKey<Biome> SKYROOT_THICKET = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, "skyroot_thicket"));
        public static final ResourceKey<Biome> GOLDEN_FOREST = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, "golden_forest"));
    }
}
