package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ClientDeveloperGlowPerkData;
import com.aetherteam.aether.perk.types.DeveloperGlow;
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

public class ClientDeveloperGlowPacket {
    /**
     * Applies the Developer Glow perk to a player on the client.
     */
    public record Apply(UUID playerUUID, DeveloperGlow developerGlow) implements CustomPacketPayload {
        public static final Type<ClientDeveloperGlowPacket.Apply> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "apply_developer_glow"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientDeveloperGlowPacket.Apply> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientDeveloperGlowPacket.Apply::playerUUID,
            DeveloperGlow.STREAM_CODEC,
            ClientDeveloperGlowPacket.Apply::developerGlow,
            ClientDeveloperGlowPacket.Apply::new);

        @Override
        public Type<ClientDeveloperGlowPacket.Apply> type() {
            return TYPE;
        }

        public static void execute(ClientDeveloperGlowPacket.Apply payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.playerUUID() != null && payload.developerGlow() != null) {
                ClientDeveloperGlowPerkData.INSTANCE.applyPerk(payload.playerUUID(), payload.developerGlow());
            }
        }
    }

    /**
     * Removes the Developer Glow perk from a player on the client.
     */
    public record Remove(UUID playerUUID) implements CustomPacketPayload {
        public static final Type<ClientDeveloperGlowPacket.Remove> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remove_developer_glow"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientDeveloperGlowPacket.Remove> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientDeveloperGlowPacket.Remove::playerUUID,
            ClientDeveloperGlowPacket.Remove::new);

        @Override
        public Type<ClientDeveloperGlowPacket.Remove> type() {
            return TYPE;
        }

        public static void execute(ClientDeveloperGlowPacket.Remove payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.playerUUID() != null) {
                ClientDeveloperGlowPerkData.INSTANCE.removePerk(payload.playerUUID());
            }
        }
    }

    /**
     * Syncs Developer Glow perk data for all players to the client.
     */
    public record Sync(Map<UUID, DeveloperGlow> developerGlows) implements CustomPacketPayload {
        public static final Type<ClientDeveloperGlowPacket.Sync> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_developer_glow"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientDeveloperGlowPacket.Sync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(Maps::newHashMapWithExpectedSize, UUIDUtil.STREAM_CODEC, DeveloperGlow.STREAM_CODEC),
            ClientDeveloperGlowPacket.Sync::developerGlows,
            ClientDeveloperGlowPacket.Sync::new);

        @Override
        public Type<ClientDeveloperGlowPacket.Sync> type() {
            return TYPE;
        }

        public static void execute(ClientDeveloperGlowPacket.Sync payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.developerGlows() != null && !payload.developerGlows().isEmpty()) {
                for (Map.Entry<UUID, DeveloperGlow> developerGlowEntry : payload.developerGlows().entrySet()) {
                    ClientDeveloperGlowPerkData.INSTANCE.applyPerk(developerGlowEntry.getKey(), developerGlowEntry.getValue());
                }
            }
        }
    }
}
