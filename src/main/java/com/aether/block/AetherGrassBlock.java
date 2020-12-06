package com.aether.block;

import com.aether.block.state.properties.AetherBlockStateProperties;
import com.aether.tags.AetherBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import net.minecraft.block.AbstractBlock;

public class AetherGrassBlock extends GrassBlock implements IAetherDoubleDropBlock {
	public static final BooleanProperty DOUBLE_DROPS = AetherBlockStateProperties.DOUBLE_DROPS;

	public AetherGrassBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(DOUBLE_DROPS, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(DOUBLE_DROPS);
	}
	
	@Override
	public void onPlantGrow(BlockState state, IWorld world, BlockPos pos, BlockPos source) {
		if (state.isIn(AetherBlockTags.AETHER_DIRT)) {
			world.setBlockState(pos, state.get(AetherBlockStateProperties.DOUBLE_DROPS)? AetherBlocks.AETHER_DIRT.getDefaultState().with(AetherBlockStateProperties.DOUBLE_DROPS, state.get(AetherBlockStateProperties.DOUBLE_DROPS)) : AetherBlocks.AETHER_DIRT.getDefaultState(), 2);
		}
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (!isSnowyConditions(state, worldIn, pos)) {
			if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			worldIn.setBlockState(pos, AetherBlocks.AETHER_DIRT.getDefaultState());
		} else {
			if (worldIn.getLight(pos.up()) >= 9) {
				BlockState blockstate = this.getDefaultState();

				for(int i = 0; i < 4; ++i) {
					BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (worldIn.getBlockState(blockpos).isIn(AetherBlocks.AETHER_DIRT) && isSnowyAndNotUnderwater(blockstate, worldIn, blockpos)) {
						worldIn.setBlockState(blockpos, blockstate.with(SNOWY, worldIn.getBlockState(blockpos.up()).isIn(Blocks.SNOW)));
					}
				}
			}

		}
	}

}
