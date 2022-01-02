package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.client.event.listeners.capability.EternalDayClientListener;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;

public class CheckTimePacket extends AetherPacket
{
    private final boolean shouldCheckTime;

    public CheckTimePacket(boolean shouldCheckTime) {
        this.shouldCheckTime = shouldCheckTime;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.shouldCheckTime);
    }

    public static CheckTimePacket decode(FriendlyByteBuf buf) {
        boolean shouldCheckTime = buf.readBoolean();
        return new CheckTimePacket(shouldCheckTime);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            EternalDayClientListener.shouldCheckTime = this.shouldCheckTime;
        }
    }
}
