package com.aetherteam.aether.entity.projectile.crystal;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.data.resources.AetherDamageTypes;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class CloudCrystal extends AbstractCrystal {
    public CloudCrystal(EntityType<? extends CloudCrystal> entityType, Level level) {
        super(entityType, level);
    }

    public CloudCrystal(Level level) {
        super(AetherEntityTypes.CLOUD_CRYSTAL.get(), level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            float bonus = entity instanceof Blaze ? 3.0F : 0.0F;
            if (livingEntity.hurt(AetherDamageTypes.indirectEntityDamageSource(this.level, AetherDamageTypes.CLOUD_CRYSTAL, this, this.getOwner()), 5.0F + bonus)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10));
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), this.getImpactExplosionSoundEvent(), SoundSource.HOSTILE, 2.0F, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F);
                this.discard();
            }
        }
    }

    @Override
    protected ParticleOptions getExplosionParticle() {
        return AetherParticleTypes.FROZEN.get();
    }

    @Override
    protected void tickMovement() {
        super.tickMovement();
        this.setDeltaMovement(this.getDeltaMovement().scale(0.99F));
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    public SoundEvent getImpactExplosionSoundEvent() {
        return AetherSoundEvents.ENTITY_CLOUD_CRYSTAL_EXPLODE.get();
    }
}
