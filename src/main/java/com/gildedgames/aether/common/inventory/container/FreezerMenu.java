package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.registry.AetherRecipeBookTypes;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.block.entity.FreezerBlockEntity;
import com.gildedgames.aether.common.registry.AetherContainerTypes;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;

public class FreezerMenu extends AbstractFurnaceMenu {
	public FreezerMenu(int windowId, Inventory playerInventory) {
		super(AetherContainerTypes.FREEZER.get(), RecipeTypes.FREEZING.get(), AetherRecipeBookTypes.FREEZER, windowId, playerInventory);
	}

	public FreezerMenu(int windowId, Inventory playerInventory, Container freezingInventory, ContainerData furnaceData) {
		super(AetherContainerTypes.FREEZER.get(), RecipeTypes.FREEZING.get(), AetherRecipeBookTypes.FREEZER, windowId, playerInventory, freezingInventory, furnaceData);
	}

	@Override
	protected boolean isFuel(ItemStack stack) {
		return FreezerBlockEntity.getFreezingMap().containsKey(stack.getItem());
	}
}
