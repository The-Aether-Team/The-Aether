package com.gildedgames.aether.common.inventory.container.slot;

import com.gildedgames.aether.common.inventory.container.IncubatorContainer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class IncubatorFuelSlot extends Slot
{
	private final IncubatorContainer container;
	
	public IncubatorFuelSlot(IncubatorContainer container, Container incubatorInventoryIn, int index, int xPosition, int yPosition) {
		super(incubatorInventoryIn, index, xPosition, yPosition);
		this.container = container;
	}
	
	@Override
	public boolean mayPlace(@Nonnull ItemStack stack) {
		return this.container.isFuel(stack);
	}
}
