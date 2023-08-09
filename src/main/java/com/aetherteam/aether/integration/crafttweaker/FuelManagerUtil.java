package com.aetherteam.aether.integration.crafttweaker;

import com.aetherteam.aether.integration.crafttweaker.actions.AddFuelAction;
import com.aetherteam.aether.integration.crafttweaker.actions.RemoveFuelAction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class FuelManagerUtil {
    public static void addFuelMethod(IIngredient ingredient, int burnTime, BiConsumer<ItemLike, Integer> addItem, BiConsumer<ItemLike[], Integer> addItems) {
        ItemLike[] items = Stream.of(ingredient.getItems()).map(IItemStack::asItemLike).toArray(ItemLike[]::new);
        if (items.length == 1) {
            CraftTweakerAPI.apply(new AddFuelAction<>(addItem, items[0], burnTime));
        } else if (items.length > 1) {
            CraftTweakerAPI.apply(new AddFuelAction<>(addItems, items, burnTime));
        }
    }

    public static void removeFuelMethod(IIngredient ingredient, Consumer<ItemLike> removeItem, Consumer<ItemLike[]> removeItems) {
        ItemLike[] items = Stream.of(ingredient.getItems()).map(IItemStack::asItemLike).toArray(ItemLike[]::new);
        if (items.length == 1) {
            CraftTweakerAPI.apply(new RemoveFuelAction<>(removeItem, items[0]));
        } else if (items.length > 1) {
            CraftTweakerAPI.apply(new RemoveFuelAction<>(removeItems, items));
        }
    }
}
