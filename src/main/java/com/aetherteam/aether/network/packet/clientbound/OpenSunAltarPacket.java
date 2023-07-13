package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public record OpenSunAltarPacket(Component name) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(this.name);
    }

    public static OpenSunAltarPacket decode(FriendlyByteBuf buf) {
        Component name = buf.readComponent();
        return new OpenSunAltarPacket(name);
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AetherClient.setToSunAltarScreen(this.name);
        }
    }
}
