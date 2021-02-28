package com.gildedgames.aether.inventory;

import com.gildedgames.aether.registry.AetherAdvancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class LoreInventory extends Inventory
{
    private final PlayerEntity playerEntity;

    public LoreInventory(PlayerEntity playerEntity) {
        super(1);
        this.playerEntity = playerEntity;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (!stack.isEmpty() && this.playerEntity instanceof ServerPlayerEntity) {
            AetherAdvancement.LORE_ENTRY.trigger((ServerPlayerEntity) playerEntity, stack);
        }
        super.setInventorySlotContents(index, stack);
    }
}
