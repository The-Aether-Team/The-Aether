package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.common.accessor.SpreadingSnowyDirtBlockAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.function.BiConsumer;

public class AetherGrassBlock extends GrassBlock {
	public AetherGrassBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}

	@Override
	public boolean onTreeGrow(BlockState state, LevelReader level, BiConsumer<BlockPos, BlockState> placeFunction, RandomSource randomSource, BlockPos pos, TreeConfiguration config) {
		placeFunction.accept(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)));
		return true;
	}

	/**
	 * Based on {@link net.minecraft.world.level.block.SpreadingSnowyDirtBlock#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}.<br><br>
	 * Warning for "deprecation" is suppressed due to being copied from what Forge does.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!SpreadingSnowyDirtBlockAccessor.callCanBeGrass(state, level, pos)) {
			if (!level.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			level.setBlockAndUpdate(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState());
		} else {
			if (!level.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
				BlockState blockstate = this.defaultBlockState();
				for(int i = 0; i < 4; ++i) {
					BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (level.getBlockState(blockpos).is(AetherBlocks.AETHER_DIRT.get()) && SpreadingSnowyDirtBlockAccessor.callCanPropagate(blockstate, level, blockpos)) {
						level.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, level.getBlockState(blockpos.above()).is(Blocks.SNOW)));
					}
				}
			}
		}
	}
}
