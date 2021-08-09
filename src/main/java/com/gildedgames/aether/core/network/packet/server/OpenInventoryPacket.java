package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ClientGrabItemPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class OpenInventoryPacket extends AetherPacket
{
    private final int playerID;

    public OpenInventoryPacket(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
    }

    public static OpenInventoryPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        return new OpenInventoryPacket(playerID);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (playerEntity != null && playerEntity.level != null && playerEntity.getServer() != null) {
            Entity entity = playerEntity.level.getEntity(this.playerID);
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) entity;
                ItemStack stack = player.inventory.getCarried();
                player.inventory.setCarried(ItemStack.EMPTY);
                player.doCloseContainer();
                if (!stack.isEmpty()) {
                    player.inventory.setCarried(stack);
                    AetherPacketHandler.sendToPlayer(new ClientGrabItemPacket(player.getId(), stack), player);
                }
            }
        }
    }
}
