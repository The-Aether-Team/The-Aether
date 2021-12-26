package com.gildedgames.aether.common.item.tools.abilities;

import net.minecraft.world.item.ItemStack;

public interface IZaniteToolItem
{
	default float calculateIncrease(ItemStack tool, float original) {
		if (original == 6.0F) {
			int current = tool.getDamageValue();
			int maxDamage = tool.getMaxDamage();
			
			if (maxDamage - 50 <= current && current <= maxDamage) {
				return 12.0F;
			} else if (maxDamage - 110 <= current && current <= maxDamage - 51) {
				return 8.0F;
			} else if (maxDamage - 200 <= current && current <= maxDamage - 111) {
				return 6.0F;
			} else if (maxDamage - 239 <= current && current <= maxDamage - 201) {
				return 4.0F;
			} else {
				return 2.0F;
			}
		} else {
			return 1.0F;
		}
	}
}
