package com.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class AerogelBlock extends BreakableBlock {

	public AerogelBlock(Block.Properties properties) {
		super(properties);
	}
	
	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 3;
	}
	
}
