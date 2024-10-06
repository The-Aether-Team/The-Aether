package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Marks the player as being in the process of teleporting to the Aether. This is used for displaying "Ascending to the Aether" in the world loading screen.
 *
 * @see com.aetherteam.aether.client.event.hooks.GuiHooks#drawAetherTravelMessage(Screen, net.minecraft.client.gui.GuiGraphics)
 */
public record AetherTravelPacket(boolean displayAetherTravel) implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "travel_across_dimensions");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
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
