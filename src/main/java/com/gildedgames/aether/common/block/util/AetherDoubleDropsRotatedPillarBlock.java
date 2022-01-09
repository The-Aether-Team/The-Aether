package com.gildedgames.aether.common.block.util;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AetherDoubleDropsRotatedPillarBlock extends RotatedPillarBlock implements IAetherDoubleDropBlock
{
	public AetherDoubleDropsRotatedPillarBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
}
