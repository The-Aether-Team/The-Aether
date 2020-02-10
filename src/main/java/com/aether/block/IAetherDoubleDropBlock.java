package com.aether.block;

import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;

public interface IAetherDoubleDropBlock {

	BooleanProperty DOUBLE_DROPS = BooleanProperty.create("double_drops");
	
	BlockState getDefaultState();
	
	default BlockState getDoubleDropsState() {
		return this.getDefaultState().with(DOUBLE_DROPS, true);
	}
	
}
