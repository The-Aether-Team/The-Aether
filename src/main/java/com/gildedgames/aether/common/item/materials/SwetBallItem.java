package com.gildedgames.aether.common.item.materials;

import com.gildedgames.aether.common.item.util.ISwetBallConversion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class SwetBallItem extends Item
{
	public SwetBallItem(Item.Properties properties) {
		super(properties);
		ISwetBallConversion.registerDefaultConversions();
		ISwetBallConversion.registerBiomeConversions();
	}
	
	@Override
	public ActionResultType useOn(@Nonnull ItemUseContext context) {
		return ISwetBallConversion.convertBlock(context);
	}
}
