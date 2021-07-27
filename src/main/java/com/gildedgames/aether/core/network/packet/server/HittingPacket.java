package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class HittingPacket extends AetherPacket
{
    private final int playerID;
    private final boolean isHitting;

    public HittingPacket(int playerID, boolean isHitting) {
        this.playerID = playerID;
        this.isHitting = isHitting;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
        buf.writeBoolean(this.isHitting);
    }

    public static HittingPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        boolean hitting = buf.readBoolean();
        return new HittingPacket(playerID, hitting);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (playerEntity != null && playerEntity.level != null && playerEntity.getServer() != null) {
            Entity entity = playerEntity.level.getEntity(this.playerID);
            if (entity instanceof ServerPlayerEntity) {
                IAetherPlayer.get((ServerPlayerEntity) entity).ifPresent(aetherPlayer -> aetherPlayer.setHitting(this.isHitting));
            }
        }
    }
}
