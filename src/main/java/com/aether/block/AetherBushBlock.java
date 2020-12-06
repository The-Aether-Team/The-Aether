package com.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock;

public class AetherBushBlock extends BushBlock {

	public AetherBushBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == AetherBlocks.AETHER_GRASS_BLOCK
			|| block == AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK
			|| block == AetherBlocks.AETHER_DIRT
			|| super.isValidGround(state, worldIn, pos);
	}
	
}
