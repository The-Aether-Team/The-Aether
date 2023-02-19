package com.gildedgames.aether.integration.jei.categories;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.recipes.block.IcestoneFreezableRecipe;
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

public class IcestoneFreezableRecipeCategory implements IRecipeCategory<IcestoneFreezableRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "icestone_freezable");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/jei_render.png");
    public static final RecipeType<IcestoneFreezableRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "icestone_freezable", IcestoneFreezableRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public IcestoneFreezableRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 49, 29, 84, 28);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherBlocks.ICESTONE.get()));
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui." + Aether.MODID + ".jei.icestone_freezable");
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
    public RecipeType<IcestoneFreezableRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IcestoneFreezableRecipe recipe, IFocusGroup focusGroup) {
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
    public void draw(IcestoneFreezableRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
    }
}
