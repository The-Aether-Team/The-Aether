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

import javax.annotation.Nullable;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.animal.Pig}.<br><br>
 * Method copies with changes to make methods more abstracted through {@link MountableMob}.
 */
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
		this.getEntityData().define(DATA_SADDLE_ID, false);
		this.getEntityData().define(DATA_PLAYER_JUMPED_ID, false);
		this.getEntityData().define(DATA_MOUNT_JUMPING_ID, false);
		this.getEntityData().define(DATA_PLAYER_CROUCHED_ID, false);
		this.getEntityData().define(DATA_ENTITY_ON_GROUND_ID, true);
	}

	/**
	 * Handles various tick methods and also sets when this entity is on the ground or not with more control than vanilla methods.
	 * @see MountableMob#tick(Mob)
	 */
	@Override
	public void tick() {
		this.tick(this);
		this.riderTick();
		super.tick();
		if (this.onGround()) {
			this.setEntityOnGround(true);
		}
		if (this.getPlayerJumped()) {
			this.setEntityOnGround(false);
		}
	}

	/**
	 * @see MountableMob#riderTick(Mob)
	 */
	public void riderTick() {
		this.riderTick(this);
	}

	/**
	 * @see MountableMob#travel(Mob, Vec3)
	 */
	@Override
	public void travel(Vec3 vector3d) {
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

	@Override
	public InteractionResult mobInteract(Player playerEntity, InteractionHand hand) {
		boolean flag = this.isFood(playerEntity.getItemInHand(hand));
		if (!flag && this.isSaddled() && !this.isVehicle() && !playerEntity.isSecondaryUseActive()) {
			if (!this.level().isClientSide()) {
				playerEntity.startRiding(this);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide());
		} else {
			InteractionResult interactionResult = super.mobInteract(playerEntity, hand);
			if (!interactionResult.consumesAction()) {
				ItemStack itemstack = playerEntity.getItemInHand(hand);
				return itemstack.is(Items.SADDLE) ? itemstack.interactLivingEntity(playerEntity, this, hand) : InteractionResult.PASS;
			} else {
				return interactionResult;
			}
		}
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() != Direction.Axis.Y) {
			int[][] offsets = DismountHelper.offsetsForDirection(direction);
			BlockPos blockPos = this.blockPosition();
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
			for (Pose pose : livingEntity.getDismountPoses()) {
				AABB bounds = livingEntity.getLocalBoundsForPose(pose);
				for (int[] offset : offsets) {
					mutableBlockPos.set(blockPos.getX() + offset[0], blockPos.getY(), blockPos.getZ() + offset[1]);
					double d0 = this.level().getBlockFloorHeight(mutableBlockPos);
					if (DismountHelper.isBlockFloorValid(d0)) {
						Vec3 vector3d = Vec3.upFromBottomCenterOf(mutableBlockPos, d0);
						if (DismountHelper.canDismountTo(this.level(), livingEntity, bounds.move(vector3d))) {
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
	protected boolean canRide(Entity entityIn) {
		return true;
	}

	@Override
	public void equipSaddle(@Nullable SoundSource soundCategory) {
		this.setSaddled(true);
		if (soundCategory != null && this.getSaddledSound() != null) {
			this.level().playSound(null, this, this.getSaddledSound(), soundCategory, 0.5F, 1.0F);
		}
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby();
	}

	/**
	 * @return Whether this entity is saddled, as a {@link Boolean}.
	 */
	@Override
	public boolean isSaddled() {
		return this.getEntityData().get(DATA_SADDLE_ID);
	}

	/**
	 * Sets whether this entity is saddled.
	 * @param isSaddled The {@link Boolean} value.
	 */
	public void setSaddled(boolean isSaddled) {
		this.getEntityData().set(DATA_SADDLE_ID, isSaddled);
	}

	/**
	 * @return Whether the player attempted to jump, as a {@link Boolean}.
	 */
	public boolean getPlayerJumped() {
		return this.getEntityData().get(DATA_PLAYER_JUMPED_ID);
	}

	/**
	 * Sets whether the player attempted to jump.
	 * @param playerJumped The {@link Boolean} value.
	 */
	public void setPlayerJumped(boolean playerJumped) {
		this.getEntityData().set(DATA_PLAYER_JUMPED_ID, playerJumped);
	}

	/**
	 * @return Whether this mount is jumping, as a {@link Boolean}.
	 */
	public boolean isMountJumping() {
		return this.getEntityData().get(DATA_MOUNT_JUMPING_ID);
	}

	/**
	 * Sets whether the mount is jumping.
	 * @param isMountJumping The {@link Boolean} value.
	 */
	public void setMountJumping(boolean isMountJumping) {
		this.getEntityData().set(DATA_MOUNT_JUMPING_ID, isMountJumping);
	}

	/**
	 * @return Whether the passenger player tried to crouch.
	 */
	public boolean playerTriedToCrouch() {
		return this.getEntityData().get(DATA_PLAYER_CROUCHED_ID);
	}

	/**
	 * Sets whether the passenger player tried to crouch.
	 * @param playerTriedToCrouch The {@link Boolean} value.
	 */
	public void setPlayerTriedToCrouch(boolean playerTriedToCrouch) {
		this.getEntityData().set(DATA_PLAYER_CROUCHED_ID, playerTriedToCrouch);
	}

	/**
	 * @return Whether this entity has been set as on the ground, as a {@link Boolean} value.
	 */
	@Override
	public boolean isEntityOnGround() {
		return this.getEntityData().get(DATA_ENTITY_ON_GROUND_ID);
	}

	/**
	 * Sets whether this entity is on the ground.
	 * @param onGround The {@link Boolean} value.
	 */
	@Override
	public void setEntityOnGround(boolean onGround) {
		this.getEntityData().set(DATA_ENTITY_ON_GROUND_ID, onGround);
	}

	/**
	 * @return A {@link Boolean} for whether this entity can perform a boosted jump, depending on whether it is saddled according to {@link MountableAnimal#isSaddled()} and is also on the ground.
	 */
	@Override
	public boolean canJump() {
		return this.isSaddled() && this.onGround();
	}

	/**
	 * @see MountableMob#getMountJumpStrength()
	 */
	@Override
	public double getMountJumpStrength() {
		return 1.8;
	}

	/**
	 * @see MountableMob#getSteeringSpeed()
	 */
	@Override
	public float getSteeringSpeed() {
		return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.625F;
	}

	/**
	 * @return A {@link Float} for the midair speed of this entity.
	 */
	@Override
	public float getFlyingSpeed() {
		return this.getControllingPassenger() != null ? this.getSteeringSpeed() * 0.25F : 0.02F;
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
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Saddled", this.isSaddled());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("Saddled")) {
			this.setSaddled(tag.getBoolean("Saddled"));
		}
	}
}