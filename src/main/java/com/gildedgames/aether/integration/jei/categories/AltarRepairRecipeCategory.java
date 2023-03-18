/*
package com.gildedgames.aether.integration.jei.categories;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.recipe.recipes.item.AltarRepairRecipe;
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

public class AltarRepairRecipeCategory extends AbstractAetherCookingRecipeCategory implements IRecipeCategory<AltarRepairRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "repairing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/altar.png");
    public static final RecipeType<AltarRepairRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "repairing", AltarRepairRecipe.class);

    private final IDrawable background;
    private final IDrawable fuelIndicator;
    private final IDrawable icon;
    private final IDrawableAnimated animatedProgressArrow;

    public AltarRepairRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 55, 16, 82, 54);
        this.fuelIndicator = helper.createDrawable(TEXTURE, 176, 0, 14, 13);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherBlocks.ALTAR.get()));
        IDrawableStatic progressArrow = helper.createDrawable(TEXTURE, 176, 14, 23, 16 );
        this.animatedProgressArrow = helper.createAnimatedDrawable(progressArrow, 100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui." + Aether.MODID + ".jei.altar.repairing");
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
    public RecipeType<AltarRepairRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AltarRepairRecipe recipe, IFocusGroup focusGroup) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();

        ItemStack damagedItem = recipeIngredients.get(0).getItems()[0].copy();
        damagedItem.setDamageValue(damagedItem.getMaxDamage() * 3 / 4);

        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(Ingredient.of(damagedItem));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 19).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(AltarRepairRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        animatedProgressArrow.draw(stack, 24, 18);
        fuelIndicator.draw(stack, 1, 20);
        drawExperience(recipe, stack, 1, background);
        drawCookTime(recipe, stack, 45, background);
    }
}
*/
