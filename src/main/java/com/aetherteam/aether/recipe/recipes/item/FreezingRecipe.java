package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.serializer.AetherCookingSerializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FreezingRecipe extends AbstractAetherCookingRecipe {
    public FreezingRecipe(String group, AetherBookCategory category, Ingredient ingredient, ItemStack result, float experience, int freezingTime) {
        super(AetherRecipeTypes.FREEZING.get(), group, category, ingredient, result, experience, freezingTime);
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.FREEZER.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.FREEZING.get();
    }

    public static class Serializer extends AetherCookingSerializer<FreezingRecipe> {
        public Serializer() {
            super(FreezingRecipe::new, 800);
        }
    }
}
