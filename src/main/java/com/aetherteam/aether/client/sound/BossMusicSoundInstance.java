package com.aetherteam.aether.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class BossMusicSoundInstance extends AbstractTickableSoundInstance {
    public BossMusicSoundInstance(
        SoundEvent event,
        SoundSource source,
        float volume,
        float pitch,
        RandomSource random,
        boolean looping,
        int delay,
        SoundInstance.Attenuation attenuation,
        double x,
        double y,
        double z,
        boolean relative
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
    }

    public static BossMusicSoundInstance forBossMusic(SoundEvent sound) {
        return new BossMusicSoundInstance(
            sound,
            SoundSource.MUSIC,
            1.0F,
            1.0F,
            SoundInstance.createUnseededRandom(),
            false,
            0,
            SoundInstance.Attenuation.NONE,
            0.0,
            0.0,
            0.0,
            true
        );
    }

    @Override
    public void tick() {

    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
