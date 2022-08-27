package com.gildedgames.aether.inventory.menu;

import com.gildedgames.aether.blockentity.AltarBlockEntity;
import com.gildedgames.aether.inventory.AetherRecipeBookTypes;
import com.gildedgames.aether.recipe.AetherRecipeTypes;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;

import javax.annotation.Nonnull;

public class AltarMenu extends AbstractAetherFurnaceMenu {
	public AltarMenu(int windowId, Inventory playerInventory) {
		super(AetherMenuTypes.ALTAR.get(), AetherRecipeTypes.ENCHANTING.get(), AetherRecipeBookTypes.ALTAR, windowId, playerInventory);
	}

	public AltarMenu(int windowId, Inventory playerInventory, Container enchantingInventory, ContainerData furnaceData) {
		super(AetherMenuTypes.ALTAR.get(), AetherRecipeTypes.ENCHANTING.get(), AetherRecipeBookTypes.ALTAR, windowId, playerInventory, enchantingInventory, furnaceData);
	}

	@Override
	public boolean isFuel(@Nonnull ItemStack stack) {
		return AltarBlockEntity.getEnchantingMap().containsKey(stack.getItem());
	}
}
