package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.AetherTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public class AetherBushBlock extends BushBlock {
	public AetherBushBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(AetherTags.Blocks.AETHER_DIRT) || super.mayPlaceOn(state, level, pos);
	}
}
