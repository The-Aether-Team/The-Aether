package com.gildedgames.aether.block.util;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class AetherDoubleDropBlock extends Block implements IAetherDoubleDropBlock
{
	private static final BooleanProperty DOUBLE_DROPS = AetherBlockStateProperties.DOUBLE_DROPS;

	public AetherDoubleDropBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(DOUBLE_DROPS, false));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DOUBLE_DROPS);
	}
}
