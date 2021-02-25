package com.gildedgames.aether.block.natural;

import java.util.Random;

import com.gildedgames.aether.block.util.IAetherDoubleDropBlock;
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
