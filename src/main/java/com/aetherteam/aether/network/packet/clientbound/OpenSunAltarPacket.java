package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.gui.screen.inventory.SunAltarScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Opens {@link SunAltarScreen} from {@link com.aetherteam.aether.block.utility.SunAltarBlock}.
 */
public record OpenSunAltarPacket(Component name, int timeScale) implements CustomPacketPayload {
    public static final Type<OpenSunAltarPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "open_sun_altar"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenSunAltarPacket> STREAM_CODEC = StreamCodec.composite(
        ComponentSerialization.STREAM_CODEC,
        OpenSunAltarPacket::name,
        ByteBufCodecs.INT,
        OpenSunAltarPacket::timeScale,
        OpenSunAltarPacket::new);

    @Override
    public Type<OpenSunAltarPacket> type() {
        return TYPE;
    }

    public static void execute(OpenSunAltarPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AetherClient.setToSunAltarScreen(payload.name(), payload.timeScale());
        }
    }
}
