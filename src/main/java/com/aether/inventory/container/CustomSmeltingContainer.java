package com.aether.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IIntArray;

public abstract class CustomSmeltingContainer extends AbstractFurnaceContainer {
	
	protected CustomSmeltingContainer(ContainerType<? extends AbstractFurnaceContainer> containerTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn, int id, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn, IIntArray p_i50104_6_) {
		super(containerTypeIn, recipeTypeIn, id, playerInventoryIn, furnaceInventoryIn, p_i50104_6_);
	}

	protected CustomSmeltingContainer(ContainerType<? extends AbstractFurnaceContainer> containerTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn, int id, PlayerInventory playerInventoryIn) {
		super(containerTypeIn, recipeTypeIn, id, playerInventoryIn);
	}

	protected final void replaceSlot(int slotNumber, Slot slot) {
		inventorySlots.set(slotNumber, slot);
		slot.slotNumber = slotNumber;
	}
	
}
