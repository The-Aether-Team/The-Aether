package com.aetherteam.aether.api.registers;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

public record AdvancementSoundOverride(Predicate<AdvancementHolder> predicate, Supplier<SoundEvent> sound) {
    /**
     * @return Whether the {@link AdvancementHolder} matches this {@link AdvancementSoundOverride}'s predicate
     */
    public boolean matches(AdvancementHolder advancement) {
        return this.predicate.test(advancement);
    }
}