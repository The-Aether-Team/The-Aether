package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.portal.PortalSoundUtil;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Plays the Aether Portal sound on the client from {@link com.aetherteam.aether.block.portal.AetherPortalForcer}.
 */
public record PortalTravelSoundPacket() implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "play_portal_travel_sound");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
    }

    public static PortalTravelSoundPacket decode(FriendlyByteBuf buf) {
        return new PortalTravelSoundPacket();
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            PortalSoundUtil.playPortalSound(Minecraft.getInstance().player);
        }
    }
}
