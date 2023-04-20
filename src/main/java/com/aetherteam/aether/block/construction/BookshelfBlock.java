package com.aetherteam.aether.block.construction;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

public class BookshelfBlock extends Block {
	public BookshelfBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
		return 1;
	}
}
