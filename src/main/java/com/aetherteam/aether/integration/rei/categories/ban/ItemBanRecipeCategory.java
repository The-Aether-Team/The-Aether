package com.aetherteam.aether.integration.rei.categories.ban;

import com.aetherteam.aether.integration.rei.AetherREIServerPlugin;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.nitrogen.integration.rei.REIClientUtils;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleRecipeInput;

import java.util.List;

public class ItemBanRecipeCategory extends AbstractPlacementBanRecipeCategory<ItemStack, Ingredient, SingleRecipeInput, ItemBanRecipe> {
    public ItemBanRecipeCategory() {
        super("item_placement_ban", AetherREIServerPlugin.ITEM_PLACEMENT_BAN, EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(Items.FLINT_AND_STEEL)));
    }

    @Override
    public List<Widget> setupDisplay(PlacementBanRecipeDisplay<ItemBanRecipe> display, Rectangle bounds) {
        List<Widget> widgets = super.setupDisplay(display, bounds);

        Point startingPoint;
        if (display.getBypassBlock().isEmpty() || display.getBypassBlock().get().isEmpty()) {
            startingPoint = new Point(bounds.getCenterX() - 8, bounds.getCenterY() - 8);
        } else {
            startingPoint = startingOffset(bounds);
            startingPoint.translate(1, 1);
        }

        widgets.add(Widgets.createSlot(startingPoint).entries(REIClientUtils.setupRendering(display.getInputEntries().get(0), (tooltip) -> this.populateTooltip(display, tooltip))));

        return widgets;
    }
}
