package com.gildedgames.aether.client;

import com.gildedgames.aether.client.gui.screen.menu.AetherTitleScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.util.LevelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Handles the Aether title screen music.
 * Based off of MusicManager.
 */
public class AetherMusicManager {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final MusicManager musicManager = Minecraft.getInstance().getMusicManager();
    private static final Random random = new Random();
    @Nullable
    private static SoundInstance currentMusic;
    private static int nextSongDelay = 100;

    /**
     * Checks every client tick if the requirements for Aether music are fulfilled.
     */
    public static void tick() {
        Music minecraftMusic = minecraft.getSituationalMusic();
        Music aetherMusic = checkForReplacements(minecraftMusic);
        if (currentMusic != null) {
            if (aetherMusic != null) {
                if (aetherMusic.replaceCurrentMusic()) {
                    if (!aetherMusic.getEvent().getLocation().equals(currentMusic.getLocation())) {
                        minecraft.getSoundManager().stop(currentMusic);
                        nextSongDelay = Mth.nextInt(random, 0, minecraftMusic.getMinDelay() / 2);
                    }
                }
                //Stops vanilla music from playing.
                if (musicManager.isPlayingMusic(minecraftMusic)) {
                    musicManager.stopPlaying();
                }
            } else if (!minecraftMusic.getEvent().getLocation().equals(currentMusic.getLocation()) && minecraftMusic.replaceCurrentMusic()) {
                minecraft.getSoundManager().stop(currentMusic);
                nextSongDelay = Mth.nextInt(random, 0, minecraftMusic.getMinDelay() / 2);
            }
            if (!minecraft.getSoundManager().isActive(currentMusic)) {
                currentMusic = null;
                nextSongDelay = Math.min(nextSongDelay, Mth.nextInt(random, minecraftMusic.getMinDelay(), minecraftMusic.getMaxDelay()));
            }
        }
        nextSongDelay = Math.min(nextSongDelay, minecraftMusic.getMaxDelay());
        if (aetherMusic != null && currentMusic == null && nextSongDelay-- <= 0) {
            startPlaying(aetherMusic);
        }
    }

    /**
     * Corrects the music to the corresponding Aether music if the situation is appropriate.
     */
    @Nullable
    public static Music checkForReplacements(Music music) {
        if ((AetherWorldDisplayHelper.loadedLevel != null || music == Musics.MENU) && AetherConfig.CLIENT.enable_aether_menu.get() && !AetherConfig.CLIENT.disable_aether_menu_music.get()) {
            return AetherTitleScreen.MENU;
        }
        if (AetherWorldDisplayHelper.loadedLevel != null) {
            return Musics.MENU;
        }
        if (music == Musics.CREATIVE && minecraft.player != null && minecraft.level != null && LevelUtil.inTag(minecraft.level, AetherTags.Dimensions.AETHER_MUSIC)) {
            return minecraft.player.level.getBiome(minecraft.player.blockPosition()).value().getBackgroundMusic().orElse(Musics.GAME);
        }
        return null;
    }

    /**
     * Vanilla copy
     * @see MusicManager#startPlaying(Music) 
     */
    public static void startPlaying(Music pSelector) {
        currentMusic = SimpleSoundInstance.forMusic(pSelector.getEvent());
        if (currentMusic.getSound() != SoundManager.EMPTY_SOUND) {
            minecraft.getSoundManager().play(currentMusic);
        }

        nextSongDelay = Integer.MAX_VALUE;
    }

    /**
     * Vanilla copy
     * @see MusicManager#stopPlaying() 
     */
    public static void stopPlaying() {
        if (currentMusic != null) {
            minecraft.getSoundManager().stop(currentMusic);
            currentMusic = null;
        }
        nextSongDelay += 100;
    }
}
