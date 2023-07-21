package com.aetherteam.aether.entity.projectile.crystal;

import com.aetherteam.aether.capability.lightning.LightningTracker;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.data.resources.AetherDamageTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Homing projectile used by the Valkyrie Queen for ranged lightning attacks.
 */
public class ThunderCrystal extends AbstractCrystal {
    private Entity target;

    /**
     * Used for registering the entity. Use the other constructor to provide more context.
     */
    public ThunderCrystal(EntityType<? extends ThunderCrystal> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @param shooter - The entity that created this projectile
     * @param target - The target to home in on
     */
    public ThunderCrystal(EntityType<? extends ThunderCrystal> entityType, Level level, Entity shooter, Entity target) {
        super(entityType, level);
        this.setOwner(shooter);
        this.target = target;
        this.setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ());
    }

    @Override
    public void tickMovement() {
        if (!this.level.isClientSide) {
            if (this.target == null || !this.target.isAlive()) {
                this.discard();
                this.playSound(AetherSoundEvents.ENTITY_THUNDER_CRYSTAL_EXPLODE.get(), 1.0F, 1.0F);
                return;
            }
            if (this.ticksInAir >= this.getLifeSpan()) {
                if (this.target != null && this.target.isAlive()) {
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(this.level);
                    if (lightningBolt != null) {
                        LightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(this.getOwner()));
                        lightningBolt.setPos(this.getX(), this.getY(), this.getZ());
                        this.level.addFreshEntity(lightningBolt);
                    }
                }
                this.playSound(AetherSoundEvents.ENTITY_THUNDER_CRYSTAL_EXPLODE.get(), 1.0F, 1.0F);
            } else {
                Vec3 motion = this.getDeltaMovement().scale(0.9);
                Vec3 targetMotion = new Vec3(this.target.getX() - this.getX(), (this.target.getEyeY() - 0.1) - this.getY(), this.target.getZ() - this.getZ()).normalize();
                this.setDeltaMovement(motion.add(targetMotion.scale(0.02)));
            }
        }
        this.checkInsideBlocks();
        Vec3 motion = this.getDeltaMovement();
        this.setPos(this.getX() + motion.x, this.getY() + motion.y, this.getZ() + motion.z);
    }

    @Override
    protected ParticleOptions getExplosionParticle() {
        return AetherParticleTypes.FROZEN.get();
    }

    /**
     * Called when the projectile hits an entity
     */
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (pResult.getEntity() instanceof LivingEntity target && target != this.getOwner()) {
            target.hurt(AetherDamageTypes.indirectEntityDamageSource(this.level, AetherDamageTypes.THUNDER_CRYSTAL, this, this.getOwner()), 5.0F);
            this.knockback(0.1, this.position().subtract(target.position()));
            target.knockback(0.25, this.getX() - target.getX(), this.getZ() - target.getZ());
        }
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float pAmount) {
        if (!this.level.isClientSide && source.getSourcePosition() != null) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2, 0.2, 0.2, 0.0);
            this.knockback(0.15 + pAmount / 8, this.position().subtract(source.getSourcePosition()));
        }
        this.ticksInAir += pAmount * 10;
        return true;
    }

    public void knockback(double strength, Vec3 target) {
        this.hasImpulse = true;
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec31 = target.normalize().scale(strength);
        this.setDeltaMovement(vec3.x / 2.0 + vec31.x, vec3.y / 2 + vec31.y, vec3.z / 2.0 + vec31.z);
    }

    /**
     * This is needed to make the crystal vulnerable to player attacks.
     */
    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    /**
     * Makes the entity despawn if requirements are reached.
     */
    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.target != null) {
            tag.putInt("Target", this.target.getId());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Target")) {
            this.target = this.level.getEntity(tag.getInt("Target"));
        }
    }
}
