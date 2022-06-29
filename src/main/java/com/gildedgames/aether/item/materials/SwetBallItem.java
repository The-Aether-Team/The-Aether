package com.gildedgames.aether.item.materials;

import com.gildedgames.aether.item.materials.util.ISwetBallConversion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;

import javax.annotation.Nonnull;

public class SwetBallItem extends Item implements ISwetBallConversion
{
	public SwetBallItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult useOn(@Nonnull UseOnContext context) {
		return convertBlock(context);
	}
}
