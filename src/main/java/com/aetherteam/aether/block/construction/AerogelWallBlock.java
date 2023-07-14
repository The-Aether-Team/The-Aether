package com.aetherteam.aether.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AerogelWallBlock extends WallBlock {
	public AerogelWallBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Relevant to lighting checks for blocks that aren't full cubes and neighboring blocks.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 * @param state The {@link BlockState} of the block.
	 * @return Whether to use the shape for light occlusion, as a {@link Boolean}.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	/**
	 * Based on {@link net.minecraft.world.level.block.AbstractGlassBlock#getVisualShape(BlockState, BlockGetter, BlockPos, CollisionContext)}.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}
}
