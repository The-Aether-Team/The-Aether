package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record EternalDayPacket(boolean isEternalDay) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.isEternalDay);
    }

    public static EternalDayPacket decode(FriendlyByteBuf buf) {
        boolean isEternalDay = buf.readBoolean();
        return new EternalDayPacket(isEternalDay);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).ifPresent(aetherTime -> aetherTime.setEternalDay(this.isEternalDay));
        }
    }
}
