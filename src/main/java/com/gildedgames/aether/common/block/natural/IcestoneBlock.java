package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.util.FreezingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class IcestoneBlock extends Block implements FreezingBlock
{
	public IcestoneBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		worldIn.scheduleTick(pos, this, 5);
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		this.freezeBlocks(worldIn, pos, state, 2.82842712475f); // Square root of 8
		worldIn.scheduleTick(pos, this, 5);
	}
}
