package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.entity.tile.FreezerTileEntity;
import com.gildedgames.aether.common.registry.AetherContainerTypes;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

public class FreezerContainer extends AbstractFurnaceContainer
{
	public FreezerContainer(int windowId, PlayerInventory playerInventory) {
		super(AetherContainerTypes.FREEZER.get(), RecipeTypes.FREEZING, RecipeBookCategory.FURNACE, windowId, playerInventory);
	}

	public FreezerContainer(int windowId, PlayerInventory playerInventory, IInventory freezingInventory, IIntArray furnaceData) {
		super(AetherContainerTypes.FREEZER.get(), RecipeTypes.FREEZING, RecipeBookCategory.FURNACE, windowId, playerInventory, freezingInventory, furnaceData);
	}

	@Override
	protected boolean isFuel(ItemStack stack) {
		return FreezerTileEntity.getFreezingMap().containsKey(stack.getItem());
	}
}
