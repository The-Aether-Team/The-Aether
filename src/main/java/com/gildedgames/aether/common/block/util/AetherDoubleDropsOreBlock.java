package com.gildedgames.aether.common.block.util;

import com.gildedgames.aether.common.block.natural.AetherOreBlock;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

import net.minecraft.block.AbstractBlock;

public class AetherDoubleDropsOreBlock extends AetherOreBlock implements IAetherDoubleDropBlock
{
	public AetherDoubleDropsOreBlock(int minExpDropped, int maxExpDropped, AbstractBlock.Properties properties) {
		super(minExpDropped, maxExpDropped, properties);
		this.setDefaultState(this.getDefaultState().with(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
}
