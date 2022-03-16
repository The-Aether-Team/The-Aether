package com.gildedgames.aether.common.world.gen.tree;

import com.gildedgames.aether.common.registry.worldgen.AetherConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GoldenOakTree extends AbstractTreeGrower {
	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean largeHive) {
		return AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE;
	}
}
