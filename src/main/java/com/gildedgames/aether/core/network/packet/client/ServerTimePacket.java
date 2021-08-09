package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.client.event.listeners.capability.EternalDayClientListener;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class ServerTimePacket extends AetherPacket
{
    private final long serverTime;

    public ServerTimePacket(long serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeLong(this.serverTime);
    }

    public static ServerTimePacket decode(PacketBuffer buf) {
        long serverTime = buf.readLong();
        return new ServerTimePacket(serverTime);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            EternalDayClientListener.serverWorldTime = this.serverTime;
        }
    }
}
