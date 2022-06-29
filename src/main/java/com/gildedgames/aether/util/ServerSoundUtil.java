package com.gildedgames.aether.util;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.player.LocalPlayer;

import javax.annotation.Nonnull;

public class ServerSoundUtil
{
    public static void playPortalSound(@Nonnull LocalPlayer localPlayer) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get(), localPlayer.level.random.nextFloat() * 0.4F + 0.8F, 0.25F));
    }
}
