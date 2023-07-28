package com.aetherteam.aether.entity.projectile.crystal;

import com.aetherteam.aether.AetherTags;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

/**
 * A crystal projectile shot by Cloud Minions. It weakens entities it hits.
 */
public class CloudCrystal extends AbstractCrystal {
    public CloudCrystal(EntityType<? extends CloudCrystal> entityType, Level level) {
        super(entityType, level);
    }

    public CloudCrystal(Level level) {
        super(AetherEntityTypes.CLOUD_CRYSTAL.get(), level);
    }

    /**
     * Weakens an entity that is hit by the projectile. The projectile is also able to damage blazes.
     * @param result The {@link EntityHitResult} of the projectile.
     */
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            float bonus = entity.getType().is(AetherTags.Entities.FIRE_MOB) ? 3.0F : 0.0F;
            if (livingEntity.hurt(AetherDamageTypes.indirectEntityDamageSource(this.getLevel(), AetherDamageTypes.CLOUD_CRYSTAL, this, this.getOwner()), 5.0F + bonus)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10));
                this.getLevel().playSound(null, this.getX(), this.getY(), this.getZ(), this.getImpactExplosionSoundEvent(), SoundSource.HOSTILE, 2.0F, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F);
                if (!this.getLevel().isClientSide()) {
                    this.discard();
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.getLevel().isClientSide()) {
            this.discard();
        }
    }

    @Override
    protected void tickMovement() {
        super.tickMovement();
        this.setDeltaMovement(this.getDeltaMovement().scale(0.99F));
    }

    @Override
    protected ParticleOptions getExplosionParticle() {
        return AetherParticleTypes.FROZEN.get();
    }

    public SoundEvent getImpactExplosionSoundEvent() {
        return AetherSoundEvents.ENTITY_CLOUD_CRYSTAL_EXPLODE.get();
    }

    /**
     * This is crystal cannot be attacked.
     */
    @Override
    public boolean isPickable() {
        return false;
    }
}
