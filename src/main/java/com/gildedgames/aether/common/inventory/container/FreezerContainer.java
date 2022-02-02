package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.block.entity.FreezerBlockEntity;
import com.gildedgames.aether.common.registry.AetherContainerTypes;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ContainerData;

public class FreezerContainer extends AbstractFurnaceMenu
{
	public FreezerContainer(int windowId, Inventory playerInventory) {
		super(AetherContainerTypes.FREEZER.get(), RecipeTypes.FREEZING, RecipeBookType.FURNACE, windowId, playerInventory);
	}

	public FreezerContainer(int windowId, Inventory playerInventory, Container freezingInventory, ContainerData furnaceData) {
		super(AetherContainerTypes.FREEZER.get(), RecipeTypes.FREEZING, RecipeBookType.FURNACE, windowId, playerInventory, freezingInventory, furnaceData);
	}

	@Override
	protected boolean isFuel(ItemStack stack) {
		return FreezerBlockEntity.getFreezingMap().containsKey(stack.getItem());
	}
}
