package com.gildedgames.aether.inventory.menu.slot;

import com.gildedgames.aether.inventory.menu.AbstractAetherFurnaceMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class AetherFurnaceFuelSlot extends Slot {
    private final AbstractAetherFurnaceMenu menu;

    public AetherFurnaceFuelSlot(AbstractAetherFurnaceMenu furnaceMenu, Container furnaceContainer, int slot, int x, int y) {
        super(furnaceContainer, slot, x, y);
        this.menu = furnaceMenu;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return this.menu.isFuel(stack);
    }

    @Override
    public int getMaxStackSize(@Nonnull ItemStack stack) {
        return super.getMaxStackSize(stack);
    }
}