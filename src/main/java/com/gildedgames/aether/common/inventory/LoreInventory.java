package com.gildedgames.aether.common.inventory;

import com.gildedgames.aether.common.advancement.LoreTrigger;
import com.gildedgames.aether.common.inventory.container.LoreBookContainer;
import com.gildedgames.aether.common.registry.AetherAdvancements;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.LoreExistsPacket;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class LoreInventory extends Inventory
{
    private final PlayerEntity playerEntity;
    private LoreBookContainer container;

    public LoreInventory(PlayerEntity playerEntity) {
        super(1);
        this.playerEntity = playerEntity;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (!stack.isEmpty()) {
            if (this.playerEntity instanceof ClientPlayerEntity) {
                if (this.container.loreEntryExists(stack)) {
                    AetherPacketHandler.sendToServer(new LoreExistsPacket(this.playerEntity.getId(), stack, true));
                } else {
                    AetherPacketHandler.sendToServer(new LoreExistsPacket(this.playerEntity.getId(), stack, false));
                }
            } else if (this.playerEntity instanceof ServerPlayerEntity) {
                if (this.container.getLoreEntryExists()) {
                    LoreTrigger.INSTANCE.trigger((ServerPlayerEntity) playerEntity, stack);
                }
            }
        }
        super.setItem(index, stack);
    }

    public void setContainer(LoreBookContainer container) {
        this.container = container;
    }
}
