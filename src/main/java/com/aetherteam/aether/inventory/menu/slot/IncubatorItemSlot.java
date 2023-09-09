package com.aetherteam.aether.inventory.menu.slot;

import com.aetherteam.aether.blockentity.IncubatorBlockEntity;
import com.aetherteam.aether.inventory.menu.IncubatorMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class IncubatorItemSlot extends Slot {
    private final IncubatorMenu menu;
    private final Player player;

    public IncubatorItemSlot(IncubatorMenu menu, Container container, int slot, int x, int y, Player player) {
        super(container, slot, x, y);
        this.menu = menu;
        this.player = player;
    }

    /**
     * Stores the player who deposits an item to this slot to a value within the Incubator.
     * @param stack The {@link ItemStack} being put in the slot.
     */
    @Override
    public void set(ItemStack stack) {
        if (!stack.isEmpty() && !this.getItem().is(stack.getItem())) {
            if (this.player instanceof ServerPlayer serverPlayer) {
                BlockEntity blockEntity = this.player.level().getBlockEntity(this.menu.getIncubatorPos());
                if (blockEntity instanceof IncubatorBlockEntity incubator) {
                    incubator.setPlayer(serverPlayer);
                }
            }
        }
        super.set(stack);
    }
}
