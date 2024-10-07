package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ClientHaloPerkData;
import com.aetherteam.aether.perk.types.Halo;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;
import java.util.UUID;

public class ClientHaloPacket {
    /**
     * Applies the Halo perk to a player on the client.
     */
    public record Apply(UUID playerUUID, Halo halo) implements CustomPacketPayload {
        public static final Type<ClientHaloPacket.Apply> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "apply_halo"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientHaloPacket.Apply> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientHaloPacket.Apply::playerUUID,
            Halo.STREAM_CODEC,
            ClientHaloPacket.Apply::halo,
            ClientHaloPacket.Apply::new);

        @Override
        public Type<ClientHaloPacket.Apply> type() {
            return TYPE;
        }

        public static void execute(ClientHaloPacket.Apply payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.playerUUID() != null && payload.halo() != null) {
                ClientHaloPerkData.INSTANCE.applyPerk(payload.playerUUID(), payload.halo());
            }
        }
    }

    /**
     * Removes the Halo perk from a player on the client.
     */
    public record Remove(UUID playerUUID) implements CustomPacketPayload {
        public static final Type<ClientHaloPacket.Remove> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remove_halo"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientHaloPacket.Remove> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientHaloPacket.Remove::playerUUID,
            ClientHaloPacket.Remove::new);

        @Override
        public Type<ClientHaloPacket.Remove> type() {
            return TYPE;
        }

        public static void execute(ClientHaloPacket.Remove payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.playerUUID() != null) {
                ClientHaloPerkData.INSTANCE.removePerk(payload.playerUUID());
            }
        }
    }

    /**
     * Syncs Halo perk data for all players to the client.
     */
    public record Sync(Map<UUID, Halo> halos) implements CustomPacketPayload {
        public static final Type<ClientHaloPacket.Sync> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_halo"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientHaloPacket.Sync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(Maps::newHashMapWithExpectedSize, UUIDUtil.STREAM_CODEC, Halo.STREAM_CODEC),
            ClientHaloPacket.Sync::halos,
            ClientHaloPacket.Sync::new);

        @Override
        public Type<?> type() {
            return TYPE;
        }

        public static void execute(ClientHaloPacket.Sync payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.halos() != null && !payload.halos().isEmpty()) {
                for (Map.Entry<UUID, Halo> haloEntry : payload.halos().entrySet()) {
                    ClientHaloPerkData.INSTANCE.applyPerk(haloEntry.getKey(), haloEntry.getValue());
                }
            }
        }
    }
}
