package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ClientGrabItemPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;

public class OpenInventoryPacket extends AetherPacket
{
    private final int playerID;

    public OpenInventoryPacket(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
    }

    public static OpenInventoryPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        return new OpenInventoryPacket(playerID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null) {
            Entity entity = playerEntity.level.getEntity(this.playerID);
            if (entity instanceof ServerPlayer player) {
                ItemStack stack = player.containerMenu.getCarried(); //TODO: Make sure this works. setCarried was previously handled in Inventory and now its not.
                player.containerMenu.setCarried(ItemStack.EMPTY);
                player.doCloseContainer();
                if (!stack.isEmpty()) {
                    player.containerMenu.setCarried(stack);
                    AetherPacketHandler.sendToPlayer(new ClientGrabItemPacket(player.getId(), stack), player);
                }
            }
        }
    }
}
