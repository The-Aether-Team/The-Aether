package com.aether.api.freezables;

import javax.annotation.Nonnull;

import com.aether.api.internal.AbstractAetherSmeltingFuel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherFreezableFuel extends AbstractAetherSmeltingFuel<AetherFreezableFuel> {

	public AetherFreezableFuel(@Nonnull Block fuelBlock, int timeGiven) {
		super(fuelBlock, timeGiven);
	}

	public AetherFreezableFuel(@Nonnull Item fuelItem, int timeGiven) {
		super(fuelItem, timeGiven);
	}

	public AetherFreezableFuel(@Nonnull ItemStack fuelStack, int timeGiven) {
		super(fuelStack, timeGiven);
	}
	
	@Override
	protected boolean canEqual(Object obj) {
		return obj instanceof AetherFreezableFuel;
	}

}
