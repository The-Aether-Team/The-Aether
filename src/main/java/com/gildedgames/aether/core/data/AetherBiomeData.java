package com.gildedgames.aether.core.data;

import com.gildedgames.aether.common.registry.AetherBiomes;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome;

// This class should never be accessed outside DataGen
class AetherBiomeData
{
    final static Holder<Biome> FLOATING_FOREST = BuiltinRegistries.BIOME.getHolderOrThrow(AetherBiomes.FLOATING_FOREST);
}
