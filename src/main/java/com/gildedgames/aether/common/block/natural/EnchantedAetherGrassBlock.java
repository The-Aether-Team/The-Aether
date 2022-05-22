package com.gildedgames.aether.common.block.natural;

import java.util.Random;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class EnchantedAetherGrassBlock extends SnowyDirtBlock
{
	public EnchantedAetherGrassBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	 protected static boolean canBlockStay(BlockState state, LevelReader world, BlockPos pos) {
		BlockPos posUp = pos.above();
		BlockState stateUp = world.getBlockState(posUp);
		int i = LayerLightEngine.getLightBlockInto(world, state, pos, stateUp, posUp, Direction.UP, stateUp.getLightBlock(world, posUp));
		return i < world.getMaxLightLevel();
	}

	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		if (!canBlockStay(state, worldIn, pos)) {
			worldIn.setBlockAndUpdate(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState());
		}
	}
}
