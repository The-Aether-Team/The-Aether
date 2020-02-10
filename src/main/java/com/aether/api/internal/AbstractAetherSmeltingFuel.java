package com.aether.api.internal;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AbstractAetherSmeltingFuel<T extends AbstractAetherSmeltingFuel<T>> extends ForgeRegistryEntry<T> {
	private final ItemStack fuelStack;
	private final int timeGiven;
	
	public AbstractAetherSmeltingFuel(@Nonnull Block fuelBlock, int timeGiven) {
		this(new ItemStack(fuelBlock), timeGiven);
	}
	
	public AbstractAetherSmeltingFuel(@Nonnull Item fuelItem, int timeGiven) {
		this(new ItemStack(fuelItem), timeGiven);
	}
	
	public AbstractAetherSmeltingFuel(@Nonnull ItemStack fuelStack, int timeGiven) {
		Validate.notNull(fuelStack, "fuel stack was null");
		
		this.fuelStack = fuelStack;
		this.timeGiven = timeGiven;
		
		this.setRegistryName(fuelStack.getItem().getRegistryName());
	}
	
	public @Nonnull ItemStack getFuelStack() {
		return fuelStack;
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
			
			return ItemStack.areItemsEqualIgnoreDurability(this.getFuelStack(), fuel.getFuelStack());
		} else {
			return false;
		}
	}
	
}
