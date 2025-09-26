package com.aetherteam.aether.client.gui.screen.inventory.recipebook;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Set;
import java.util.stream.Collectors;

public class AltarRecipeBookComponent extends AbstractFurnaceRecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
        ResourceLocation.fromNamespaceAndPath(Aether.MODID, "recipe_book/altar_filter_enabled"),
        ResourceLocation.fromNamespaceAndPath(Aether.MODID, "recipe_book/altar_filter_disabled"),
        ResourceLocation.fromNamespaceAndPath(Aether.MODID, "recipe_book/altar_filter_enabled_highlighted"),
        ResourceLocation.fromNamespaceAndPath(Aether.MODID, "recipe_book/altar_filter_disabled_highlighted")
    );
    private static final Component FILTER_NAME = Component.translatable("gui.aether.recipebook.toggleRecipes.enchantable");

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected Set<Item> getFuelItems() {
        return BuiltInRegistries.ITEM.getDataMap(AetherDataMaps.ALTAR_FUEL).keySet().stream().map(BuiltInRegistries.ITEM::get).collect(Collectors.toSet());
    }
}
