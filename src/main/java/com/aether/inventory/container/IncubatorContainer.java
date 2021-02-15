package com.aether.inventory.container;

import java.util.UUID;
import java.util.function.Consumer;

import com.aether.entity.tile.IncubatorTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IncubatorContainer extends Container {
	protected final IInventory incubatorInventory;
	protected final IIntArray incubatorData;
	protected final Consumer<UUID> playerUUIDAcceptor;
	protected final World world;

	public IncubatorContainer(int id, PlayerInventory playerInventoryIn) {
		this(id, playerInventoryIn, new Inventory(2), new IntArray(3), (uuid) -> {});
	}
	
	public IncubatorContainer(int id, PlayerInventory playerInventoryIn, IInventory incubatorInventoryIn, IIntArray incubatorDataIn, Consumer<UUID> playerUUIDConsumerIn) {
		super(AetherContainerTypes.INCUBATOR, id);
		assertInventorySize(incubatorInventoryIn, 2);
		assertIntArraySize(incubatorDataIn, 3);
		this.incubatorInventory = incubatorInventoryIn;
		this.incubatorData = incubatorDataIn;
		this.playerUUIDAcceptor = playerUUIDConsumerIn;
		this.world = playerInventoryIn.player.world;
		this.addSlot(new IncubatorEggSlot(this, incubatorInventoryIn, 0, 73, 17, playerInventoryIn.player));
		this.addSlot(new IncubatorFuelSlot(this, incubatorInventoryIn, 0, 73, 53));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
		}

		this.trackIntArray(incubatorDataIn);
	}
	
	public void clear() {
		this.incubatorInventory.clear();
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return incubatorInventory.isUsableByPlayer(playerIn);
	}
	
	public boolean isFuel(ItemStack stack) {
		return IncubatorTileEntity.isFuel(stack);
	}
	
	public boolean isEgg(ItemStack stack) {
		return true;
		//return stack.getItem() == AetherItems.MOA_EGG;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index != 1 && index != 0) {
				if (this.isFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (this.isEgg(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 2 && index < 29) {
					if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}
			else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getProgressionScaled() {
		int progress = this.incubatorData.get(0);
		int ticksRequired = this.incubatorData.get(2);
		return progress != 0 && ticksRequired != 0? progress * 24 / ticksRequired : 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean isIncubating() {
		return this.incubatorData.get(1) > 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getFreezingTimeRemaining() {
		return (this.incubatorData.get(1) * 12) / 500; 
	}

}
