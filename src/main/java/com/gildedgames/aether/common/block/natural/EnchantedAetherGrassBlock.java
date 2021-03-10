package com.gildedgames.aether.common.block.natural;

import java.util.Random;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.block.AbstractBlock;

public class EnchantedAetherGrassBlock extends SnowyDirtBlock
{
	public EnchantedAetherGrassBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	 protected static boolean canBlockStay(BlockState state, IWorldReader world, BlockPos pos) {
		BlockPos posUp = pos.above();
		BlockState stateUp = world.getBlockState(posUp);
		int i = LightEngine.getLightBlockInto(world, state, pos, stateUp, posUp, Direction.UP, stateUp.getLightBlock(world, posUp));
		return i < world.getMaxLightLevel();
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (!canBlockStay(state, worldIn, pos)) {
			worldIn.setBlockAndUpdate(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState());
		}
	}
}
