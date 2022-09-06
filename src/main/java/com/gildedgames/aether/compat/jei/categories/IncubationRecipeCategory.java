package com.gildedgames.aether.compat.jei.categories;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.recipe.recipes.item.IncubationRecipe;
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

public class IncubationRecipeCategory implements IRecipeCategory<IncubationRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "incubation");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/incubator.png");
    public static final RecipeType<IncubationRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "incubation", IncubationRecipe.class);

    private final IDrawable background;
    private final IDrawable fuelIndicator;
    private final IDrawable icon;
    private final IDrawableAnimated animatedProgressArrow;

    public IncubationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 83);
        this.fuelIndicator = helper.createDrawable(TEXTURE, 176, 0, 14, 13);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherBlocks.INCUBATOR.get()));
        IDrawableStatic progressArrow = helper.createDrawable(TEXTURE, 179, 16, 10, 54 );
        this.animatedProgressArrow = helper.createAnimatedDrawable(progressArrow, 5700, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui." + Aether.MODID + ".jei.incubating");
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
    public RecipeType<IncubationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IncubationRecipe recipe, IFocusGroup focusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();

        builder.addSlot(RecipeIngredientRole.INPUT, 73, 17).addIngredients(recipeIngredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 73, 53).addIngredients(Ingredient.of(AetherBlocks.AMBROSIUM_TORCH.get()));
    }

    @Override
    public void draw(IncubationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        animatedProgressArrow.draw(stack, 103, 16);
        fuelIndicator.draw(stack, 73, 36);
    }
}
