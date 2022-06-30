package com.gildedgames.aether.inventory.menu.slot;

import com.gildedgames.aether.inventory.menu.IncubatorMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class IncubatorFuelSlot extends Slot
{
	private final IncubatorMenu container;
	
	public IncubatorFuelSlot(IncubatorMenu container, Container incubatorInventoryIn, int index, int xPosition, int yPosition) {
		super(incubatorInventoryIn, index, xPosition, yPosition);
		this.container = container;
	}
	
	@Override
	public boolean mayPlace(@Nonnull ItemStack stack) {
		return this.container.isFuel(stack);
	}
}
