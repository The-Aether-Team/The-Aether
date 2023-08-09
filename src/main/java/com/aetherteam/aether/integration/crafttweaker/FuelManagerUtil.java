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
    /**
     * Sets up a method for adding fuel with CraftTweaker, supporting a single or multiple fuel items.
     * @param ingredient The {@link IIngredient} for the fuel item, which will be converted into {@link ItemLike}s.
     * @param burnTime The {@link Integer} burn time for an item or multiple items.
     * @param addItem A {@link BiConsumer} that takes an {@link ItemLike} for a fuel item and an {@link Integer} for the burn time,
     *                this is a parameter for a method from the block entity that the fuel is for.
     * @param addItems A {@link BiConsumer} that takes multiple {@link ItemLike}s fuel items and an {@link Integer} for the burn time,
     *                 this is a parameter for a method from the block entity that the fuel is for.
     */
    public static void addFuelMethod(IIngredient ingredient, int burnTime, BiConsumer<ItemLike, Integer> addItem, BiConsumer<ItemLike[], Integer> addItems) {
        ItemLike[] items = Stream.of(ingredient.getItems()).map(IItemStack::asItemLike).toArray(ItemLike[]::new);
        if (items.length == 1) {
            CraftTweakerAPI.apply(new AddFuelAction<>(addItem, items[0], burnTime));
        } else if (items.length > 1) {
            CraftTweakerAPI.apply(new AddFuelAction<>(addItems, items, burnTime));
        }
    }

    /**
     * Sets up a method for removing fuel with CraftTweaker, supporting a single or multiple fuel items.
     * @param ingredient The {@link IIngredient} for the fuel item, which will be converted into {@link ItemLike}s.
     * @param removeItem A {@link Consumer} that takes an {@link ItemLike} for a fuel item,
     *                   this is a parameter for a method from the block entity that the fuel is for.
     * @param removeItems A {@link Consumer} that takes multiple {@link ItemLike}s for fuel items,
     *                    this is a parameter for a method from the block entity that the fuel is for.
     */
    public static void removeFuelMethod(IIngredient ingredient, Consumer<ItemLike> removeItem, Consumer<ItemLike[]> removeItems) {
        ItemLike[] items = Stream.of(ingredient.getItems()).map(IItemStack::asItemLike).toArray(ItemLike[]::new);
        if (items.length == 1) {
            CraftTweakerAPI.apply(new RemoveFuelAction<>(removeItem, items[0]));
        } else if (items.length > 1) {
            CraftTweakerAPI.apply(new RemoveFuelAction<>(removeItems, items));
        }
    }
}
