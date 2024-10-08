package com.aetherteam.aether.integration.rei.categories.ban;

import com.aetherteam.aether.integration.rei.AetherREIServerPlugin;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.nitrogen.integration.rei.REIClientUtils;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.input.BlockStateRecipeInput;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BlockBanRecipeCategory extends AbstractPlacementBanRecipeCategory<BlockState, BlockStateIngredient, BlockStateRecipeInput, BlockBanRecipe> {
    public BlockBanRecipeCategory() {
        super("block_placement_ban", AetherREIServerPlugin.BLOCK_PLACEMENT_BAN, EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(Blocks.TORCH)));
    }

    @Override
    public List<Widget> setupDisplay(PlacementBanRecipeDisplay<BlockBanRecipe> display, Rectangle bounds) {
        List<Widget> widgets = super.setupDisplay(display, bounds);

        Point startingPoint;
        if (display.getBypassBlock().isEmpty() || display.getBypassBlock().get().isEmpty()) {
            startingPoint = new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 8);
        } else {
            startingPoint = startingOffset(bounds);
            startingPoint.translate(1, 1);
        }

        if (display.getBlockStateIngredient() != null) {
            widgets.add(Widgets.createSlot(startingPoint).entries(REIClientUtils.setupRendering(display.getInputEntries().get(0), display.getBlockStateIngredient().getPairs(), (tooltip) -> this.populateTooltip(display, tooltip))));
        }

        return widgets;
    }
}
