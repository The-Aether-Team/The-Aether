package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.AudioHooks;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

public class AudioListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(AudioListener::onPlaySound);
        bus.addListener(AudioListener::onClientTick);
        bus.addListener(AudioListener::onPlayerRespawn);
    }

    /**
     * @see AudioHooks#shouldCancelMusic(SoundInstance)
     */
    public static void onPlaySound(PlaySoundEvent event) {
        SoundEngine soundEngine = event.getEngine();
        SoundInstance sound = event.getOriginalSound();
        if (AudioHooks.shouldCancelMusic(sound) || AudioHooks.preventAmbientPortalSound(soundEngine, sound)) {
            event.setSound(null);
        }
        AudioHooks.overrideActivatedPortalSound(soundEngine, sound);
    }

    /**
     * @see AudioHooks#tick()
     */
    public static void onClientTick(ClientTickEvent.Post event) {
        AudioHooks.tick();
    }

    /**
     * @see AudioHooks#stop()
     */
    public static void onPlayerRespawn(ClientPlayerNetworkEvent.Clone event) {
        AudioHooks.stop();
    }
}
