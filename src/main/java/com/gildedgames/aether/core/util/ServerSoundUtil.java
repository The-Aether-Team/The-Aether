package com.gildedgames.aether.core.util;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;

import javax.annotation.Nonnull;

public class ServerSoundUtil
{
    public static void playPortalSound(@Nonnull ClientPlayerEntity playerEntity) {
        Minecraft.getInstance().getSoundManager().play(SimpleSound.forLocalAmbience(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get(), playerEntity.level.random.nextFloat() * 0.4F + 0.8F, 0.25F));
    }
}
