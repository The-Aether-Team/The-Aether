package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

import java.util.UUID;

public class MovementPacket extends AetherPacket
{
    private final int playerID;
    private final boolean isMoving;

    public MovementPacket(int playerID, boolean isMoving) {
        this.playerID = playerID;
        this.isMoving = isMoving;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
        buf.writeBoolean(this.isMoving);
    }

    public static MovementPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        boolean isMoving = buf.readBoolean();
        return new MovementPacket(playerID, isMoving);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (playerEntity != null && playerEntity.level != null && playerEntity.getServer() != null) {
            Entity entity = playerEntity.level.getEntity(this.playerID);
            if (entity instanceof ServerPlayerEntity) {
                IAetherPlayer.get((ServerPlayerEntity) entity).ifPresent(aetherPlayer -> aetherPlayer.setMoving(this.isMoving));
            }
        }
    }
}
