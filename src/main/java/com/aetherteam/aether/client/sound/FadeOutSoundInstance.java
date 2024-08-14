package com.aetherteam.aether.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class FadeOutSoundInstance extends AbstractTickableSoundInstance {
    private final float startingVolume;
    private boolean fadeOut;
    private int fade;

    public FadeOutSoundInstance(
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
        boolean relative
    ) {
        super(event, source, random);
        this.volume = volume;
        this.startingVolume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.looping = looping;
        this.delay = delay;
        this.attenuation = attenuation;
        this.relative = relative;
    }

    @Override
    public void tick() {
        if (this.fadeOut) {
            this.fade++;
            this.volume = (float) Math.exp(-(this.fade / (50 / 3.0))) - (1 - this.startingVolume);
            if (this.fade >= 50) {
                this.stop();
            }
        }
    }

    public void fadeOut() {
        this.fadeOut = true;
    }
}
