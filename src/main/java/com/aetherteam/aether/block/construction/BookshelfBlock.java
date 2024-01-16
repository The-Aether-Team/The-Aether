package com.aetherteam.aether.block.construction;

import io.github.fabricators_of_create.porting_lib.enchant.EnchantmentBonusBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BookshelfBlock extends Block implements EnchantmentBonusBlock {
	public BookshelfBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
		return 1;
	}
}
