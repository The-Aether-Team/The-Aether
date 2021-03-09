package com.gildedgames.aether.common.block.construction;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AerogelWallBlock extends WallBlock
{
	public AerogelWallBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}

//	@OnlyIn(Dist.CLIENT)
//	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
//		return adjacentBlockState.isIn(this) || super.isSideInvisible(state, adjacentBlockState, side);
//	}

	@Override
	public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}
}
