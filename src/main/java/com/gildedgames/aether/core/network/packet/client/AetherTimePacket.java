package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.client.event.listeners.capability.EternalDayClientListener;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;

public class AetherTimePacket extends AetherPacket
{
    private final long aetherTime;

    public AetherTimePacket(long aetherTime) {
        this.aetherTime = aetherTime;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(this.aetherTime);
    }

    public static AetherTimePacket decode(FriendlyByteBuf buf) {
        long aetherTime = buf.readLong();
        return new AetherTimePacket(aetherTime);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            EternalDayClientListener.aetherTime = this.aetherTime;
        }
    }
}
