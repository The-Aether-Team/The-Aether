package com.aether.block.construction;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class AerogelWallBlock extends WallBlock
{
	public AerogelWallBlock(Properties properties) {
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
