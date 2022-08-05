package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.client.AetherMusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class AudioHooks {
    public static SoundInstance lastSound = null;

    public static boolean shouldCancelSound(SoundInstance sound) {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            if (lastSound != null && !sound.getLocation().equals(lastSound.getLocation()) && sound.getSource() == SoundSource.MUSIC) {
                boolean flag = AetherMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(AetherMusicManager.getCurrentMusic().getLocation())
                        || (AetherMusicManager.isAetherMenuEnabled() && sound.getLocation().equals(SoundEvents.MUSIC_MENU.getLocation()))
                        || (Minecraft.getInstance().player != null && AetherMusicManager.isCreative(Minecraft.getInstance().player.level.getBiome(Minecraft.getInstance().player.blockPosition()), Minecraft.getInstance().player) && sound.getLocation().equals(SoundEvents.MUSIC_CREATIVE.getLocation()));
                Aether.LOGGER.info(flag);
                return flag;
            }
            lastSound = sound;
        }
        return false;
    }

    public static void tick() {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            AetherMusicManager.tick();
        }
    }

    public static void stop() {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            AetherMusicManager.stopPlaying();
        }
    }
}
