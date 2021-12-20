package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.Level;

public class AetherDimensions
{
	public static final ResourceKey<DimensionType> AETHER_DIMENSION = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"));
	public static final ResourceKey<Level> AETHER_WORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"));
}
