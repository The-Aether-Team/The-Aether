package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Sets a player's invisibility on the client side from the server side.
 */
public record SetInvisibilityPacket(int playerID, boolean invisible) implements CustomPacketPayload {
    public static final Type<SetInvisibilityPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "set_invisibility_status"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetInvisibilityPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        SetInvisibilityPacket::playerID,
        ByteBufCodecs.BOOL,
        SetInvisibilityPacket::invisible,
        SetInvisibilityPacket::new);

    @Override
    public Type<SetInvisibilityPacket> type() {
        return TYPE;
    }

    public static void execute(SetInvisibilityPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(payload.playerID()) instanceof Player player) {
                player.setInvisible(payload.invisible());
            }
        }
    }
}
