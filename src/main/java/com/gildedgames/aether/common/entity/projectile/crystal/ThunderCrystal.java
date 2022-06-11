package com.gildedgames.aether.common.entity.projectile.crystal;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.core.capability.lightning.LightningTracker;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

/**
 * Homing projectile used by the Valkyrie Queen for ranged lightning attacks.
 */
public class ThunderCrystal extends AbstractCrystal implements IEntityAdditionalSpawnData {
    public static final EntityDataAccessor<Float> DATA_X_ROT_ID = SynchedEntityData.defineId(ThunderCrystal.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_Y_ROT_ID = SynchedEntityData.defineId(ThunderCrystal.class, EntityDataSerializers.FLOAT);
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
    protected void defineSynchedData() {
        this.entityData.define(DATA_X_ROT_ID, 0F);
        this.entityData.define(DATA_Y_ROT_ID, 0F);
    }

    @Override
    public void tickMovement() {
        if (this.ticksInAir >= this.getLifeSpan() || this.target == null || !this.target.isAlive()) {
            this.playSound(AetherSoundEvents.ENTITY_THUNDER_CRYSTAL_EXPLODE.get(), 1.0F, 1.0F);
            this.discard();
        } else {
            this.updateRotationAngles();
            this.setDeltaMovement(
                    this.getDeltaMovement().x * 0.98 - Mth.sin(this.getYRotation()) * 0.02,
                    this.getDeltaMovement().y * 0.98 + Mth.sin(this.getXRot()) * 0.02,
                    this.getDeltaMovement().z * 0.98 + Mth.cos(this.getYRotation()) * 0.02
            );
        }
        this.checkInsideBlocks();
        Vec3 motion = this.getDeltaMovement();
        this.setPos(this.getX() + motion.x, this.getY() + motion.y, this.getZ() + motion.z);
    }

    private void updateRotationAngles() {
        double x = this.target.getX() - this.getX();
        double z = this.target.getZ() - this.getZ();
        double y = this.target.getEyeY() - this.getEyeY();
        double distance = Math.sqrt(x * x + z * z);

        float targetYaw = (float) (Mth.atan2(z, x) * (180F / Mth.PI) - 90F);
        float yRot = Mth.wrapDegrees(this.getYRotation());
        this.setYRotation(Mth.approachDegrees(yRot, targetYaw, 10F));
        this.setYRot(this.getYRotation());

        float targetPitch = (float) (Mth.atan2(y, distance) * (180F / Mth.PI));
        float xRot = Mth.wrapDegrees(this.getXRotation());
        this.setXRotation(Mth.approachDegrees(xRot, targetPitch, 10F));
        this.setXRot(this.getXRotation());
    }

    /**
     * Called when the projectile hits an entity
     */
    @Override
    protected void onHitEntity(@Nonnull EntityHitResult pResult) {
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
            }
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

    private float getXRotation() {
        return this.entityData.get(DATA_X_ROT_ID);
    }

    private void setXRotation(float pitch) {
        this.entityData.set(DATA_X_ROT_ID, pitch);
    }

    private float getYRotation() {
        return this.entityData.get(DATA_Y_ROT_ID);
    }

    private void setYRotation(float pitch) {
        this.entityData.set(DATA_Y_ROT_ID, pitch);
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.target = this.level.getEntity(nbt.getInt("target"));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.target != null) {
            nbt.putInt("target", this.target.getId());
        }
    }

    /**
     * Synchronizes target data with the client.
     */
    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag nbt = new CompoundTag();
        if (this.getOwner() != null) {
            nbt.putInt("owner", this.getOwner().getId());
        }
        if (this.target != null) {
            nbt.putInt("target", this.target.getId());
        }
        buffer.writeNbt(nbt);
    }

    /**
     * Synchronizes target data with the client.
     */
    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        CompoundTag nbt = additionalData.readNbt();
        if (nbt != null) {
            this.setOwner(this.level.getEntity(nbt.getInt("owner")));
            this.target = this.level.getEntity(nbt.getInt("target"));
        }
    }
}
