package com.gildedgames.aether.entity.passive;

import com.gildedgames.aether.entity.NotGrounded;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

import javax.annotation.Nonnull;
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
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public abstract class MountableAnimal extends AetherAnimal implements ItemSteerable, Saddleable, NotGrounded {
	private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_PLAYER_JUMPED_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_MOUNT_JUMPING_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_PLAYER_CROUCHED_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_ENTITY_ON_GROUND_ID = SynchedEntityData.defineId(MountableAnimal.class, EntityDataSerializers.BOOLEAN);
	private static final UUID MOUNT_HEIGHT_UUID = UUID.fromString("B2D5A57A-8DA5-4127-8091-14A4CCD000F1");
	private static final UUID DEFAULT_HEIGHT_UUID = UUID.fromString("31535561-F99D-4E14-ACE7-F636EAAD6180");

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
		if (this.getControllingPassenger() instanceof Player player) {
			AetherPlayer.get(player).ifPresent(aetherPlayer -> {
				if (aetherPlayer.isJumping() && !this.isMountJumping()) {
					this.setPlayerJumped(true);
				}
			});
		}
	}

	@Override
	public void travel(@Nonnull Vec3 vector3d) {
		if (this.isAlive()) {
			LivingEntity entity = this.getControllingPassenger();
			if (this.isVehicle() && entity != null) {
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
				AttributeInstance stepHeight = this.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
				if (stepHeight != null) {
					if (stepHeight.hasModifier(this.getDefaultStepHeightModifier())) {
						stepHeight.removeModifier(this.getDefaultStepHeightModifier());
					}
					if (!stepHeight.hasModifier(this.getMountStepHeightModifier())) {
						stepHeight.addTransientModifier(this.getMountStepHeightModifier());
					}
				}
				this.flyingSpeed = this.getSteeringSpeed() * 0.25F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed(this.getSteeringSpeed());
					this.travelWithInput(new Vec3(f, vector3d.y, f1));
				} else if (entity instanceof Player)  {
					this.setDeltaMovement(Vec3.ZERO);
				}
				if (this.onGround) {
					this.setPlayerJumped(false);
					this.setMountJumping(false);
				}
				if (entity instanceof ServerPlayer serverPlayer) {
					ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
					serverGamePacketListenerImplAccessor.setAboveGroundTickCount(0);
					serverGamePacketListenerImplAccessor.setAboveGroundVehicleTickCount(0);
				}
				this.calculateEntityAnimation(this, false);
			} else {
				AttributeInstance stepHeight = this.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
				if (stepHeight != null) {
					if (stepHeight.hasModifier(this.getMountStepHeightModifier())) {
						stepHeight.removeModifier(this.getMountStepHeightModifier());
					}
					if (!stepHeight.hasModifier(this.getDefaultStepHeightModifier())) {
						stepHeight.addTransientModifier(this.getDefaultStepHeightModifier());
					}
				}
				this.flyingSpeed = 0.02F;
				this.travelWithInput(vector3d);
			}
		}
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

	public void onJump() {
		net.minecraftforge.common.ForgeHooks.onLivingJump(this);
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

	public boolean canJump() {
		return this.isSaddled() && this.isOnGround();
	}

	protected double getMountJumpStrength() {
		return 1.8;
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
	public boolean isEntityOnGround() {
		return this.entityData.get(DATA_ENTITY_ON_GROUND_ID);
	}

	@Override
	public void setEntityOnGround(boolean onGround) {
		this.entityData.set(DATA_ENTITY_ON_GROUND_ID, onGround);
	}

	public AttributeModifier getMountStepHeightModifier() {
		return new AttributeModifier(MOUNT_HEIGHT_UUID, "Mounted step height increase", 0.4, AttributeModifier.Operation.ADDITION);
	}

	public AttributeModifier getDefaultStepHeightModifier() {
		return new AttributeModifier(DEFAULT_HEIGHT_UUID, "Default step height increase", -0.1, AttributeModifier.Operation.ADDITION);
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
