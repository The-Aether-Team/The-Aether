package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.entity.MountableMob;
import com.aetherteam.aether.entity.NotGrounded;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MountableAnimal extends AetherAnimal implements MountableMob, Saddleable, NotGrounded {
	private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_PLAYER_JUMPED_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_MOUNT_JUMPING_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_PLAYER_CROUCHED_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);

	protected MountableAnimal(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_SADDLE_ID, false);
		this.entityData.define(DATA_PLAYER_JUMPED_ID, false);
		this.entityData.define(DATA_MOUNT_JUMPING_ID, false);
		this.entityData.define(DATA_PLAYER_CROUCHED_ID, false);
		this.entityData.define(DATA_ENTITY_ON_GROUND_ID, true);
	}

	@Override
	public void tick() {
		this.tick(this);
		this.riderTick();
		super.tick();
		if (this.isOnGround()) {
			this.setEntityOnGround(true);
		}
		if (this.getPlayerJumped()) {
			this.setEntityOnGround(false);
		}
	}

	public void riderTick() {
		this.riderTick(this);
	}

	@Override
	public void travel(@Nonnull Vec3 vector3d) {
		this.travel(this, vector3d);
	}

	@Override
	public void travelWithInput(Vec3 travelVec) {
		super.travel(travelVec);
	}

	@Override
	protected void jumpFromGround() {
		super.jumpFromGround();
		this.setEntityOnGround(false);
	}

	@Nonnull
	@Override
	public InteractionResult mobInteract(Player playerEntity, @Nonnull InteractionHand hand) {
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
				return itemstack.is(Items.SADDLE) ? itemstack.interactLivingEntity(playerEntity, this, hand) : InteractionResult.PASS;
			} else {
				return actionresulttype;
			}
		}
	}

	@Nonnull
	@Override
	public Vec3 getDismountLocationForPassenger(@Nonnull LivingEntity livingEntity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() != Direction.Axis.Y) {
			int[][] aint = DismountHelper.offsetsForDirection(direction);
			BlockPos blockPos = this.blockPosition();
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			for (Pose pose : livingEntity.getDismountPoses()) {
				AABB axisalignedbb = livingEntity.getLocalBoundsForPose(pose);
				for (int[] aint1 : aint) {
					mutableBlockPos.set(blockPos.getX() + aint1[0], blockPos.getY(), blockPos.getZ() + aint1[1]);
					double d0 = this.level.getBlockFloorHeight(mutableBlockPos);
					if (DismountHelper.isBlockFloorValid(d0)) {
						Vec3 vector3d = Vec3.upFromBottomCenterOf(mutableBlockPos, d0);
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
	public LivingEntity getControllingPassenger() {
		if (this.getFirstPassenger() instanceof LivingEntity livingEntity && this.isSaddled()) {
			return livingEntity;
		}
		return null;
	}

	@Override
	protected boolean canRide(@Nonnull Entity entityIn) {
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

	@Override
	public boolean canJump() {
		return this.isSaddled() && this.isOnGround();
	}

	@Override
	public double getMountJumpStrength() {
		return 1.8;
	}

	@Override
	public float getSteeringSpeed() {
		return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.625F;
	}

	@Override
	public float getFlyingSpeed() {
		return this.getControllingPassenger() != null ? this.getSteeringSpeed() * 0.25F : 0.02F;
	}

	public boolean playerTriedToCrouch() {
		return this.entityData.get(DATA_PLAYER_CROUCHED_ID);
	}

	public void setPlayerTriedToCrouch(boolean playerTriedToCrouch) {
		this.entityData.set(DATA_PLAYER_CROUCHED_ID, playerTriedToCrouch);
	}

	@Override
	public boolean isEntityOnGround() {
		return this.entityData.get(DATA_ENTITY_ON_GROUND_ID);
	}

	@Override
	public void setEntityOnGround(boolean onGround) {
		this.entityData.set(DATA_ENTITY_ON_GROUND_ID, onGround);
	}

	@Override
	public double jumpFactor() {
		return this.getBlockJumpFactor();
	}

	@Nullable
	protected SoundEvent getSaddledSound() {
		return null;
	}

	@Override
	public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Saddled", this.isSaddled());
	}

	@Override
	public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("Saddled")) {
			this.setSaddled(tag.getBoolean("Saddled"));
		}
	}
}