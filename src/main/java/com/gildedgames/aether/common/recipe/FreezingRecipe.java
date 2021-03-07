package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class FreezingRecipe extends AbstractCookingRecipe
{
	public FreezingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
		super(RecipeTypes.FREEZING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(AetherBlocks.FREEZER.get());
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return AetherRecipes.FREEZING.get();
	}

	public static class Serializer extends CookingRecipeSerializer<FreezingRecipe>
	{
		public Serializer() {
			super(FreezingRecipe::new, 200);
		}
	}
}
