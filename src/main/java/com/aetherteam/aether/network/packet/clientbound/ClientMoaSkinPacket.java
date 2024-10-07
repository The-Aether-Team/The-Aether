package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
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

public abstract class ClientMoaSkinPacket {
    /**
     * Applies a Moa Skin for a player on the client.
     */
    public record Apply(UUID playerUUID, MoaData moaSkinData) implements CustomPacketPayload {
        public static final Type<ClientMoaSkinPacket.Apply> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "apply_moa_skin"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientMoaSkinPacket.Apply> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientMoaSkinPacket.Apply::playerUUID,
            MoaData.STREAM_CODEC,
            ClientMoaSkinPacket.Apply::moaSkinData,
            ClientMoaSkinPacket.Apply::new);

        @Override
        public Type<ClientMoaSkinPacket.Apply> type() {
            return TYPE;
        }

        public static void execute(ClientMoaSkinPacket.Apply payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.playerUUID() != null && payload.moaSkinData() != null) {
                ClientMoaSkinPerkData.INSTANCE.applyPerk(payload.playerUUID(), payload.moaSkinData());
            }
        }
    }

    /**
     * Removes a Moa Skin for a player on the client.
     */
    public record Remove(UUID playerUUID) implements CustomPacketPayload {
        public static final Type<ClientMoaSkinPacket.Remove> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remove_moa_skin"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientMoaSkinPacket.Remove> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientMoaSkinPacket.Remove::playerUUID,
            ClientMoaSkinPacket.Remove::new);

        @Override
        public Type<?> type() {
            return TYPE;
        }

        public static void execute(ClientMoaSkinPacket.Remove payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.playerUUID() != null) {
                ClientMoaSkinPerkData.INSTANCE.removePerk(payload.playerUUID());
            }
        }
    }

    /**
     * Syncs Moa Skin data for all players to the client.
     */
    public record Sync(Map<UUID, MoaData> moaSkinsData) implements CustomPacketPayload {
        public static final Type<ClientMoaSkinPacket.Sync> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_moa_skin"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientMoaSkinPacket.Sync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(Maps::newHashMapWithExpectedSize, UUIDUtil.STREAM_CODEC, MoaData.STREAM_CODEC),
            ClientMoaSkinPacket.Sync::moaSkinsData,
            ClientMoaSkinPacket.Sync::new);

        @Override
        public Type<?> type() {
            return TYPE;
        }

        public static void execute(ClientMoaSkinPacket.Sync payload, IPayloadContext context) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && payload.moaSkinsData() != null && !payload.moaSkinsData().isEmpty()) {
                for (Map.Entry<UUID, MoaData> moaSkinsDataEntry : payload.moaSkinsData().entrySet()) {
                    ClientMoaSkinPerkData.INSTANCE.applyPerk(moaSkinsDataEntry.getKey(), moaSkinsDataEntry.getValue());
                }
            }
        }
    }
}
