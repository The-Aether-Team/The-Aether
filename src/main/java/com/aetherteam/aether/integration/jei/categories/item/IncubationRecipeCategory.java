package com.aetherteam.aether.integration.jei.categories.item;

//import com.aetherteam.aether.Aether;
//import com.aetherteam.aether.block.AetherBlocks;
//import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
//import mezz.jei.api.gui.drawable.IDrawableAnimated;
//import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.recipe.IFocusGroup;
//import mezz.jei.api.recipe.RecipeIngredientRole;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.core.NonNullList;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//
//public class IncubationRecipeCategory extends AbstractAetherCookingRecipeCategory<IncubationRecipe> implements IRecipeCategory<IncubationRecipe> {
//    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "incubation");
//    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/incubator.png");
//    public static final RecipeType<IncubationRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "incubation", IncubationRecipe.class);
//
//    public IncubationRecipeCategory(IGuiHelper guiHelper) {
//        super("incubating", UID,
//                guiHelper.createDrawable(TEXTURE, 72, 16, 70, 54),
//                guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherBlocks.INCUBATOR.get())),
//                guiHelper.createDrawable(TEXTURE, 176, 0, 14, 13),
//                guiHelper.createAnimatedDrawable(guiHelper.createDrawable(TEXTURE, 179, 16, 10, 54), 5700, IDrawableAnimated.StartDirection.BOTTOM, false),
//                RECIPE_TYPE);
//    }
//
//    @Override
//    public void setRecipe(IRecipeLayoutBuilder builder, IncubationRecipe recipe, IFocusGroup focusGroup) {
//        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
//
//        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipeIngredients.get(0));
//    }
//
//    @Override
//    public void draw(IncubationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
//        this.animatedProgressArrow.draw(guiGraphics, 31, 0);
//        this.fuelIndicator.draw(guiGraphics, 1, 20);
//        this.drawCookingTime(guiGraphics, 45, recipe.getIncubationTime(), this.background);
//    }
//}
