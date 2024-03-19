package com.aetherteam.aether.api.registers;

import net.minecraft.advancements.Advancement;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public record AdvancementSoundOverride(int priority, Predicate<Advancement> predicate, Supplier<SoundEvent> sound) {
    /**
     * @return Whether the {@link Advancement} matches this {@link AdvancementSoundOverride}'s predicate
     */
    public boolean matches(Advancement advancement) {
        return this.predicate.test(advancement);
    }
}
