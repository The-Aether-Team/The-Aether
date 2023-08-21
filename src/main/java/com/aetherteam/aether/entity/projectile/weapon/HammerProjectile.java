package com.aetherteam.aether.entity.projectile.weapon;

import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class HammerProjectile extends ThrowableProjectile {
    private static final EntityDataAccessor<Boolean> DATA_JEB_ID = SynchedEntityData.defineId(HammerProjectile.class, EntityDataSerializers.BOOLEAN);

    private int ticksInAir = 0;

    public HammerProjectile(EntityType<? extends HammerProjectile> type, Level level) {
        super(type, level);
    }

    public HammerProjectile(LivingEntity owner, Level level) {
        super(AetherEntityTypes.HAMMER_PROJECTILE.get(), owner, level);
    }

    public HammerProjectile(Level level) {
        super(AetherEntityTypes.HAMMER_PROJECTILE.get(), level);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_JEB_ID, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isOnGround()) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 500) {
            if (!this.getLevel().isClientSide()) {
                this.discard();
            }
        }
        if (this.getLevel().isClientSide()) {
            this.getLevel().addParticle(ParticleTypes.CLOUD, this.getX(), this.getY() + 0.2, this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    /**
     * Sets the movement parameters for this projectile when shot.
     * @param rotationPitch The {@link Float} for the rotation pitch.
     * @param rotationYaw The {@link Float} for the rotation yaw.
     * @param velocity The {@link Float} velocity of the projectile.
     * @param inaccuracy The {@link Float} inaccuracy of the projectile.
     */
    public void shoot(float rotationPitch, float rotationYaw, float velocity, float inaccuracy) {
        float x = -Mth.sin(rotationYaw * Mth.DEG_TO_RAD) * Mth.cos(rotationPitch * Mth.DEG_TO_RAD);
        float y = -Mth.sin(rotationPitch * Mth.DEG_TO_RAD);
        float z = Mth.cos(rotationYaw * Mth.DEG_TO_RAD) * Mth.cos(rotationPitch * Mth.DEG_TO_RAD);
        super.shoot(x, y, z, velocity, inaccuracy);
    }

    /**
     * Discards the entity when it hits something.
     * @param result The {@link HitResult} of the projectile.
     */
    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.getLevel().isClientSide()) {
            this.getLevel().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    /**
     * Launches an entity on hit on the server, and spawns explosion particles on client.
     * @param result The {@link EntityHitResult} of the projectile.
     */
    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        this.launchTarget(target);
        if (this.getLevel().isClientSide()) {
            this.spawnParticles();
        }
    }

    /**
     * Launches nearby entities in a 5 block radius when hitting a block on the server, and spawns explosion particles on client.
     * @param result The {@link BlockHitResult} of the projectile.
     */
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        List<Entity> list = this.getLevel().getEntities(this, this.getBoundingBox().inflate(5.0));
        for (Entity target : list) {
            this.launchTarget(target);
        }
        if (this.getLevel().isClientSide()) {
            this.spawnParticles();
        }
    }

    private void spawnParticles() {
        for (int j = 0; j < 8; j++) {
            this.getLevel().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.getLevel().addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.getLevel().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.getLevel().addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.getLevel().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    /**
     * Hurts and pushes back an entity.
     * @param target The {@link Entity} to affect.
     */
    private void launchTarget(Entity target) {
        if (target != this.getOwner()) {
            if (this.getOwner() == null || target != this.getOwner().getVehicle()) {
                if (target instanceof LivingEntity livingEntity) {
                    livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 7);
                    livingEntity.push(this.getDeltaMovement().x(), 0.6, this.getDeltaMovement().z());
                }
            }
        }
    }

    @Override
    protected float getGravity() {
        return 0.0F;
    }

    public void setIsJeb(boolean isJeb) {
        this.getEntityData().set(DATA_JEB_ID, isJeb);
    }

    /**
     * @return Whether the texture for this projectile is of the Hammer of Jeb, as a {@link Boolean}.
     */
    public boolean getIsJeb() {
        return this.getEntityData().get(DATA_JEB_ID);
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
   
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
