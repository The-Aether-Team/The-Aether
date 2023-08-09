package com.aetherteam.aether.integration.crafttweaker;

import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.aether.IncubatorFuelManager")
public class IncubatorFuelManager {
    @ZenCodeType.Method
    public static void addFuel(IIngredient ingredient, int burnTime) {
        FuelManagerUtil.addFuelMethod(ingredient, burnTime, IncubatorBlockEntity::addItemIncubatingTime, IncubatorBlockEntity::addItemsIncubatingTime);
    }

    @ZenCodeType.Method
    public static void removeFuel(IIngredient ingredient) {
        FuelManagerUtil.removeFuelMethod(ingredient, IncubatorBlockEntity::removeItemIncubatingTime, IncubatorBlockEntity::removeItemsIncubatingTime);
    }
}
