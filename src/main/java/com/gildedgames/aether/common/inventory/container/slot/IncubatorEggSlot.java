package com.gildedgames.aether.common.inventory.container.slot;

import com.gildedgames.aether.common.inventory.container.IncubatorContainer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class IncubatorEggSlot extends Slot
{
	private final IncubatorContainer container;
	
	public IncubatorEggSlot(IncubatorContainer container, Container incubatorInventoryIn, int index, int xPosition, int yPosition) {
		super(incubatorInventoryIn, index, xPosition, yPosition);
		this.container = container;
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
	
	@Override
	public boolean mayPlace(@Nonnull ItemStack stack) {
		return this.container.isEgg(stack);
	}
}
