package com.aetherteam.aether.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen;
import com.aetherteam.nitrogen.client.NitrogenClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;

/**
 * This class is used to replace the menu screen music when applicable, and replace the creative mode music when in an Aether biome.
 */
public class AetherMusicManager {
    private static final RandomSource random = RandomSource.create();
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final MusicManager musicManager = Minecraft.getInstance().getMusicManager();
    @Nullable
    private static SoundInstance currentMusic;
    private static int nextSongDelay = 100;

    public static void tick() {
        Music music = getSituationalMusic();
        if (music != null) {
            if (currentMusic != null) {
                if (!music.getEvent().get().getLocation().equals(currentMusic.getLocation()) && music.replaceCurrentMusic()) {
                    minecraft.getSoundManager().stop(currentMusic);
                    nextSongDelay = Mth.nextInt(random, 0, music.getMinDelay() / 2);
                }

                if (!minecraft.getSoundManager().isActive(currentMusic)) {
                    currentMusic = null;
                    nextSongDelay = Math.min(nextSongDelay, Mth.nextInt(random, music.getMinDelay(), music.getMaxDelay()));
                }
            }

            nextSongDelay = Math.min(nextSongDelay, music.getMaxDelay());
            if (currentMusic == null && nextSongDelay-- <= 0) {
                startPlaying(music);
            }
        } else {
            currentMusic = null;
            if (nextSongDelay-- <= 0) {
                nextSongDelay = Math.min(Integer.MAX_VALUE, Mth.nextInt(random, AetherConfig.CLIENT.music_backup_min_delay.get(), AetherConfig.CLIENT.music_backup_max_delay.get()));
            }
        }
    }

    /**
     * CODE COPY
     * @see MusicManager#startPlaying(Music)
     */
    public static void startPlaying(Music pSelector) {
        musicManager.stopPlaying(); // non-copy, cancels vanilla music if Aether music starts
        currentMusic = SimpleSoundInstance.forMusic(pSelector.getEvent().get());
        if (currentMusic.getSound() != SoundManager.EMPTY_SOUND) {
            minecraft.getSoundManager().play(currentMusic);
        }
        nextSongDelay = Integer.MAX_VALUE;
    }

    /**
     * CODE COPY
     * @see MusicManager#stopPlaying()
     */
    public static void stopPlaying() {
        if (currentMusic != null) {
            minecraft.getSoundManager().stop(currentMusic);
            currentMusic = null;
        }
        nextSongDelay += 100;
    }

    @Nullable
    public static SoundInstance getCurrentMusic() {
        return currentMusic;
    }

    public static Music getSituationalMusic() {
        if (!(minecraft.screen instanceof WinScreen)) {
            Music activeMusic = NitrogenClient.MENU_HELPER.getActiveMusic();
            if (isAetherWorldPreviewEnabled()) {
                return AetherTitleScreen.MENU;
            } else if (isVanillaWorldPreviewEnabled()) {
                return Musics.MENU;
            } else if (minecraft.player != null) {
                Holder<Biome> holder = minecraft.player.level.getBiome(minecraft.player.blockPosition());
                if (isCreative(holder, minecraft.player)) {
                    return (holder.value().getBackgroundMusic().orElse(Musics.GAME));
                }
            } else if (activeMusic != Musics.MENU) {
                return activeMusic;
            }
        }
        return null;
    }

    public static boolean isAetherMenuEnabled() {
        return AetherMenuUtil.isAetherMenu() && !AetherConfig.CLIENT.disable_aether_menu_music.get();
    }

    public static boolean isAetherWorldPreviewEnabled() {
        return AetherMenuUtil.isAetherMenu() && isWorldPreviewEnabled() && !AetherConfig.CLIENT.disable_aether_world_preview_menu_music.get();
    }

    public static boolean isVanillaWorldPreviewEnabled() {
        return isWorldPreviewEnabled() && !AetherConfig.CLIENT.disable_vanilla_world_preview_menu_music.get();
    }

    public static boolean isWorldPreviewEnabled() {
        return minecraft.player != null && AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null;
    }

    public static boolean isCreative(Holder<Biome> holder, Player player) {
        return player.level.dimension() != Level.END && player.level.dimension() != Level.NETHER && holder.is(AetherTags.Biomes.AETHER_MUSIC)
                && !musicManager.isPlayingMusic(Musics.UNDER_WATER) && (!player.isUnderWater() || !holder.is(BiomeTags.PLAYS_UNDERWATER_MUSIC))
                && player.getAbilities().instabuild && player.getAbilities().mayfly;
    }
}
