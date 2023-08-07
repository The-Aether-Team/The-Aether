package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.event.hooks.DimensionHooks;
import com.aetherteam.nitrogen.network.BasePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

/**
 * Marks the player as being in the process of teleporting to the Aether. This is used for displaying "Ascending to the Aether" in the world loading screen.
 * @see com.aetherteam.aether.client.event.hooks.GuiHooks#drawAetherTravelMessage(Screen, PoseStack)
 */
public record AetherTravelPacket(boolean displayAetherTravel) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.displayAetherTravel());
    }

    public static AetherTravelPacket decode(FriendlyByteBuf buf) {
        boolean displayAetherTravel = buf.readBoolean();
        return new AetherTravelPacket(displayAetherTravel);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DimensionHooks.displayAetherTravel = this.displayAetherTravel();
        }
    }
}
