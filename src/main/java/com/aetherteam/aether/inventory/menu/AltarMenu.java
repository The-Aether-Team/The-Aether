package com.aetherteam.aether.inventory.menu;

import com.aetherteam.aether.blockentity.AltarBlockEntity;
import com.aetherteam.aether.inventory.AetherRecipeBookTypes;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;

public class AltarMenu extends AbstractAetherFurnaceMenu {
	public AltarMenu(int containerId, Inventory playerInventory) {
		super(AetherMenuTypes.ALTAR.get(), AetherRecipeTypes.ENCHANTING.get(), AetherRecipeBookTypes.ALTAR, containerId, playerInventory);
	}

	public AltarMenu(int containerId, Inventory playerInventory, Container altarContainer, ContainerData data) {
		super(AetherMenuTypes.ALTAR.get(), AetherRecipeTypes.ENCHANTING.get(), AetherRecipeBookTypes.ALTAR, containerId, playerInventory, altarContainer, data);
	}

	@Override
	public boolean isFuel(ItemStack stack) {
		return AltarBlockEntity.getEnchantingMap().containsKey(stack.getItem());
	}
}
