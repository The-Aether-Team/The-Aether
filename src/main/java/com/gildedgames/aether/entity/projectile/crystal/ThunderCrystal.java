package com.gildedgames.aether.entity.projectile.crystal;

import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.capability.lightning.LightningTracker;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

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
            if (this.ticksInAir >= this.getLifeSpan() || this.target == null || !this.target.isAlive()) {
                this.placeLightning();
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

    /**
     * Called when the projectile hits an entity
     */
    @Override
    protected void onHit(@Nonnull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            if (pResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() == this.getOwner())
                return;
            this.placeLightning();
        }
    }

    /**
     * This method summons lightning for the impact.
     */
    private void placeLightning() {
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(this.level);
        if (lightningBolt != null) {
            LightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(this.getOwner()));
            lightningBolt.moveTo(this.getX(), this.getY(), this.getZ());
            this.level.addFreshEntity(lightningBolt);
        }
        this.discard();
    }


    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(@Nonnull DamageSource pSource, float pAmount) {
        if (!this.level.isClientSide) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
            this.playSound(AetherSoundEvents.ENTITY_THUNDER_CRYSTAL_EXPLODE.get(), 1.0F, 1.0F);
            this.discard();
        }
        return true;
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
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.target != null) {
            tag.putInt("Target", this.target.getId());
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Target")) {
            this.target = this.level.getEntity(tag.getInt("Target"));
        }
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
