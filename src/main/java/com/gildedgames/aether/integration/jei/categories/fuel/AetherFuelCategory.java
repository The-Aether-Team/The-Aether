/*
package com.gildedgames.aether.integration.jei.categories.fuel;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Comparator;
import java.util.List;

public class AetherFuelCategory implements IRecipeCategory<AetherFuelRecipe> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/altar.png");
    public static final RecipeType<AetherFuelRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "fueling", AetherFuelRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> cachedFuelIndicator;

    public AetherFuelCategory(IGuiHelper helper) {

        List<String> craftingStations = List.of(
                AetherBlocks.ALTAR.get().getName().getString(),
                AetherBlocks.FREEZER.get().getName().getString(),
                AetherBlocks.INCUBATOR.get().getName().getString()
        );
        String longestString = craftingStations.stream().max(Comparator.comparingInt(String::length)).get();
        Component longestStationName = Component.literal(longestString);

        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        Component maxBurnTimeText = createBurnTimeText(10000, longestStationName);
        int maxStringWidth = fontRenderer.width(maxBurnTimeText.getString());
        int backgroundHeight = 34;
        int textPadding = 20;

        this.background = helper.drawableBuilder(TEXTURE, 55, 36, 18, backgroundHeight)
                .addPadding(0, 0, 0, textPadding + maxStringWidth)
                .build();
        this.icon = helper.createDrawable(TEXTURE, 176, 0, 14, 13);

        this.cachedFuelIndicator = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer burnTime) {
                        return helper.drawableBuilder(TEXTURE, 176, 0, 14, 13)
                                .buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
                    }
                });
    }

    @Override
    public RecipeType<AetherFuelRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui." + Aether.MODID + ".jei.fuel");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(IRecipeLayoutBuilder builder, AetherFuelRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 17)
                .addItemStacks(recipe.getInput());
    }

    @Override
    public void draw(AetherFuelRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        int burnTime = recipe.getBurnTime();
        IDrawableAnimated fuelIndicator = cachedFuelIndicator.getUnchecked(burnTime);
        fuelIndicator.draw(stack, 1, 0);

        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        Component burnTimeText = createBurnTimeText(recipe.getBurnTime(), recipe.getUsage().getName());
        int stringWidth = font.width(burnTimeText);
        font.draw(stack, burnTimeText, background.getWidth() - stringWidth, 14, 0xFF808080);
    }

    private static Component createBurnTimeText(int burnTime, Component usage) {
        return Component.translatable("gui.jei.category.smelting.time.seconds", burnTime / 20)
                .append(" (")
                .append(usage)
                .append(")");
    }
}
*/
