package com.aether.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TintedBlockItem extends BlockItem implements IItemColor {

	private final int hexColor, updatedHexColor;
	
	public TintedBlockItem(int hexColor, int updatedHexColor, Block blockIn, Item.Properties builder) {
		super(blockIn, builder);
		this.hexColor = hexColor;
		this.updatedHexColor = updatedHexColor;
	}

	@Override
	public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
		return hexColor;
	}
	
	public final int getColor(boolean updatedVersion) {
		return updatedVersion? updatedHexColor : hexColor;
	}

}
