package com.aetherteam.aether.network.packet;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

/**
 * Sync packet for values in the {@link AetherPlayerAttachment} class.
 */
public class AetherPlayerSyncPacket extends SyncEntityPacket<AetherPlayerAttachment> {
    public static final Type<AetherPlayerSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_aether_player_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AetherPlayerSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
        AetherPlayerSyncPacket::write,
        AetherPlayerSyncPacket::decode);

    public AetherPlayerSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AetherPlayerSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public Type<AetherPlayerSyncPacket> type() {
        return TYPE;
    }

    public static AetherPlayerSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new AetherPlayerSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<AetherPlayerAttachment>> getAttachment() {
        return AetherDataAttachments.AETHER_PLAYER;
    }

    public static void execute(AetherPlayerSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}
