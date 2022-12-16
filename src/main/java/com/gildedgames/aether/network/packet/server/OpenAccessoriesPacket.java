package com.gildedgames.aether.network.packet.server;

import com.gildedgames.aether.inventory.menu.AccessoriesMenu;
import com.gildedgames.aether.network.AetherPacket;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.ClientGrabItemPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkHooks;

public record OpenAccessoriesPacket(ItemStack carryStack) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(this.carryStack);
    }

    public static OpenAccessoriesPacket decode(FriendlyByteBuf buf) {
        ItemStack carryStack = buf.readItem();
        return new OpenAccessoriesPacket(carryStack);
    }

    @Override
    public void execute(Player player) {
        if (player != null && player.getServer() != null && player instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.isCreative() ? this.carryStack : serverPlayer.containerMenu.getCarried();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((id, inventory, playerEntity) -> new AccessoriesMenu(id, inventory), Component.translatable("container.crafting")));
            if (!itemStack.isEmpty()) {
                serverPlayer.containerMenu.setCarried(itemStack);
                AetherPacketHandler.sendToPlayer(new ClientGrabItemPacket(itemStack), serverPlayer);
            }
        }
    }
}
