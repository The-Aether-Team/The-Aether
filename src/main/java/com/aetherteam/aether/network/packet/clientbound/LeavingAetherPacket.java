package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Marks the player as being in the process of leaving the Aether. This is used for displaying "Descending from the Aether" in the world loading screen.
 *
 * @see com.aetherteam.aether.client.event.hooks.GuiHooks#drawAetherTravelMessage(Screen, GuiGraphics)
 */
public record LeavingAetherPacket(boolean playerLeavingAether) implements CustomPacketPayload {
    public static final Type<LeavingAetherPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "leave_aether"));

    public static final StreamCodec<RegistryFriendlyByteBuf, LeavingAetherPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        LeavingAetherPacket::playerLeavingAether,
        LeavingAetherPacket::new);

    @Override
    public Type<LeavingAetherPacket> type() {
        return TYPE;
    }

    public static void execute(LeavingAetherPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DimensionHooks.playerLeavingAether = payload.playerLeavingAether();
        }
    }
}
