package com.gildedgames.aether.recipe.recipes.item;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.recipe.AetherBookCategory;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.serializer.AetherCookingSerializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.resources.ResourceLocation;

public class EnchantingRecipe extends AbstractAetherCookingRecipe {
	public EnchantingRecipe(ResourceLocation idIn, String groupIn, AetherBookCategory category, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
		super(AetherRecipeTypes.ENCHANTING.get(), idIn, groupIn, category, ingredientIn, resultIn, experienceIn, cookTimeIn);
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
			super(EnchantingRecipe::new, 200);
		}
	}
}
