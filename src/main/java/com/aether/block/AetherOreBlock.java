package com.aether.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.MathHelper;

public class AetherOreBlock extends OreBlock implements IAetherDoubleDropBlock {
	private final int minExpDropped;
	private final int maxExpDropped;
	
	public AetherOreBlock(int minExpDropped, int maxExpDropped, Block.Properties properties) {
		super(properties);
		this.minExpDropped = minExpDropped;
		this.maxExpDropped = maxExpDropped;
		this.setDefaultState(this.getDefaultState().with(DOUBLE_DROPS, false));
	}
	
	@Override
	protected int getExperience(Random rand) {
		return MathHelper.nextInt(rand, minExpDropped, maxExpDropped);
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(DOUBLE_DROPS);
	}
	
}
