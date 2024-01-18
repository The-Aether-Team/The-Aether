package com.aetherteam.aether.integration.rei.categories.ban;

import com.aetherteam.aether.integration.rei.categories.BiomeTooltip;
import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.nitrogen.integration.rei.REIClientUtils;
import com.aetherteam.nitrogen.integration.rei.categories.AbstractRecipeCategory;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mezz.jei.common.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractPlacementBanRecipeCategory<T, S extends Predicate<T>, R extends AbstractPlacementBanRecipe<T, S>> extends AbstractRecipeCategory<PlacementBanRecipeDisplay<R>> implements BiomeTooltip {

    public AbstractPlacementBanRecipeCategory(String id, CategoryIdentifier<PlacementBanRecipeDisplay<R>> uid, Renderer icon) {
        super(id, uid, 116, 18, icon);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.aether.jei." + this.id);
    }

    @Override
    public List<Widget> setupDisplay(PlacementBanRecipeDisplay<R> display, Rectangle bounds) {
        var widgets = super.setupDisplay(display, bounds);
        var bypassBlock = display.getBypassBlock();

        if (bypassBlock != null && !bypassBlock.isEmpty()) {
            var bypassPoint = startingOffset(bounds);
            bypassPoint.translate(99, 1);

            var bypassSlot = Widgets.createSlot(bypassPoint)
                    .entries(REIClientUtils.setupRendering(display.getInputEntries().get(0), bypassBlock.getPairs(), (tooltip) -> this.populateTooltip(display, tooltip)));
            widgets.add(bypassSlot);

            var text = Component.translatable(Translator.translateToLocalFormatted("gui.aether.jei.bypass"));
            var labelPoint = new Point(bounds.getCenterX(), bounds.getCenterY() - (Minecraft.getInstance().font.lineHeight / 2));
            widgets.add(Widgets.createLabel(labelPoint, text).color(0xFF808080, 0xFFBBBBBB));
        }

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return super.getDisplayHeight();
    }

    @Override
    public int getDisplayWidth(PlacementBanRecipeDisplay<R> display) {
        return super.getDisplayWidth(display);
    }

    protected void populateTooltip(PlacementBanRecipeDisplay<R> display, Tooltip tooltip) {
        if (Minecraft.getInstance().level == null) return;

        this.populateBiomeInformation(display.getBiomeKey(), display.getBiomeTag(), tooltip);
    }
}
