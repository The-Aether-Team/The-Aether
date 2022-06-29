package com.gildedgames.aether.world.generation.tree;

import com.gildedgames.aether.world.generation.AetherConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SkyrootTree extends AbstractTreeGrower {
	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull RandomSource random, boolean largeHive) {
		return AetherConfiguredFeatures.copy(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION, BuiltinRegistries.CONFIGURED_FEATURE);
	}
}
