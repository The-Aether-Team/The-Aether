package com.aetherteam.aether.integration.jei.categories;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractAetherRecipeCategory<T> implements IRecipeCategory<T> {
    protected final String id;
    protected final ResourceLocation uid;
    protected final IDrawable background;
    protected final IDrawable icon;
    protected final RecipeType<T> recipeType;

    public AbstractAetherRecipeCategory(String id, ResourceLocation uid, IDrawable background, IDrawable icon, RecipeType<T> recipeType) {
        this.id = id;
        this.uid = uid;
        this.background = background;
        this.icon = icon;
        this.recipeType = recipeType;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.aether.jei." + this.id);
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    public ResourceLocation getUid() {
        return this.uid;
    }

    @Override
    public RecipeType<T> getRecipeType() {
        return this.recipeType;
    }
}
