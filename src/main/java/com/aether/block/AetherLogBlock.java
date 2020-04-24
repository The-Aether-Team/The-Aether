package com.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.StateContainer;

public class AetherLogBlock extends LogBlock implements IAetherDoubleDropBlock {

	public AetherLogBlock(MaterialColor verticalColor, Block.Properties properties) {
		super(verticalColor, properties);
		this.setDefaultState(this.getDefaultState().with(DOUBLE_DROPS, false));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(DOUBLE_DROPS);
	}
	
}
