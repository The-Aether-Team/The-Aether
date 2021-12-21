package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class AetherBushBlock extends BushBlock
{
	public AetherBushBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block.is(AetherTags.Blocks.AETHER_DIRT) || super.mayPlaceOn(state, worldIn, pos);
	}
}
