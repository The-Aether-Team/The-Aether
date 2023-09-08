package com.aetherteam.aether.entity.miscellaneous;

import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkHooks;

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

    /**
     * Necessary to define, even if empty.
     */
    @Override
    protected void defineSynchedData() { }

    @Override
    public void tick() {
        super.tick();
        LivingEntity passenger = this.getControllingPassenger();
        if (passenger != null) {
            this.checkSlowFallDistance(); // Resets the Parachute's fall distance.
            this.moveParachute(passenger);
            this.spawnExplosionParticle();
            if (this.isOnGround() || this.isInFluidType()) { // The parachute breaks when it collides with something.
                this.ejectPassengers();
                this.die();
            }
        } else {
            this.die();
        }
    }

    /**
     * Handles parachute and passenger movement.
     * @param passenger The {@link LivingEntity} passenger.
     */
    public void moveParachute(LivingEntity passenger) {
        if (this.isVehicle()) {
            this.setYRot(passenger.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(passenger.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            float x = passenger.xxa * 0.5F; // Side-to-side movement is slowed.
            float z = passenger.zza; // Forward movement is normal.
            if (z <= 0.0F) {
                z *= 0.25F; // Backwards movement is slowed.
            }
            Vec3 travelVec = new Vec3(x, passenger.yya, z);
            AttributeInstance gravity = passenger.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            double gravityModifier = gravity != null ? gravity.getValue() : 0.08;

            Vec3 movement = this.calculateMovement(travelVec);
            double y = movement.y();
            if (!this.isNoGravity()) {
                y -= gravityModifier;
            }
            y *= 0.98;

            double fallSpeed = Math.max(gravityModifier * -3.125, -0.25); // Slows fall speed and slows the parachute from falling too slow and getting stuck midair.
            this.setDeltaMovement(movement.x() * (double) 0.91F, Math.max(y, fallSpeed), movement.z() * (double) 0.91F);

            if (passenger instanceof ServerPlayer serverPlayer) { // Prevents the player from being kicked for flying.
                ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
                serverGamePacketListenerImplAccessor.aether$setAboveGroundVehicleTickCount(0);
            }
        }
    }

    /**
     * Calculates the movement vector for the parachute from the passenger.
     * @param vec3 The passenger {@link Vec3}.
     * @return The modified {@link Vec3}.
     */
    public Vec3 calculateMovement(Vec3 vec3) {
        float speed = 0.03F;
        this.moveRelative(speed, vec3);
        this.move(MoverType.SELF, this.getDeltaMovement());
        return this.getDeltaMovement();
    }

    /**
     * Kills the parachute along with spawning particles.
     */
    public void die() {
        this.spawnExplosionParticle();
        if (!this.getLevel().isClientSide()) {
            this.kill();
        }
    }

    /**
     * Spawn explosion particles in {@link Parachute#handleEntityEvent(byte)}.
     */
    public void spawnExplosionParticle() {
        if (!this.getLevel().isClientSide()) {
            this.getLevel().broadcastEntityEvent(this, (byte) 70);
        }
    }

    @Override
    protected boolean canRide(Entity entity) {
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

    /**
     * Handles where the passenger will dismount to.
     * @param passenger The {@link LivingEntity} passenger.
     * @return The {@link Vec3} position to dismount to.
     */
    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return this.position().add(0.0, 0.5, 0.0);
        } else {
            Vec3 dismountLocation = this.position().add(0.0, 0.5, 0.0);
            // Fixes a block clipping exploit by pushing the player away from a block if it tries to dismount to an unsafe spot (like inside a block).
            if (!DismountHelper.canDismountTo(this.getLevel(), passenger, passenger.getType().getDimensions().makeBoundingBox(dismountLocation))) {
                return this.position().add(0.0, 1.0, 0.0).add(new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()).scale(0.5).reverse());
            }
            return dismountLocation;
        }
    }

    @Nullable
    @Override
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
    protected void addAdditionalSaveData(CompoundTag tag) { }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) { }
   
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
