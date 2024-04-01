package com.aetherteam.aether.integration.rei.categories.block;

import com.aetherteam.aether.integration.rei.AetherREIServerPlugin;
import com.aetherteam.aether.integration.rei.categories.BiomeTooltip;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.aetherteam.aether.recipe.recipes.block.PlacementConversionRecipe;
import com.aetherteam.aether.recipe.recipes.block.SwetBallRecipe;
import com.aetherteam.nitrogen.integration.rei.displays.BlockStateRecipeDisplay;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class BiomeParameterRecipeCategory<R extends AbstractBiomeParameterRecipe> extends AetherBlockStateRecipeCategory<R> implements BiomeTooltip {
    protected BiomeParameterRecipeCategory(String id, CategoryIdentifier<BlockStateRecipeDisplay<R>> uid, int width, int height, Renderer icon) {
        super(id, uid, width, height, icon);
    }

    @Override
    protected void populateTooltip(BlockStateRecipeDisplay<R> display, Tooltip tooltip) {
        if (display.getRecipe().getBiome().isPresent()) {
            this.populateBiomeInformation(display.getRecipe().getBiome().get().left(), display.getRecipe().getBiome().get().right(), tooltip);
        }
    }

    public static BiomeParameterRecipeCategory<PlacementConversionRecipe> placementConversion() {
        return new BiomeParameterRecipeCategory<>("placement_conversion", AetherREIServerPlugin.PLACEMENT_CONVERSION, 84, 28, EntryStacks.of(AetherItems.AETHER_PORTAL_FRAME.get()));
    }

    public static BiomeParameterRecipeCategory<SwetBallRecipe> swetBall() {
        return new BiomeParameterRecipeCategory<>("swet_ball_conversion", AetherREIServerPlugin.SWET_BALL_CONVERSION, 84, 28, EntryStacks.of(AetherItems.SWET_BALL.get()));
    }
}
