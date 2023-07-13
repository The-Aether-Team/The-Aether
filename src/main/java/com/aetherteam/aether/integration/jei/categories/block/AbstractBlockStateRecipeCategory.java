package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.integration.jei.BlockStateRenderer;
import com.aetherteam.aether.integration.jei.FluidStateRenderer;
import com.aetherteam.aether.integration.jei.categories.AbstractAetherRecipeCategory;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.common.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractBlockStateRecipeCategory<T extends AbstractBlockStateRecipe> extends AbstractAetherRecipeCategory<T> {
    protected final IPlatformFluidHelper<?> fluidHelper;

    public AbstractBlockStateRecipeCategory(String id, ResourceLocation uid, IDrawable background, IDrawable icon, RecipeType<T> recipeType, IPlatformFluidHelper<?> fluidHelper) {
        super(id, uid, background, icon, recipeType);
        this.fluidHelper = fluidHelper;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focusGroup) {
        BlockStateIngredient recipeIngredients = recipe.getIngredient();
        BlockPropertyPair recipeResult = recipe.getResult();
        BlockPropertyPair[] pairs = recipeIngredients.getPairs();

        List<Object> ingredients = new ArrayList<>();
        for (BlockPropertyPair pair : pairs) {
            if (pair.block() instanceof LiquidBlock liquidBlock) {
                ingredients.add(this.fluidHelper.create(liquidBlock.getFluid(), 1000));
            } else {
                BlockState state = pair.block().defaultBlockState();
                for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : pair.properties().entrySet()) {
                    state = BlockStateRecipeUtil.setHelper(propertyEntry, state);
                }
                ItemStack stack = pair.block().getCloneItemStack(Minecraft.getInstance().level, BlockPos.ZERO, state);
                stack = stack.isEmpty() ? new ItemStack(Blocks.STONE) : stack;
                ingredients.add(stack);
            }
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 6).addIngredientsUnsafe(ingredients).addTooltipCallback((recipeSlotView, tooltip) -> this.populateAdditionalInformation(recipe, tooltip))
                .setCustomRenderer(Services.PLATFORM.getFluidHelper().getFluidIngredientType(), new FluidStateRenderer(Services.PLATFORM.getFluidHelper())).setCustomRenderer(VanillaTypes.ITEM_STACK, new BlockStateRenderer(pairs));

        Object ingredient;
        if (recipeResult.block() instanceof LiquidBlock liquidBlock) {
            ingredient = this.fluidHelper.create(liquidBlock.getFluid(), 1000);
        } else {
            BlockState resultState = recipeResult.block().defaultBlockState();
            for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : recipeResult.properties().entrySet()) {
                resultState = BlockStateRecipeUtil.setHelper(propertyEntry, resultState);
            }
            ItemStack stack = recipeResult.block().getCloneItemStack(Minecraft.getInstance().level, BlockPos.ZERO, resultState);
            ingredient = stack.isEmpty() ? new ItemStack(Blocks.STONE) : stack;
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 6).addIngredientsUnsafe(List.of(ingredient))
                .setCustomRenderer(Services.PLATFORM.getFluidHelper().getFluidIngredientType(), new FluidStateRenderer(Services.PLATFORM.getFluidHelper())).setCustomRenderer(VanillaTypes.ITEM_STACK, new BlockStateRenderer(recipeResult));
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {

    }

    protected void populateAdditionalInformation(T recipe, List<Component> tooltip) {

    }
}
