package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.util.IIcestoneBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class IcestoneBlock extends Block implements IIcestoneBlock
{
	public IcestoneBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		worldIn.getBlockTicks().scheduleTick(pos, this, 10);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		freezeFluids(worldIn, pos);
		worldIn.getBlockTicks().scheduleTick(pos, this, 10);
	}
}
