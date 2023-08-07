package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.integration.jei.categories.BiomeTooltip;
import com.aetherteam.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public abstract class AbstractBiomeParameterRecipeCategory<T extends AbstractBiomeParameterRecipe> extends AetherBlockStateRecipeCategory<T> implements BiomeTooltip {
    public AbstractBiomeParameterRecipeCategory(String id, ResourceLocation uid, IDrawable background, IDrawable icon, RecipeType<T> recipeType, IPlatformFluidHelper<?> fluidHelper) {
        super(id, uid, background, icon, recipeType, fluidHelper);
    }

    @Override
    protected void populateAdditionalInformation(T recipe, List<Component> tooltip) {
        if (Minecraft.getInstance().level != null) {
            this.populateBiomeInformation(recipe.getBiomeKey(), recipe.getBiomeTag(), tooltip);
        }
    }
}
