package com.gildedgames.aether.common.block.util;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.state.StateContainer;

import net.minecraft.block.AbstractBlock.Properties;

public class AetherDoubleDropsRotatedPillarBlock extends RotatedPillarBlock implements IAetherDoubleDropBlock
{
	public AetherDoubleDropsRotatedPillarBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
}
