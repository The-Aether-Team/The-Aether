package com.aetherteam.aether.network.packet;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.PhoenixArrowAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

/**
 * Sync packet for values in the {@link PhoenixArrowAttachment} class.
 */
public class PhoenixArrowSyncPacket extends SyncEntityPacket<PhoenixArrowAttachment> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_phoenix_arrow_attachment");

    public PhoenixArrowSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public PhoenixArrowSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static PhoenixArrowSyncPacket decode(FriendlyByteBuf buf) {
        return new PhoenixArrowSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<PhoenixArrowAttachment>> getAttachment() {
        return AetherDataAttachments.PHOENIX_ARROW;
    }
}
