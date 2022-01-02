package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherBiomeProvider;
import net.minecraft.world.level.biome.Biome;

// This class should never be accessed outside DataGen
class AetherBiomeData
{
    final static Biome FLOATING_FOREST = AetherBiomeProvider.makeDefaultBiome().setRegistryName(Aether.MODID, "floating_forest");
}
