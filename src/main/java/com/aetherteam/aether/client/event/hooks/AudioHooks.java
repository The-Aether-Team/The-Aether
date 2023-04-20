package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherMusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class AudioHooks {
    public static boolean shouldCancelSound(SoundInstance sound) {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            if (sound.getSource() == SoundSource.MUSIC) {
                return AetherMusicManager.getSituationalMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(AetherMusicManager.getSituationalMusic().getEvent().get()).getLocation())
                        || (AetherMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(AetherMusicManager.getCurrentMusic().getLocation()));
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
