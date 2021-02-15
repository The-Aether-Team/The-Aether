package com.aether.block.construction;

import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

import net.minecraft.block.AbstractBlock.Properties;

public class AerogelStairsBlock extends StairsBlock {

	public AerogelStairsBlock(Supplier<BlockState> state, Properties properties) {
		super(state, properties);
	}
	
	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}

}
