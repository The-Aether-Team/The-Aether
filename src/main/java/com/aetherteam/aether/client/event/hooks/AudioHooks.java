package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherMusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;

public class AudioHooks {
    /**
     * Stops other music from playing over Aether music.
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onPlaySound(PlaySoundEvent)
     */
    public static boolean shouldCancelSound(SoundInstance sound) {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            if (sound.getSource() == SoundSource.MUSIC) {
                // Check whether there is Aether music and the sound that attempts to play does not match it.
                return AetherMusicManager.getSituationalMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(AetherMusicManager.getSituationalMusic().getEvent().get()).getLocation())
                        || (AetherMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(AetherMusicManager.getCurrentMusic().getLocation()));
            }
        }
        return false;
    }

    /**
     * Ticks the Aether's music manager.
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onClientTick(TickEvent.ClientTickEvent)
     */
    public static void tick() {
        if (!AetherConfig.CLIENT.disable_music_manager.get() && !Minecraft.getInstance().isPaused()) {
            AetherMusicManager.tick();
        }
    }

    /**
     * Resets the music on respawn.
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onPlayerRespawn(ClientPlayerNetworkEvent.Clone)
     */
    public static void stop() {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            AetherMusicManager.stopPlaying();
        }
    }
}
