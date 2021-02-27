package com.gildedgames.aether.crafting;

import com.gildedgames.aether.registry.AetherRecipe;
import com.gildedgames.aether.registry.AetherRecipe.RecipeTypes;
import com.gildedgames.aether.registry.AetherBlocks;
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
		return AetherRecipe.FREEZING.get();
	}

	public static class Serializer extends CookingRecipeSerializer<FreezingRecipe>
	{
		public Serializer() {
			super(FreezingRecipe::new, 200);
		}
	}
}
