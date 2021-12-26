package com.gildedgames.aether.common.block.natural;

import java.util.Random;

import com.gildedgames.aether.common.block.util.IAetherDoubleDropBlock;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.util.Mth;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class AetherOreBlock extends OreBlock implements IAetherDoubleDropBlock
{
	private final int minExpDropped;
	private final int maxExpDropped;
	
	public AetherOreBlock(int minExpDropped, int maxExpDropped, BlockBehaviour.Properties properties) {
		super(properties);
		this.minExpDropped = minExpDropped;
		this.maxExpDropped = maxExpDropped;
	}
	
//	@Override
//	protected int xpOnDrop(Random rand) {
//		return Mth.nextInt(rand, minExpDropped, maxExpDropped);
//	}
}
