package com.aether.block.construction;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;

public class AerogelWallBlock extends WallBlock
{
	public AerogelWallBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}
}
