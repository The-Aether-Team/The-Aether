package com.aether.world;

import com.aether.biome.AetherBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;

public class AetherBiomeProviderSettings implements IBiomeProviderSettings {
    private Biome biome;

    public AetherBiomeProviderSettings() {
        this.biome = AetherBiomes.AETHER_VOID.get();
    }

    public Biome getBiome() {
        return this.biome;
    }
}
