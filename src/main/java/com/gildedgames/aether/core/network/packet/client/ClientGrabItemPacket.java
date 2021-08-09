package com.gildedgames.aether.core.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class ClientGrabItemPacket extends AetherPacket
{
    private final int entityID;
    private final ItemStack stack;

    public ClientGrabItemPacket(int entityID, ItemStack stack) {
        this.entityID = entityID;
        this.stack = stack;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeItem(this.stack);
    }

    public static ClientGrabItemPacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        ItemStack stack = buf.readItem();
        return new ClientGrabItemPacket(entityID, stack);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.entityID);
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                player.inventory.setCarried(this.stack);
            }
        }
    }
}
