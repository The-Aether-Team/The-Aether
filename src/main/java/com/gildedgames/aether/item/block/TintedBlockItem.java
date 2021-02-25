package com.gildedgames.aether.item.block;

import com.gildedgames.aether.item.IAetherItemColor;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TintedBlockItem extends BlockItem implements IAetherItemColor
{
	private final int hexColor, updatedHexColor;
	
	public TintedBlockItem(int hexColor, int updatedHexColor, Block blockIn, Item.Properties builder) {
		super(blockIn, builder);
		this.hexColor = hexColor;
		this.updatedHexColor = updatedHexColor;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public final int getColor(boolean updatedVersion) {
		return updatedVersion? updatedHexColor : hexColor;
	}
}
