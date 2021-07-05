package com.gildedgames.aether.common.item.materials;

import com.gildedgames.aether.common.item.materials.abilities.ISwetBallConversion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class SwetBallItem extends Item implements ISwetBallConversion
{
	public SwetBallItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType useOn(@Nonnull ItemUseContext context) {
		return convertBlock(context);
	}
}
