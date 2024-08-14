package com.aetherteam.aether.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class MusicSoundInstance extends AbstractTickableSoundInstance {
    private final boolean isBossMusic;

    public MusicSoundInstance(
        SoundEvent event,
        SoundSource source,
        float volume,
        float pitch,
        RandomSource random,
        boolean looping,
        int delay,
        Attenuation attenuation,
        double x,
        double y,
        double z,
        boolean relative,
        boolean isBossMusic
    ) {
        super(event, source, random);
        this.volume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.looping = looping;
        this.delay = delay;
        this.attenuation = attenuation;
        this.relative = relative;
        this.isBossMusic = isBossMusic;
    }

    public static MusicSoundInstance forMusic(SoundEvent sound) {
        return new MusicSoundInstance(
            sound,
            SoundSource.MUSIC,
            1.0F,
            1.0F,
            SoundInstance.createUnseededRandom(),
            false,
            0,
            Attenuation.NONE,
            0.0,
            0.0,
            0.0,
            true,
            false
        );
    }

    public static MusicSoundInstance forBossMusic(SoundEvent sound) {
        return new MusicSoundInstance(
            sound,
            SoundSource.MUSIC,
            1.0F,
            1.0F,
            SoundInstance.createUnseededRandom(),
            true,
            0,
            Attenuation.NONE,
            0.0,
            0.0,
            0.0,
            true,
            true
        );
    }

    @Override
    public void tick() {

    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isBossMusic() {
        return this.isBossMusic;
    }
}
