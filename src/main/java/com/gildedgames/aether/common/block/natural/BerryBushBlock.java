package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import net.minecraft.block.AbstractBlock;

public class BerryBushBlock extends AetherBushBlock
{
	public BerryBushBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
	
	@Override
	public void destroy(IWorld worldIn, BlockPos pos, BlockState state) {
		super.destroy(worldIn, pos, state);
		worldIn.setBlock(pos, AetherBlocks.BERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 3);
	}
}
