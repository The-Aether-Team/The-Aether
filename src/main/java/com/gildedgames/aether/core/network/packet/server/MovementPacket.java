package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;

public class MovementPacket extends AetherPacket
{
    private final int playerID;
    private final boolean isMoving;

    public MovementPacket(int playerID, boolean isMoving) {
        this.playerID = playerID;
        this.isMoving = isMoving;
    }

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
            IAetherPlayer.get(serverPlayer).ifPresent(aetherPlayer -> aetherPlayer.setMoving(this.isMoving));
        }
    }
}
