package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.util.IIcestoneBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ticks.ScheduledTick;

import java.util.Random;

public class IcestoneBlock extends Block implements IIcestoneBlock
{
	public IcestoneBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		ScheduledTick<Block> scheduledTick = new ScheduledTick<>(this, pos, 5, 0);
		worldIn.getBlockTicks().schedule(scheduledTick);
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		freezeFluids(worldIn, pos);
		ScheduledTick<Block> scheduledTick = new ScheduledTick<>(this, pos, 5, 0);
		worldIn.getBlockTicks().schedule(scheduledTick);
	}
}
