package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ServerPerkData;
import com.aetherteam.aether.perk.types.Halo;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class ServerHaloPacket {
    /**
     * Applies the Halo perk to a player on the server.
     */
    public record Apply(UUID playerUUID, Halo halo) implements CustomPacketPayload {
        public static final Type<ServerHaloPacket.Apply> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "add_halo_server"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ServerHaloPacket.Apply> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ServerHaloPacket.Apply::playerUUID,
            Halo.STREAM_CODEC,
            ServerHaloPacket.Apply::halo,
            ServerHaloPacket.Apply::new);

        @Override
        public Type<ServerHaloPacket.Apply> type() {
            return TYPE;
        }

        public static void execute(ServerHaloPacket.Apply payload, IPayloadContext context) {
            Player playerEntity = context.player();
            if (playerEntity.getServer() != null && payload.playerUUID() != null && payload.halo() != null) {
                ServerPerkData.HALO_INSTANCE.applyPerkWithVerification(playerEntity.getServer(), payload.playerUUID(), payload.halo());
            }
        }
    }

    /**
     * Removes the Halo perk from a player on the server.
     */
    public record Remove(UUID playerUUID) implements CustomPacketPayload {
        public static final Type<ServerHaloPacket.Remove> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remove_halo_server"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ServerHaloPacket.Remove> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ServerHaloPacket.Remove::playerUUID,
            ServerHaloPacket.Remove::new);

        @Override
        public Type<ServerHaloPacket.Remove> type() {
            return TYPE;
        }

        public static void execute(ServerHaloPacket.Remove payload, IPayloadContext context) {
            Player playerEntity = context.player();
            if (playerEntity.getServer() != null && payload.playerUUID() != null) {
                ServerPerkData.HALO_INSTANCE.removePerk(playerEntity.getServer(), payload.playerUUID());
            }
        }
    }
}
