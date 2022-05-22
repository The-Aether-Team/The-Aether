package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

public class EnchantingRecipe extends AbstractCookingRecipe
{
	public EnchantingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
		super(RecipeTypes.ENCHANTING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(AetherBlocks.ALTAR.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return AetherRecipes.ENCHANTING.get();
	}

	public static class Serializer extends SimpleCookingSerializer<EnchantingRecipe>
	{
		public Serializer() {
			super(EnchantingRecipe::new, 200);
		}
	}
}
