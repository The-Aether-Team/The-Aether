package com.gildedgames.aether.recipe.recipes.item;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

public class EnchantingRecipe extends AbstractCookingRecipe
{
	public EnchantingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
		super(AetherRecipeTypes.ENCHANTING.get(), idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(AetherBlocks.ALTAR.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return AetherRecipeSerializers.ENCHANTING.get();
	}

	public static class Serializer extends SimpleCookingSerializer<EnchantingRecipe>
	{
		public Serializer() {
			super(EnchantingRecipe::new, 200);
		}
	}
}
