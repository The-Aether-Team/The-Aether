package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.common.accessor.SpreadingSnowyDirtBlockAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.function.BiConsumer;

public class EnchantedAetherGrassBlock extends GrassBlock {
	public EnchantedAetherGrassBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onTreeGrow(BlockState state, LevelReader level, BiConsumer<BlockPos, BlockState> placeFunction, RandomSource randomSource, BlockPos pos, TreeConfiguration config) {
		placeFunction.accept(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState());
		return true;
	}

	/**
	 * Based on part of {@link net.minecraft.world.level.block.SpreadingSnowyDirtBlock#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}.<br><br>
	 * Warning for "deprecation" is suppressed due to being copied from what Forge does.
	 */
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!SpreadingSnowyDirtBlockAccessor.callCanBeGrass(state, level, pos)) {
			if (!level.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			level.setBlockAndUpdate(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState());
		}
	}
}
