package com.aetherteam.aether.entity.miscellaneous;

import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Parachute extends Entity {
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
        LivingEntity passenger = this.getControllingPassenger();
        if (passenger != null) {
            this.checkSlowFallDistance();
            this.moveParachute(passenger);
            this.spawnExplosionParticle();
            if (this.isOnGround() || this.isInFluidType()) {
                this.ejectPassengers();
                this.die();
            }
        } else {
            this.die();
        }
    }

    public void moveParachute(LivingEntity passenger) {
        if (this.isVehicle()) {
            this.setYRot(passenger.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(passenger.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            float f = passenger.xxa * 0.5F;
            float f1 = passenger.zza;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
            }
            Vec3 travelVec = new Vec3(f, passenger.yya, f1);
            AttributeInstance gravity = passenger.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            double d0 = gravity != null ? gravity.getValue() : 0.08;

            Vec3 movement = this.calculateMovement(travelVec);
            double d2 = movement.y;
            if (!this.isNoGravity()) {
                d2 -= d0;
            }
            d2 *= 0.98;

            double fallSpeed = Math.max(d0 * -3.125, -0.25);
            this.setDeltaMovement(movement.x * (double) 0.91F, Math.max(d2, fallSpeed), movement.z * (double) 0.91F);

            if (passenger instanceof ServerPlayer serverPlayer) {
                ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
                serverGamePacketListenerImplAccessor.aether$setAboveGroundVehicleTickCount(0);
            }
        }
    }

    public Vec3 calculateMovement(Vec3 vec3) {
        float speed = 0.03F;
        this.moveRelative(speed, vec3);
        this.move(MoverType.SELF, this.getDeltaMovement());
        return this.getDeltaMovement();
    }

    public void spawnExplosionParticle() {
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte) 70);
        }
    }

    public void die() {
        this.spawnExplosionParticle();
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
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return this.position().add(0.0, 0.5, 0.0);
        } else {
            Vec3 dismountLocation = this.position().add(0.0, 0.5, 0.0);
            if (!DismountHelper.canDismountTo(this.level, passenger, passenger.getType().getDimensions().makeBoundingBox(dismountLocation))) {
                return this.position().add(0.0, 1.0, 0.0).add(new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(0.5).reverse());
            }
            return dismountLocation;
        }
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof LivingEntity rider) {
            return rider;
        }
        return null;
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
    public void handleEntityEvent(byte id) {
        if (id == 70) {
            EntityUtil.spawnMovementExplosionParticles(this);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundTag tag) { }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundTag tag) { }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
