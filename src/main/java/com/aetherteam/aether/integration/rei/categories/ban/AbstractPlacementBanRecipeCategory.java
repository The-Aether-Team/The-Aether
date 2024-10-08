package com.aetherteam.aether.integration.rei.categories.ban;

import com.aetherteam.aether.integration.rei.categories.BiomeTooltip;
import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.nitrogen.integration.rei.REIClientUtils;
import com.aetherteam.nitrogen.integration.rei.categories.AbstractRecipeCategory;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractPlacementBanRecipeCategory<T, S extends Predicate<T>, F extends RecipeInput, R extends AbstractPlacementBanRecipe<T, S, F>> extends AbstractRecipeCategory<PlacementBanRecipeDisplay<R>> implements BiomeTooltip {
    public AbstractPlacementBanRecipeCategory(String id, CategoryIdentifier<PlacementBanRecipeDisplay<R>> uid, Renderer icon) {
        super(id, uid, 116, 18, icon);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.aether.jei." + this.id);
    }

    @Override
    public List<Widget> setupDisplay(PlacementBanRecipeDisplay<R> display, Rectangle bounds) {
        List<Widget> widgets = super.setupDisplay(display, bounds);
        Optional<BlockStateIngredient> bypassBlock = display.getBypassBlock();

        if (bypassBlock.isPresent() && !bypassBlock.get().isEmpty()) {
            Point bypassPoint = startingOffset(bounds);
            bypassPoint.translate(99, 1);

            Slot bypassSlot = Widgets.createSlot(bypassPoint).entries(REIClientUtils.setupRendering(display.getInputEntries().get(1), bypassBlock.get().getPairs(), (tooltip) -> this.populateTooltip(display, tooltip)));
            widgets.add(bypassSlot);

            MutableComponent text = Component.translatable("gui.aether.jei.bypass");
            Point labelPoint = new Point(bounds.getCenterX(), bounds.getCenterY() - (Minecraft.getInstance().font.lineHeight / 2));
            widgets.add(Widgets.createLabel(labelPoint, text).color(0xFF808080, 0xFFBBBBBB));
        }
        return widgets;
    }

    @Override
    public int getDisplayWidth(PlacementBanRecipeDisplay<R> display) {
        return super.getDisplayWidth(display);
    }

    protected void populateTooltip(PlacementBanRecipeDisplay<R> display, Tooltip tooltip) {
        if (Minecraft.getInstance().level != null) {
            this.populateBiomeInformation(display.getBiome().left(), display.getBiome().right(), tooltip);
        }
    }
}
