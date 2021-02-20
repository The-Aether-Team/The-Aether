package com.aether.block.natural;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock;

import java.util.Map;
import java.util.function.Supplier;

public class IcestoneBlock extends Block
{
//	public static final Map<FluidState, Block> FREEZABLES = Maps.newHashMap();

	public IcestoneBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

//	public static void registerFreezableFluid(Supplier<FluidState> fluid, Supplier<Block> block) {
//		FREEZABLES.put(fluid.get(), block.get());
//	}

	//TODO: implement the above commented out code to the modpack config.
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		for (int x = -3; x < 3; x++) {
			for (int y = -3; y < 3; y++) {
				for (int z = -3; z < 3; z++) {
					BlockPos newPos = pos.add(x, y, z);
					BlockState state2 = worldIn.getBlockState(newPos);
					Block block = state2.getBlock();
					if (block instanceof FlowingFluidBlock) {
						FluidState fluidState = state2.getFluidState();
						if (fluidState.isTagged(FluidTags.WATER)) { // TODO configuration value or registry for custom liquids?
							worldIn.setBlockState(newPos, Blocks.ICE.getDefaultState());
						}
						else if (fluidState.isTagged(FluidTags.LAVA)) {
							worldIn.setBlockState(newPos, Blocks.OBSIDIAN.getDefaultState());
							worldIn.playSound(null, newPos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
						}
					}
				}
			}
		}
	}
	
}
