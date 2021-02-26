package com.gildedgames.aether.block.util;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;

import net.minecraft.block.AbstractBlock;

public class AetherDoubleDropBlock extends Block implements IAetherDoubleDropBlock
{
	private static final BooleanProperty DOUBLE_DROPS = AetherBlockStateProperties.DOUBLE_DROPS;

	public AetherDoubleDropBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(DOUBLE_DROPS, false));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(DOUBLE_DROPS);
	}
}
