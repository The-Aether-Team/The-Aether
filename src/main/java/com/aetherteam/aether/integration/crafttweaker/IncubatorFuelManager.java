package com.aetherteam.aether.integration.crafttweaker;

import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
import com.aetherteam.aether.integration.crafttweaker.actions.AddFuelAction;
import com.aetherteam.aether.integration.crafttweaker.actions.RemoveFuelAction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.MCTag;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.aether.IncubatorFuelManager")
public class IncubatorFuelManager {
    @ZenCodeType.Method
    public static void addFuel(IItemStack item, int burnTime) {
        CraftTweakerAPI.apply(new AddFuelAction<>(IncubatorBlockEntity::addItemIncubatingTime, IncubatorBlockEntity::removeItemIncubatingTime, item.asItemLike(), burnTime));
    }

    @ZenCodeType.Method
    public static void addFuelTag(MCTag tag, int burnTime) {
        CraftTweakerAPI.apply(new AddFuelAction<>(IncubatorBlockEntity::addItemTagIncubatingTime, IncubatorBlockEntity::removeItemTagIncubatingTime, tag.getTagKey(), burnTime));
    }

    @ZenCodeType.Method
    public static void removeFuel(IItemStack item, int burnTime) {
        CraftTweakerAPI.apply(new RemoveFuelAction<>(IncubatorBlockEntity::removeItemIncubatingTime, IncubatorBlockEntity::addItemIncubatingTime, item.asItemLike(), burnTime));
    }

    @ZenCodeType.Method
    public static void removeFuelTag(MCTag tag, int burnTime) {
        CraftTweakerAPI.apply(new RemoveFuelAction<>(IncubatorBlockEntity::removeItemTagIncubatingTime, IncubatorBlockEntity::addItemTagIncubatingTime, tag.getTagKey(), burnTime));
    }
}
