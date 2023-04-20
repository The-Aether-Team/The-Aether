package com.aetherteam.aether.network.packet.server;

import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record ClearItemPacket(int playerID) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
    }

    public static ClearItemPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        return new ClearItemPacket(playerID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
        }
    }
}
