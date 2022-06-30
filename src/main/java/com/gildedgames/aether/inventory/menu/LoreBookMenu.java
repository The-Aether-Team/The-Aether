package com.gildedgames.aether.inventory.menu;

import com.gildedgames.aether.inventory.container.LoreInventory;
import com.gildedgames.aether.inventory.AetherMenuTypes;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nonnull;

public class LoreBookMenu extends AbstractContainerMenu
{
    private final LoreInventory bookInventory;

    private boolean loreEntryExists;

    public LoreBookMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new LoreInventory(playerInventory.player));
    }

    public LoreBookMenu(int id, Inventory playerInventory, LoreInventory bookInventory) {
        super(AetherMenuTypes.BOOK_OF_LORE.get(), id);
        checkContainerSize(bookInventory, 1);
        this.bookInventory = bookInventory;
        bookInventory.setContainer(this);
        bookInventory.startOpen(playerInventory.player);

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

    public static LoreBookMenu create(int id, Inventory playerInventory) {
        return new LoreBookMenu(id, playerInventory);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return this.bookInventory.stillValid(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 1) {
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public void removed(@Nonnull Player playerIn) {
        super.removed(playerIn);
        if (playerIn instanceof ServerPlayer serverPlayer) {
            ItemStack stack = this.bookInventory.getItem(0);
            if (!stack.isEmpty()) {
                if (playerIn.isAlive() && !serverPlayer.hasDisconnected()) {
                    playerIn.getInventory().placeItemBackInInventory(stack);
                } else {
                    playerIn.drop(stack, false);
                }
                this.bookInventory.setItem(0, ItemStack.EMPTY);
            }
        }
        this.bookInventory.stopOpen(playerIn);
    }

    public boolean getLoreEntryExists() {
        return this.loreEntryExists;
    }

    public void setLoreEntryExists(boolean exists) {
        this.loreEntryExists = exists;
    }

    @OnlyIn(Dist.CLIENT)
    public String getLoreEntryKey(ItemStack stack) {
        return "lore." + stack.getDescriptionId();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean loreEntryExists(ItemStack stack) {
        return I18n.exists(getLoreEntryKey(stack));
    }
}
