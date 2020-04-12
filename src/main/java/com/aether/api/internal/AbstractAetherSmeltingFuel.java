package com.aether.api.internal;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;

import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AbstractAetherSmeltingFuel<T extends AbstractAetherSmeltingFuel<T>> extends ForgeRegistryEntry<T> {
	private final Item fuelItem;
	private final int timeGiven;
	
	public AbstractAetherSmeltingFuel(@Nonnull Item fuelStack, int timeGiven) {
		Validate.notNull(fuelStack, "fuel stack was null");
		
		this.fuelItem = fuelStack;
		this.timeGiven = timeGiven;
		
		this.setRegistryName(fuelStack.getItem().getRegistryName());
	}
	
	public @Nonnull Item getFuelItem() {
		return fuelItem;
	}
	
	public int getTimeGiven() {
		return timeGiven;
	}
	
	protected boolean canEqual(Object obj) {
		return obj instanceof AbstractAetherSmeltingFuel;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (this.canEqual(obj) && ((AbstractAetherSmeltingFuel<?>) obj).canEqual(this)) {
			AbstractAetherSmeltingFuel<?> fuel = (AbstractAetherSmeltingFuel<?>) obj;
			
			return fuel.getFuelItem() == this.getFuelItem();
		} else {
			return false;
		}
	}
	
}
