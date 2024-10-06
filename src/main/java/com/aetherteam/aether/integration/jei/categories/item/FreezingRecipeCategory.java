package com.aetherteam.aether.integration.jei.categories.item;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.recipes.item.FreezingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class FreezingRecipeCategory extends AbstractAetherCookingRecipeCategory<FreezingRecipe> implements IRecipeCategory<FreezingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "freezing");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu/freezer.png");
    public static final RecipeType<FreezingRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "freezing", FreezingRecipe.class);


    public FreezingRecipeCategory(IGuiHelper guiHelper) {
        super("freezing", UID,
            guiHelper.createDrawable(TEXTURE, 55, 16, 82, 54),
            guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherBlocks.FREEZER.get())),
            guiHelper.drawableBuilder(FLAME_TEXTURE, 0, 0, 14, 14).setTextureSize(14, 14).build(),
            guiHelper.createAnimatedDrawable(guiHelper.drawableBuilder(ARROW_TEXTURE, 0, 0, 24, 16).setTextureSize(24, 16).build(), 100, IDrawableAnimated.StartDirection.LEFT, false),
            RECIPE_TYPE);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FreezingRecipe recipe, IFocusGroup focusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();

        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipeIngredients.get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 19).addItemStack(recipe.getResult());
    }

    @Override
    public void draw(FreezingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.animatedProgressArrow.draw(guiGraphics, 24, 18);
        this.fuelIndicator.draw(guiGraphics, 1, 20);
        this.drawExperience(recipe, guiGraphics, 1, this.background);
        this.drawCookingTime(guiGraphics, 45, recipe.getCookingTime(), this.background);
    }
}
