package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.serializer.AetherCookingSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class EnchantingRecipe extends AbstractAetherCookingRecipe {
	public EnchantingRecipe(ResourceLocation id, String group, AetherBookCategory category, Ingredient ingredient, ItemStack result, float experience, int enchantingTime) {
		super(AetherRecipeTypes.ENCHANTING.get(), id, group, category, ingredient, result, experience, enchantingTime);
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(AetherBlocks.ALTAR.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return AetherRecipeSerializers.ENCHANTING.get();
	}

	public static class Serializer extends AetherCookingSerializer<EnchantingRecipe> {
		public Serializer() {
			super(EnchantingRecipe::new, 250);
		}
	}
}
