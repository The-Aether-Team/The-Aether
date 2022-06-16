package com.gildedgames.aether.common.entity.projectile.crystal;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.core.capability.lightning.LightningTracker;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

/**
 * Homing projectile used by the Valkyrie Queen for ranged lightning attacks.
 */
public class ThunderCrystal extends AbstractCrystal {
    private Entity target;
    private int health = 20;

    /**
     * Used for registering the entity. Use the other constructor to provide more context.
     */
    public ThunderCrystal(EntityType<? extends ThunderCrystal> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @param target - The target to home in on.
     */
    public ThunderCrystal(EntityType<? extends ThunderCrystal> entityType, Level level, Entity shooter, Entity target, double x, double y, double z) {
        super(entityType, level);
        this.setOwner(shooter);
        this.target = target;
        this.setPos(x, y, z);
    }

    @Override
    public void tickMovement() {
        if (!this.level.isClientSide) {
            if (this.ticksInAir >= this.getLifeSpan() || this.target == null || !this.target.isAlive()) {
                this.playSound(AetherSoundEvents.ENTITY_THUNDER_CRYSTAL_EXPLODE.get(), 1.0F, 1.0F);
                this.discard();
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
    protected void onHitEntity(@Nonnull EntityHitResult pResult) {
        this.placeLightning();
        this.discard();
    }

    @Override
    protected void onHitBlock(@Nonnull BlockHitResult pResult) {
        this.placeLightning();
        this.discard();
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
    }


    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(@Nonnull DamageSource pSource, float pAmount) {
        if (!this.level.isClientSide) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
            this.health -= pAmount;
            if (this.health <= 0) {
                this.playSound(AetherSoundEvents.ENTITY_THUNDER_CRYSTAL_EXPLODE.get(), 1.0F, 1.0F);
                this.discard();
            } else {
                Entity attacker = pSource.getEntity();
                if (attacker != null) {
                    double x = attacker.getX() - this.getX();
                    double z = attacker.getZ() - this.getZ();
                    this.knockback(x, z);
                }
            }
        }
        return true;
    }

    /**
     * @see net.minecraft.world.entity.LivingEntity#knockback(double, double, double)
     * Allows the player to push back a thunder ball.
     */
    private void knockback(double xAngle, double zAngle) {
        this.hasImpulse = true;
        Vec3 motion = this.getDeltaMovement();
        Vec3 pushback = new Vec3(xAngle, 0.0D, zAngle).normalize().scale(0.4);
        this.setDeltaMovement(motion.x / 2.0D - pushback.x, Math.min(motion.y, 0.1), motion.z / 2.0D - pushback.z);
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

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.health = nbt.getInt("Health");
        this.target = this.level.getEntity(nbt.getInt("Target"));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Health", this.health);
        if (this.target != null) {
            nbt.putInt("Target", this.target.getId());
        }
    }
}
