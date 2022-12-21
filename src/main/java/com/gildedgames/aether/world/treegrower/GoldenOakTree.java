package com.gildedgames.aether.world.treegrower;

import com.gildedgames.aether.data.resources.registries.AetherConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GoldenOakTree extends AbstractTreeGrower {
	@Nullable
	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
		return AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION;
	}
}
