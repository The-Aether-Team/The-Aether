package com.aether.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;

public abstract class CustomSmeltingContainer extends AbstractFurnaceContainer {

	protected CustomSmeltingContainer(ContainerType<?> containerType, IRecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory bookCategory, int id, PlayerInventory playerInventory) {
		super(containerType, recipeType, bookCategory, id, playerInventory);
	}

	protected CustomSmeltingContainer(ContainerType<?> containerType, IRecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory bookCategory, int id, PlayerInventory playerInventory, IInventory inventory, IIntArray intArray) {
		super(containerType, recipeType, bookCategory, id, playerInventory, inventory, intArray);
	}

	protected final void replaceSlot(int slotNumber, Slot slot) {
		inventorySlots.set(slotNumber, slot);
		slot.slotNumber = slotNumber;
	}
	
}
