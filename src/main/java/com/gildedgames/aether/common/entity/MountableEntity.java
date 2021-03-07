package com.gildedgames.aether.common.entity;

import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.MoverType;
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
	public static final DataParameter<Boolean> RIDER_SNEAKING = EntityDataManager.createKey(MountableEntity.class, DataSerializers.BOOLEAN);
	
	protected float jumpPower;
	protected boolean mountJumping;
	protected boolean playStepSound = false;
	protected boolean canJumpMidAir = false;
	
	protected MountableEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerData() {
		super.registerData();

		this.dataManager.register(RIDER_SNEAKING, false);
	}
	
	@Override
	public boolean canRiderInteract() {
		return true;
	}
	
	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return true;
	}
	
	@Override
	public boolean canBeRiddenInWater() {
		return true;
	}
	
	public boolean isRiderSneaking() {
		return this.dataManager.get(RIDER_SNEAKING);
	}
	
	public void setRiderSneaking(boolean isRiderSneaking) {
		this.dataManager.set(RIDER_SNEAKING, isRiderSneaking);
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
		if (this.world.isRemote) {
			return;
		}
		
		if (this.isBeingRidden() && this.getRidingEntity() != null) {
			Entity passenger = this.getPassengers().get(0);
			
			if (passenger.isSneaking()) {
				if (this.onGround) {
					passenger.setSneaking(false);
					passenger.stopRiding();
					
					return;
				}
				
				this.setRiderSneaking(true);
				passenger.setSneaking(false);
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
			
			this.prevRotationYaw = this.rotationYaw = player.rotationYaw;
			this.prevRotationPitch = this.rotationPitch = player.rotationPitch;
			
			this.rotationYawHead = player.rotationYawHead;
			
			float strafe = player.moveStrafing;
			float vertical = player.moveVertical;
			float forward = player.moveForward;
			
			if (forward < 0.0F) {
				forward *= 0.25F;
			}
			
			float f;
			
			{
				double d1 = player.getPosX() - this.getPosX();
				double d2 = player.getPosZ() - this.getPosZ();
				
				f = (float)(MathHelper.atan2(d2, d1) * (180.0 / Math.PI)) - 90.0F;
			}
			
			if (player.moveStrafing != 0.0F && player.world.isRemote) {
				this.rotationYaw = this.updateRotation(this.rotationYaw, f, 40.0F);
			}
			
//			if (AetherAPI.get(player).map(IAetherPlayer::isJumping).orElse(false)) {
//				this.onMountedJump(strafe, forward);
//			}
			
			if (this.jumpPower > 0.0F && !this.isMountJumping() && (this.onGround || this.canJumpMidAir)) {
				this.setMotion(this.getMotion().getX(), this.getMountJumpStrength() * this.jumpPower, this.getMotion().getZ());
				
				if (this.isPotionActive(Effects.JUMP_BOOST)) {
					this.addVelocity(0.0, 0.1 * (this.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1), 0.0);
				}
				
				this.setMountJumping(true);
				this.isAirBorne = true;
				this.jumpPower = 0.0F;
				
				if (!this.world.isRemote) {
					this.move(MoverType.SELF, this.getMotion());
				}
			}
			
			this.setMotion(this.getMotion().getX() * 0.35, this.getMotion().getY(), this.getMotion().getZ() * 0.35F);
			
			this.stepHeight = 1.0F;
			
			if (!this.world.isRemote) {
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.6F;
				super.travel(new Vector3d(strafe, vertical, forward));
			}
			
			if (this.onGround) {
				this.jumpPower = 0.0F;
				this.setMountJumping(false);
			}
			
			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d0 = this.getPosX() - this.prevPosX;
			double d1 = this.getPosZ() - this.prevPosZ;
			float f4 = 4.0F * MathHelper.sqrt(d0 * d0 + d1 * d1);
			
			if (f4 > 1.0F) {
				f4 = 1.0F;
			}
			
			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else {
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
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
	public void setJumpPower(int jumpPowerIn) {
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
