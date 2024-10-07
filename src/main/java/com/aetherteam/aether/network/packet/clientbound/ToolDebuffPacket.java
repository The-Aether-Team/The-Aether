package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Stores a client value for whether tools are debuffed in the Aether for {@link com.aetherteam.aether.event.hooks.AbilityHooks.ToolHooks}.
 */
public record ToolDebuffPacket(boolean debuffTools) implements CustomPacketPayload {
    public static final Type<ToolDebuffPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "apply_tool_debuff"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ToolDebuffPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        ToolDebuffPacket::debuffTools,
        ToolDebuffPacket::new);

    @Override
    public Type<ToolDebuffPacket> type() {
        return TYPE;
    }

    public static void execute(ToolDebuffPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AbilityHooks.ToolHooks.debuffTools = payload.debuffTools();
        }
    }
}
