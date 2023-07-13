package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.ClientGrabItemPacket;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;

public record OpenInventoryPacket(ItemStack carryStack) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(this.carryStack);
    }

    public static OpenInventoryPacket decode(FriendlyByteBuf buf) {
        ItemStack carryStack = buf.readItem();
        return new OpenInventoryPacket(carryStack);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.isCreative() ? this.carryStack : serverPlayer.containerMenu.getCarried();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            serverPlayer.doCloseContainer();
            if (!itemStack.isEmpty()) {
                if (!serverPlayer.isCreative()) {
                    serverPlayer.containerMenu.setCarried(itemStack);
                    PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, new ClientGrabItemPacket(itemStack), serverPlayer);
                }
            }
        }
    }
}
