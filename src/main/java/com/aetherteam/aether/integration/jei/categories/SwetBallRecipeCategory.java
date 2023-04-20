package com.aetherteam.aether.integration.jei.categories;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.BlockPropertyPair;
import com.aetherteam.aether.recipe.BlockStateIngredient;
import com.aetherteam.aether.recipe.recipes.block.SwetBallRecipe;
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

public class SwetBallRecipeCategory implements IRecipeCategory<SwetBallRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "swet_ball_convert");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/jei_render.png");
    public static final RecipeType<SwetBallRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "swet_ball_convert", SwetBallRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SwetBallRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 49, 29, 84, 28);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherItems.SWET_BALL.get()));
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui." + Aether.MODID + ".jei.swet_ball_convert");
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
    public RecipeType<SwetBallRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SwetBallRecipe recipe, IFocusGroup focusGroup) {
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
    public void draw(SwetBallRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
    }
}
