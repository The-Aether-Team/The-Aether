package com.aetherteam.aether.integration.jei;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.integration.jei.categories.*;
import com.aetherteam.aether.integration.jei.categories.fuel.AetherFuelCategory;
import com.aetherteam.aether.integration.jei.categories.fuel.AetherFuelRecipeMaker;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class AetherJEIPlugin implements IModPlugin {
    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Aether.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new EnchantingCookingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AltarRepairRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FreezingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new IncubationRecipeCategory((registration.getJeiHelpers().getGuiHelper())));
        registration.addRecipeCategories(new AetherFuelCategory((registration.getJeiHelpers().getGuiHelper())));
        registration.addRecipeCategories(new SwetBallRecipeCategory((registration.getJeiHelpers().getGuiHelper())));
        registration.addRecipeCategories(new AmbrosiumRecipeCategory((registration.getJeiHelpers().getGuiHelper())));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<?> unfilteredRecipes = rm.getAllRecipesFor(AetherRecipeTypes.ENCHANTING.get());
        List<AltarRepairRecipe> repairRecipes = new ArrayList<>();
        List<EnchantingRecipe> enchantingRecipes = new ArrayList<>();
        unfilteredRecipes.stream().filter(recipe -> recipe instanceof AltarRepairRecipe) //filters altar repair and regular altar recipes.
                .forEach(recipe -> repairRecipes.add((AltarRepairRecipe) recipe));
        unfilteredRecipes.stream().filter(recipe -> recipe instanceof EnchantingRecipe)
                .forEach(recipe -> enchantingRecipes.add((EnchantingRecipe) recipe));

        registration.addRecipes(EnchantingCookingRecipeCategory.RECIPE_TYPE, enchantingRecipes);
        registration.addRecipes(AltarRepairRecipeCategory.RECIPE_TYPE, repairRecipes);
        registration.addRecipes(FreezingRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.FREEZING.get()));
        registration.addRecipes(IncubationRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.INCUBATION.get()));
        registration.addRecipes(AetherFuelCategory.RECIPE_TYPE, AetherFuelRecipeMaker.getFuelRecipes());
        registration.addRecipes(SwetBallRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.SWET_BALL_CONVERSION.get()));
        registration.addRecipes(AmbrosiumRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.ALTAR.get().asItem()), EnchantingCookingRecipeCategory.RECIPE_TYPE, AltarRepairRecipeCategory.RECIPE_TYPE, AetherFuelCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.FREEZER.get().asItem()), FreezingRecipeCategory.RECIPE_TYPE, AetherFuelCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(AetherBlocks.INCUBATOR.get().asItem()), IncubationRecipeCategory.RECIPE_TYPE, AetherFuelCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(AetherItems.SWET_BALL.get().asItem()), SwetBallRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(AetherItems.AMBROSIUM_SHARD.get().asItem()), AmbrosiumRecipeCategory.RECIPE_TYPE);
    }
}
