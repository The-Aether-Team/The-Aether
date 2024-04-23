package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.event.hooks.AudioHooks;
import io.github.fabricators_of_create.porting_lib.client_events.event.client.PlaySoundCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;

public class AudioListener {
    /**
     * @see AudioHooks#shouldCancelSound(SoundInstance)
     */
    public static SoundInstance onPlaySound(SoundEngine engine, SoundInstance sound, SoundInstance originalSound) {
        if (AudioHooks.shouldCancelSound(originalSound)) {
            return null;
        }
        return sound;
    }

    /**
     * @see AudioHooks#tick()
     */
    public static void onClientTick(Minecraft minecraft) {
        AudioHooks.tick();
    }

    /**
     * @see AudioHooks#stop()
     */
    public static void onPlayerRespawn() {
        AudioHooks.stop();
    }

    public static void init() {
        PlaySoundCallback.EVENT.register(AudioListener::onPlaySound);
        ClientTickEvents.END_CLIENT_TICK.register(AudioListener::onClientTick);
    }
}
