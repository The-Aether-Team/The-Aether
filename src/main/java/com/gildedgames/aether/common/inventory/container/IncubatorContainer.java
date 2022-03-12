package com.gildedgames.aether.common.inventory.container;

import com.gildedgames.aether.common.block.entity.IncubatorBlockEntity;

import com.gildedgames.aether.common.inventory.container.slot.IncubatorEggSlot;
import com.gildedgames.aether.common.inventory.container.slot.IncubatorFuelSlot;
import com.gildedgames.aether.common.registry.AetherContainerTypes;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class IncubatorContainer extends AbstractContainerMenu
{
	public final Container container;
	public final ContainerData data;
	public final Level level;

	public IncubatorContainer(int id, Inventory playerInventoryIn) {
		this(id, playerInventoryIn, new SimpleContainer(2), new SimpleContainerData(3));
	}
	
	public IncubatorContainer(int id, Inventory playerInventory, Container incubatorInventory, ContainerData incubatorData) {
		super(AetherContainerTypes.INCUBATOR.get(), id);
		checkContainerSize(incubatorInventory, 2);
		checkContainerDataCount(incubatorData, 3);
		this.container = incubatorInventory;
		this.data = incubatorData;
		this.level = playerInventory.player.level;
		this.addSlot(new IncubatorEggSlot(this, incubatorInventory, 0, 73, 17));
		this.addSlot(new IncubatorFuelSlot(this, incubatorInventory, 1, 73, 53));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

		this.addDataSlots(incubatorData);
	}

	public int getSize() {
		return 2;
	}

	public boolean stillValid(@Nonnull Player player) {
		return this.container.stillValid(player);
	}

	@Nonnull
	@Override
	public ItemStack quickMoveStack(@Nonnull Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack itemStack1 = slot.getItem();
			itemStack = itemStack1.copy();
			if (index != 1 && index != 0) {
				if (this.isEgg(itemStack1)) {
					if (!this.moveItemStackTo(itemStack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (this.isFuel(itemStack1)) {
					if (!this.moveItemStackTo(itemStack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 2 && index < 29) {
					if (!this.moveItemStackTo(itemStack1, 29, 38, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemStack1, 2, 29, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemStack1, 2, 38, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			}
			else {
				slot.setChanged();
			}

			if (itemStack1.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, itemStack1);
		}
		return itemStack;
	}

	public boolean isEgg(ItemStack stack) {
		return stack.is(AetherTags.Items.MOA_EGGS);
	}

	public boolean isFuel(ItemStack stack) {
		return IncubatorBlockEntity.getIncubatingMap().containsKey(stack.getItem());
	}

	public int getIncubationProgressScaled() {
		return this.data.get(2) != 0 ? (this.data.get(1) * 54) / this.data.get(2) : 0;
	}

	public boolean isIncubating() {
		return this.data.get(0) > 0;
	}

	public int getIncubationTimeRemaining() {
		return (this.data.get(0) * 12) / 500;
	}
}
