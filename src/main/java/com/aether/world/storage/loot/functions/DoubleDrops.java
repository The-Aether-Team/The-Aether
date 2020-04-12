package com.aether.world.storage.loot.functions;

import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class DoubleDrops extends LootFunction {

	protected DoubleDrops(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected ItemStack doApply(ItemStack stack, LootContext context) {
		ItemStack tool = context.get(LootParameters.TOOL);
		if (tool != null) {
			
		}
	}

}
