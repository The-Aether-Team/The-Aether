package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.network.packet.clientbound.ClientGrabItemPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * [CODE COPY] - {@link top.theillusivec4.curios.common.network.client.CPacketOpenVanilla}.<br><br> //todo new reference
 * Adapted to Nitrogen packet system.
 */
public record OpenInventoryPacket(ItemStack carryStack) implements CustomPacketPayload {
    public static final Type<OpenInventoryPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "open_vanilla_inventory"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenInventoryPacket> STREAM_CODEC = StreamCodec.composite(
        ItemStack.OPTIONAL_STREAM_CODEC,
        OpenInventoryPacket::carryStack,
        OpenInventoryPacket::new);

    @Override
    public Type<OpenInventoryPacket> type() {
        return TYPE;
    }

    public static void execute(OpenInventoryPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.isCreative() ? payload.carryStack() : serverPlayer.containerMenu.getCarried();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            serverPlayer.doCloseContainer();
            if (!itemStack.isEmpty()) {
                if (!serverPlayer.isCreative()) {
                    serverPlayer.containerMenu.setCarried(itemStack);
                    PacketDistributor.sendToPlayer(serverPlayer, new ClientGrabItemPacket(itemStack));
                }
            }
        }
    }
}
