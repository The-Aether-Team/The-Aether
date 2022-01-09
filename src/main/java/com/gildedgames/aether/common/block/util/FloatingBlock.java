package com.gildedgames.aether.common.block.util;

import java.util.Random;

import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class FloatingBlock extends Block
{
	private final boolean powered;
	
	public FloatingBlock(boolean powered, BlockBehaviour.Properties properties) {
		super(properties);
		this.powered = powered;
	}

	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, worldIn, pos, oldState, isMoving);
		worldIn.scheduleTick(pos, this, this.getDelayAfterPlace());
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
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
		//worldIn.getBlockTicks().scheduleTick(pos, this, 2); //TODO: Not sure what to do with this. Check FallingBlock.
	}
	
	private void floatBlock(Level worldIn, BlockPos pos) {
		if (!worldIn.isClientSide) {
			FloatingBlockEntity floatingblockentity = new FloatingBlockEntity(worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, worldIn.getBlockState(pos));
			worldIn.removeBlock(pos, false);
			worldIn.addFreshEntity(floatingblockentity);
		}
	}

	public void onLand(Level worldIn, BlockPos pos, BlockState blockState, BlockState blockState1, FloatingBlockEntity fallingBlockEntity) { }

	public void onBroken(Level worldIn, BlockPos pos, FloatingBlockEntity fallingBlockEntity) { }

	protected int getDelayAfterPlace() {
		return 2;
	}
}
