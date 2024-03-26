package com.aetherteam.aether.inventory.menu.slot;

import com.aetherteam.aether.inventory.menu.IncubatorMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class IncubatorFuelSlot extends Slot {
    private final IncubatorMenu menu;

    public IncubatorFuelSlot(IncubatorMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.menu.isFuel(stack);
    }
}
