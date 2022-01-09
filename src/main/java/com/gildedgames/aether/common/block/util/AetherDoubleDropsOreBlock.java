package com.gildedgames.aether.common.block.util;

import com.gildedgames.aether.common.block.natural.AetherOreBlock;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class AetherDoubleDropsOreBlock extends AetherOreBlock implements IAetherDoubleDropBlock
{
	public AetherDoubleDropsOreBlock(int minExpDropped, int maxExpDropped, BlockBehaviour.Properties properties) {
		super(minExpDropped, maxExpDropped, properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
}
