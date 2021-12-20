package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.util.IIcestoneBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class IcestoneBlock extends Block implements IIcestoneBlock
{
	public IcestoneBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		worldIn.getBlockTicks().scheduleTick(pos, this, 5);
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		freezeFluids(worldIn, pos);
		worldIn.getBlockTicks().scheduleTick(pos, this, 5);
	}
}
