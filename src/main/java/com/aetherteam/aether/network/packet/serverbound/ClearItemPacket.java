package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Clears the item currently held by the player's mouse in a container GUI.
 */
public record ClearItemPacket(int playerID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID());
    }

    public static ClearItemPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        return new ClearItemPacket(playerID);
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.getLevel().getEntity(this.playerID()) instanceof ServerPlayer serverPlayer) {
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
        }
    }
}
