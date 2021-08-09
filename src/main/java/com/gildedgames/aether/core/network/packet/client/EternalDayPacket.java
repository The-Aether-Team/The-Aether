package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.client.event.listeners.capability.EternalDayClientListener;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class EternalDayPacket extends AetherPacket
{
    private final boolean isEternalDay;

    public EternalDayPacket(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeBoolean(this.isEternalDay);
    }

    public static EternalDayPacket decode(PacketBuffer buf) {
        boolean isEternalDay = buf.readBoolean();
        return new EternalDayPacket(isEternalDay);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            EternalDayClientListener.isEternalDay = this.isEternalDay;
        }
    }
}
