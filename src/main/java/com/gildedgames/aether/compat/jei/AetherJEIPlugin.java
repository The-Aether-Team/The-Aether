package com.gildedgames.aether.compat.jei;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.compat.jei.categories.AltarRepairRecipeCategory;
import com.gildedgames.aether.compat.jei.categories.EnchantingRecipeCategory;
import com.gildedgames.aether.compat.jei.categories.FreezingRecipeCategory;
import com.gildedgames.aether.compat.jei.categories.IncubationRecipeCategory;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.recipes.item.AltarRepairRecipe;
import com.gildedgames.aether.recipe.recipes.item.EnchantingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class AetherJEIPlugin implements IModPlugin {
    public static final RecipeType<EnchantingRecipe> ENCHANTING = RecipeType.create(Aether.MODID, "enchanting", EnchantingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Aether.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new EnchantingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AltarRepairRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FreezingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new IncubationRecipeCategory((registration.getJeiHelpers().getGuiHelper())));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<?> unfilteredRecipes = rm.getAllRecipesFor(AetherRecipeTypes.ENCHANTING.get());
        List<AltarRepairRecipe> repairRecipes = new ArrayList<>();
        List<EnchantingRecipe> enchantingRecipes = new ArrayList<>();
        unfilteredRecipes.stream().filter(recipe -> recipe instanceof AltarRepairRecipe) //filters altar repair and regular altar recipes.
                .forEach(recipe -> {
                    repairRecipes.add((AltarRepairRecipe) recipe);
                });
        unfilteredRecipes.stream().filter(recipe -> recipe instanceof EnchantingRecipe)
                .forEach(recipe -> {
                    enchantingRecipes.add((EnchantingRecipe) recipe);
                });

        registration.addRecipes(EnchantingRecipeCategory.RECIPE_TYPE, enchantingRecipes);
        registration.addRecipes(AltarRepairRecipeCategory.RECIPE_TYPE, repairRecipes);
        registration.addRecipes(FreezingRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.FREEZING.get()));
        registration.addRecipes(IncubationRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.INCUBATION.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.ALTAR.get().asItem()), EnchantingRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.ALTAR.get().asItem()), AltarRepairRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.FREEZER.get().asItem()), FreezingRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.INCUBATOR.get().asItem()), IncubationRecipeCategory.RECIPE_TYPE);
    }
}
