package com.aether.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IAetherBlockColor extends IBlockColor {

	int getColor(boolean updatedVersion);
	
	@Override
	@OnlyIn(Dist.CLIENT)
	default int getColor(BlockState state, ILightReader world, BlockPos pos, int p_getColor_4_) {
		return this.getColor(false);
	}
	
}
