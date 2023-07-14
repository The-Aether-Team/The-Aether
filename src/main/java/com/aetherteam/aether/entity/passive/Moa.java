package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.WingedBird;
import com.aetherteam.aether.entity.ai.goal.ContinuousMeleeAttackGoal;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.entity.ai.goal.MoaFollowGoal;
import com.aetherteam.aether.entity.ai.navigator.FallPathNavigation;
import com.aetherteam.aether.entity.monster.AechorPlant;
import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.event.EggLayEvent;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.miscellaneous.MoaEggItem;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.MoaInteractPacket;
import com.aetherteam.aether.perk.data.ServerMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Moa extends MountableAnimal implements WingedBird {
	private static final EntityDataAccessor<Optional<UUID>> DATA_MOA_UUID_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<String> DATA_MOA_TYPE_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Optional<UUID>> DATA_RIDER_UUID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_LAST_RIDER_UUID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Integer> DATA_REMAINING_JUMPS_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_HUNGRY_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_AMOUNT_FED_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_PLAYER_GROWN_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_SITTING_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Optional<UUID>> DATA_FOLLOWING_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UUID);

	public float wingRotation;
	public float prevWingRotation;
	public float destPos;
	public float prevDestPos;

	private int jumpCooldown;
	private int flapCooldown;

	public int eggTime = this.getEggTime();

	public Moa(EntityType<? extends Moa> type, Level level) {
		super(type, level);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 0.65));
		this.goalSelector.addGoal(2, new MoaFollowGoal(this, 1.0));
		this.goalSelector.addGoal(3, new ContinuousMeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(4, new FallingRandomStrollGoal(this, 0.35));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Swet.class, false, (livingEntity) -> this.getFollowing() == null && this.isPlayerGrown() && !this.isBaby() && livingEntity instanceof Swet swet && !swet.isFriendly()));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AechorPlant.class, false, (livingEntity) -> this.getFollowing() == null && this.isPlayerGrown() && !this.isBaby()));
	}

	@Nonnull
	@Override
	protected PathNavigation createNavigation(@Nonnull Level level) {
		return new FallPathNavigation(this, level);
	}

	@Nonnull
	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 35.0)
				.add(Attributes.MOVEMENT_SPEED, 1.0)
				.add(Attributes.FOLLOW_RANGE, 16.0)
				.add(Attributes.ATTACK_DAMAGE, 5.0);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_MOA_UUID_ID, Optional.empty());
		this.entityData.define(DATA_MOA_TYPE_ID, "");
		this.entityData.define(DATA_RIDER_UUID, Optional.empty());
		this.entityData.define(DATA_LAST_RIDER_UUID, Optional.empty());
		this.entityData.define(DATA_REMAINING_JUMPS_ID, 0);
		this.entityData.define(DATA_HUNGRY_ID, false);
		this.entityData.define(DATA_AMOUNT_FED_ID, 0);
		this.entityData.define(DATA_PLAYER_GROWN_ID, false);
		this.entityData.define(DATA_SITTING_ID, false);
		this.entityData.define(DATA_FOLLOWING_ID, Optional.empty());
	}

	@Override
	public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
		this.generateMoaUUID();
		if (tag != null) {
			if (tag.contains("IsBaby")) {
				this.setBaby(tag.getBoolean("IsBaby"));
			}
			if (tag.contains("MoaType")) {
				this.setMoaType(AetherMoaTypes.get(tag.getString("MoaType")));
			}
			if (tag.contains("Hungry")) {
				this.setHungry(tag.getBoolean("Hungry"));
			}
			if (tag.contains("PlayerGrown")) {
				this.setPlayerGrown(tag.getBoolean("PlayerGrown"));
			}
		}
		if (spawnData == null) {
			spawnData = new AgeableMob.AgeableMobGroupData(false);
		}
		if (this.getMoaType() == null) {
			this.setMoaType(AetherMoaTypes.getWeightedChance(this.random));
		}
		return super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
	}

	@Override
	public void onSyncedDataUpdated(@Nonnull EntityDataAccessor<?> dataAccessor) {
		if (DATA_SITTING_ID.equals(dataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(dataAccessor);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.animateWings();
	}

	@Override
	public void tick() {
		super.tick();
		AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
		if (gravity != null) {
			double max = this.isVehicle() ? -0.5 : -0.1;
			double fallSpeed = Math.max(gravity.getValue() * -1.25, max);
			if (this.getDeltaMovement().y < fallSpeed && !this.playerTriedToCrouch()) {
				this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
				this.hasImpulse = true;
				this.setEntityOnGround(false);
			}
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

		if (!this.level.isClientSide() && this.isAlive()) {
			if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
				this.heal(1.0F);
			}
			if (!this.isBaby() && this.getPassengers().isEmpty() && --this.eggTime <= 0) {
				MoaType moaType = this.getMoaType();
				if (moaType != null) {
					EggLayEvent eggLayEvent = AetherEventDispatch.onLayEgg(this, AetherSoundEvents.ENTITY_MOA_EGG.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F, this.getMoaType().getEgg());
					if (!eggLayEvent.isCanceled()) {
						if (eggLayEvent.getSound() != null) {
							this.playSound(eggLayEvent.getSound(), eggLayEvent.getVolume(), eggLayEvent.getPitch());
						}
						if (eggLayEvent.getItem() != null) {
							this.spawnAtLocation(eggLayEvent.getItem());
						}
					}
				}
				this.eggTime = this.getEggTime();
			}
		}

		if (this.isBaby()) {
			if (!this.isHungry()) {
				if (!this.level.isClientSide()) {
					if (this.random.nextInt(2000) == 0) {
						this.setHungry(true);
					}
				}
			} else {
				if (this.random.nextInt(10) == 0) {
					this.level.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + (this.random.nextDouble() - 0.5) * this.getBbWidth(), this.getY() + 1, this.getZ() + (this.random.nextDouble() - 0.5) * this.getBbWidth(), 0.0, 0.0, 0.0);
				}
			}
		} else {
			this.setHungry(false);
			this.setAmountFed(0);
		}

		if (this.getControllingPassenger() instanceof Player player) {
			if (this.getRider() == null) {
				this.setRider(player.getUUID());
			}
		} else {
			if (this.getRider() != null) {
				this.setRider(null);
			}
		}
	}

	@Override
	public void riderTick() {
		if (!this.isSitting()) {
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
				this.checkSlowFallDistance();
			}
		}
	}

	@Override
	protected void addPassenger(Entity passenger) {
		if (passenger instanceof Player player) {
			this.generateMoaUUID();
			if (this.getLastRider() == null || this.getLastRider() != player.getUUID()) {
				this.setLastRider(player.getUUID());
			}
			if (!player.getLevel().isClientSide()) {
				AetherPlayer.get(player).ifPresent((aetherPlayer) -> aetherPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setLastRiddenMoa", this.getMoaUUID()));
				Map<UUID, MoaData> userSkinsData = ServerMoaSkinPerkData.INSTANCE.getServerPerkData(player.getServer());
				if (userSkinsData.containsKey(this.getLastRider())) {
					ServerMoaSkinPerkData.INSTANCE.applyPerkWithVerification(player.getServer(), this.getLastRider(), new MoaData(this.getMoaUUID(), userSkinsData.get(this.getLastRider()).moaSkin()));
				}
			}
		}
		super.addPassenger(passenger);
	}

	@Override
	public void travel(@Nonnull Vec3 vector3d) {
		if (!this.isSitting()) {
			super.travel(vector3d);
		} else {
			if (this.isAlive()) {
				LivingEntity entity = this.getControllingPassenger();
				if (this.isVehicle() && entity != null) {
					EntityUtil.copyRotations(this, entity);
					if (this.isControlledByLocalInstance()) {
						this.travelWithInput(new Vec3(0, vector3d.y(), 0));
						this.lerpSteps = 0;
					} else {
						this.calculateEntityAnimation(false);
						this.setDeltaMovement(Vec3.ZERO);
					}
				} else {
					this.travelWithInput(new Vec3(0, vector3d.y(), 0));
				}
			}
		}
	}

	@Override
	public void onJump(Mob moa) {
		super.onJump(moa);
		this.setJumpCooldown(10);
		if (!this.isOnGround()) {
			this.setRemainingJumps(this.getRemainingJumps() - 1);
			this.spawnExplosionParticle();
		}
		this.setFlapCooldown(0);
	}

	@Nonnull
	@Override
	public InteractionResult mobInteract(Player playerEntity, @Nonnull InteractionHand hand) {
		ItemStack itemStack = playerEntity.getItemInHand(hand);
		if (this.isPlayerGrown() && itemStack.is(AetherItems.NATURE_STAFF.get())) {
			itemStack.hurtAndBreak(1, playerEntity, (p) -> p.broadcastBreakEvent(hand));
			this.setSitting(!this.isSitting());
			this.spawnExplosionParticle();
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		} else if (this.isPlayerGrown() && itemStack.isEmpty() && playerEntity.isShiftKeyDown()) {
			if (this.getFollowing() == null) {
				this.setFollowing(playerEntity.getUUID());
			} else {
				this.setFollowing(null);
			}
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		} else if (!this.level.isClientSide() && this.isPlayerGrown() && this.isBaby() && this.isHungry() && this.getAmountFed() < 3 && itemStack.is(AetherTags.Items.MOA_FOOD_ITEMS)) {
			if (!playerEntity.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			this.setAmountFed(this.getAmountFed() + 1);
			if (this.getAmountFed() >= 3) {
				this.setBaby(false);
			}
			this.setHungry(false);
			PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, new MoaInteractPacket(playerEntity.getId(), hand == InteractionHand.MAIN_HAND)); // packet necessary to play animation because this code segment is server-side only, so no animations.
			return InteractionResult.CONSUME;
		} else if (this.isPlayerGrown() && !this.isBaby() && this.getHealth() < this.getMaxHealth() && itemStack.is(AetherTags.Items.MOA_FOOD_ITEMS)) {
			if (!playerEntity.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			this.heal(5.0F);
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		} else {
			return super.mobInteract(playerEntity, hand);
		}
	}

	public void spawnExplosionParticle() {
		for (int i = 0; i < 20; ++i) {
			EntityUtil.spawnMovementExplosionParticles(this);
		}
	}

	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);
		if (passenger instanceof Player player && (this.getFollowing() == null || this.getFollowing().toString().equals(player.getUUID().toString()))) {
			this.setFollowing(player.getUUID());
		}
	}

	public void generateMoaUUID() {
		if (this.getMoaUUID() == null) {
			this.setMoaUUID(UUID.randomUUID());
		}
	}

	@Nullable
	public UUID getMoaUUID() {
		return this.entityData.get(DATA_MOA_UUID_ID).orElse(null);
	}

	private void setMoaUUID(@Nullable UUID uuid) {
		this.entityData.set(DATA_MOA_UUID_ID, Optional.ofNullable(uuid));
	}

	@Nullable
	public MoaType getMoaType() {
		return AetherMoaTypes.get(this.entityData.get(DATA_MOA_TYPE_ID));
	}

	public void setMoaType(MoaType moaType) {
		this.entityData.set(DATA_MOA_TYPE_ID, moaType.toString());
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

	public UUID getFollowing() {
		return this.entityData.get(DATA_FOLLOWING_ID).orElse(null);
	}

	public void setFollowing(UUID uuid) {
		this.entityData.set(DATA_FOLLOWING_ID, Optional.ofNullable(uuid));
	}

	@Override
	public float getWingRotation() {
		return this.wingRotation;
	}

	@Override
	public void setWingRotation(float rot) {
		this.wingRotation = rot;
	}

	@Override
	public float getPrevWingRotation() {
		return this.prevWingRotation;
	}

	@Override
	public void setPrevWingRotation(float rot) {
		this.prevWingRotation = rot;
	}

	@Override
	public float getDestPos() {
		return this.destPos;
	}

	@Override
	public void setDestPos(float pos) {
		this.destPos = pos;
	}

	@Override
	public float getPrevDestPos() {
		return this.prevDestPos;
	}

	@Override
	public void setPrevDestPos(float pos) {
		this.prevDestPos = pos;
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
		MoaType moaType = this.getMoaType();
		return moaType != null ? moaType.getMaxJumps() : AetherMoaTypes.BLUE.get().getMaxJumps();
	}

	public int getEggTime() {
		return this.random.nextInt(6000) + 6000;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
		return AetherSoundEvents.ENTITY_MOA_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_MOA_DEATH.get();
	}

	@Override
	protected SoundEvent getSaddledSound() {
		return AetherSoundEvents.ENTITY_MOA_SADDLE.get();
	}

	@Override
	protected void playStepSound(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		this.playSound(AetherSoundEvents.ENTITY_MOA_STEP.get(), 0.15F, 1.0F);
	}

	@Override
	public boolean isFood(@Nonnull ItemStack stack) {
		return false;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance effect) {
		return (effect.getEffect() != AetherEffects.INEBRIATION.get() || !this.isPlayerGrown()) && super.canBeAffected(effect);
	}

	@Override
	public float getSpeed() {
		MoaType moaType = this.getMoaType();
		return moaType != null ? moaType.getSpeed() : AetherMoaTypes.BLUE.get().getSpeed();
	}

	@Override
	public boolean canJump() {
		return this.getRemainingJumps() > 0 && this.getJumpCooldown() == 0;
	}

	@Override
	public boolean isSaddleable() {
		return super.isSaddleable() && this.isPlayerGrown();
	}

	@Override
	public double getMountJumpStrength() {
		return this.isOnGround() ? 0.95 : 0.90;
	}

	@Override
	public float getSteeringSpeed() {
        MoaType moaType = this.getMoaType();
		return moaType != null ? moaType.getSpeed() : AetherMoaTypes.BLUE.get().getSpeed();
	}

	@Override
	public float getFlyingSpeed() {
		if (this.isVehicle()) {
			return this.getSteeringSpeed() * 0.45F;
		} else {
			return this.getSteeringSpeed() * 0.025F;
		}
	}

	@Override
	public int getMaxFallDistance() {
		return this.isOnGround() ? super.getMaxFallDistance() : 14;
	}

	@Override
	public double getPassengersRidingOffset() {
		return this.isSitting() ? 0.25 : 1.25;
	}

	@Override
	public float getScale() {
		return 1.0F;
	}

	@Nonnull
	@Override
	public EntityDimensions getDimensions(@Nonnull Pose pose) {
		EntityDimensions dimensions = super.getDimensions(pose);
		if (this.isSitting()) {
			dimensions = dimensions.scale(1.0F, 0.5F);
		}
		if (this.isBaby()) {
			dimensions = dimensions.scale(1.0F, 0.5F);
		}
		return dimensions;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(@Nonnull ServerLevel level, @Nonnull AgeableMob entity) {
		return null;
	}

	@Override
	public boolean canBreed() {
		return false;
	}

    @Override
    public void setAge(int age) {
		if (age == -24000 || (age == 0 && this.getAmountFed() >= 3)) {
            super.setAge(age);
        }
    }

	@Override
	public ItemStack getPickResult() {
		MoaEggItem moaEggItem = MoaEggItem.byId(this.getMoaType());
		return moaEggItem == null ? null : new ItemStack(moaEggItem);
	}

	@Override
	public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("MoaUUID")) {
			this.setMoaUUID(tag.getUUID("MoaUUID"));
		}
		if (tag.contains("IsBaby")) {
			this.setBaby(tag.getBoolean("IsBaby"));
		}
		if (tag.contains("MoaType") && AetherMoaTypes.get(tag.getString("MoaType")) != null) {
			this.setMoaType(AetherMoaTypes.get(tag.getString("MoaType")));
		} else {
			this.setMoaType(AetherMoaTypes.getWeightedChance(this.random));
		}
		if (tag.hasUUID("Rider")) {
			this.setRider(tag.getUUID("Rider"));
		}
		if (tag.hasUUID("LastRider")) {
			this.setLastRider(tag.getUUID("LastRider"));
		}
		if (tag.contains("RemainingJumps")) {
			this.setRemainingJumps(tag.getInt("RemainingJumps"));
		}
		if (tag.contains("Hungry")) {
			this.setHungry(tag.getBoolean("Hungry"));
		}
		if (tag.contains("AmountFed")) {
			this.setAmountFed(tag.getInt("AmountFed"));
		}
		if (tag.contains("PlayerGrown")) {
			this.setPlayerGrown(tag.getBoolean("PlayerGrown"));
		}
		if (tag.contains("Sitting")) {
			this.setSitting(tag.getBoolean("Sitting"));
		}
		if (tag.contains("Following")) {
			this.setFollowing(tag.getUUID("Following"));
		}
	}

	@Override
	public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (this.getMoaUUID() != null) {
			tag.putUUID("MoaUUID", this.getMoaUUID());
		}
		tag.putBoolean("IsBaby", this.isBaby());
		tag.putString("MoaType", Objects.requireNonNullElse(this.getMoaType(), AetherMoaTypes.BLUE).toString());
		if (this.getRider() != null) {
			tag.putUUID("Rider", this.getRider());
		}
		if (this.getLastRider() != null) {
			tag.putUUID("LastRider", this.getLastRider());
		}
		tag.putInt("RemainingJumps", this.getRemainingJumps());
		tag.putBoolean("Hungry", this.isHungry());
		tag.putInt("AmountFed", this.getAmountFed());
		tag.putBoolean("PlayerGrown", this.isPlayerGrown());
		tag.putBoolean("Sitting", this.isSitting());
		if (this.getFollowing() != null) {
			tag.putUUID("Following", this.getFollowing());
		}
	}
}