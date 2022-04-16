package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.event.hooks.DimensionHooks;
import com.gildedgames.aether.core.network.AetherPacket.AbstractAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class AetherTravelPacket extends AbstractAetherPacket {
    private final boolean displayAetherTravel;

    public AetherTravelPacket(boolean displayAetherTravel) {
        this.displayAetherTravel = displayAetherTravel;
    }

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
