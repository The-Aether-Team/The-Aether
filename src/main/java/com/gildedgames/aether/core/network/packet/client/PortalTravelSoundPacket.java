package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import com.gildedgames.aether.core.util.ServerSoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PortalTravelSoundPacket extends AetherPacket
{
    public PortalTravelSoundPacket() { }

    @Override
    public void encode(PacketBuffer buf) { }

    public static PortalTravelSoundPacket decode(PacketBuffer buf) {
        return new PortalTravelSoundPacket();
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            ServerSoundUtil.playPortalSound(Minecraft.getInstance().player);
        }
    }
}
