package com.gildedgames.aether.core.network.packet;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;


public class ExtendedAttackPacket extends AetherPacket
{
    private final UUID playerUUID;
    private final int targetEntityID;

    public ExtendedAttackPacket(UUID playerUUID, int target) {
        this.playerUUID = playerUUID;
        this.targetEntityID = target;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeLong(this.playerUUID.getMostSignificantBits()).writeLong(this.playerUUID.getLeastSignificantBits());
        buf.writeVarInt(this.targetEntityID);
    }

    public static ExtendedAttackPacket decode(PacketBuffer buf) {
        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        return new ExtendedAttackPacket(uuid, buf.readVarInt());
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (playerEntity != null && playerEntity.level != null && playerEntity.getServer() != null) {
            ServerPlayerEntity player = playerEntity.getServer().getPlayerList().getPlayer(this.playerUUID);
            Entity target = playerEntity.level.getEntity(this.targetEntityID);
            if (player != null && target != null) {
                double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
                if (player.distanceToSqr(target) < reach * reach) {
                    player.attack(target);
                }
            }
        }
    }
}
