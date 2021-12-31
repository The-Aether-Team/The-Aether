package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class ExtendedAttackPacket extends AetherPacket
{
    private final UUID playerUUID;
    private final int targetEntityID;

    public ExtendedAttackPacket(UUID playerUUID, int target) {
        this.playerUUID = playerUUID;
        this.targetEntityID = target;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(this.playerUUID.getMostSignificantBits()).writeLong(this.playerUUID.getLeastSignificantBits());
        buf.writeVarInt(this.targetEntityID);
    }

    public static ExtendedAttackPacket decode(FriendlyByteBuf buf) {
        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        return new ExtendedAttackPacket(uuid, buf.readVarInt());
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null) {
            ServerPlayer serverPlayer = playerEntity.getServer().getPlayerList().getPlayer(this.playerUUID);
            Entity target = playerEntity.level.getEntity(this.targetEntityID);
            if (serverPlayer != null && target != null) {
                double reach = serverPlayer.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
                if (serverPlayer.distanceToSqr(target) < reach * reach) {
                    serverPlayer.attack(target);
                }
            }
        }
    }
}
