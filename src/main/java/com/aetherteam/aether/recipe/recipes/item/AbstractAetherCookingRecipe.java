package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.recipe.AetherBookCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class AbstractAetherCookingRecipe extends AbstractCookingRecipe {
    private final AetherBookCategory category;

    public AbstractAetherCookingRecipe(RecipeType<?> recipeType, ResourceLocation id, String group, AetherBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(recipeType, id, group, ingredient, result, experience, cookingTime);
        this.category = category;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public AetherBookCategory aetherCategory() {
        return this.category;
    }
}
