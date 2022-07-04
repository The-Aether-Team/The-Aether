package com.gildedgames.aether.inventory.menu;

import com.gildedgames.aether.blockentity.FreezerBlockEntity;
import com.gildedgames.aether.inventory.AetherRecipeBookTypes;
import com.gildedgames.aether.recipe.AetherRecipeTypes;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;

public class FreezerMenu extends AbstractFurnaceMenu {
	public FreezerMenu(int windowId, Inventory playerInventory) {
		super(AetherMenuTypes.FREEZER.get(), AetherRecipeTypes.FREEZING.get(), AetherRecipeBookTypes.FREEZER, windowId, playerInventory);
	}

	public FreezerMenu(int windowId, Inventory playerInventory, Container freezingInventory, ContainerData furnaceData) {
		super(AetherMenuTypes.FREEZER.get(), AetherRecipeTypes.FREEZING.get(), AetherRecipeBookTypes.FREEZER, windowId, playerInventory, freezingInventory, furnaceData);
	}

	@Override
	protected boolean isFuel(ItemStack stack) {
		return FreezerBlockEntity.getFreezingMap().containsKey(stack.getItem());
	}
}
