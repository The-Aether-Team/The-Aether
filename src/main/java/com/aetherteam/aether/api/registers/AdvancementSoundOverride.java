package com.aetherteam.aether.api.registers;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class AdvancementSoundOverride {
    private final Predicate<String> predicate;
    private final Supplier<SoundEvent> sound;

    public AdvancementSoundOverride(Predicate<String> predicate, Supplier<SoundEvent> sound) {
        this.predicate = predicate;
        this.sound = sound;
    }

    /**
     * @return Whether or not the {@link String} matches this {@link AdvancementSoundOverride}'s predicate
     */
    public boolean matches(String string) {
        return this.predicate.test(string);
    }

    /**
     * @return The {@link SoundEvent} to play when the predicate is true
     */
    public SoundEvent getSound() {
        return this.sound.get();
    }
}
