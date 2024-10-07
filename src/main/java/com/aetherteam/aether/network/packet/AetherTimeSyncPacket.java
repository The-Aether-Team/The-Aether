package com.aetherteam.aether.network.packet;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherTimeAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncLevelPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Supplier;

/**
 * Sync packet for values in the {@link AetherTimeAttachment} class.
 */
public class AetherTimeSyncPacket extends SyncLevelPacket<AetherTimeAttachment> {
    public static final Type<AetherTimeSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_aether_time_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AetherTimeSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
        AetherTimeSyncPacket::write,
        AetherTimeSyncPacket::decode);

    public AetherTimeSyncPacket(Triple<String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AetherTimeSyncPacket(String key, INBTSynchable.Type type, Object value) {
        super(key, type, value);
    }

    @Override
    public Type<AetherTimeSyncPacket> type() {
        return TYPE;
    }

    public static AetherTimeSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new AetherTimeSyncPacket(SyncLevelPacket.decodeValues(buf));
    }

    @Override
    public Supplier<AttachmentType<AetherTimeAttachment>> getAttachment() {
        return AetherDataAttachments.AETHER_TIME;
    }

    public static void execute(AetherTimeSyncPacket payload, IPayloadContext context) {
        SyncLevelPacket.execute(payload, context.player());
    }
}
