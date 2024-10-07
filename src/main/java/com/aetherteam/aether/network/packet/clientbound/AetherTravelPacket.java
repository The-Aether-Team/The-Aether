package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Marks the player as being in the process of teleporting to the Aether. This is used for displaying "Ascending to the Aether" in the world loading screen.
 *
 * @see com.aetherteam.aether.client.event.hooks.GuiHooks#drawAetherTravelMessage(Screen, net.minecraft.client.gui.GuiGraphics)
 */
public record AetherTravelPacket(boolean displayAetherTravel) implements CustomPacketPayload {
    public static final Type<AetherTravelPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "travel_across_dimensions"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AetherTravelPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        AetherTravelPacket::displayAetherTravel,
        AetherTravelPacket::new);

    @Override
    public Type<AetherTravelPacket> type() {
        return TYPE;
    }

    public static void execute(AetherTravelPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DimensionHooks.displayAetherTravel = payload.displayAetherTravel();
        }
    }
}
