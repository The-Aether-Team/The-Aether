package com.gildedgames.aether.block.construction;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class AerogelStairsBlock extends StairsBlock
{
	public AerogelStairsBlock(Supplier<BlockState> state, Properties properties) {
		super(state, properties);
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
