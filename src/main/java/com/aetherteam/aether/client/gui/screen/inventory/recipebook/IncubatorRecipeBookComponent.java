package com.aetherteam.aether.client.gui.screen.inventory.recipebook;

import com.aetherteam.aether.api.AetherDataMaps;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IncubatorRecipeBookComponent extends RecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            new ResourceLocation("recipe_book/furnace_filter_enabled"),
            new ResourceLocation("recipe_book/furnace_filter_disabled"),
            new ResourceLocation("recipe_book/furnace_filter_enabled_highlighted"),
            new ResourceLocation("recipe_book/furnace_filter_disabled_highlighted")
    );
    private static final Component FILTER_NAME = Component.translatable("gui.aether.recipebook.toggleRecipes.incubatable");
    @Nullable
    private Ingredient fuels;

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
        }
    }

    @Override
    public void setupGhostRecipe(RecipeHolder<?> recipe, List<Slot> slots) {
        this.ghostRecipe.setRecipe(recipe);
        Slot fuelSlot = slots.get(1);
        if (fuelSlot.getItem().isEmpty()) {
            if (this.fuels == null) {
                this.fuels = Ingredient.of(this.getFuelItems().stream().map(ItemStack::new));
            }
            this.ghostRecipe.addIngredient(this.fuels, fuelSlot.x, fuelSlot.y);
        }

        Ingredient ingredient = recipe.value().getIngredients().get(0);
        if (!ingredient.isEmpty()) {
            Slot eggSlot = slots.get(0);
            this.ghostRecipe.addIngredient(ingredient, eggSlot.x, eggSlot.y);
        }
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    protected Set<Item> getFuelItems() {
        return BuiltInRegistries.ITEM.getDataMap(AetherDataMaps.INCUBATOR_FUEL).keySet().stream().map(BuiltInRegistries.ITEM::get).collect(Collectors.toSet());
    }
}
