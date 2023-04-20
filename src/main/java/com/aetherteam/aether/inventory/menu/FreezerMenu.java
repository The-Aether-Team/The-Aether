package com.aetherteam.aether.inventory.menu;

import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import com.aetherteam.aether.inventory.AetherRecipeBookTypes;
import com.aetherteam.aether.recipe.AetherRecipeTypes;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;

public class FreezerMenu extends AbstractAetherFurnaceMenu {
	public FreezerMenu(int containerId, Inventory playerInventory) {
		super(AetherMenuTypes.FREEZER.get(), AetherRecipeTypes.FREEZING.get(), AetherRecipeBookTypes.FREEZER, containerId, playerInventory);
	}

	public FreezerMenu(int containerId, Inventory playerInventory, Container freezerContainer, ContainerData data) {
		super(AetherMenuTypes.FREEZER.get(), AetherRecipeTypes.FREEZING.get(), AetherRecipeBookTypes.FREEZER, containerId, playerInventory, freezerContainer, data);
	}

	@Override
	public boolean isFuel(ItemStack stack) {
		return FreezerBlockEntity.getFreezingMap().containsKey(stack.getItem());
	}
}
