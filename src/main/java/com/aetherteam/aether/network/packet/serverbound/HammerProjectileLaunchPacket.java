package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Handles syncing {@link HammerProjectile} damage to the server.
 */
public record HammerProjectileLaunchPacket(int targetID, int projectileID) implements CustomPacketPayload {
    public static final Type<HammerProjectileLaunchPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "launch_hammer_projectile"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HammerProjectileLaunchPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        HammerProjectileLaunchPacket::targetID,
        ByteBufCodecs.INT,
        HammerProjectileLaunchPacket::projectileID,
        HammerProjectileLaunchPacket::new);

    @Override
    public Type<HammerProjectileLaunchPacket> type() {
        return TYPE;
    }

    public static void execute(HammerProjectileLaunchPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.getServer() != null) {
            Entity target = playerEntity.level().getEntity(payload.targetID());
            Entity projectile = playerEntity.level().getEntity(payload.projectileID());
            if (projectile instanceof HammerProjectile hammerProjectile) {
                hammerProjectile.launchTarget(target);
            }
        }
    }
}
