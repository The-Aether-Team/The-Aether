package com.gildedgames.aether.common.world.gen.tree;

import com.gildedgames.aether.common.registry.worldgen.AetherFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

import javax.annotation.Nullable;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GoldenOakTree extends AbstractTreeGrower {
	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
		return AetherFeatures.ConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE;
	}
}
