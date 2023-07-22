package com.aetherteam.aether.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AerogelBlock extends HalfTransparentBlock {
	public AerogelBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Determines the amount of light this will block.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 * @param state The {@link BlockState} of the block.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @return The {@link Integer} of how many light levels are blocked, plus 2 extra by default.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return 3;
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
	 * [VANILLA COPY] - {@link net.minecraft.world.level.block.AbstractGlassBlock#getVisualShape(BlockState, BlockGetter, BlockPos, CollisionContext)}.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}
}
