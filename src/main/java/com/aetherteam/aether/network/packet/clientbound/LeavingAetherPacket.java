package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Marks the player as being in the process of leaving the Aether. This is used for displaying "Descending from the Aether" in the world loading screen.
 *
 * @see com.aetherteam.aether.client.event.hooks.GuiHooks#drawAetherTravelMessage(Screen, GuiGraphics)
 */
public record LeavingAetherPacket(boolean playerLeavingAether) implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "leave_aether");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.playerLeavingAether());
    }

    public static LeavingAetherPacket decode(FriendlyByteBuf buf) {
        boolean playerLeavingAether = buf.readBoolean();
        return new LeavingAetherPacket(playerLeavingAether);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DimensionHooks.playerLeavingAether = this.playerLeavingAether();
        }
    }
}
