package com.gildedgames.aether.block.util;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class AetherDoubleDropsOreBlock extends DropExperienceBlock implements IAetherDoubleDropBlock
{
	public AetherDoubleDropsOreBlock(BlockBehaviour.Properties properties, UniformInt xpRange) {
		super(properties, xpRange);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
}
