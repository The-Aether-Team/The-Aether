package com.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

public class DefaultAetherDoubleDropBlock extends Block implements IAetherDoubleDropBlock {

	public DefaultAetherDoubleDropBlock(Block.Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(DOUBLE_DROPS, false));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(DOUBLE_DROPS);
	}

}
