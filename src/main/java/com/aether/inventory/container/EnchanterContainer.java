package com.aether.inventory.container;

import java.util.List;

import com.aether.api.AetherAPI;
import com.aether.item.crafting.AetherRecipeTypes;
import com.google.common.collect.Lists;

import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class EnchanterContainer extends CustomSmeltingContainer {

	public EnchanterContainer(int id, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn, IIntArray p_i50104_6_) {
		super(AetherContainerTypes.ENCHANTER, AetherRecipeTypes.ENCHANTING, id, playerInventoryIn, furnaceInventoryIn, p_i50104_6_);
		replaceSlot(1, new EnchanterFuelSlot(this, furnaceInventoryIn, 1, 56, 53));
		replaceSlot(2, new CustomSmeltingResultSlot(playerInventoryIn.player, furnaceInventoryIn, 2, 116, 35));
	}

	public EnchanterContainer(int id, PlayerInventory playerInventoryIn) {
		this(id, playerInventoryIn, new Inventory(3), new IntArray(4));
	}

	@Override
	public List<RecipeBookCategories> getRecipeBookCategories() {
		return Lists.newArrayList(RecipeBookCategories.SEARCH, RecipeBookCategories.BUILDING_BLOCKS, RecipeBookCategories.MISC);
	}
	
	@Override
	public boolean isFuel(ItemStack stack) {
		return AetherAPI.isEnchantmentFuel(stack.getItem());
	}
	
}
