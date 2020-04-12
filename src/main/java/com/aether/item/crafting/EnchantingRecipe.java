package com.aether.item.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class EnchantingRecipe extends AbstractCookingRecipe {

	public EnchantingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
		super(AetherRecipeTypes.ENCHANTING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return AetherRecipeSerializers.ENCHANTING;
	}

}
