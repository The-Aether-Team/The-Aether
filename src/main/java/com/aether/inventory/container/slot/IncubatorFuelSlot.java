package com.aether.inventory.container.slot;

import com.aether.inventory.container.IncubatorContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class IncubatorFuelSlot extends Slot {
	private final IncubatorContainer container;
	
	public IncubatorFuelSlot(IncubatorContainer container, IInventory incubatorInventoryIn, int index, int xPosition, int yPosition) {
		super(incubatorInventoryIn, index, xPosition, yPosition);
		this.container = container;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return this.container.isFuel(stack);
	}

}
