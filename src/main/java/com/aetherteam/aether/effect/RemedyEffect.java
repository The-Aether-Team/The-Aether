package com.aetherteam.aether.effect;

import com.aetherteam.aether.capability.player.AetherPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RemedyEffect extends MobEffect {
    private int effectDuration;

    public RemedyEffect() {
        super(MobEffectCategory.BENEFICIAL, 5031241);
    }

    /**
     * Tracks the starting effect duration through {@link AetherPlayer} and removes Inebriation if the entity has it.
     * @param livingEntity The affected {@link LivingEntity}.
     * @param amplifier The {@link Integer} amplifier for the effect.
     */
    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            if (player.getLevel().isClientSide()) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                    if (aetherPlayer.getRemedyStartDuration() <= 0) {
                        aetherPlayer.setRemedyStartDuration(this.effectDuration);
                    }
                });
            }
        }
        if (livingEntity.hasEffect(AetherEffects.INEBRIATION.get())) {
            livingEntity.removeEffect(AetherEffects.INEBRIATION.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        this.effectDuration = duration;
        return true;
    }
}
