package com.gildedgames.aether.block.util;

import com.gildedgames.aether.entity.block.FloatingBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public class FloatingBlock extends Block implements Floatable
{
	private final boolean powered;
	
	public FloatingBlock(boolean powered, BlockBehaviour.Properties properties) {
		super(properties);
		this.powered = powered;
	}

	@Override
	public void onPlace(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {
		super.onPlace(state, level, pos, oldState, isMoving);
		level.scheduleTick(pos, this, this.getDelayAfterPlace());
	}

	@Nonnull
	@Override
	public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos pos, @Nonnull BlockPos facingPos) {
		level.scheduleTick(pos, this, this.getDelayAfterPlace());
		return super.updateShape(state, facing, facingState, level, pos, facingPos);
	}

	public void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		super.tick(state, level, pos, random);
		if ((this.powered && level.hasNeighborSignal(pos)) || (!this.powered && isFree(level.getBlockState(pos.above())) && pos.getY() <= level.getMaxBuildHeight())) {
			FloatingBlockEntity floatingBlockEntity = new FloatingBlockEntity(level, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, level.getBlockState(pos));
			level.addFreshEntity(floatingBlockEntity);
		} else {
			level.scheduleTick(pos, this, this.getDelayAfterPlace());
		}
	}

	public static boolean isFree(BlockState state) {
		Material material = state.getMaterial();
		return state.isAir() || state.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
	}

	protected int getDelayAfterPlace() {
		return 2;
	}
}
