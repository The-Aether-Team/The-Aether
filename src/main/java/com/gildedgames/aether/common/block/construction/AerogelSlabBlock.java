package com.gildedgames.aether.common.block.construction;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class AerogelSlabBlock extends SlabBlock
{
	public AerogelSlabBlock(Properties properties) {
		super(properties);
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 3;
	}
	
	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}
}
