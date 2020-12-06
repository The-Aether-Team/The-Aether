package com.aether.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import net.minecraft.block.AbstractBlock;

public class AetherOreBlock extends OreBlock implements IAetherDoubleDropBlock {
	private final int minExpDropped;
	private final int maxExpDropped;
	
	public AetherOreBlock(int minExpDropped, int maxExpDropped, AbstractBlock.Properties properties) {
		super(properties);
		this.minExpDropped = minExpDropped;
		this.maxExpDropped = maxExpDropped;
	}
	
	@Override
	protected int getExperience(Random rand) {
		return MathHelper.nextInt(rand, minExpDropped, maxExpDropped);
	}
	
}
