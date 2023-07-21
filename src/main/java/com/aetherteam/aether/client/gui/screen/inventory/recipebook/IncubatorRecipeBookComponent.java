package com.aetherteam.aether.client.gui.screen.inventory.recipebook;

import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class IncubatorRecipeBookComponent extends RecipeBookComponent {
    private static final Component FILTER_NAME = Component.translatable("gui.aether.recipebook.toggleRecipes.incubatable");
    @Nullable
    private Ingredient fuels;

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(152, 182, 28, 18, RECIPE_BOOK_LOCATION);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
        }
    }

    @Override
    public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        this.ghostRecipe.setRecipe(recipe);
        Slot slot = slots.get(1);
        if (slot.getItem().isEmpty()) {
            if (this.fuels == null) {
                this.fuels = Ingredient.of(this.getFuelItems().stream().map(ItemStack::new));
            }
            this.ghostRecipe.addIngredient(this.fuels, slot.x, slot.y);
        }

        Ingredient ingredient = recipe.getIngredients().get(0);
        if (!ingredient.isEmpty()) {
            Slot slot1 = slots.get(0);
            this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
        }
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    protected Set<Item> getFuelItems() {
        return IncubatorBlockEntity.getIncubatingMap().keySet();
    }
}
