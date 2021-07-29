package com.gildedgames.aether.core.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class ClientGrabItemPacket extends AetherPacket
{
    private final ItemStack stack;

    public ClientGrabItemPacket(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeItem(this.stack);
    }

    public static ClientGrabItemPacket decode(PacketBuffer buf) {
        ItemStack stack = buf.readItem();
        return new ClientGrabItemPacket(stack);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            PlayerEntity player = Minecraft.getInstance().player;
            player.inventory.setCarried(this.stack);
        }
    }
}
