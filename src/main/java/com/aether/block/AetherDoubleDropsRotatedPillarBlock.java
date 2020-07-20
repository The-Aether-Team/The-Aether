package com.aether.block;

import com.aether.block.state.properties.AetherBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.state.StateContainer;

public class AetherDoubleDropsRotatedPillarBlock extends RotatedPillarBlock {

	public AetherDoubleDropsRotatedPillarBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}

}
