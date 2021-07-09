package com.gildedgames.aether.common.entity.miscellaneous;

import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractParachuteEntity extends Entity
{
    private float parachuteSpeed;

    public AbstractParachuteEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.blocksBuilding = true;
        this.setDeltaMovement(Vector3d.ZERO);
        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();
    }

    @Override
    protected void defineSynchedData() { }

    @Override
    public void tick() {
        super.tick();
        boolean hasControllingPassenger = this.getControllingPassenger() != null;
        if (hasControllingPassenger) {
            Entity passenger = this.getControllingPassenger();
            this.fallDistance = 0.0F;
            this.moveParachute(passenger);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.spawnExplosionParticle();
            if (this.isOnGround() || this.isInWater() || this.isInLava()) {
                this.ejectPassengers();
                this.die();
            }
        } else {
            this.die();
        }
    }

    private void moveParachute(Entity passenger) {
        if (this.isVehicle()) {
            Vector3d parachuteVec = this.getDeltaMovement();
            Vector3d passengerVec = passenger.getDeltaMovement();
            if (passengerVec.x() != 0.0D || passengerVec.z() != 0.0D) {
                this.parachuteSpeed = MathHelper.approach(this.parachuteSpeed, 0.6F, 0.025F);
            } else {
                this.parachuteSpeed = MathHelper.approach(this.parachuteSpeed, 0.0F, 0.0005F);
            }
            double x = this.parachuteSpeed * (passengerVec.x() * 10.0D);
            double z = this.parachuteSpeed * (passengerVec.z() * 10.0D);
            this.setDeltaMovement(parachuteVec.add((new Vector3d(x, 0.0D, z)).subtract(parachuteVec).scale(0.2D)));
            Vector3d parachuteVec2 = this.getDeltaMovement();
            this.setDeltaMovement(parachuteVec2.x(), -0.15, parachuteVec2.z());
        }
    }

    public void spawnExplosionParticle() {
        for (int i = 0; i < 1; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            double d4 = this.getX() + ((double) this.random.nextFloat() * this.getBbWidth() * 2.0D) - this.getBbWidth() - d0 * d3;
            double d5 = this.getY() + ((double) this.random.nextFloat() * this.getBbHeight()) - d1 * d3;
            double d6 = this.getZ() + ((double) this.random.nextFloat() * this.getBbWidth() * 2.0D) - this.getBbWidth() - d2 * d3;
            this.level.addParticle(ParticleTypes.POOF, d4, d5, d6, d0, d1, d2);
        }
    }

    public void die() {
        if (!this.level.isClientSide) {
            this.kill();
        }
        this.spawnExplosionParticle();
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return true;
    }

    @Override
    public boolean canRiderInteract() {
        return false;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 1.35D;
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) { }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) { }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
