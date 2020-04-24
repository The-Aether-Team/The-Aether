package com.aether.item;

import net.minecraft.item.ItemStack;

public interface IZaniteToolItem {

	default float calculateIncrease(ItemStack tool, float original) {
		int current = tool.getDamage();
		
		if (original == 4.0f) {
			int maxDamage = tool.getMaxDamage();
			
			if (maxDamage - 50 <= current && current <= maxDamage) {
				return 12.0f;
			}
			else if (maxDamage - 110 <= current && current <= maxDamage - 51) {
				return 8.0f;
			}
			else if (maxDamage - 200 <= current && current <= maxDamage - 111) {
				return 6.0f;
			}
			else if (maxDamage - 239 <= current && current <= maxDamage - 201) {
				return 4.0f;
			}
			else {
				return 2.0f;
			}
		}
		else {
			return 1.0f;
		}
	}
	
}
