package com.gildedgames.aether.client.gui.screen.inventory.recipebook;

import com.gildedgames.aether.common.block.entity.AltarBlockEntity;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;

import javax.annotation.Nonnull;
import java.util.Set;

public class AltarRecipeBookComponent extends AbstractFurnaceRecipeBookComponent {
    private static final Component FILTER_NAME = new TranslatableComponent("gui.aether.recipebook.toggleRecipes.enchantable");

    @Override
    @Nonnull
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    @Nonnull
    protected Set<Item> getFuelItems() {
        return AltarBlockEntity.getEnchantingMap().keySet();
    }
}
