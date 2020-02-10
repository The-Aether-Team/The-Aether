package com.aether.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;

public class EnchantedAetherGrassBlock extends Block {

	public EnchantedAetherGrassBlock(Block.Properties properties) {
		super(properties);
	}
	
	 protected static boolean canBlockStay(BlockState state, IWorldReader world, BlockPos pos) {
	      BlockPos posUp = pos.up();
		BlockState stateUp = world.getBlockState(posUp);

		int i = LightEngine.func_215613_a(world, state, pos, stateUp, posUp, Direction.UP, stateUp.getOpacity(world, posUp));
		return i < world.getMaxLightLevel();
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		if (!worldIn.isRemote) {
			if (!canBlockStay(state, worldIn, pos)) {
				worldIn.setBlockState(pos, AetherBlocks.AETHER_DIRT.getDefaultState());
			}
		}
	}
	
}
