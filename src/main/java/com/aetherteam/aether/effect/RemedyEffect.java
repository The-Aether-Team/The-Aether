package com.aetherteam.aether.effect;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
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
     * Tracks the starting effect duration through {@link AetherPlayerAttachment} and removes Inebriation if the entity has it.
     *
     * @param livingEntity The affected {@link LivingEntity}.
     * @param amplifier    The {@link Integer} amplifier for the effect.
     */
    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            if (player.level().isClientSide()) {
                var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                if (data.getRemedyStartDuration() <= 0) {
                    data.setSynched(player.getId(), INBTSynchable.Direction.SERVER, "setRemedyStartDuration", this.effectDuration);
                }
            }
        }
        if (livingEntity.hasEffect(AetherEffects.INEBRIATION.get())) {
            livingEntity.removeEffect(AetherEffects.INEBRIATION.get());
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        this.effectDuration = duration;
        return true;
    }
}
