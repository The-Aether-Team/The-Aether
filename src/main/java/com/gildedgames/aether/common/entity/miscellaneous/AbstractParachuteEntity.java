package com.gildedgames.aether.common.entity.miscellaneous;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraftforge.network.NetworkHooks;

public abstract class AbstractParachuteEntity extends Entity
{
    private float parachuteSpeed;

    public AbstractParachuteEntity(EntityType<?> entityType, Level world) {
        super(entityType, world);
        this.blocksBuilding = true;
        this.setDeltaMovement(Vec3.ZERO);
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
            this.fallDistance *= 0.0F;
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
            Vec3 parachuteVec = this.getDeltaMovement();
            Vec3 passengerVec = passenger.getDeltaMovement();
            if (passengerVec.x() != 0.0D || passengerVec.z() != 0.0D) {
                this.parachuteSpeed = Mth.approach(this.parachuteSpeed, 0.8F, 0.025F);
            } else {
                this.parachuteSpeed = Mth.approach(this.parachuteSpeed, 0.0F, 0.0005F);
            }
            double x = this.parachuteSpeed * (passengerVec.x() * 12.0D);
            double z = this.parachuteSpeed * (passengerVec.z() * 12.0D);
            this.setDeltaMovement(parachuteVec.add((new Vec3(x, 0.0D, z)).subtract(parachuteVec).scale(0.2D)));
            Vec3 parachuteVec2 = this.getDeltaMovement();
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
	public boolean isAttackable() {
		return false;
	}

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) { }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) { }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
