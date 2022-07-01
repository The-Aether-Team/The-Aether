package com.gildedgames.aether.inventory.menu;

import com.gildedgames.aether.blockentity.AltarBlockEntity;
import com.gildedgames.aether.inventory.AetherRecipeBookTypes;
import com.gildedgames.aether.recipe.AetherRecipeTypes;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;

public class AltarMenu extends AbstractFurnaceMenu
{
	public AltarMenu(int windowId, Inventory playerInventory) {
		super(AetherMenuTypes.ALTAR.get(), AetherRecipeTypes.ENCHANTING.get(), AetherRecipeBookTypes.ALTAR, windowId, playerInventory);
	}

	public AltarMenu(int windowId, Inventory playerInventory, Container enchantingInventory, ContainerData furnaceData) {
		super(AetherMenuTypes.ALTAR.get(), AetherRecipeTypes.ENCHANTING.get(), AetherRecipeBookTypes.ALTAR, windowId, playerInventory, enchantingInventory, furnaceData);
	}

	@Override
	protected boolean isFuel(ItemStack stack) {
		return AltarBlockEntity.getEnchantingMap().containsKey(stack.getItem());
	}
}
