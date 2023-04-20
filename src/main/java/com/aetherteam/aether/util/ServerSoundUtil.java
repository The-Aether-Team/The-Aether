package com.aetherteam.aether.util;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;

public class ServerSoundUtil {
    /**
     * Based on {@link net.minecraft.client.renderer.LevelRenderer#levelEvent(int, BlockPos, int)}, event 1032.
     */
    public static void playPortalSound(LocalPlayer localPlayer) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get(), localPlayer.level.random.nextFloat() * 0.4F + 0.8F, 0.25F));
    }
}
