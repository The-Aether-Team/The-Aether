package com.gildedgames.aether.entity.miscellaneous;

import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.ExplosionParticlePacket;
import com.gildedgames.aether.util.EntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraftforge.network.NetworkHooks;

public class Parachute extends Entity {
    private float parachuteSpeed;

    public Parachute(EntityType<? extends Parachute> type, Level level) {
        super(type, level);
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
            this.resetFallDistance();
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
            if (passengerVec.x() != 0.0 || passengerVec.z() != 0.0) {
                this.parachuteSpeed = Mth.approach(this.parachuteSpeed, 0.8F, 0.025F);
            } else {
                this.parachuteSpeed = Mth.approach(this.parachuteSpeed, 0.0F, 0.0005F);
            }
            double x = this.parachuteSpeed * (passengerVec.x() * 12.0);
            double z = this.parachuteSpeed * (passengerVec.z() * 12.0);
            this.setDeltaMovement(parachuteVec.add((new Vec3(x, 0.0D, z)).subtract(parachuteVec).scale(0.2)));
            Vec3 parachuteVec2 = this.getDeltaMovement();

            if (passenger instanceof LivingEntity livingEntity) {
                AttributeInstance gravity = livingEntity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
                if (gravity != null) {
                    double fallSpeed = Math.max(gravity.getValue() * -1.875, -0.075);
                    this.setDeltaMovement(parachuteVec2.x(), fallSpeed, parachuteVec2.z());
                }
            }
            if (passenger instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.aboveGroundTickCount = 0;
                serverPlayer.connection.aboveGroundVehicleTickCount = 0;
            }
        }
    }

    public void spawnExplosionParticle() {
        if (!this.level.isClientSide) {
            AetherPacketHandler.sendToAll(new ExplosionParticlePacket(this.getId()));
        } else {
            EntityUtil.spawnMovementExplosionParticles(this);
        }
    }

    public void die() {
        for (int i = 0; i < 10; i++) {
            this.spawnExplosionParticle();
        }
        if (!this.level.isClientSide) {
            this.kill();
        }
    }

    @Override
    protected boolean canRide(@Nonnull Entity entity) {
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
        return 1.35;
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
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundTag tag) { }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundTag tag) { }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
