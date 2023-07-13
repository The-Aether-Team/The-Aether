package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.event.hooks.DimensionHooks;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record AetherTravelPacket(boolean displayAetherTravel) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.displayAetherTravel);
    }

    public static AetherTravelPacket decode(FriendlyByteBuf buf) {
        boolean displayAetherTravel = buf.readBoolean();
        return new AetherTravelPacket(displayAetherTravel);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DimensionHooks.displayAetherTravel = this.displayAetherTravel;
        }
    }
}
