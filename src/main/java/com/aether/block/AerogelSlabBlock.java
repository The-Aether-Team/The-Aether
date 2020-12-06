package com.aether.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;

import net.minecraft.block.AbstractBlock.Properties;

public class AerogelSlabBlock extends SlabBlock {

	public AerogelSlabBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}

}
