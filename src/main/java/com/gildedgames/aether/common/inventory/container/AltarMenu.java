package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.block.entity.AltarBlockEntity;
import com.gildedgames.aether.common.registry.AetherRecipeBookTypes;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.registry.AetherContainerTypes;

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
