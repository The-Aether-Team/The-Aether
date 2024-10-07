package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ServerPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public abstract class ServerMoaSkinPacket {
    /**
     * Applies a Moa Skin for a player on the server.
     */
    public record Apply(UUID playerUUID, MoaData moaSkinData) implements CustomPacketPayload {
        public static final Type<ServerMoaSkinPacket.Apply> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "apply_moa_skin_server"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ServerMoaSkinPacket.Apply> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ServerMoaSkinPacket.Apply::playerUUID,
            MoaData.STREAM_CODEC,
            ServerMoaSkinPacket.Apply::moaSkinData,
            ServerMoaSkinPacket.Apply::new);

        @Override
        public Type<ServerMoaSkinPacket.Apply> type() {
            return TYPE;
        }

        public static void execute(ServerMoaSkinPacket.Apply payload, IPayloadContext context) {
            Player playerEntity = context.player();
            if (playerEntity.getServer() != null && payload.playerUUID() != null && payload.moaSkinData() != null) {
                ServerPerkData.MOA_SKIN_INSTANCE.applyPerkWithVerification(playerEntity.getServer(), payload.playerUUID(), payload.moaSkinData());
            }
        }
    }

    /**
     * Removes a Moa Skin for a player on the server.
     */
    public record Remove(UUID playerUUID) implements CustomPacketPayload {
        public static final Type<ServerMoaSkinPacket.Remove> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remove_moa_skin_server"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ServerMoaSkinPacket.Remove> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ServerMoaSkinPacket.Remove::playerUUID,
            ServerMoaSkinPacket.Remove::new);

        @Override
        public Type<ServerMoaSkinPacket.Remove> type() {
            return TYPE;
        }

        public static void execute(ServerMoaSkinPacket.Remove payload, IPayloadContext context) {
            Player playerEntity = context.player();
            if (playerEntity.getServer() != null && payload.playerUUID() != null) {
                ServerPerkData.MOA_SKIN_INSTANCE.removePerk(playerEntity.getServer(), payload.playerUUID());
            }
        }
    }
}
