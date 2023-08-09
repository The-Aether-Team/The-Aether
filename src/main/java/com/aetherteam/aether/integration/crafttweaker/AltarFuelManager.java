package com.aetherteam.aether.integration.crafttweaker;

import com.aetherteam.aether.blockentity.AltarBlockEntity;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.aether.AltarFuelManager")
public class AltarFuelManager {
    @ZenCodeType.Method
    public static void addFuel(IIngredient ingredient, int burnTime) {
        FuelManagerUtil.addFuelMethod(ingredient, burnTime, AltarBlockEntity::addItemEnchantingTime, AltarBlockEntity::addItemsEnchantingTime);
    }

    @ZenCodeType.Method
    public static void removeFuel(IIngredient ingredient) {
        FuelManagerUtil.removeFuelMethod(ingredient, AltarBlockEntity::removeItemEnchantingTime, AltarBlockEntity::removeItemsEnchantingTime);
    }
}
