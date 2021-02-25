package com.gildedgames.aether.api.freezables;

import javax.annotation.Nonnull;

import com.gildedgames.aether.api.internal.AbstractAetherSmeltingFuel;

import net.minecraft.item.Item;

public class AetherFreezableFuel extends AbstractAetherSmeltingFuel<AetherFreezableFuel> {

	public AetherFreezableFuel(@Nonnull Item fuelItem, int timeGiven) {
		super(fuelItem, timeGiven);
	}
	
	@Override
	protected boolean canEqual(Object obj) {
		return obj instanceof AetherFreezableFuel;
	}

}
