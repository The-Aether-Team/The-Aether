package com.aether.world.gen.feature;

import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class AetherDungeonFeature extends Feature<NoFeatureConfig> {
	
	public AetherDungeonFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}
	
	public static class Decorations {
		
		public static final ContainerBlock CHEST = (ContainerBlock) Blocks.CHEST;
		
	}

}
