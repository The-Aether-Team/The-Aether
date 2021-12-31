package com.gildedgames.aether.core.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;

public class ClientGrabItemPacket extends AetherPacket
{
    private final int entityID;
    private final ItemStack stack;

    public ClientGrabItemPacket(int entityID, ItemStack stack) {
        this.entityID = entityID;
        this.stack = stack;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(this.stack);
    }

    public static ClientGrabItemPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        ItemStack stack = buf.readItem();
        return new ClientGrabItemPacket(entityID, stack);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level.getEntity(this.entityID) instanceof Player player) {
            player.containerMenu.setCarried(this.stack); //TODO: Make sure this works. setCarried was previously handled in Inventory and now its not. This can't be verified until the addWidget code in GuiListener is fixed.
        }
    }
}
