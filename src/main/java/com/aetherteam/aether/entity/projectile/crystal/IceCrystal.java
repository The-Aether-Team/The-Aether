package com.aetherteam.aether.entity.projectile.crystal;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.data.resources.AetherDamageTypes;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * A crystal projectile occasionally generated by the Sun Spirit. It can be hit back at the Sun Spirit to deal damage.
 */
public class IceCrystal extends AbstractCrystal {
    public double xPower;
    public double zPower;
    public boolean attacked = false;

    public IceCrystal(EntityType<? extends IceCrystal> entityType, Level level) {
        super(entityType, level);
    }

    public IceCrystal(Level level, Entity shooter) {
        this(AetherEntityTypes.ICE_CRYSTAL.get(), level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getY(), shooter.getZ());
        float rotation = this.random.nextFloat() * 360;
        this.xPower = Mth.sin(rotation) * 0.20;
        this.zPower = -Mth.cos(rotation) * 0.20;
        this.setDeltaMovement(this.xPower, 0, this.zPower);
    }

    /**
     * @see IceCrystal#doDamage(Entity)
     */
    @Override
    protected void onHitEntity(EntityHitResult result) {
        this.doDamage(result.getEntity());
    }

    /**
     * Until the crystal is hit by a player, it will bounce off the walls. If it has already been attacked once, it will break on the next block it touches.
     * @param result The {@link BlockHitResult} of the projectile.
     */
    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (this.attacked) { // Destroy the projectile if it has hit a wall after an attack.
            this.getLevel().playSound(null, this.getX(), this.getY(), this.getZ(), this.getImpactExplosionSoundEvent(), SoundSource.HOSTILE, 2.0F, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F);
            if (!this.getLevel().isClientSide()) {
                this.discard();
            }
        } else {
            this.markHurt();
            switch (result.getDirection()) { // Bounce this projectile off of blocks.
                case NORTH, SOUTH -> this.zPower = -this.zPower;
                case WEST, EAST -> this.xPower = -this.xPower;
            }
            this.setDeltaMovement(this.xPower, 0, this.zPower);
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.projectile.AbstractHurtingProjectile#hurt(DamageSource, float)}<br><br>
     * The Ice Crystal needs to move only horizontally when attacked, so yPower isn't copied over.
     */
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.markHurt();
            Entity entity = source.getEntity();
            if (entity != null) {
                if (!this.getLevel().isClientSide()) {
                    Vec3 vec3 = entity.getLookAngle();
                    this.xPower = vec3.x() * 2.5;
                    this.zPower = vec3.z() * 2.5;
                    this.setDeltaMovement(this.xPower, 0, this.zPower);
                    this.setOwner(entity);
                    this.attacked = true;
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Weakens an entity that is hit by the projectile. The Sun Spirit can be damaged by the {@link AetherDamageTypes#ICE_CRYSTAL} damage type.
     * @param entity The hit {@link Entity}
     */
    public void doDamage(Entity entity) {
        if (this.getOwner() != entity) {
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.hurt(AetherDamageTypes.indirectEntityDamageSource(this.getLevel(), AetherDamageTypes.ICE_CRYSTAL, this, this.getOwner()), 7.0F)) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10));
                    this.level.playSound(null, this.getX(), this.getY(), this.getZ(), this.getImpactExplosionSoundEvent(), SoundSource.HOSTILE, 2.0F, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F);
                    this.discard();
                    this.getLevel().playSound(null, this.getX(), this.getY(), this.getZ(), this.getImpactExplosionSoundEvent(), SoundSource.HOSTILE, 2.0F, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F);
                    if (!this.getLevel().isClientSide()) {
                        this.discard();
                    }
                }
            }
        }
    }

    public SoundEvent getImpactExplosionSoundEvent() {
        return AetherSoundEvents.ENTITY_ICE_CRYSTAL_EXPLODE.get();
    }

    @Override
    protected ParticleOptions getExplosionParticle() {
        return AetherParticleTypes.FROZEN.get();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putDouble("XSpeed", this.xPower);
        tag.putDouble("ZSpeed", this.zPower);
        tag.putBoolean("Attacked", this.attacked);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.xPower = tag.getDouble("XSpeed");
        this.zPower = tag.getDouble("ZSpeed");
        this.attacked = tag.getBoolean("Attacked");
    }
}
