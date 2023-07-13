package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record InvisibilityTogglePacket(int playerID) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
    }

    public static InvisibilityTogglePacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        return new InvisibilityTogglePacket(playerID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            AetherPlayer.get(serverPlayer).ifPresent(aetherPlayer -> aetherPlayer.setInvisibilityEnabled(!aetherPlayer.isInvisibilityEnabled()));
        }
    }
}
