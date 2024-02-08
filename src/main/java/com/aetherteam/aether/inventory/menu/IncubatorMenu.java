package com.aetherteam.aether.inventory.menu;

import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
import com.aetherteam.aether.inventory.AetherRecipeBookTypes;
import com.aetherteam.aether.inventory.menu.slot.IncubatorFuelSlot;
import com.aetherteam.aether.inventory.menu.slot.IncubatorItemSlot;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class IncubatorMenu extends RecipeBookMenu<Container> {
	public final Container container;
	public final ContainerData data;
	public final Level level;

	public IncubatorMenu(int containerId, Inventory playerInventory) {
		this(containerId, playerInventory, new SimpleContainer(2), new SimpleContainerData(7));
	}
	
	public IncubatorMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
		super(AetherMenuTypes.INCUBATOR.get(), containerId);
		checkContainerSize(container, 2);
		checkContainerDataCount(data, 7);
		this.container = container;
		this.data = data;
		this.level = playerInventory.player.level();
		this.addSlot(new IncubatorItemSlot(this, container, 0, 73, 17, playerInventory.player));
		this.addSlot(new IncubatorFuelSlot(this, container, 1, 73, 53));
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
		this.addDataSlots(data);
	}

	@Override
	public void fillCraftSlotsStackedContents(StackedContents contents) {
		if (this.container instanceof StackedContentsCompatible stackedContentsCompatible) {
			stackedContentsCompatible.fillStackedContents(contents);
		}
	}

	@Override
	public void clearCraftingContent() {
		this.getSlot(0).set(ItemStack.EMPTY);
	}

	@Override
	public boolean recipeMatches(RecipeHolder<? extends Recipe<Container>> recipe) {
		return recipe.value().matches(this.container, this.level);
	}

	@Override
	public int getResultSlotIndex() {
		return -1;
	}

	@Override
	public int getGridWidth() {
		return 1;
	}

	@Override
	public int getGridHeight() {
		return 1;
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack itemStack1 = slot.getItem();
			itemStack = itemStack1.copy();
			if (index != 1 && index != 0) {
				if (this.canIncubate(itemStack1)) {
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

	protected boolean canIncubate(ItemStack stack) {
		return this.level.getRecipeManager().getRecipeFor(AetherRecipeTypes.INCUBATION.get(), new SimpleContainer(stack), this.level).isPresent();
	}

	public boolean isFuel(ItemStack stack) {
		return IncubatorBlockEntity.getIncubatingMap().containsKey(stack.getItem());
	}

	public int getIncubationProgressScaled() {
		return this.data.get(3) != 0 ? (this.data.get(2) * 54) / this.data.get(3) : 0;
	}

	public boolean isIncubating() {
		return this.data.get(0) > 0;
	}

	public int getIncubationTimeRemaining() {
		int i = this.data.get(1);
		if (i == 0) {
			i = 1000;
		}
		return (this.data.get(0) * 11) / i;
	}

	public BlockPos getIncubatorPos() {
		int x = this.data.get(4);
		int y = this.data.get(5);
		int z = this.data.get(6);
		return new BlockPos(x, y, z);
	}

	@Override
	public RecipeBookType getRecipeBookType() {
		return AetherRecipeBookTypes.INCUBATOR;
	}

	@Override
	public boolean shouldMoveToInventory(int slot) {
		return slot != 1;
	}
}
