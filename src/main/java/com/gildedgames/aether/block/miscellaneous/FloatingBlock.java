package com.gildedgames.aether.block.miscellaneous;

import com.gildedgames.aether.block.Floatable;
import com.gildedgames.aether.entity.block.FloatingBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.material.Material;

/**
 * Based on {@link net.minecraft.world.level.block.FallingBlock}.
 */
public class FloatingBlock extends Block implements Floatable {
	private final boolean powered;
	
	public FloatingBlock(boolean powered, Properties properties) {
		super(properties);
		this.powered = powered;
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		level.scheduleTick(pos, this, this.getDelayAfterPlace());
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
		level.scheduleTick(pos, this, this.getDelayAfterPlace());
		return super.updateShape(state, direction, facingState, level, pos, facingPos);
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(state, level, pos, random);
		if (((this.powered && level.hasNeighborSignal(pos)) || (!this.powered && pos.getY() <= level.getMaxBuildHeight())) && isFree(level.getBlockState(pos.above()))) {
			FloatingBlockEntity floatingBlockEntity = new FloatingBlockEntity(level, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, level.getBlockState(pos));
			if (this.powered) {
				floatingBlockEntity.setNatural(false);
			}
			level.addFreshEntity(floatingBlockEntity);
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			this.floating(floatingBlockEntity);
		} else {
			level.scheduleTick(pos, this, this.getDelayAfterPlace());
		}
	}

	protected void floating(FloatingBlockEntity entity) { }

	public static boolean isFree(BlockState state) {
		Material material = state.getMaterial();
		return state.isAir() || state.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
	}

	protected int getDelayAfterPlace() {
		return 2;
	}
}
