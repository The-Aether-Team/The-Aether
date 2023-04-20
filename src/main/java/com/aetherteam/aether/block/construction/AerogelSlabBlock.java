package com.aetherteam.aether.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

public class AerogelSlabBlock extends SlabBlock {
	public AerogelSlabBlock(Properties properties) {
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
	 * Relevant to lighting checks for blocks that aren't full cubes and neighboring blocks.
	 * @param state The {@link BlockState} of the block.
	 * @return Whether to use the shape for light occlusion, as a {@link Boolean}.
	 */
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	/**
	 * Skips rendering for the sides of slab blocks if the neighboring slab matches the position, making the sides not visible when looking through the blocks.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 * @param state The {@link BlockState} of the block.
	 * @param adjacentBlockState The {@link BlockState} of the adjacent block.
	 * @param direction The {@link Direction} of the side to check for rendering.
	 * @return Whether to skip the rendering for a side, as a {@link Boolean}.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction direction) {
        if (adjacentBlockState.is(this)) {
            switch (direction) {
                case UP -> {
                    return state.getValue(TYPE) != SlabType.BOTTOM && adjacentBlockState.getValue(TYPE) != SlabType.TOP;
                }
                case DOWN -> {
                    return state.getValue(TYPE) != SlabType.TOP && adjacentBlockState.getValue(TYPE) != SlabType.BOTTOM;
                }
                default -> {
                    return state.getValue(TYPE) == adjacentBlockState.getValue(TYPE);
                }
            }
        }
		return super.skipRendering(state, adjacentBlockState, direction);
	}
}
