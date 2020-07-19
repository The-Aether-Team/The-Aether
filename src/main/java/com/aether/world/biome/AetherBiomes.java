package com.aether.world.biome;

import com.aether.Aether;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherBiomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Aether.MODID);

    public static final RegistryObject<Biome> AETHER_SKYLANDS = BIOMES.register("aether_skylands", AetherSkylandsBiome::new);
    
}
