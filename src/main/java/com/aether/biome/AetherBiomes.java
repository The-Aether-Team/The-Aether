package com.aether.biome;

import com.aether.Aether;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherBiomes {

    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, Aether.MODID);

    public static final RegistryObject<Biome> AETHER_VOID = BIOMES.register("aether_void", AetherVoidBiome::new);
    
}
