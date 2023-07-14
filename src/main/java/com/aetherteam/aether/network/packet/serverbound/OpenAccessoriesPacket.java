package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.inventory.menu.AccessoriesMenu;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.ClientGrabItemPacket;
import com.aetherteam.nitrogen.network.BasePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkHooks;

/**
 * Based on {@link top.theillusivec4.curios.common.network.client.CPacketOpenCurios}.
 */
public record OpenAccessoriesPacket(ItemStack carryStack) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(this.carryStack());
    }

    public static OpenAccessoriesPacket decode(FriendlyByteBuf buf) {
        ItemStack carryStack = buf.readItem();
        return new OpenAccessoriesPacket(carryStack);
    }

    @Override
    public void execute(Player player) {
        if (player != null && player.getServer() != null && player instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.isCreative() ? this.carryStack() : serverPlayer.containerMenu.getCarried();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((id, inventory, playerEntity) -> new AccessoriesMenu(id, inventory), Component.translatable("container.crafting")));
            if (!itemStack.isEmpty()) {
                serverPlayer.containerMenu.setCarried(itemStack);
                PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, new ClientGrabItemPacket(itemStack), serverPlayer);
            }
        }
    }
}
