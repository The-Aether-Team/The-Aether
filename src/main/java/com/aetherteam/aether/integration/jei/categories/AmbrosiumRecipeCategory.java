/*
package com.gildedgames.aether.integration.jei.categories;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.recipes.block.AmbrosiumRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class AmbrosiumRecipeCategory implements IRecipeCategory<AmbrosiumRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "ambrosium_convert");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/jei_render.png");
    public static final RecipeType<AmbrosiumRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "ambrosium_convert", AmbrosiumRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public AmbrosiumRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 49, 29, 84, 28);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui." + Aether.MODID + ".jei.ambrosium_convert");
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
    public RecipeType<AmbrosiumRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AmbrosiumRecipe recipe, IFocusGroup focusGroup) {
        BlockStateIngredient recipeIngredients = recipe.getIngredient();
        BlockPropertyPair recipeResult = recipe.getResult();

        BlockPropertyPair[] pairs = recipeIngredients.getPairs();

        int i = 0;
        for (BlockPropertyPair pair : pairs) {
            builder.addSlot(RecipeIngredientRole.INPUT, 8, 6 + i).addIngredients(Ingredient.of(pair.block()));
            i += 16;
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 6).addIngredients(Ingredient.of(recipeResult.block()));
    }

    @Override
    public void draw(AmbrosiumRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
    }
}
*/
