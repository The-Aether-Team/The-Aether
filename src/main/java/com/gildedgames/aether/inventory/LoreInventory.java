package com.gildedgames.aether.inventory;

import com.gildedgames.aether.advancement.triggers.LoreTrigger;
import com.gildedgames.aether.inventory.container.LoreBookMenu;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.server.LoreExistsPacket;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class LoreInventory extends SimpleContainer
{
    private final Player playerEntity;
    private LoreBookMenu container;

    public LoreInventory(Player playerEntity) {
        super(1);
        this.playerEntity = playerEntity;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (!stack.isEmpty()) {
            if (this.playerEntity.level.isClientSide && this.playerEntity instanceof LocalPlayer) {
                if (this.container.loreEntryExists(stack)) {
                    AetherPacketHandler.sendToServer(new LoreExistsPacket(this.playerEntity.getId(), stack, true));
                } else {
                    AetherPacketHandler.sendToServer(new LoreExistsPacket(this.playerEntity.getId(), stack, false));
                }
            } else if (this.playerEntity instanceof ServerPlayer) {
                if (this.container.getLoreEntryExists()) {
                    LoreTrigger.INSTANCE.trigger((ServerPlayer) playerEntity, stack);
                }
            }
        }
        super.setItem(index, stack);
    }

    public void setContainer(LoreBookMenu container) {
        this.container = container;
    }
}
