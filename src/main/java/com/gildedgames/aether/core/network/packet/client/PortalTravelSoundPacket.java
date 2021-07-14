package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvents;

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
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forLocalAmbience(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get(), Minecraft.getInstance().player.level.random.nextFloat() * 0.4F + 0.8F, 0.25F));
        }
    }
}
