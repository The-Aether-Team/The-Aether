package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.util.IAetherDoubleDropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.OreBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AetherOreBlock extends OreBlock implements IAetherDoubleDropBlock
{
	private final UniformInt xpRange;
	
	public AetherOreBlock(UniformInt xpRange, BlockBehaviour.Properties properties) {
		super(properties);
		this.xpRange = xpRange;
	}

	@Override
	public int getExpDrop(BlockState state, LevelReader reader, BlockPos pos, int fortune, int silkTouch) {
		return silkTouch == 0 ? this.xpRange.sample(RANDOM) : 0;
	}
}
