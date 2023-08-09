package com.aetherteam.aether.integration.crafttweaker;

import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.aether.FreezerFuelManager")
public class FreezerFuelManager {
    @ZenCodeType.Method
    public static void addFuel(IIngredient ingredient, int burnTime) {
        FuelManagerUtil.addFuelMethod(ingredient, burnTime, FreezerBlockEntity::addItemFreezingTime, FreezerBlockEntity::addItemsFreezingTime);
    }

    @ZenCodeType.Method
    public static void removeFuel(IIngredient ingredient) {
        FuelManagerUtil.removeFuelMethod(ingredient, FreezerBlockEntity::removeItemFreezingTime, FreezerBlockEntity::removeItemsFreezingTime);
    }
}
