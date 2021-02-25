package com.gildedgames.aether.block.util;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;

import net.minecraft.block.BlockState;

public interface IAetherDoubleDropBlock {

	BlockState getDefaultState();
	
	default BlockState getDoubleDropsState() {
		return this.getDefaultState().with(AetherBlockStateProperties.DOUBLE_DROPS, true);
	}
	
}
