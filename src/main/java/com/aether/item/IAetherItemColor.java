package com.aether.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IAetherItemColor /*extends IItemColor*/ {

	int getColor(boolean updatedVersion);
	
//	@Override
	@OnlyIn(Dist.CLIENT)
	default int getColor(ItemStack stack, int p_getColor_2_) {
		return getColor(false);
	}
	
}
