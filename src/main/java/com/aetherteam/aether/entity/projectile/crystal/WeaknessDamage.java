package com.aetherteam.aether.entity.projectile.crystal;

import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public interface WeaknessDamage {
    default void damageWithWeakness(AbstractCrystal crystal, LivingEntity livingEntity, RandomSource random) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10));
        if (crystal.getImpactExplosionSoundEvent() != null) {
            crystal.getLevel().playSound(null, crystal.getX(), crystal.getY(), crystal.getZ(), crystal.getImpactExplosionSoundEvent(), SoundSource.HOSTILE, 2.0F, random.nextFloat() - random.nextFloat() * 0.2F + 1.2F);
        }
        if (!crystal.getLevel().isClientSide()) {
            crystal.discard();
        }
    }
}
