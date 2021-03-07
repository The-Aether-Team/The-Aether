package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.entity.tile.AltarTileEntity;
import com.gildedgames.aether.common.registry.AetherContainerTypes;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

public class AltarContainer extends AbstractFurnaceContainer
{
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
