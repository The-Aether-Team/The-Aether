package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherBiomeProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//TODO: Will likely undergo changes with Drullkus' branch.
public class AetherBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Aether.MODID);

    public static final ResourceKey<Biome> FLOATING_FOREST = register("floating_forest");

    private static ResourceKey<Biome> register(String key) {
        BIOMES.register(key, AetherBiomeProvider::makeEmptyBiome);
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Aether.MODID, key));
    }

    //TODO: Move settings creation to the world DataGen somehow.
//    private static void register(ResourceKey<Biome> key, Biome biome) {
//        BuiltinRegistries.register(BuiltinRegistries.BIOME, key, biome);
//    }
//
//    static {
//        register(FLOATING_FOREST, AetherBiomeProvider.makeDefaultBiome());
//    }
}
