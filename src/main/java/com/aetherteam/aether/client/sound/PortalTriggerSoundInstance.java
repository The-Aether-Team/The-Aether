package com.aetherteam.aether.client.sound;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.aether.capability.player.AetherPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.LazyOptional;

public class PortalTriggerSoundInstance extends AbstractTickableSoundInstance {
    private final Player player;
    private final float startingVolume;
    private int fade;

    public PortalTriggerSoundInstance(
        Player player,
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
        this.player = player;
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

    public static PortalTriggerSoundInstance forLocalAmbience(Player player, SoundEvent pSound, float pVolume, float pPitch) {
        return new PortalTriggerSoundInstance(
            player,
            pSound,
            SoundSource.AMBIENT,
            pPitch,
            pVolume,
            SoundInstance.createUnseededRandom(),
            false,
            0,
            Attenuation.NONE,
            0.0,
            0.0,
            0.0,
            true
        );
    }

    @Override
    public void tick() {
        LazyOptional<AetherPlayer> aetherPlayer = player.getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY);
        aetherPlayer.ifPresent((cap) -> {
            if (!cap.isInPortal()) {
                this.fade++;
                this.volume = (float) Math.exp(-(this.fade / (75 / 1.5))) - (1 - this.startingVolume);
                if (this.fade >= 75) {
                    this.stop();
                }
            }
        });
    }
}
