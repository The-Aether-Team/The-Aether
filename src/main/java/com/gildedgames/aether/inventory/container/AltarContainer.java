package com.gildedgames.aether.inventory.container;

import java.util.List;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.crafting.AetherRecipeTypes;
import com.gildedgames.aether.inventory.container.slot.CustomSmeltingFuelSlot;
import com.gildedgames.aether.inventory.container.slot.CustomSmeltingResultSlot;
import com.gildedgames.aether.registry.AetherContainerTypes;
import com.google.common.collect.Lists;

import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class AltarContainer extends CustomSmeltingContainer {

	public AltarContainer(int id, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn, IIntArray furnaceDataIn) {
		super(AetherContainerTypes.ALTAR.get(), AetherRecipeTypes.ENCHANTING, RecipeBookCategory.CRAFTING, id, playerInventoryIn, furnaceInventoryIn, furnaceDataIn);
		replaceSlot(1, new CustomSmeltingFuelSlot(this, furnaceInventoryIn, 1, 56, 53));
		replaceSlot(2, new CustomSmeltingResultSlot(playerInventoryIn.player, furnaceInventoryIn, 2, 116, 35));
	}

	public AltarContainer(int id, PlayerInventory playerInventoryIn) {
		this(id, playerInventoryIn, new Inventory(3), new IntArray(4));
	}

	@Override
	public List<RecipeBookCategories> getRecipeBookCategories() {
		return Lists.newArrayList(RecipeBookCategories.CRAFTING_SEARCH, RecipeBookCategories.CRAFTING_BUILDING_BLOCKS, RecipeBookCategories.CRAFTING_MISC);
	}
	
	@Override
	public boolean isFuel(ItemStack stack) {
		return AetherAPI.isEnchantmentFuel(stack.getItem());
	}
	
}
