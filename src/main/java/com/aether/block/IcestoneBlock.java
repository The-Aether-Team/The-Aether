package com.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IcestoneBlock extends Block {

	public IcestoneBlock(Block.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		for (int x = -3; x < 3; x++) {
			for (int y = -3; y < 3; y++) {
				for (int z = -3; z < 3; z++) {
					BlockPos newPos = pos.add(x, y, z);
					BlockState state2 = worldIn.getBlockState(newPos);
					Block block = state2.getBlock();
//					if (state2.getBlock() instanceof FlowingFluidBlock) {
//						IFluidState fluidState = state2.getFluidState();
//						if (!fluidState.isEmpty() && fluidState.getFluid().getAttributes().getTemperature() <= Fluids.WATER.getAttributes().getTemperature()) {
//							
//						}
//					}	
					if (block instanceof FlowingFluidBlock) {
						IFluidState fluidState = state2.getFluidState();
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
