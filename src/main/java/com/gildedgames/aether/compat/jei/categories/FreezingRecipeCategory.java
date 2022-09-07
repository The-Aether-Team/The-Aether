package com.gildedgames.aether.compat.jei.categories;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.recipe.recipes.item.FreezingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class FreezingRecipeCategory implements IRecipeCategory<FreezingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "freezing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/freezer.png");
    public static final RecipeType<FreezingRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "freezing", FreezingRecipe.class);

    private final IDrawable background;
    private final IDrawable fuelIndicator;
    private final IDrawable icon;
    private final IDrawableAnimated animatedProgressArrow;

    public FreezingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.fuelIndicator = helper.createDrawable(TEXTURE, 176, 0, 14, 13);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherBlocks.FREEZER.get()));
        IDrawableStatic progressArrow = helper.createDrawable(TEXTURE, 176, 14, 23, 16 );
        this.animatedProgressArrow = helper.createAnimatedDrawable(progressArrow, 100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui." + Aether.MODID + ".jei.freezing");
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
        return UID;
    }

    @Override
    public RecipeType<FreezingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FreezingRecipe recipe, IFocusGroup focusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();

        builder.addSlot(RecipeIngredientRole.INPUT, 56, 17).addIngredients(recipeIngredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 53).addIngredients(Ingredient.of(AetherBlocks.ICESTONE.get()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(FreezingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        animatedProgressArrow.draw(stack, 79, 34);
        fuelIndicator.draw(stack, 56, 36);
    }
}
