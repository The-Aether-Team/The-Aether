package com.gildedgames.aether.common.block.construction;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StairBlock;

import net.minecraft.util.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AerogelStairsBlock extends StairBlock
{
	public AerogelStairsBlock(Supplier<BlockState> state, Properties properties) {
		super(state, properties);
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 3;
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

//	@OnlyIn(Dist.CLIENT)
//	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
//		return adjacentBlockState.isIn(this) || super.isSideInvisible(state, adjacentBlockState, side);
//	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}
}
