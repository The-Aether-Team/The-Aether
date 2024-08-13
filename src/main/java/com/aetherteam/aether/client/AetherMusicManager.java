package com.aetherteam.aether.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.api.AetherMenus;
import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.aether.client.sound.MusicSoundInstance;
import com.aetherteam.aether.entity.AetherBossMob;
import com.aetherteam.aether.mixin.mixins.client.accessor.BossHealthOverlayAccessor;
import com.aetherteam.cumulus.api.Menus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.LerpingBossEvent;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class is used to replace the menu screen music when applicable, and replace the creative mode music when in an Aether biome.
 */
public class AetherMusicManager {
    private static final int FADE_LIMIT = 50;
    private static final RandomSource random = RandomSource.create();
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final MusicManager musicManager = Minecraft.getInstance().getMusicManager();
    @Nullable
    private static SoundInstance currentMusic;
    private static int nextSongDelay = 100;
    private static Integer fade = null;

    /**
     * [CODE COPY] - {@link MusicManager#tick()}.<br><br>
     * Modified to have a {@link Music} null check.
     */
    public static void tick() {
        Music music = getSituationalMusic();
        if (currentMusic instanceof MusicSoundInstance musicSoundInstance) {
            if (musicSoundInstance.isBossMusic()) {
                if (music == null || !isAetherBossMusic(music)) {
                    if (fade == null) {
                        fade = 0;
                    }
                    musicSoundInstance.setVolume((float) Math.exp(-(fade / (FADE_LIMIT / 3.0))));
                    fade++;
                    if (fade >= FADE_LIMIT) {
                        fade = null;
                        minecraft.getSoundManager().stop(currentMusic);
                        currentMusic = null;
                        nextSongDelay = Math.min(Integer.MAX_VALUE, Mth.nextInt(random, AetherConfig.CLIENT.music_backup_min_delay.get(), AetherConfig.CLIENT.music_backup_max_delay.get() / 2)); // End boss music
                    }
                }
            } else {
                if (music != null && isAetherBossMusic(music)) {
                    if (fade == null) {
                        fade = 0;
                    }
                    musicSoundInstance.setVolume((float) Math.exp(-(fade / (FADE_LIMIT / 3.0))));
                    fade++;
                    if (fade >= FADE_LIMIT) {
                        fade = null;
                        minecraft.getSoundManager().stop(currentMusic);
                        currentMusic = null;
                        nextSongDelay = FADE_LIMIT / 3; // Start boss music
                    }
                }
            }
        }

        if (music != null) {
            if (currentMusic != null) {
                if (fade == null) {
                    if (!music.getEvent().value().getLocation().equals(currentMusic.getLocation()) && music.replaceCurrentMusic()) {
                        minecraft.getSoundManager().stop(currentMusic); // Non-copy, cancels vanilla music if Aether music starts
                        nextSongDelay = Mth.nextInt(random, 0, music.getMinDelay() / 2);
                    }

                    if (!minecraft.getSoundManager().isActive(currentMusic)) {
                        currentMusic = null;
                        nextSongDelay = Math.min(nextSongDelay, Mth.nextInt(random, music.getMinDelay(), music.getMaxDelay()));
                    }
                }
            }

            nextSongDelay = Math.min(nextSongDelay, music.getMaxDelay());
            if (currentMusic == null && nextSongDelay-- <= 0) {
                startPlaying(music);
            }
        } else {
            if (currentMusic == null || !minecraft.getSoundManager().isActive(currentMusic)) {
                currentMusic = null;
                if (nextSongDelay-- <= 0) {
                    nextSongDelay = Math.min(Integer.MAX_VALUE, Mth.nextInt(random, AetherConfig.CLIENT.music_backup_min_delay.get(), AetherConfig.CLIENT.music_backup_max_delay.get()));
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link MusicManager#startPlaying(Music)}.
     */
    public static void startPlaying(Music music) {
        musicManager.stopPlaying(); // Non-copy, cancels vanilla music if Aether music starts
        if (isAetherBossMusic(music)) {
            currentMusic = MusicSoundInstance.forBossMusic(music.getEvent().value());
        } else {
            currentMusic = MusicSoundInstance.forMusic(music.getEvent().value());
        }
        if (currentMusic.getSound() != SoundManager.EMPTY_SOUND) {
            minecraft.getSoundManager().play(currentMusic);
        }
        nextSongDelay = Integer.MAX_VALUE;
    }

    /**
     * [CODE COPY] - {@link MusicManager#stopPlaying()}.
     */
    public static void stopPlaying() {
        if (currentMusic != null) {
            minecraft.getSoundManager().stop(currentMusic); // Non-copy, cancels vanilla music if Aether music starts
            currentMusic = null;
        }
        nextSongDelay += 100;
    }

    @Nullable
    public static SoundInstance getCurrentMusic() {
        return currentMusic;
    }

    /**
     * Determines when to play different music.
     *
     * @return The {@link Music} to play.
     */
    @Nullable
    public static <T extends LivingEntity & AetherBossMob<?>> Music getSituationalMusic() {
        if (!(minecraft.screen instanceof WinScreen)) {
            if (isAetherWorldPreviewEnabled()) { // Play Aether menu music when the Aether menu world preview is enabled.
                return AetherMenus.THE_AETHER.get().getMusic();
            } else if (isVanillaWorldPreviewEnabled()) { // Play Minecraft menu music when the Minecraft menu world preview is enabled.
                return Menus.MINECRAFT.get().getMusic();
            } else if (minecraft.player != null) { // Otherwise replace creative music with biome music in the Aether.
                if (isAetherBossMusicActive()) {
                    T boss = getBossFromFight();
                    if (boss != null && boss.getHealth() > 0) {
                        Music bossMusic = boss.getBossMusic();
                        if (bossMusic != null) {
                            return boss.getBossMusic();
                        }
                    }
                } else {
                    Holder<Biome> holder = minecraft.player.level().getBiome(minecraft.player.blockPosition());
                    if (isCreative(holder, minecraft.player)) {
                        return (holder.value().getBackgroundMusic().orElse(Musics.GAME));
                    }
                }
            }
        }
        return null;
    }

    public static boolean isAetherBossMusic(Music music) {
        return music.getEvent().is(AetherTags.SoundEvents.BOSS_MUSIC);
    }

    public static boolean isAetherBossMusicActive() {
        return !AetherConfig.CLIENT.disable_aether_boss_music.get() && !getAetherBossFights().isEmpty() && minecraft.gui.getBossOverlay().shouldPlayMusic();
    }

    public static Map<UUID, LerpingBossEvent> getAetherBossFights() {
        return ((BossHealthOverlayAccessor) minecraft.gui.getBossOverlay()).getEvents().entrySet().stream().filter((entry) -> GuiHooks.isAetherBossBar(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <T extends LivingEntity & AetherBossMob<?>> T getBossFromFight() {
        for (Map.Entry<UUID, LerpingBossEvent> event : getAetherBossFights().entrySet()) {
            UUID eventUUID = event.getKey();
            int entityId = GuiHooks.BOSS_EVENTS.get(eventUUID);
            Entity entity = minecraft.player.level().getEntity(entityId);
            if (entity instanceof LivingEntity && entity instanceof AetherBossMob<?>) {
                return (T) entity;
            }
        }
        return null;
    }

    /**
     * @return Whether the world preview is enabled for an Aether menu, and the music isn't cancelled through {@link AetherConfig.Client#disable_aether_world_preview_menu_music}.
     */
    public static boolean isAetherWorldPreviewEnabled() {
        return AetherMenuUtil.isAetherMenu() && isWorldPreviewEnabled() && !AetherConfig.CLIENT.disable_aether_world_preview_menu_music.get();
    }

    /**
     * @return Whether the world preview is enabled for a Minecraft menu, and the music isn't cancelled through {@link AetherConfig.Client#disable_vanilla_world_preview_menu_music}.
     */
    public static boolean isVanillaWorldPreviewEnabled() {
        return AetherMenuUtil.isMinecraftMenu() && isWorldPreviewEnabled() && !AetherConfig.CLIENT.disable_vanilla_world_preview_menu_music.get();
    }

    /**
     * @return Whether the world preview is enabled, according to {@link WorldDisplayHelper#isActive()}, and if the player exists.
     */
    public static boolean isWorldPreviewEnabled() {
        return minecraft.player != null && WorldDisplayHelper.isActive();
    }

    /**
     * [CODE COPY] - {@link Minecraft#getSituationalMusic()}.<br><br>
     * Based on vanilla creative music checks, but also checks if the biome plays Aether music.
     */
    public static boolean isCreative(Holder<Biome> holder, Player player) {
        return player.level().dimension() != Level.END && player.level().dimension() != Level.NETHER && holder.is(AetherTags.Biomes.AETHER_MUSIC)
                && !musicManager.isPlayingMusic(Musics.UNDER_WATER) && (!player.isUnderWater() || !holder.is(BiomeTags.PLAYS_UNDERWATER_MUSIC))
                && player.getAbilities().instabuild && player.getAbilities().mayfly;
    }
}
