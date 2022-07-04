package com.gildedgames.aether.world.treegrower;

import com.gildedgames.aether.data.resources.AetherConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

import javax.annotation.Nullable;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SkyrootTree extends LevelTreeGrower {
	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(ServerLevel serverLevel, RandomSource randomSource, boolean largeHive) {
		return serverLevel.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY).getOrCreateHolderOrThrow(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION);
	}
}
