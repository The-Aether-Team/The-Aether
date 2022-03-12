package com.gildedgames.aether.common.world.gen.tree;

import com.gildedgames.aether.common.registry.AetherFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SkyrootTree extends AbstractTreeGrower {
	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean largeHive) {
		return AetherFeatures.Features.SKYROOT_TREE_CONFIGURED_FEATURE;
	}
}
