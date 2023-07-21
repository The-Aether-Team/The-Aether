package com.aetherteam.aether.entity.projectile.weapon;

import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
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
        this.entityData.define(DATA_JEB_ID, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 500) {
            this.discard();
        }
        if (this.level.isClientSide) {
            this.level.addParticle(ParticleTypes.CLOUD, this.getX(), this.getY() + 0.2, this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    public void shoot(float rotationPitch, float rotationYaw, float v, float velocity, float inaccuracy) {
        float x = -Mth.sin(rotationYaw * ((float) Math.PI / 180.0F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180.0F));
        float y = -Mth.sin((rotationPitch + v) * ((float) Math.PI / 180.0F));
        float z = Mth.cos(rotationYaw * ((float) Math.PI / 180.0F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180.0F));
        super.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level.isClientSide) {
            Entity target = result.getEntity();
            launchTarget(target);
        } else  {
            this.spawnParticles();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level.isClientSide) {
            List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate(5.0));
            for (Entity target : list) {
                this.launchTarget(target);
            }
        } else  {
            this.spawnParticles();
        }
    }

    private void spawnParticles() {
        for (int j = 0; j < 8; j++) {
            this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    private void launchTarget(Entity target) {
        if (target != this.getOwner()) {
            if (this.getOwner() == null || target != this.getOwner().getVehicle()) {
                if (target instanceof LivingEntity livingEntity) {
                    livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 5);
                    livingEntity.push(this.getDeltaMovement().x, 0.6, this.getDeltaMovement().z);
                }
            }
        }
    }

    @Override
    protected float getGravity() {
        return 0.0F;
    }

    public void setIsJeb(boolean isJeb) {
        this.entityData.set(DATA_JEB_ID, isJeb);
    }

    public boolean getIsJeb() {
        return this.entityData.get(DATA_JEB_ID);
    }

   
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
