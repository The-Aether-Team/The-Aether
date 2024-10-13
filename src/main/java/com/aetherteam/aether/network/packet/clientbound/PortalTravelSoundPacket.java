package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.portal.PortalClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Plays the Aether Portal sound on the client from {@link com.aetherteam.aether.block.portal.AetherPortalForcer}.
 */
public record PortalTravelSoundPacket() implements CustomPacketPayload {
    public static final Type<PortalTravelSoundPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "play_portal_travel_sound"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PortalTravelSoundPacket> STREAM_CODEC = StreamCodec.unit(new PortalTravelSoundPacket()); //todo verify

    @Override
    public Type<PortalTravelSoundPacket> type() {
        return TYPE;
    }

    public static void execute(PortalTravelSoundPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            PortalClientUtil.playTravelSound();
        }
    }
}
