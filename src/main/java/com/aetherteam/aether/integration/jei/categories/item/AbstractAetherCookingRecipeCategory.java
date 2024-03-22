package com.aetherteam.aether.integration.jei.categories.item;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.integration.jei.categories.AbstractRecipeCategory;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;

public abstract class AbstractAetherCookingRecipeCategory<T> extends AbstractRecipeCategory<T> {
    public static final ResourceLocation FLAME_TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/sprites/menu/lit_progress.png");
    public static final ResourceLocation ARROW_TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/sprites/menu/burn_progress.png");
    public static final ResourceLocation INCUBATION_PROGRESS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/sprites/menu/incubation_progress.png");
    protected final IDrawable fuelIndicator;
    protected final IDrawableAnimated animatedProgressArrow;

    public AbstractAetherCookingRecipeCategory(String id, ResourceLocation uid, IDrawable background, IDrawable icon, IDrawable fuelIndicator, IDrawableAnimated animatedProgressArrow, RecipeType<T> recipeType) {
        super(id, uid, background, icon, recipeType);
        this.fuelIndicator = fuelIndicator;
        this.animatedProgressArrow = animatedProgressArrow;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.aether.jei." + this.id);
    }

    protected void drawExperience(AbstractCookingRecipe recipe, GuiGraphics guiGraphics, int y, IDrawable background) {
        float experience = recipe.getExperience();
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            Font fontRenderer = Minecraft.getInstance().font;
            int stringWidth = fontRenderer.width(experienceString);
            guiGraphics.drawString(fontRenderer, experienceString, background.getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }

    protected void drawCookingTime(GuiGraphics guiGraphics, int y, int time, IDrawable background) {
        if (time > 0) {
            int cookTimeSeconds = time / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Font fontRenderer = Minecraft.getInstance().font;
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, background.getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }
}
