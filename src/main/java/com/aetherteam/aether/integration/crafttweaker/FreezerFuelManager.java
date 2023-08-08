package com.aetherteam.aether.integration.crafttweaker;

import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import com.aetherteam.aether.integration.crafttweaker.actions.AddFuelAction;
import com.aetherteam.aether.integration.crafttweaker.actions.RemoveFuelAction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.MCTag;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.aether.FreezerFuelManager")
public class FreezerFuelManager {
    @ZenCodeType.Method
    public static void addFuel(IItemStack item, int burnTime) {
        CraftTweakerAPI.apply(new AddFuelAction<>(FreezerBlockEntity::addItemFreezingTime, FreezerBlockEntity::removeItemFreezingTime, item.asItemLike(), burnTime));
    }

    @ZenCodeType.Method
    public static void addFuelTag(MCTag tag, int burnTime) {
        CraftTweakerAPI.apply(new AddFuelAction<>(FreezerBlockEntity::addItemTagFreezingTime, FreezerBlockEntity::removeItemTagFreezingTime, tag.getTagKey(), burnTime));
    }

    @ZenCodeType.Method
    public static void removeFuel(IItemStack item, int burnTime) {
        CraftTweakerAPI.apply(new RemoveFuelAction<>(FreezerBlockEntity::removeItemFreezingTime, FreezerBlockEntity::addItemFreezingTime, item.asItemLike(), burnTime));
    }

    @ZenCodeType.Method
    public static void removeFuelTag(MCTag tag, int burnTime) {
        CraftTweakerAPI.apply(new RemoveFuelAction<>(FreezerBlockEntity::removeItemTagFreezingTime, FreezerBlockEntity::addItemTagFreezingTime, tag.getTagKey(), burnTime));
    }
}
