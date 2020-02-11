package com.aether.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BerryBushStemBlock extends AetherBushBlock implements IGrowable {
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

	public BerryBushStemBlock(Block.Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		if (!worldIn.isAreaLoaded(pos, 1)) {
			return;
		}

		if (worldIn.getLight(pos.up()) >= 9 && random.nextInt(60) == 0) {
			this.grow(worldIn, random, pos, state);
		}
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return rand.nextFloat() < 0.45f;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
		worldIn.setBlockState(pos, AetherBlocks.BERRY_BUSH.getDefaultState());
	}

}
