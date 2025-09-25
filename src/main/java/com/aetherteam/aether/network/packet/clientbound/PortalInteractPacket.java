package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PortalInteractPacket(int playerID, boolean mainHand) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PortalInteractPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "interact_with_portal"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PortalInteractPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        PortalInteractPacket::playerID,
        ByteBufCodecs.BOOL,
        PortalInteractPacket::mainHand,
        PortalInteractPacket::new);

    @Override
    public CustomPacketPayload.Type<PortalInteractPacket> type() {
        return TYPE;
    }

    public static void execute(PortalInteractPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(payload.playerID());
            if (entity instanceof Player player) {
                player.swing(payload.mainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
                player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
            }
        }
    }
}
