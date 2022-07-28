package com.gildedgames.aether.client;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import javax.annotation.Nullable;

/**
 * This class is used to replace the menu screen music when applicable, and replace the creative mode music when in an Aether biome.
 */
public class AetherMusicManager {
    public static Minecraft minecraft = Minecraft.getInstance();
    public static boolean isPlaying = false;
    @Nullable
    public static SoundInstance currentMusic;

    /**
     * Sets the game's music depending on the situation. This method runs every tick.
     */
    public static void tick() {
        if (currentMusic != null) {
            if (!minecraft.getSoundManager().isActive(currentMusic)) {
                minecraft.getSoundManager().stop(currentMusic);
                currentMusic = null;
            }
        }
        isPlaying = currentMusic != null;
        if (isPlaying) {
            minecraft.getMusicManager().stopPlaying();
        }
    }

    /**
     * Stops whatever music is currently playing.
     */
    public static void stopMusic() {
        if (currentMusic != null) {
            minecraft.getSoundManager().stop(currentMusic);
            currentMusic = null;
        }
    }

    /**
     * Play the Aether menu music over the vanilla menu music.
     */
    public static void handleMenuMusic() {
        if (currentMusic == null && AetherConfig.CLIENT.enable_aether_menu.get() && !AetherConfig.CLIENT.disable_aether_menu_music.get()) {
            SoundEvent sound = AetherSoundEvents.MUSIC_MENU.get();
            currentMusic = SimpleSoundInstance.forMusic(sound);
            minecraft.getSoundManager().play(currentMusic);
            isPlaying = true;
        }
    }

    /**
     * Handles the music for the world preview.
     */
    public static void handleWorldPreviewMusic() {
        if (currentMusic == null) {
            SoundEvent sound = AetherConfig.CLIENT.enable_aether_menu.get() && !AetherConfig.CLIENT.disable_aether_menu_music.get() ? AetherSoundEvents.MUSIC_MENU.get() : SoundEvents.MUSIC_MENU;
            currentMusic = SimpleSoundInstance.forMusic(sound);
            minecraft.getSoundManager().play(currentMusic);
            isPlaying = true;
        }
    }

    /**
     * Plays the biome's music over the creative music.
     */
    public static void handleCreativeMusic() {
        if (currentMusic == null && minecraft.player != null && minecraft.player.level.getBiome(minecraft.player.blockPosition()).is(AetherTags.Biomes.AETHER_MUSIC) && minecraft.player.getAbilities().instabuild && minecraft.player.getAbilities().mayfly) {
            SoundEvent sound = minecraft.player.level.getBiome(minecraft.player.blockPosition()).get().getBackgroundMusic().orElse(Musics.GAME).getEvent();
            currentMusic = SimpleSoundInstance.forMusic(sound);
            minecraft.getSoundManager().play(currentMusic);
            isPlaying = true;
        }
    }
}
