package com.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class BerryBushBlock extends AetherBushBlock {
	
	public BerryBushBlock(Block.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
		super.onPlayerDestroy(worldIn, pos, state);
		worldIn.setBlockState(pos, AetherBlocks.BERRY_BUSH_STEM.getDefaultState(), 3);
	}
	
}
