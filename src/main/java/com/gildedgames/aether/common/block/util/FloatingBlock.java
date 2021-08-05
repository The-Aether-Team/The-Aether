package com.gildedgames.aether.common.block.util;

import java.util.Random;

import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.block.AbstractBlock;

public class FloatingBlock extends Block
{
	private final boolean powered;
	
	public FloatingBlock(boolean powered, AbstractBlock.Properties properties) {
		super(properties);
		this.powered = powered;
	}

	@Override
	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		worldIn.getBlockTicks().scheduleTick(pos, this, 2);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		if (this.powered) {
			if (worldIn.hasNeighborSignal(pos)) {
				this.floatBlock(worldIn, pos);
			}
		} else {
			if (worldIn.isEmptyBlock(pos.above()) || FallingBlock.isFree(worldIn.getBlockState(pos.above()))) {
				this.floatBlock(worldIn, pos);
			}
		}
		worldIn.getBlockTicks().scheduleTick(pos, this, 2);
	}
	
	private void floatBlock(World worldIn, BlockPos pos) {
		if (!worldIn.isClientSide) {
			FloatingBlockEntity floatingblockentity = new FloatingBlockEntity(worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, worldIn.getBlockState(pos));
			worldIn.removeBlock(pos, false);
			worldIn.addFreshEntity(floatingblockentity);
		}
	}

	public void onLand(World worldIn, BlockPos pos, BlockState blockState, BlockState blockState1, FloatingBlockEntity fallingBlockEntity) { }

	public void onBroken(World worldIn, BlockPos pos, FloatingBlockEntity fallingBlockEntity) { }
}
