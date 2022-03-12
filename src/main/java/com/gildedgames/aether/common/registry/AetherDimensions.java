package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class AetherDimensions
{
	public static final ResourceKey<DimensionType> AETHER_DIMENSION = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"));
	public static final ResourceKey<Level> AETHER_WORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether")); //todo rename to aether_level

//	public static <E> void register(ResourceKey<DimensionType> key, DimensionType settings) {
//		BuiltinRegistries.register(BuiltinRegistries.ACCESS.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), key.location(), settings);
//	}
//
//	static {
//		register(AETHER_DIMENSION, AetherWorldProvider.aetherDimensionType());
//	}
}
