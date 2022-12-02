package com.gildedgames.aether.inventory.menu.slot;

import com.gildedgames.aether.inventory.menu.AbstractAetherFurnaceMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AetherFurnaceFuelSlot extends Slot {
    private final AbstractAetherFurnaceMenu menu;

    public AetherFurnaceFuelSlot(AbstractAetherFurnaceMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.menu.isFuel(stack);
    }
}