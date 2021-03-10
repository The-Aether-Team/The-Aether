package com.gildedgames.aether.common.block.util;

import java.util.Random;

import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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
		worldIn.getBlockTicks().scheduleTick(pos, this, 2);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		worldIn.getBlockTicks().scheduleTick(currentPos, this, 2);
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		this.checkFloatable(worldIn, pos);
	}
	
	private void checkFloatable(World worldIn, BlockPos pos) {
		if ((worldIn.isEmptyBlock(pos.above()) || FallingBlock.isFree(worldIn.getBlockState(pos.above()))) && (!this.powered || worldIn.hasNeighborSignal(pos))) {
			if (!worldIn.isClientSide) {
				FloatingBlockEntity floatingblockentity = new FloatingBlockEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, worldIn.getBlockState(pos));
				this.onStartFloating(floatingblockentity);
				worldIn.addFreshEntity(floatingblockentity);
			}
		}
	}
	
	protected void onStartFloating(FloatingBlockEntity floatingEntity) {}
	
	public void onEndFloating(World worldIn, BlockPos pos, BlockState floatingState, BlockState hitState) {}
	
	public void onBroken(World worldIn, BlockPos pos) {}
}
