package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.client.AetherMusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class AudioHooks {
    public static boolean shouldCancelSound(SoundInstance sound) {
        Aether.LOGGER.info(0);
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            Aether.LOGGER.info(1);
            if (sound.getSource() == SoundSource.MUSIC) {
                Aether.LOGGER.info(2);
                boolean flag = AetherMusicManager.getSituationalMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(AetherMusicManager.getSituationalMusic().getEvent()).getLocation())
                        || (AetherMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(AetherMusicManager.getCurrentMusic().getLocation()));
                Aether.LOGGER.info(flag);
                return flag;
            }
        }
        return false;
    }

    public static void tick() {
        if (!AetherConfig.CLIENT.disable_music_manager.get() && !Minecraft.getInstance().isPaused()) {
            AetherMusicManager.tick();
        }
    }

    public static void stop() {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            AetherMusicManager.stopPlaying();
        }
    }
}
