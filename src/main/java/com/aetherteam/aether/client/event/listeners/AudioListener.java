package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.AudioHooks;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class AudioListener {
    /**
     * @see AudioHooks#shouldCancelMusic(SoundInstance)
     */
    @SubscribeEvent
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
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            AudioHooks.tick();
        }
    }

    /**
     * @see AudioHooks#stop()
     */
    @SubscribeEvent
    public static void onPlayerRespawn(ClientPlayerNetworkEvent.Clone event) {
        AudioHooks.stop();
    }
}
