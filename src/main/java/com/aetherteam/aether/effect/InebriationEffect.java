package com.aetherteam.aether.effect;

import com.aetherteam.aether.data.resources.registries.AetherDamageTypes;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;

public class InebriationEffect extends MobEffect {
    private int effectDuration;
    private double rotationDirection, motionDirection;

    public InebriationEffect() {
        super(MobEffectCategory.HARMFUL, 5319035);
    }

    /**
     * Damages the affected entity with Inebriation damage every 50 ticks, and performs distraction behavior in {@link InebriationEffect#distractEntity(LivingEntity)} every tick.
     * @param livingEntity The affected {@link LivingEntity}.
     * @param amplifier The {@link Integer} amplifier for the effect.
     */
    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (this.effectDuration % 50 == 0) {
            livingEntity.hurt(AetherDamageTypes.damageSource(livingEntity.level(), AetherDamageTypes.INEBRIATION), 1.0F);
        }
        this.distractEntity(livingEntity);
    }

    /**
     * Distraction code for Inebriation, which randomly modifies the affected entity's motion and rotation, moving them around and rotating their camera.
     * It also spawns red dye particles from the player's head position.
     * @param livingEntity The affected {@link LivingEntity}.
     */
    private void distractEntity(LivingEntity livingEntity) {
        double gaussian = livingEntity.level().getRandom().nextGaussian();
        double newMotionDirection = 0.1 * gaussian;
        double newRotationDirection = (Math.PI / 4.0) * gaussian;

        this.motionDirection = 0.2 * newMotionDirection + 0.8 * this.motionDirection;
        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(this.motionDirection, 0, this.motionDirection));

        this.rotationDirection = 0.125 * newRotationDirection + (1.0 - 0.125) * this.rotationDirection;
        livingEntity.setYRot((float) (livingEntity.getYRot() + this.rotationDirection));
        livingEntity.setXRot((float) (livingEntity.getXRot() + this.rotationDirection));

        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, Items.RED_DYE.getDefaultInstance()),
                    livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * 0.8, livingEntity.getZ(),
                    1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        this.effectDuration = duration;
        return true;
    }
}
