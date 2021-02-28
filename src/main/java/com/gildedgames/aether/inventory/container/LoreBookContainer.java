package com.gildedgames.aether.inventory.container;

import com.gildedgames.aether.inventory.LoreInventory;
import com.gildedgames.aether.registry.AetherContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LoreBookContainer extends Container
{
    private final IInventory bookInventory;

    public LoreBookContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new LoreInventory(playerInventory.player));
    }

    public LoreBookContainer(int id, PlayerInventory playerInventory, IInventory bookInventory) {
        super(AetherContainerTypes.BOOK_OF_LORE.get(), id);
        assertInventorySize(bookInventory, 1);
        this.bookInventory = bookInventory;
        bookInventory.openInventory(playerInventory.player);

        this.addSlot(new Slot(bookInventory, 0, 83, 63));

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 48 + i1 * 18, 113 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 48 + l * 18, 171));
        }
    }

    public static LoreBookContainer create(int id, PlayerInventory playerInventory) {
        return new LoreBookContainer(id, playerInventory);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.bookInventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 1) {
                if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        playerIn.dropItem(this.bookInventory.getStackInSlot(0), false);
        super.onContainerClosed(playerIn);
        this.bookInventory.closeInventory(playerIn);
    }

    @OnlyIn(Dist.CLIENT)
    public String getLoreEntryKey(ItemStack stack) {
        return "lore." + stack.getItem().getRegistryName().getNamespace() + "." + stack.getTranslationKey()
                .replace(stack.getItem().getRegistryName().getNamespace() + ".", "")
                .replace("item.", "")
                .replace("block.", "")
                .replace("tile.", "")
                .replace(".name", "")
                .replace(".", "_");
    }
}
