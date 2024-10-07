package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Clears the item currently held by the player's mouse in a container GUI.
 */
public record ClearItemPacket(int playerID) implements CustomPacketPayload {
    public static final Type<ClearItemPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "clear_held_item"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClearItemPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        ClearItemPacket::playerID,
        ClearItemPacket::new);

    @Override
    public Type<ClearItemPacket> type() {
        return TYPE;
    }

    public static void execute(ClearItemPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.getServer() != null && playerEntity.level().getEntity(payload.playerID()) instanceof ServerPlayer serverPlayer) {
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
        }
    }
}
