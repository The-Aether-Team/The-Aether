package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.network.IAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class EternalDayPacket extends IAetherPacket.AetherPacket {
    private final boolean isEternalDay;

    public EternalDayPacket(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

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
            Minecraft.getInstance().level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).ifPresent((aetherTime -> aetherTime.setEternalDay(this.isEternalDay)));
        }
    }
}
