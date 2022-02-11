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

public class TntPresent extends Entity
{
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(TntPresent.class, EntityDataSerializers.INT);

    public TntPresent(EntityType<? extends TntPresent> type, Level worldIn) {
        super(type, worldIn);
        this.blocksBuilding = true;
    }

    public TntPresent(Level worldIn, double x, double y, double z) {
        this(AetherEntityTypes.TNT_PRESENT.get(), worldIn);
        this.setPos(x, y, z);
        double d0 = worldIn.random.nextDouble() * (double) ((float) Math.PI * 2.0F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02, 0.2, -Math.cos(d0) * 0.02);
        this.setFuse(10);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 80);
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
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

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    public void setFuse(int fuse) {
        this.entityData.set(DATA_FUSE_ID, fuse);
    }

    @Nonnull
    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
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
        compound.putShort("Fuse", (short) this.getFuse());
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
