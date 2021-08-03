package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class CheckTimePacket extends AetherPacket
{
    private final boolean shouldCheckTime;

    public CheckTimePacket(boolean shouldCheckTime) {
        this.shouldCheckTime = shouldCheckTime;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeBoolean(this.shouldCheckTime);
    }

    public static CheckTimePacket decode(PacketBuffer buf) {
        boolean shouldCheckTime = buf.readBoolean();
        return new CheckTimePacket(shouldCheckTime);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            World world = Minecraft.getInstance().level;
            IEternalDay.get(world).ifPresent(eternalDay -> eternalDay.setCheckTime(this.shouldCheckTime));
        }
    }
}
