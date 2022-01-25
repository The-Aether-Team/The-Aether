package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.client.event.listeners.capability.EternalDayClientListener;
import com.gildedgames.aether.core.network.AetherPacket.BaseAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;

public class ServerTimePacket extends BaseAetherPacket
{
    private final long serverTime;

    public ServerTimePacket(long serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(this.serverTime);
    }

    public static ServerTimePacket decode(FriendlyByteBuf buf) {
        long serverTime = buf.readLong();
        return new ServerTimePacket(serverTime);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            EternalDayClientListener.serverLevelTime = this.serverTime;
        }
    }
}
