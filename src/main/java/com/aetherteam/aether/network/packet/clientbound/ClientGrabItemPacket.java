package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * [CODE COPY] - {@link top.theillusivec4.curios.common.network.server.SPacketGrabbedItem}.<br><br>
 * Adapted to Nitrogen packet system.
 */
public record ClientGrabItemPacket(ItemStack stack) implements BasePacket {
    public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "grab_from_accessories_inventory");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
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
