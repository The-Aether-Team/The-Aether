package com.aether.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class CustomSmeltingResultSlot extends FurnaceResultSlot {

	public CustomSmeltingResultSlot(PlayerEntity player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
		super(player, inventoryIn, slotIndex, xPosition, yPosition);
	}

	@Override
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(this.player.world, this.player, this.removeCount);
		if (!this.player.world.isRemote && this.inventory instanceof AbstractFurnaceTileEntity) {
			((AbstractFurnaceTileEntity) this.inventory).onCrafting(this.player);
		}

		this.removeCount = 0;
	}

}
