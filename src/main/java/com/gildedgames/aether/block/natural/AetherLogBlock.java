package com.gildedgames.aether.block.natural;

import com.gildedgames.aether.block.util.IAetherDoubleDropBlock;
import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class AetherLogBlock extends RotatedPillarBlock implements IAetherDoubleDropBlock
{
	public AetherLogBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
}
