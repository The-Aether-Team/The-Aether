package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.network.AetherPacket;
import com.gildedgames.aether.util.ServerSoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;

public record PortalTravelSoundPacket() implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) { }

    public static PortalTravelSoundPacket decode(FriendlyByteBuf buf) {
        return new PortalTravelSoundPacket();
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            ServerSoundUtil.playPortalSound(Minecraft.getInstance().player);
        }
    }
}
