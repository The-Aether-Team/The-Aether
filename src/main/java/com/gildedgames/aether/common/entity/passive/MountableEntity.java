package com.gildedgames.aether.common.entity.passive;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.vehicle.DismountHelper;

public abstract class MountableEntity extends AetherAnimalEntity implements ItemSteerable, Saddleable
{
	private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(MountableEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_PLAYER_JUMPED_ID = SynchedEntityData.defineId(MountableEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_MOUNT_JUMPING_ID = SynchedEntityData.defineId(MountableEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_PLAYER_CROUCHED_ID = SynchedEntityData.defineId(MountableEntity.class, EntityDataSerializers.BOOLEAN);

	protected MountableEntity(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_SADDLE_ID, false);
		this.entityData.define(DATA_PLAYER_JUMPED_ID, false);
		this.entityData.define(DATA_MOUNT_JUMPING_ID, false);
		this.entityData.define(DATA_PLAYER_CROUCHED_ID, false);
	}

	@Override
	public void tick() {
		this.riderTick();
		super.tick();
	}

	public void riderTick() {
		if (this.getControllingPassenger() instanceof Player) {
			Player playerEntity = (Player) this.getControllingPassenger();
			IAetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> {
				if (aetherPlayer.isJumping() && !this.isMountJumping()) {
					this.setPlayerJumped(true);
				}
			});
		}
	}

	@Override
	public void travel(Vec3 vector3d) {
		if (this.isAlive()) {
			if (this.isVehicle() && this.canBeControlledByRider() && this.getControllingPassenger() instanceof Player) {
				Player entity = (Player) this.getControllingPassenger();
				this.setYRot(entity.getYRot());
				this.yRotO = this.getYRot();
				this.setXRot(entity.getXRot() * 0.5F);
				this.setRot(this.getYRot(), this.getXRot());
				this.yBodyRot = this.getYRot();
				this.yHeadRot = this.yBodyRot;
				float f = entity.xxa * 0.5F;
				float f1 = entity.zza;
				if (f1 <= 0.0F) {
					f1 *= 0.25F;
				}
				if (this.getPlayerJumped() && !this.isMountJumping() && this.canJump()) {
					double jumpStrength = this.getMountJumpStrength() * (double) this.getBlockJumpFactor();
					this.setDeltaMovement(this.getDeltaMovement().x(), jumpStrength, this.getDeltaMovement().z());
					if (this.hasEffect(MobEffects.JUMP)) {
						this.push(0.0, 0.1 * (this.getEffect(MobEffects.JUMP).getAmplifier() + 1), 0.0);
					}
					this.setMountJumping(true);
					this.hasImpulse = true;
					this.setPlayerJumped(false);
					this.onJump();
				}
				this.maxUpStep = 1.0F;
				this.flyingSpeed = this.getSteeringSpeed() * 0.25F;
				if (this.isControlledByLocalInstance()) {
					float speed = this.getSteeringSpeed();
					this.setSpeed(speed);
					this.travelWithInput(new Vec3(f, vector3d.y, f1));
					this.lerpSteps = 0;
				} else {
					this.calculateEntityAnimation(this, false);
					this.setDeltaMovement(Vec3.ZERO);
				}
				if (this.onGround) {
					this.setPlayerJumped(false);
					this.setMountJumping(false);
				}
			} else {
				this.maxUpStep = 0.5F;
				this.flyingSpeed = 0.02F;
				this.travelWithInput(vector3d);
			}
		}
	}

	@Override
	public void travelWithInput(Vec3 vector3d) {
		super.travel(vector3d);
	}

	public void onJump() {
		net.minecraftforge.common.ForgeHooks.onLivingJump(this);
	}

	@Override
	public InteractionResult mobInteract(Player playerEntity, InteractionHand hand) {
		boolean flag = this.isFood(playerEntity.getItemInHand(hand));
		if (!flag && this.isSaddled() && !this.isVehicle() && !playerEntity.isSecondaryUseActive()) {
			if (!this.level.isClientSide) {
				playerEntity.startRiding(this);
			}
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		} else {
			InteractionResult actionresulttype = super.mobInteract(playerEntity, hand);
			if (!actionresulttype.consumesAction()) {
				ItemStack itemstack = playerEntity.getItemInHand(hand);
				return itemstack.getItem() == Items.SADDLE ? itemstack.interactLivingEntity(playerEntity, this, hand) : InteractionResult.PASS;
			} else {
				return actionresulttype;
			}
		}
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() != Direction.Axis.Y) {
			int[][] aint = DismountHelper.offsetsForDirection(direction);
			BlockPos blockpos = this.blockPosition();
			BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
			for (Pose pose : livingEntity.getDismountPoses()) {
				AABB axisalignedbb = livingEntity.getLocalBoundsForPose(pose);
				for (int[] aint1 : aint) {
					blockpos$mutable.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
					double d0 = this.level.getBlockFloorHeight(blockpos$mutable);
					if (DismountHelper.isBlockFloorValid(d0)) {
						Vec3 vector3d = Vec3.upFromBottomCenterOf(blockpos$mutable, d0);
						if (DismountHelper.canDismountTo(this.level, livingEntity, axisalignedbb.move(vector3d))) {
							livingEntity.setPose(pose);
							return vector3d;
						}
					}
				}
			}
		}
		return super.getDismountLocationForPassenger(livingEntity);
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		if (this.isSaddled()) {
			this.spawnAtLocation(Items.SADDLE);
		}
	}

	@Nullable
	@Override
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	@Override
	public boolean canBeControlledByRider() {
		Entity entity = this.getControllingPassenger();
		return entity instanceof Player && this.isSaddled();
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return true;
	}

	@Override
	public void equipSaddle(@Nullable SoundSource soundCategory) {
		this.setSaddled(true);
		if (soundCategory != null && this.getSaddledSound() != null) {
			this.level.playSound(null, this, this.getSaddledSound(), soundCategory, 0.5F, 1.0F);
		}
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby();
	}

	@Override
	public boolean isSaddled() {
		return this.entityData.get(DATA_SADDLE_ID);
	}

	public void setSaddled(boolean isSaddled) {
		this.entityData.set(DATA_SADDLE_ID, isSaddled);
	}

	public boolean getPlayerJumped() {
		return this.entityData.get(DATA_PLAYER_JUMPED_ID);
	}

	public void setPlayerJumped(boolean playerJumped) {
		this.entityData.set(DATA_PLAYER_JUMPED_ID, playerJumped);
	}

	public boolean isMountJumping() {
		return this.entityData.get(DATA_MOUNT_JUMPING_ID);
	}

	public void setMountJumping(boolean isMountJumping) {
		this.entityData.set(DATA_MOUNT_JUMPING_ID, isMountJumping);
	}

	public boolean canJump() {
		return this.isSaddled() && this.isOnGround();
	}

	protected double getMountJumpStrength() {
		return 1.8D;
	}

	@Override
	public float getSteeringSpeed() {
		return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.625F;
	}

	public boolean playerTriedToCrouch() {
		return this.entityData.get(DATA_PLAYER_CROUCHED_ID);
	}

	public void setPlayerTriedToCrouch(boolean playerTriedToCrouch) {
		this.entityData.set(DATA_PLAYER_CROUCHED_ID, playerTriedToCrouch);
	}

	@Override
	public boolean boost() {
		return false;
	}

	@Nullable
	protected SoundEvent getSaddledSound() {
		return null;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setSaddled(compound.getBoolean("Saddled"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Saddled", this.isSaddled());
	}
}
