package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.client.event.listeners.capability.EternalDayClientListener;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class AetherTimePacket extends AetherPacket
{
    private final long aetherTime;

    public AetherTimePacket(long aetherTime) {
        this.aetherTime = aetherTime;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeLong(this.aetherTime);
    }

    public static AetherTimePacket decode(PacketBuffer buf) {
        long aetherTime = buf.readLong();
        return new AetherTimePacket(aetherTime);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().level.isClientSide) {
            EternalDayClientListener.aetherTime = this.aetherTime;
        }
    }
}
