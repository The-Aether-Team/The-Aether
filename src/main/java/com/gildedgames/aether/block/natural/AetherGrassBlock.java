package com.gildedgames.aether.block.natural;

import com.gildedgames.aether.block.DoubleDrops;
import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AetherGrassBlock extends GrassBlock implements DoubleDrops
{
	private static final BooleanProperty DOUBLE_DROPS = AetherBlockStateProperties.DOUBLE_DROPS;

	public AetherGrassBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(DOUBLE_DROPS, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DOUBLE_DROPS);
	}
	
//	@Override
//	public void onPlantGrow(BlockState state, LevelAccessor world, BlockPos pos, BlockPos source) {
//		if (state.is(AetherTags.Blocks.AETHER_DIRT)) {
//			world.setBlock(pos, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS) ? AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)) : AetherBlocks.AETHER_DIRT.get().defaultBlockState(), 2);
//		}
//	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
		if (!canBeGrass(state, worldIn, pos)) {
			if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			worldIn.setBlockAndUpdate(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState());
		} else {
			if (worldIn.getMaxLocalRawBrightness(pos.above()) >= 9) {
				BlockState blockstate = this.defaultBlockState();
				for(int i = 0; i < 4; ++i) {
					BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if (worldIn.getBlockState(blockpos).is(AetherBlocks.AETHER_DIRT.get()) && canPropagate(blockstate, worldIn, blockpos)) {
						worldIn.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, worldIn.getBlockState(blockpos.above()).is(Blocks.SNOW)));
					}
				}
			}
		}
	}
}
