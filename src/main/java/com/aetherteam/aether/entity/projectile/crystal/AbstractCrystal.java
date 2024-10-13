package com.aetherteam.aether.entity.projectile.crystal;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;

public abstract class AbstractCrystal extends Projectile {
    protected int ticksInAir = 0;

    protected AbstractCrystal(EntityType<? extends AbstractCrystal> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    /**
     * Necessary to define, even if empty.
     */
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) { }

    /**
     * [CODE COPY] - {@link ThrowableProjectile#tick()}.<br><br>
     * Remove code for slowing down the projectile in water.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.onGround()) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > this.getLifeSpan()) {
            if (!this.level().isClientSide()) {
                this.discard();
            }
        }
        HitResult result = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        boolean flag = false;
        if (result.getType() != HitResult.Type.MISS && !flag && !EventHooks.onProjectileImpact(this, result)) {
            this.onHit(result);
        }
        this.checkInsideBlocks();
        this.tickMovement();
    }

    /**
     * Spawns explosion particles when this projectile is removed from the world.
     *
     * @param reason The {@link net.minecraft.world.entity.Entity.RemovalReason}.
     */
    @Override
    public void remove(RemovalReason reason) {
        this.spawnExplosionParticles();
        super.remove(reason);
    }

    /**
     * Handles the crystal's movement. Override this if you need different movement code.
     */
    protected void tickMovement() {
        Vec3 vector3d = this.getDeltaMovement();
        double d2 = this.getX() + vector3d.x();
        double d0 = this.getY() + vector3d.y();
        double d1 = this.getZ() + vector3d.z();
        this.updateRotation();
        this.setPos(d2, d0, d1);
    }

    /**
     * Creates the crystal's explosion particles.
     */
    public void spawnExplosionParticles() {
        if (this.level() instanceof ServerLevel level) {
            for (int i = 0; i < 20; i++) {
                double x = (this.random.nextFloat() - 0.5F) * 0.5;
                double y = (this.random.nextFloat() - 0.5F) * 0.5;
                double z = (this.random.nextFloat() - 0.5F) * 0.5;
                level.sendParticles(this.getExplosionParticle(), this.getX(), this.getY(), this.getZ(), 1, x, y, z, 0.0F);
            }
        }
    }

    /**
     * @return {@link ParticleOptions} for what explosion particles to spawn. Used by subclasses.
     */
    protected abstract ParticleOptions getExplosionParticle();

    @Nullable
    protected SoundEvent getImpactExplosionSoundEvent() {
        return null;
    }

    /**
     * @return The lifespan of the crystal, as an {@link Integer}.
     */
    public int getLifeSpan() {
        return 300;
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

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TicksInAir", this.ticksInAir);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("TicksInAir")) {
            this.ticksInAir = tag.getInt("TicksInAir");
        }
    }
}
