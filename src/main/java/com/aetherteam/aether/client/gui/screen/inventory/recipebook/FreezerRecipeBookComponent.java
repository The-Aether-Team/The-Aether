package com.aetherteam.aether.client.gui.screen.inventory.recipebook;

import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.Set;

public class FreezerRecipeBookComponent extends AbstractFurnaceRecipeBookComponent {
    private static final Component FILTER_NAME = Component.translatable("gui.aether.recipebook.toggleRecipes.freezable");

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected Set<Item> getFuelItems() {
        return FreezerBlockEntity.getFreezingMap().keySet();
    }
}
