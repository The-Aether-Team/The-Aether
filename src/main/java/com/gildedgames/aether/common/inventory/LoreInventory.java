package com.gildedgames.aether.common.inventory;

import com.gildedgames.aether.common.registry.AetherAdvancements;
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
    public void setItem(int index, ItemStack stack) {
        if (!stack.isEmpty() && this.playerEntity instanceof ServerPlayerEntity) {
            AetherAdvancements.LORE_ENTRY.trigger((ServerPlayerEntity) playerEntity, stack);
        }
        super.setItem(index, stack);
    }
}
