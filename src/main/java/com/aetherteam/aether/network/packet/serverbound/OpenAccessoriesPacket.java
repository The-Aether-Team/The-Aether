package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.inventory.menu.AetherAccessoriesMenu;
import com.aetherteam.aether.network.packet.clientbound.ClientGrabItemPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * [CODE COPY] - {@link top.theillusivec4.curios.common.network.client.CPacketOpenCurios}.<br><br> //todo new reference
 * Adapted to Nitrogen packet system.
 */
public record OpenAccessoriesPacket(ItemStack carryStack) implements CustomPacketPayload {
    public static final Type<OpenAccessoriesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "open_accessories"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenAccessoriesPacket> STREAM_CODEC = StreamCodec.composite(
        ItemStack.OPTIONAL_STREAM_CODEC,
        OpenAccessoriesPacket::carryStack,
        OpenAccessoriesPacket::new);

    @Override
    public Type<OpenAccessoriesPacket> type() {
        return TYPE;
    }

    public static void execute(OpenAccessoriesPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.isCreative() ? payload.carryStack() : serverPlayer.containerMenu.getCarried();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, player) -> new AetherAccessoriesMenu(id, inventory), Component.translatable("container.crafting")));
            if (!itemStack.isEmpty()) {
                serverPlayer.containerMenu.setCarried(itemStack);
                PacketDistributor.sendToPlayer(serverPlayer, new ClientGrabItemPacket(itemStack));
            }
        }
    }
}
