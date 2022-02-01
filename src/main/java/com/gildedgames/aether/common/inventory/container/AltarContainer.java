package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.block.entity.AltarBlockEntity;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import com.gildedgames.aether.common.registry.AetherContainerTypes;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ContainerData;

public class AltarContainer extends AbstractFurnaceMenu
{
	public AltarContainer(int windowId, Inventory playerInventory) {
		super(AetherContainerTypes.ALTAR.get(), RecipeTypes.ENCHANTING, RecipeBookType.FURNACE, windowId, playerInventory);
	}

	public AltarContainer(int windowId, Inventory playerInventory, Container enchantingInventory, ContainerData furnaceData) {
		super(AetherContainerTypes.ALTAR.get(), RecipeTypes.ENCHANTING, RecipeBookType.FURNACE, windowId, playerInventory, enchantingInventory, furnaceData);
	}

	@Override
	protected boolean isFuel(ItemStack stack) {
		return AltarBlockEntity.getEnchantingMap().containsKey(stack.getItem());
	}
}
