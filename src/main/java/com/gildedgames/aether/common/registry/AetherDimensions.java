package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class AetherDimensions {

	public static final RegistryKey<DimensionType> AETHER_DIMENSION = RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"));
	public static final RegistryKey<World> AETHER_WORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"));

}
