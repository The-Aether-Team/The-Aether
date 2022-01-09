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
    private final ItemStack carryStack;

    public OpenInventoryPacket(int playerID, ItemStack carryStack) {
        this.playerID = playerID;
        this.carryStack = carryStack;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
        buf.writeItem(this.carryStack);
    }

    public static OpenInventoryPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        ItemStack carryStack = buf.readItem();
        return new OpenInventoryPacket(playerID, carryStack);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.isCreative() ?  this.carryStack : serverPlayer.containerMenu.getCarried();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            serverPlayer.doCloseContainer();
            if (!itemStack.isEmpty()) {
                serverPlayer.containerMenu.setCarried(itemStack);
                AetherPacketHandler.sendToPlayer(new ClientGrabItemPacket(serverPlayer.getId(), itemStack), serverPlayer);
            }
        }
    }
}
