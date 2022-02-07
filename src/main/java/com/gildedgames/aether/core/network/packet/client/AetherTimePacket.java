package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.network.IAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class AetherTimePacket extends IAetherPacket.AetherPacket {
    private final long dayTime;

    public AetherTimePacket(long dayTime) {
        this.dayTime = dayTime;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(this.dayTime);
    }

    public static AetherTimePacket decode(FriendlyByteBuf buf) {
        long dayTime = buf.readLong();
        return new AetherTimePacket(dayTime);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).ifPresent(aetherTime -> aetherTime.setDayTime(this.dayTime));
        }
    }
}
