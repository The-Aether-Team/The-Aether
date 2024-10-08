package com.aetherteam.aether.integration.jei.categories.ban;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class ItemBanRecipeCategory extends AbstractPlacementBanRecipeCategory<ItemStack, Ingredient, SingleRecipeInput, ItemBanRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "item_placement_ban");
    public static final RecipeType<ItemBanRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "item_placement_ban", ItemBanRecipe.class);

    public ItemBanRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super(guiHelper, "item_placement_ban", UID,
            guiHelper.createBlankDrawable(116, 18),
            guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.FLINT_AND_STEEL)),
            RECIPE_TYPE, fluidHelper);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ItemBanRecipe recipe, IFocusGroup focusGroup) {
        Ingredient ingredient = recipe.getIngredient();
        if (recipe.getBypassBlock().isEmpty() || recipe.getBypassBlock().get().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 50, 1).addIngredients(ingredient).addTooltipCallback((recipeSlotView, tooltip) -> this.populateAdditionalInformation(recipe, tooltip));
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(ingredient).addTooltipCallback((recipeSlotView, tooltip) -> this.populateAdditionalInformation(recipe, tooltip));
        }
        super.setRecipe(builder, recipe, focusGroup);
    }
}
