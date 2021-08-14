package com.gildedgames.aether.common.entity;

import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class MountableEntity extends AetherAnimalEntity implements IJumpingMount {
	public static final DataParameter<Boolean> RIDER_SNEAKING = EntityDataManager.defineId(MountableEntity.class, DataSerializers.BOOLEAN);
	
	protected float jumpPower;
	protected boolean mountJumping;
	protected boolean playStepSound = false;
	protected boolean canJumpMidAir = false;
	
	protected MountableEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();

		this.entityData.define(RIDER_SNEAKING, false);
	}
	
	@Override
	public boolean canRiderInteract() {
		return true;
	}
	
	@Override
	protected boolean canRide(Entity entityIn) {
		return true;
	}
	
	@Override
	public boolean rideableUnderWater() {
		return true;
	}
	
	public boolean isRiderSneaking() {
		return this.entityData.get(RIDER_SNEAKING);
	}
	
	public void setRiderSneaking(boolean isRiderSneaking) {
		this.entityData.set(RIDER_SNEAKING, isRiderSneaking);
	}
	
	public boolean isMountJumping() {
		return this.mountJumping;
	}
	
	public void setMountJumping(boolean isMountJumping) {
		this.mountJumping = isMountJumping;
	}
	
	@Override
	public void tick() {
		this.riderTick();
		super.tick();
	}
	
	public void riderTick() {
		if (this.level.isClientSide) {
			return;
		}
		
		if (this.isVehicle() && this.getVehicle() != null) {
			Entity passenger = this.getPassengers().get(0);
			
			if (passenger.isShiftKeyDown()) {
				if (this.onGround) {
					passenger.setShiftKeyDown(false);
					passenger.stopRiding();
					
					return;
				}
				
				this.setRiderSneaking(true);
				passenger.setShiftKeyDown(false);
			}
			else {
				this.setRiderSneaking(false);
			}
		}
		else {
			this.setRiderSneaking(false);
		}
	}
	
	@Override
	public void travel(Vector3d positionIn) {
		Optional<PlayerEntity> optionalEntity = this.getPassengers().stream().filter(entity -> entity instanceof PlayerEntity).map(PlayerEntity.class::cast).findFirst();
		
		if (optionalEntity.isPresent()) {
			PlayerEntity player = optionalEntity.get();
			
			this.yRotO = this.yRot = player.yRot;
			this.xRotO = this.xRot = player.xRot;
			
			this.yHeadRot = player.yHeadRot;
			
			float strafe = player.xxa;
			float vertical = player.yya;
			float forward = player.zza;
			
			if (forward < 0.0F) {
				forward *= 0.25F;
			}
			
			float f;
			
			{
				double d1 = player.getX() - this.getX();
				double d2 = player.getZ() - this.getZ();
				
				f = (float)(MathHelper.atan2(d2, d1) * (180.0 / Math.PI)) - 90.0F;
			}
			
			if (player.xxa != 0.0F && player.level.isClientSide) {
				this.yRot = this.updateRotation(this.yRot, f, 40.0F);
			}
			
//			if (AetherAPI.get(player).map(IAetherPlayer::isJumping).orElse(false)) {
//				this.onMountedJump(strafe, forward);
//			}
			
			if (this.jumpPower > 0.0F && !this.isMountJumping() && (this.onGround || this.canJumpMidAir)) {
				this.setDeltaMovement(this.getDeltaMovement().x(), this.getMountJumpStrength() * this.jumpPower, this.getDeltaMovement().z());
				
				if (this.hasEffect(Effects.JUMP)) {
					this.push(0.0, 0.1 * (this.getEffect(Effects.JUMP).getAmplifier() + 1), 0.0);
				}
				
				this.setMountJumping(true);
				this.hasImpulse = true;
				this.jumpPower = 0.0F;
			}
			
			this.setDeltaMovement(this.getDeltaMovement().x() * 0.35, this.getDeltaMovement().y(), this.getDeltaMovement().z() * 0.35F);
			
			this.maxUpStep = 1.0F;

			this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
			
			if (!this.level.isClientSide) {
				this.flyingSpeed = this.getSpeed() * 0.6F;
				super.travel(new Vector3d(strafe, vertical, forward));
				this.move(MoverType.SELF, this.getDeltaMovement());
			}
			
			if (this.onGround) {
				this.jumpPower = 0.0F;
				this.setMountJumping(false);
			}
			
			this.animationSpeedOld = this.animationSpeed;
			double d0 = this.getX() - this.xo;
			double d1 = this.getZ() - this.zo;
			float f4 = 4.0F * MathHelper.sqrt(d0 * d0 + d1 * d1);
			
			if (f4 > 1.0F) {
				f4 = 1.0F;
			}
			
			this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
			this.animationPosition += this.animationSpeed;
		}
		else {
			this.maxUpStep = 0.5F;
			this.flyingSpeed = 0.02F;
			super.travel(positionIn);
		}
		
	}
	
	private float updateRotation(float angle, float targetAngle, float maxIncrease) {
		float f = MathHelper.wrapDegrees(targetAngle - angle);
		
		f = MathHelper.clamp(f, -maxIncrease, maxIncrease);
		
		return angle + f;
	}
	
	protected double getMountJumpStrength() {
		return 1.0;
	}
	
	@Override
	public void onPlayerJump(int jumpPowerIn) {
		if (jumpPowerIn < 0) {
			jumpPowerIn = 0;
		}
		
		if (jumpPowerIn >= 90) {
			this.jumpPower = 1.0F;
		}
		else {
			this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
		}
	}
	
	@Override
	public void handleStartJump(int jumpPower) {
		this.setMountJumping(true);
	}
	
	@Override
	public void handleStopJump() {}
	
}
