package com.gildedgames.aether.recipe;

import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

public class FreezingRecipe extends AbstractCookingRecipe
{
	public FreezingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
		super(AetherRecipes.RecipeTypes.FREEZING.get(), idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(AetherBlocks.FREEZER.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return AetherRecipes.FREEZING.get();
	}

	public static class Serializer extends SimpleCookingSerializer<FreezingRecipe>
	{
		public Serializer() {
			super(FreezingRecipe::new, 200);
		}
	}
}
