package com.gildedgames.aether.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

public class AerogelSlabBlock extends SlabBlock
{
	public AerogelSlabBlock(Properties properties) {
		super(properties);
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 3;
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
    @SuppressWarnings("deprecation")
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.is(this)) {
            switch (side) {
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
		return super.skipRendering(state, adjacentBlockState, side);
	}
}
