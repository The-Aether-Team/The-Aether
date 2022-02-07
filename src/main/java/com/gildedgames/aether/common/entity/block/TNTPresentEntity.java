package com.gildedgames.aether.common.entity.block;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.network.NetworkHooks;

public class TNTPresentEntity extends Entity
{
    private static final EntityDataAccessor<Integer> FUSE = SynchedEntityData.defineId(TNTPresentEntity.class, EntityDataSerializers.INT);
    private int fuse = 80;

    public TNTPresentEntity(EntityType<? extends TNTPresentEntity> type, Level worldIn) {
        super(type, worldIn);
        this.blocksBuilding = true;
    }

    public TNTPresentEntity(Level worldIn, double x, double y, double z) {
        this(AetherEntityTypes.TNT_PRESENT.get(), worldIn);
        this.setPos(x, y, z);
        double d0 = worldIn.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(10);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(FUSE, 80);
    }

    @Override
    public void onSyncedDataUpdated(@Nonnull EntityDataAccessor<?> dataAccessor) {
        if (FUSE.equals(dataAccessor)) {
            this.fuse = this.getFuseDataManager();
        }
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.discard();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected Explosion explode() {
        Explosion explosion = new Explosion(this.level, this, null, null, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, false, Explosion.BlockInteraction.NONE);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.level, explosion)) {
            return explosion;
        }
        explosion.explode();
        explosion.finalizeExplosion(true);
        return explosion;
    }

    public void setFuse(int fuseIn) {
        this.entityData.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public int getFuseDataManager() {
        return this.entityData.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected float getEyeHeight(@Nonnull Pose pose, @Nonnull EntityDimensions dimensions) {
        return 0.15F;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putShort("Fuse", (short)this.getFuse());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.setFuse(compound.getShort("Fuse"));
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
