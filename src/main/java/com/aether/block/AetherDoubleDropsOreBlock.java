package com.aether.block;

import com.aether.block.state.properties.AetherBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

public class AetherDoubleDropsOreBlock extends AetherOreBlock implements IAetherDoubleDropBlock {
	
	public AetherDoubleDropsOreBlock(int minExpDropped, int maxExpDropped, Block.Properties properties) {
		super(minExpDropped, maxExpDropped, properties);
		this.setDefaultState(this.getDefaultState().with(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
	
}
