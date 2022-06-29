package com.gildedgames.aether.inventory.container;

import com.gildedgames.aether.block.entity.AltarBlockEntity;
import com.gildedgames.aether.inventory.AetherRecipeBookTypes;
import com.gildedgames.aether.recipe.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.inventory.AetherContainerTypes;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;

public class AltarMenu extends AbstractFurnaceMenu
{
	public AltarMenu(int windowId, Inventory playerInventory) {
		super(AetherContainerTypes.ALTAR.get(), RecipeTypes.ENCHANTING.get(), AetherRecipeBookTypes.ALTAR, windowId, playerInventory);
	}

	public AltarMenu(int windowId, Inventory playerInventory, Container enchantingInventory, ContainerData furnaceData) {
		super(AetherContainerTypes.ALTAR.get(), RecipeTypes.ENCHANTING.get(), AetherRecipeBookTypes.ALTAR, windowId, playerInventory, enchantingInventory, furnaceData);
	}

	@Override
	protected boolean isFuel(ItemStack stack) {
		return AltarBlockEntity.getEnchantingMap().containsKey(stack.getItem());
	}
}
