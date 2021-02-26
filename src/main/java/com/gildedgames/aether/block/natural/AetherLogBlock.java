package com.gildedgames.aether.block.natural;

import com.gildedgames.aether.block.util.IAetherDoubleDropBlock;
import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.state.StateContainer;

import net.minecraft.block.AbstractBlock;

public class AetherLogBlock extends RotatedPillarBlock implements IAetherDoubleDropBlock
{
	public AetherLogBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
}
