package com.gildedgames.aether.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class AetherDimensions {

	public static final RegistryKey<DimensionType> AETHER_DIMENSION = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(Aether.MODID, "aether"));
	public static final RegistryKey<World> AETHER_WORLD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(Aether.MODID, "aether"));

}
