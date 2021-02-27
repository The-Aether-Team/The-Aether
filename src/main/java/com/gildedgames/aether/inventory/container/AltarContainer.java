package com.gildedgames.aether.inventory.container;

import com.gildedgames.aether.registry.AetherRecipe.RecipeTypes;
import com.gildedgames.aether.entity.tile.AltarTileEntity;
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

public class AltarContainer extends AbstractFurnaceContainer
{
	//TODO: Recipe Book
	public AltarContainer(int windowId, PlayerInventory playerInventory) {
		super(AetherContainerTypes.ALTAR.get(), RecipeTypes.ENCHANTING, RecipeBookCategory.FURNACE, windowId, playerInventory);
	}

	public AltarContainer(int windowId, PlayerInventory playerInventory, IInventory enchantingInventory, IIntArray furnaceData) {
		super(AetherContainerTypes.ALTAR.get(), RecipeTypes.ENCHANTING, RecipeBookCategory.FURNACE, windowId, playerInventory, enchantingInventory, furnaceData);
	}

	@Override
	public boolean isFuel(ItemStack stack) {
		return AltarTileEntity.getEnchantingMap().containsKey(stack.getItem());
	}
}
