package com.aether.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class AetherDungeonFeature extends Feature<NoFeatureConfig> {

	public AetherDungeonFeature(Codec<NoFeatureConfig> codec) {
		super(codec);
	}
}
