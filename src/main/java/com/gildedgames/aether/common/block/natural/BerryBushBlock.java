package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import net.minecraft.block.AbstractBlock;

public class BerryBushBlock extends AetherBushBlock
{
	public BerryBushBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	public void destroy(IWorld worldIn, BlockPos pos, BlockState state) {
		super.destroy(worldIn, pos, state);
		worldIn.setBlock(pos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState(), 3);
	}
}
