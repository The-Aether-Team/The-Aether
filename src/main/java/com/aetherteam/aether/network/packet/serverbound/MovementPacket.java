package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;

public record MovementPacket(int playerID, boolean isMoving) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
        buf.writeBoolean(this.isMoving);
    }

    public static MovementPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        boolean isMoving = buf.readBoolean();
        return new MovementPacket(playerID, isMoving);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            AetherPlayer.get(serverPlayer).ifPresent(aetherPlayer -> aetherPlayer.setMoving(this.isMoving));
        }
    }
}
