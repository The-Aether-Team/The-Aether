package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.AudioHooks;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.event.TickEvent;

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
     * @see AudioHooks#shouldCancelSound(SoundInstance)
     */
    public static void onPlaySound(PlaySoundEvent event) {
        SoundEngine soundEngine = event.getEngine();
        SoundInstance sound = event.getOriginalSound();
        SoundInstance newSound = event.getSound();
        if (AudioHooks.shouldCancelSound(sound) || AudioHooks.shouldCancelPortalSound(soundEngine, newSound)) {
            event.setSound(null);
        }
    }

    /**
     * @see AudioHooks#tick()
     */
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            AudioHooks.tick();
        }
    }

    /**
     * @see AudioHooks#stop()
     */
    public static void onPlayerRespawn(ClientPlayerNetworkEvent.Clone event) {
        AudioHooks.stop();
    }
}
