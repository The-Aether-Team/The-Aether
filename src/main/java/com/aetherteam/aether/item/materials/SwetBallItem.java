package com.aetherteam.aether.item.materials;

import com.aetherteam.aether.item.materials.behavior.ItemUseConversion;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.block.SwetBallRecipe;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class SwetBallItem extends Item implements ItemUseConversion<SwetBallRecipe> {
	public SwetBallItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		return this.convertBlock(AetherRecipeTypes.SWET_BALL_CONVERSION.get(), context);
	}
}
