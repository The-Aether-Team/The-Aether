package com.aether.api.enchantments;

import javax.annotation.Nonnull;

import com.aether.api.internal.AbstractAetherSmeltingFuel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherEnchantmentFuel extends AbstractAetherSmeltingFuel<AetherEnchantmentFuel> {

	public AetherEnchantmentFuel(@Nonnull Block fuelBlock, int timeGiven) {
		super(fuelBlock, timeGiven);
	}

	public AetherEnchantmentFuel(@Nonnull Item fuelItem, int timeGiven) {
		super(fuelItem, timeGiven);
	}

	public AetherEnchantmentFuel(@Nonnull ItemStack fuelStack, int timeGiven) {
		super(fuelStack, timeGiven);
	}
	
	@Override
	protected boolean canEqual(Object obj) {
		return obj instanceof AetherEnchantmentFuel;
	}
	
}
