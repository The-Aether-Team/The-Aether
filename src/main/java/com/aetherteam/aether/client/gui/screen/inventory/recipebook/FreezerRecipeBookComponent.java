package com.aetherteam.aether.client.gui.screen.inventory.recipebook;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Set;

public class FreezerRecipeBookComponent extends AbstractFurnaceRecipeBookComponent {
    protected static final ResourceLocation FILTER_LOCATION = new ResourceLocation(Aether.MODID, "textures/gui/inventory/filter.png");
    private static final Component FILTER_NAME = Component.translatable("gui.aether.recipebook.toggleRecipes.freezable");

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(0, 36, 28, 18, FILTER_LOCATION);
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected Set<Item> getFuelItems() {
        return FreezerBlockEntity.getFreezingMap().keySet();
    }
}
