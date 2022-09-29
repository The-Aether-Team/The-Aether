package com.gildedgames.aether.item.materials;

import com.gildedgames.aether.item.materials.behavior.ItemUseConversion;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.recipes.block.SwetBallRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;

public class SwetBallItem extends Item implements ItemUseConversion<SwetBallRecipe> {
	public SwetBallItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		return this.convertBlock(AetherRecipeTypes.SWET_BALL_CONVERSION.get(), context);
	}
}
