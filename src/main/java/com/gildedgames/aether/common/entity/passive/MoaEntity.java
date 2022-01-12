package com.gildedgames.aether.common.entity.passive;

import javax.annotation.Nullable;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.ai.FallingRandomWalkingGoal;
import com.gildedgames.aether.common.entity.ai.navigator.FallPathNavigator;
import com.gildedgames.aether.common.registry.AetherEntityTypes;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.api.registers.MoaType;
import com.gildedgames.aether.core.registry.AetherMoaTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

//TODO:
//Raising system, which depends on the Incubator.
//Fixing the issues with using isOnGround not properly detecting if the Moa and other MountableEntities are on the ground.
//Implement visual HUD for Moa jumps.
//Make isSaddleable() and the Nature Staff functionality dependent on isPlayerGrown().
//Make MoaEntity and other MountableEntities affected by Slowfall and other movement modifiers.

public class MoaEntity extends MountableEntity
{
	private static final EntityDataAccessor<String> DATA_MOA_TYPE_ID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Optional<UUID>> DATA_RIDER_UUID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_LAST_RIDER_UUID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Integer> DATA_REMAINING_JUMPS_ID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_HUNGRY_ID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_AMOUNT_FED_ID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_PLAYER_GROWN_ID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_SITTING_ID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.BOOLEAN);

	public float wingRotation;
	public float prevWingRotation;
	public float destPos;
	public float prevDestPos;

	private int jumpCooldown;
	private int flapCooldown;

	public int eggTime = this.random.nextInt(50) + 775;

	public MoaEntity(EntityType<? extends MoaEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public MoaEntity(Level worldIn) {
		this(AetherEntityTypes.MOA.get(), worldIn);
		this.maxUpStep = 1.0F;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 0.65F));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.0F, Ingredient.of(AetherItems.NATURE_STAFF.get()), false));
		this.goalSelector.addGoal(3, new FallingRandomWalkingGoal(this, 0.35F));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FallPathNavigator(this, world);
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 35.0D)
				.add(Attributes.MOVEMENT_SPEED, 1.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		MoaType moaType = AetherMoaTypes.getRandomMoaType();
		this.entityData.define(DATA_MOA_TYPE_ID, moaType.getRegistryName());
		this.entityData.define(DATA_RIDER_UUID, Optional.empty());
		this.entityData.define(DATA_LAST_RIDER_UUID, Optional.empty());
		this.entityData.define(DATA_REMAINING_JUMPS_ID, moaType.getMaxJumps());
		this.entityData.define(DATA_HUNGRY_ID, false);
		this.entityData.define(DATA_AMOUNT_FED_ID, 0);
		this.entityData.define(DATA_PLAYER_GROWN_ID, false);
		this.entityData.define(DATA_SITTING_ID, false);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getDeltaMovement().y < -0.1 && !this.playerTriedToCrouch()) {
			this.setDeltaMovement(this.getDeltaMovement().x, -0.1, this.getDeltaMovement().z);
		}
		if (this.isOnGround()) {
			this.setRemainingJumps(this.getMaxJumps());
		}
		if (this.getJumpCooldown() > 0) {
			this.setJumpCooldown(this.getJumpCooldown() - 1);
			this.setPlayerJumped(false);
		} else if (this.getJumpCooldown() == 0) {
			this.setMountJumping(false);
		}

		if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && this.getPassengers().isEmpty() && --this.eggTime <= 0) {
			this.playSound(AetherSoundEvents.ENTITY_MOA_EGG.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			this.spawnAtLocation(this.getMoaType().getEgg());
			this.eggTime = this.random.nextInt(50) + 775;
		}
	}

	@Override
	public void riderTick() {
		super.riderTick();
		if (this.getControllingPassenger() instanceof Player) {
			if (this.getFlapCooldown() > 0) {
				this.setFlapCooldown(this.getFlapCooldown() - 1);
			} else if (this.getFlapCooldown() == 0) {
				if (!this.isOnGround()) {
					this.level.playSound(null, this, AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundSource.NEUTRAL, 0.15F, Mth.clamp(this.random.nextFloat(), 0.7F, 1.0F) + Mth.clamp(this.random.nextFloat(), 0.0F, 0.3F));
					this.setFlapCooldown(15);
				}
			}
		}
	}

	@Override
	public void travel(Vec3 vector3d) {
		if (!this.isSitting()) {
			super.travel(vector3d);
		} else {
			if (this.isAlive()) {
				if (this.isVehicle() && this.canBeControlledByRider() && this.getControllingPassenger() instanceof Player entity) {
					this.setYRot(entity.getYRot());
					this.yRotO = this.getYRot();
					this.setXRot(entity.getXRot() * 0.5F);
					this.setRot(this.getYRot(), this.getXRot());
					this.yBodyRot = this.getYRot();
					this.yHeadRot = this.yBodyRot;
					if (this.isControlledByLocalInstance()) {
						this.travelWithInput(new Vec3(0, vector3d.y(), 0));
						this.lerpSteps = 0;
					} else {
						this.calculateEntityAnimation(this, false);
						this.setDeltaMovement(Vec3.ZERO);
					}
				} else {
					this.travelWithInput(new Vec3(0, vector3d.y(), 0));
				}
			}
		}
	}

	@Override
	public void onJump() {
		super.onJump();
		this.setJumpCooldown(10);
		this.setRemainingJumps(this.getRemainingJumps() - 1);
		if (!this.isOnGround()) {
			this.spawnExplosionParticle();
		}
		this.setFlapCooldown(0);
	}

	@Override
	public InteractionResult mobInteract(Player playerEntity, InteractionHand hand) {
		ItemStack itemstack = playerEntity.getItemInHand(hand);
		if (itemstack.getItem() == AetherItems.NATURE_STAFF.get()) {
			itemstack.hurtAndBreak(2, playerEntity, (p) -> p.broadcastBreakEvent(hand));
			this.setSitting(!this.isSitting());
			this.spawnExplosionParticle();
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		} else {
			return super.mobInteract(playerEntity, hand);
		}
	}

	public void spawnExplosionParticle() {
		for (int i = 0; i < 20; ++i) {
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

	//	@Override
//	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
//		ItemStack stack = player.getItemInHand(hand);
//
//		if (!stack.isEmpty() && this.isPlayerGrown()) {
//			if (this.isBaby() && this.isHungry()) {
//				if (this.getAmountFed() < 3 && stack.getItem() == AetherItems.AECHOR_PETAL.get()) {
//					if (!player.abilities.instabuild) {
//						stack.shrink(1);
//					}
//
//					this.increaseAmountFed(1);
//
//					if (this.getAmountFed() >= 3) {
//						this.setAge(0);
//					}
//					else {
//						this.resetHunger();
//					}
//				}
//			}
//		}
//
//		return super.mobInteract(player, hand);
//	}

	public MoaType getMoaType() {
		return AetherMoaTypes.MOA_TYPES.get(this.entityData.get(DATA_MOA_TYPE_ID));
	}

	public void setMoaType(MoaType moaType) {
		this.entityData.set(DATA_MOA_TYPE_ID, moaType.getRegistryName());
	}

	@Nullable
	public UUID getRider() {
		return this.entityData.get(DATA_RIDER_UUID).orElse(null);
	}

	public void setRider(@Nullable UUID uuid) {
		this.entityData.set(DATA_RIDER_UUID, Optional.ofNullable(uuid));
	}

	@Nullable
	public UUID getLastRider() {
		return this.entityData.get(DATA_LAST_RIDER_UUID).orElse(null);
	}

	public void setLastRider(@Nullable UUID uuid) {
		this.entityData.set(DATA_LAST_RIDER_UUID, Optional.ofNullable(uuid));
	}

	public int getRemainingJumps() {
		return this.entityData.get(DATA_REMAINING_JUMPS_ID);
	}

	public void setRemainingJumps(int remainingJumps) {
		this.entityData.set(DATA_REMAINING_JUMPS_ID, remainingJumps);
	}

	public boolean isHungry() {
		return this.entityData.get(DATA_HUNGRY_ID);
	}

	public void setHungry(boolean hungry) {
		this.entityData.set(DATA_HUNGRY_ID, hungry);
	}

	public int getAmountFed() {
		return this.entityData.get(DATA_AMOUNT_FED_ID);
	}

	public void setAmountFed(int amountFed) {
		this.entityData.set(DATA_AMOUNT_FED_ID, amountFed);
	}

	public boolean isPlayerGrown() {
		return this.entityData.get(DATA_PLAYER_GROWN_ID);
	}

	public void setPlayerGrown(boolean playerGrown) {
		this.entityData.set(DATA_PLAYER_GROWN_ID, playerGrown);
	}

	public boolean isSitting() {
		return this.entityData.get(DATA_SITTING_ID);
	}

	public void setSitting(boolean isSitting) {
		this.entityData.set(DATA_SITTING_ID, isSitting);
	}

	public int getJumpCooldown() {
		return this.jumpCooldown;
	}

	public void setJumpCooldown(int jumpCooldown) {
		this.jumpCooldown = jumpCooldown;
	}

	public int getFlapCooldown() {
		return this.flapCooldown;
	}

	public void setFlapCooldown(int flapCooldown) {
		this.flapCooldown = flapCooldown;
	}

	public int getMaxJumps() {
		return this.getMoaType().getMaxJumps();
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return AetherSoundEvents.ENTITY_MOA_HURT.get();
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_MOA_DEATH.get();
	}

	@Nullable
	@Override
	protected SoundEvent getSaddledSound() {
		return AetherSoundEvents.ENTITY_MOA_SADDLE.get();
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	@Override
	public float getSpeed() {
		return this.getMoaType().getSpeed();
	}

	@Override
	public boolean canJump() {
		return this.getRemainingJumps() > 0 && this.getJumpCooldown() == 0;
	}

	@Override
	public boolean isSaddleable() {
		return super.isSaddleable();
	}

	@Override
	protected double getMountJumpStrength() {
		return this.isOnGround() ? 0.9D : 0.75D;
	}

	@Override
	public float getSteeringSpeed() {
		return this.getMoaType().getSpeed();
	}

	@Override
	protected int calculateFallDamage(float distance, float damageMultiplier) {
		return 0;
	}

	@Override
	public int getMaxFallDistance() {
		return this.isOnGround() ? super.getMaxFallDistance() : 14;
	}

	@Override
	public double getPassengersRidingOffset() {
		return this.isSitting() ? 0.25 : 1.25;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return null;
	}

	@Override
	public boolean canBreed() {
		return false;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setMoaType(AetherMoaTypes.MOA_TYPES.get(compound.getString("MoaType")));
		if (compound.hasUUID("Rider")) {
			this.setRider(compound.getUUID("Rider"));
		}
		if (compound.hasUUID("LastRider")) {
			this.setLastRider(compound.getUUID("LastRider"));
		}
		this.setRemainingJumps(compound.getInt("RemainingJumps"));
		this.setHungry(compound.getBoolean("Hungry"));
		this.setAmountFed(compound.getInt("AmountFed"));
		this.setPlayerGrown(compound.getBoolean("PlayerGrown"));
		this.setSitting(compound.getBoolean("Sitting"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("MoaType", this.getMoaType().getRegistryName());
		if (this.getRider() != null) {
			compound.putUUID("Rider", this.getRider());
		}
		if (this.getLastRider() != null) {
			compound.putUUID("LastRider", this.getLastRider());
		}
		compound.putInt("RemainingJumps", this.getRemainingJumps());
		compound.putBoolean("Hungry", this.isHungry());
		compound.putInt("AmountFed", this.getAmountFed());
		compound.putBoolean("PlayerGrown", this.isPlayerGrown());
		compound.putBoolean("Sitting", this.isSitting());
	}



//	protected int ticksOffGround, ticksUntilFlap, secsUntilFlying, secsUntilWalking, secsUntilHungry, secsUntilEgg;
//
//	@SuppressWarnings("unused")
//	@Override
//	public void tick() {
//		super.tick();
//		if (this.secsUntilHungry > 0) {
//			if (this.tickCount % 20 == 0) {
//				--this.secsUntilHungry;
//			}
//		}
//		else if (!this.isHungry()) {
//			this.setHungry(true);
//		}
//
//		if (this.level.isClientSide && this.isHungry() && this.isBaby()) {
//			if (this.rand.nextInt(10) == 0) {
//				this.level.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + (this.rand.nextDouble() - 0.5) * this.getBbWidth(), this.getY() + 1, this.getZ() + (this.rand.nextDouble() - 0.5) * this.getBbWidth(), 0.0, 0.0, 0.0);
//			}
//		}
//	}
//
//	public void resetHunger() {
//		if (!this.level.isClientSide) {
//			this.setHungry(false);
//		}
//
//		this.secsUntilHungry = 40 + this.rand.nextInt(40);
//	}
//
//	public void onMountedJump() {
//		if (this.getRemainingJumps() > 0 && this.getDeltaMovement().y() < 0.0) {
//			if (!this.onGround) {
//				float jumpPower = this.jumpPower;
//				if (jumpPower < 0.7F) {
//					jumpPower = 0.7F;
//				}
//				this.setDeltaMovement(this.getDeltaMovement().x(), jumpPower, this.getDeltaMovement().z());
//				this.level.playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7F, 1.0F) + MathHelper.clamp(this.rand.nextFloat(), 0.0F, 0.3F));
//
//				if (!this.level.isClientSide) {
//					this.setRemainingJumps(this.getRemainingJumps() - 1);
//					ForgeHooks.onLivingJump(this);
//					this.spawnAnim();
//				}
//			}
//			else {
//				this.setDeltaMovement(this.getDeltaMovement().x(), 0.89, this.getDeltaMovement().z());
//			}
//		}
//	}
//

//
}