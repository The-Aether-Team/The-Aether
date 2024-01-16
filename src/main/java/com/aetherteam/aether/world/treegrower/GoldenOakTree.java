package com.aetherteam.aether.world.treegrower;

import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import org.jetbrains.annotations.Nullable;

public class GoldenOakTree extends AbstractTreeGrower {
	@Nullable
	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
		return AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION;
	}
}
