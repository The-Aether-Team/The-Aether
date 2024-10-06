package com.aetherteam.aether.network.packet;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherTimeAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncLevelPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Supplier;

/**
 * Sync packet for values in the {@link AetherTimeAttachment} class.
 */
public class AetherTimeSyncPacket extends SyncLevelPacket<AetherTimeAttachment> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_aether_time_attachment");

    public AetherTimeSyncPacket(Triple<String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AetherTimeSyncPacket(String key, INBTSynchable.Type type, Object value) {
        super(key, type, value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static AetherTimeSyncPacket decode(FriendlyByteBuf buf) {
        return new AetherTimeSyncPacket(SyncLevelPacket.decodeValues(buf));
    }

    @Override
    public Supplier<AttachmentType<AetherTimeAttachment>> getAttachment() {
        return AetherDataAttachments.AETHER_TIME;
    }
}
