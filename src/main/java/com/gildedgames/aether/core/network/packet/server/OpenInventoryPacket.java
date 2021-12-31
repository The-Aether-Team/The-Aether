package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.ClientGrabItemPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;

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
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.containerMenu.getCarried(); //TODO: Make sure this works. setCarried was previously handled in Inventory and now its not. This can't be verified until the addWidget code in GuiListener is fixed.
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            serverPlayer.doCloseContainer();
            if (!itemStack.isEmpty()) {
                serverPlayer.containerMenu.setCarried(itemStack);
                AetherPacketHandler.sendToPlayer(new ClientGrabItemPacket(serverPlayer.getId(), itemStack), serverPlayer);
            }
        }
    }
}
