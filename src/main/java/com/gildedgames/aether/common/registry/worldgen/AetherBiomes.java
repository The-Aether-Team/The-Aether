package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.biome.AetherBiomeKeys;
import com.gildedgames.aether.common.world.biome.AetherBiomeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AetherBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Aether.MODID);

    public static final RegistryObject<Biome> SKYROOT_GROVE = register(AetherBiomeKeys.SKYROOT_GROVE, AetherBiomeBuilder::skyrootGrove);
    public static final RegistryObject<Biome> SKYROOT_FOREST = register(AetherBiomeKeys.SKYROOT_FOREST, AetherBiomeBuilder::skyrootForest);
    public static final RegistryObject<Biome> SKYROOT_THICKET = register(AetherBiomeKeys.SKYROOT_THICKET, AetherBiomeBuilder::skyrootThicket);
    public static final RegistryObject<Biome> GOLDEN_FOREST = register(AetherBiomeKeys.GOLDEN_FOREST, AetherBiomeBuilder::goldenForest);

    private static RegistryObject<Biome> register(ResourceKey<Biome> biomeResourceKey, Supplier<Biome> biome) {
        return BIOMES.register(biomeResourceKey.location().getPath(), biome);
    }
}
