package com.gildedgames.aether.world.treegrower;

import com.gildedgames.aether.data.resources.AetherConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SkyrootTree extends AbstractTreeGrower {
	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull ServerLevel level, @Nonnull ChunkGenerator chunkGenerator, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull RandomSource random, boolean hasFlowers) {
		return level.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY).getOrCreateHolderOrThrow(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION);
	}

	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull RandomSource random, boolean pLargeHive) {
		return null;
	}
}
