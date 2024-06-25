package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherMusicManager;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.mixin.mixins.client.SoundEngineAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.event.TickEvent;

import java.util.List;
import java.util.Optional;

public class AudioHooks {
    /**
     * Stops other music from playing over Aether music.
     *
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onPlaySound(PlaySoundEvent)
     */
    public static boolean shouldCancelSound(SoundInstance sound) {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            Holder<SoundEvent> soundEvent = getSoundEvent(sound);
            if (sound.getSource() == SoundSource.MUSIC && soundEvent != null && !soundEvent.is(AetherTags.SoundEvents.ACHIEVEMENT_SOUNDS)) {
                // Check whether there is Aether music and the sound that attempts to play does not match it.
                return AetherMusicManager.getSituationalMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(AetherMusicManager.getSituationalMusic().getEvent().value()).getLocation())
                        || (AetherMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(AetherMusicManager.getCurrentMusic().getLocation()));
            }
        }
        return false;
    }

    /**
     * Prevents Aether Portal sounds from overlapping each other, due to their extended audio duration.
     *
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onPlaySound(PlaySoundEvent)
     */
    public static boolean shouldCancelPortalSound(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null) {
            if (sound.getSource() == SoundSource.BLOCKS) {
                Holder<SoundEvent> soundEvent = getSoundEvent(sound);
                if (soundEvent != null && soundEvent.is(AetherTags.SoundEvents.PORTAL_SOUNDS)) {
                    List<ResourceLocation> activeSounds = ((SoundEngineAccessor) soundEngine).aether$getInstanceToChannel().keySet().stream().map(SoundInstance::getLocation).toList();
                    return activeSounds.contains(sound.getLocation());
                }
            }
        }
        return false;
    }

    private static Holder<SoundEvent> getSoundEvent(SoundInstance sound) {
        SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(sound.getLocation());
        if (soundEvent != null) {
            Optional<ResourceKey<SoundEvent>> optionalResourceKey = BuiltInRegistries.SOUND_EVENT.getResourceKey(soundEvent);
            if (optionalResourceKey.isPresent()) {
                return BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(optionalResourceKey.get());
            }
        }
        return null;
    }

    /**
     * Ticks the Aether's music manager.
     *
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onClientTick(TickEvent.ClientTickEvent)
     */
    public static void tick() {
        if (!AetherConfig.CLIENT.disable_music_manager.get() && !Minecraft.getInstance().isPaused()) {
            AetherMusicManager.tick();
        }
    }

    /**
     * Resets the music on respawn.
     *
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onPlayerRespawn(ClientPlayerNetworkEvent.Clone)
     */
    public static void stop() {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            AetherMusicManager.stopPlaying();
        }
    }
}
