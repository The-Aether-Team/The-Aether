package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Based on {@link top.theillusivec4.curios.common.network.server.SPacketGrabbedItem}.
 */
public record ClientGrabItemPacket(ItemStack stack) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(this.stack());
    }

    public static ClientGrabItemPacket decode(FriendlyByteBuf buf) {
        ItemStack stack = buf.readItem();
        return new ClientGrabItemPacket(stack);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().player.containerMenu.setCarried(this.stack());
        }
    }
}
