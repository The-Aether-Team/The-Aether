package com.gildedgames.aether.entity.projectile.crystal;

import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.AetherEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

/**
 * A damaging projectile shot by the sun spirit. It floats around the room for 15 seconds.
 */
public class FireCrystal extends AbstractCrystal {
    public double xPower;
    public double yPower;
    public double zPower;

    /**
     * Used for registering the entity. Use the other constructor to provide more context.
     */
    public FireCrystal(EntityType<? extends FireCrystal> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @param shooter - The entity that created this projectile
     */
    public FireCrystal(Level level, Entity shooter) {
        this(AetherEntityTypes.FIRE_CRYSTAL.get(), level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getY() + 1, shooter.getZ());
        this.xPower = 0.2 + this.random.nextFloat() * 0.15 * (this.random.nextInt(2) == 0 ? 1 : -1);
        this.yPower = 0.2 + this.random.nextFloat() * 0.15 * (this.random.nextInt(2) == 0 ? 1 : -1);
        this.zPower = 0.2 + this.random.nextFloat() * 0.15 * (this.random.nextInt(2) == 0 ? 1 : -1);
        this.setDeltaMovement(this.xPower, this.yPower, this.zPower);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hurt(new IndirectEntityDamageSource("incineration", this, this.getOwner()).setProjectile(), 5.0F)) {
                livingEntity.setSecondsOnFire(5);
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), this.getImpactExplosionSoundEvent(), SoundSource.HOSTILE, 2.0F, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F);
                this.spawnExplosionParticles();
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.markHurt();
        switch (result.getDirection()) {
            case NORTH, SOUTH -> this.zPower = -this.zPower;
            case UP, DOWN -> this.yPower = -this.yPower;
            case WEST, EAST -> this.xPower = -this.xPower;
        }
        this.setDeltaMovement(this.xPower, this.yPower, this.zPower);
    }

    @Override
    public void spawnExplosionParticles() {
        if (this.level instanceof ServerLevel level) {
            for (int i = 0; i < 20; i++) {
                double x = (this.random.nextFloat() - 0.5F) * 0.5;
                double y = (this.random.nextFloat() - 0.5F) * 0.5;
                double z = (this.random.nextFloat() - 0.5F) * 0.5;
                level.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 1, x, y, z, 0.0F);
            }
        }
    }

    protected SoundEvent getImpactExplosionSoundEvent() {
        return AetherSoundEvents.ENTITY_FIRE_CRYSTAL_EXPLODE.get();
    }

    /** [VANILLA COPY] - AbstractHurtingProjectile.hurt(DamageSource, float)
     * The fire crystal doesn't reset the owner when hit back. It'll be a threat until it despawns.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.markHurt();
            Entity entity = source.getEntity();
            if (entity != null) {
                if (!this.level.isClientSide) {
                    Vec3 vec3 = entity.getLookAngle();
                    this.setDeltaMovement(vec3);
                    this.xPower = vec3.x;
                    this.yPower = vec3.y;
                    this.zPower = vec3.z;
                }

                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * This is needed to make the crystal vulnerable to player attacks.
     */
    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putDouble("XSpeed", this.xPower);
        tag.putDouble("YSpeed", this.yPower);
        tag.putDouble("ZSpeed", this.zPower);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.xPower = tag.getDouble("XSpeed");
        this.yPower = tag.getDouble("YSpeed");
        this.zPower = tag.getDouble("ZSpeed");
    }
}
