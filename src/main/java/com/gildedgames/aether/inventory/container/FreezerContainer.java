package com.gildedgames.aether.inventory.container;

import com.gildedgames.aether.registry.AetherRecipe.RecipeTypes;
import com.gildedgames.aether.entity.tile.FreezerTileEntity;
import com.gildedgames.aether.registry.AetherContainerTypes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

import java.util.List;

public class FreezerContainer extends AbstractFurnaceContainer
{
	public FreezerContainer(int windowId, PlayerInventory playerInventory) {
		super(AetherContainerTypes.FREEZER.get(), RecipeTypes.FREEZING, RecipeBookCategory.FURNACE, windowId, playerInventory);
	}

	public FreezerContainer(int windowId, PlayerInventory playerInventory, IInventory freezingInventory, IIntArray furnaceData) {
		super(AetherContainerTypes.FREEZER.get(), RecipeTypes.FREEZING, RecipeBookCategory.FURNACE, windowId, playerInventory, freezingInventory, furnaceData);
	}

	@Override
	public boolean isFuel(ItemStack stack) {
		return FreezerTileEntity.getFreezingMap().containsKey(stack.getItem());
	}
}
