package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.MountableMob;
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
import com.aetherteam.aether.perk.data.ServerPerkData;
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
import net.neoforged.neoforge.common.NeoForgeMod;

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

	private float wingRotation;
	private float prevWingRotation;
	private float destPos;
	private float prevDestPos;

	private int jumpCooldown;
	private int flapCooldown;

	private int eggTime = this.getEggTime();

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

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new FallPathNavigation(this, level);
	}

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
		this.getEntityData().define(DATA_MOA_UUID_ID, Optional.empty());
		this.getEntityData().define(DATA_MOA_TYPE_ID, "");
		this.getEntityData().define(DATA_RIDER_UUID, Optional.empty());
		this.getEntityData().define(DATA_LAST_RIDER_UUID, Optional.empty());
		this.getEntityData().define(DATA_REMAINING_JUMPS_ID, 0);
		this.getEntityData().define(DATA_HUNGRY_ID, false);
		this.getEntityData().define(DATA_AMOUNT_FED_ID, 0);
		this.getEntityData().define(DATA_PLAYER_GROWN_ID, false);
		this.getEntityData().define(DATA_SITTING_ID, false);
		this.getEntityData().define(DATA_FOLLOWING_ID, Optional.empty());
	}

	/**
	 * Sets up Moas when spawned.
	 * @param level The {@link ServerLevelAccessor} where the entity is spawned.
	 * @param difficulty The {@link DifficultyInstance} of the game.
	 * @param reason The {@link MobSpawnType} reason.
	 * @param spawnData The {@link SpawnGroupData}.
	 * @param tag The {@link CompoundTag} to apply to this entity.
	 * @return The {@link SpawnGroupData} to return.
	 */
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
		this.generateMoaUUID();
		if (tag != null) { // Applies NBT when spawned from incubation.
			if (tag.contains("IsBaby")) {
				this.setBaby(tag.getBoolean("IsBaby"));
			}
			if (tag.contains("MoaType")) {
				MoaType moaType = AetherMoaTypes.get(tag.getString("MoaType"));
				if (moaType != null) {
					this.setMoaType(moaType);
				}
			}
			if (tag.contains("Hungry")) {
				this.setHungry(tag.getBoolean("Hungry"));
			}
			if (tag.contains("PlayerGrown")) {
				this.setPlayerGrown(tag.getBoolean("PlayerGrown"));
			}
		}
		if (spawnData == null) { // Disallow baby Moas from spawning in spawn groups.
			spawnData = new AgeableMob.AgeableMobGroupData(false);
		}
		if (this.getMoaType() == null) { // A random Moa Type to set during natural spawning.
			this.setMoaType(AetherMoaTypes.getWeightedChance(this.getRandom()));
		}
		return super.finalizeSpawn(level, difficulty, reason, spawnData, tag);
	}

	/**
	 * Refreshes the Moa's bounding box dimensions.
	 * @param dataAccessor The {@link EntityDataAccessor} for the entity.
	 */
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
		if (DATA_SITTING_ID.equals(dataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(dataAccessor);
	}

	/**
	 * Handles wing animation.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
		this.animateWings();
	}

	/**
	 * Handles Moa behavior.
	 */
	@Override
	public void tick() {
		super.tick();
		AttributeInstance gravity = this.getAttribute(NeoForgeMod.ENTITY_GRAVITY.get());
		if (gravity != null) {
			double max = this.isVehicle() ? -0.5 : -0.1;
			double fallSpeed = Math.max(gravity.getValue() * -1.25, max); // Entity isn't allowed to fall too slowly from gravity.
			if (this.getDeltaMovement().y() < fallSpeed && !this.playerTriedToCrouch()) {
				this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
				this.hasImpulse = true;
				this.setEntityOnGround(false);
			}
		}
		if (this.onGround()) { // Reset jumps when the Moa is on the ground.
			this.setRemainingJumps(this.getMaxJumps());
		}
		if (this.getJumpCooldown() > 0) { // Handles jump reset behavior.
			this.setJumpCooldown(this.getJumpCooldown() - 1);
			this.setPlayerJumped(false);
		} else if (this.getJumpCooldown() == 0) {
			this.setMountJumping(false);
		}

		// Handles egg laying.
		if (!this.level().isClientSide() && this.isAlive()) {
			if (this.getRandom().nextInt(900) == 0 && this.deathTime == 0) {
				this.heal(1.0F);
			}
			if (!this.isBaby() && this.getPassengers().isEmpty() && --this.eggTime <= 0) {
				MoaType moaType = this.getMoaType();
				if (moaType != null) {
					EggLayEvent eggLayEvent = AetherEventDispatch.onLayEgg(this, AetherSoundEvents.ENTITY_MOA_EGG.get(), 1.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F, this.getMoaType().getEgg());
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

		// Handles baby hunger.
		if (this.isBaby()) {
			if (!this.isHungry()) {
				if (!this.level().isClientSide()) {
					if (this.getRandom().nextInt(2000) == 0) {
						this.setHungry(true);
					}
				}
			} else {
				if (this.getRandom().nextInt(10) == 0) {
					this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), this.getY() + 1, this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), 0.0, 0.0, 0.0);
				}
			}
		} else {
			this.setHungry(false);
			this.setAmountFed(0);
		}

		// Handles rider tracking.
		if (this.getControllingPassenger() instanceof Player player) {
			if (this.getRider() == null) {
				this.setRider(player.getUUID());
			}
		} else {
			if (this.getRider() != null) {
				this.setRider(null);
			}
		}

		// Handles flap cooldown for sounds.
		if (this.getFlapCooldown() > 0) {
			this.setFlapCooldown(this.getFlapCooldown() - 1);
		} else if (this.getFlapCooldown() == 0) {
			if (!this.onGround()) {
				this.level().playSound(null, this, AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundSource.NEUTRAL, 0.15F, Mth.clamp(this.getRandom().nextFloat(), 0.7F, 1.0F) + Mth.clamp(this.getRandom().nextFloat(), 0.0F, 0.3F));
				this.setFlapCooldown(15);
			}
		}
		this.checkSlowFallDistance(); // Resets the Moa's fall distance.
	}

	/**
	 * Tracks the last rider and Moa Skin data when a player mounts a Moa.
	 * @param passenger The passenger {@link Entity}.
	 */
	@Override
	protected void addPassenger(Entity passenger) {
		if (passenger instanceof Player player) {
			this.generateMoaUUID();
			if (this.getLastRider() == null || this.getLastRider() != player.getUUID()) {
				this.setLastRider(player.getUUID());
			}
			if (!player.level().isClientSide()) {
				AetherPlayer.get(player).ifPresent((aetherPlayer) -> aetherPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setLastRiddenMoa", this.getMoaUUID())); // Tracks the player as having last ridden this Moa.
				Map<UUID, MoaData> userSkinsData = ServerPerkData.MOA_SKIN_INSTANCE.getServerPerkData(player.getServer());
				if (userSkinsData.containsKey(this.getLastRider())) { // Tracks a Moa Skin as being tied to this Moa and this passenger.
					ServerPerkData.MOA_SKIN_INSTANCE.applyPerkWithVerification(player.getServer(), this.getLastRider(), new MoaData(this.getMoaUUID(), userSkinsData.get(this.getLastRider()).moaSkin()));
				}
			}
		}
		super.addPassenger(passenger);
	}

	/**
	 * Handles travel movement and entity rotations.
	 * @param vector The {@link Vec3} for travel movement.
	 */
	@Override
	public void travel(Vec3 vector) {
		if (!this.isSitting()) {
			super.travel(vector);
		} else {
			if (this.isAlive()) {
				LivingEntity entity = this.getControllingPassenger();
				if (this.isVehicle() && entity != null) {
					EntityUtil.copyRotations(this, entity);
					if (this.isControlledByLocalInstance()) {
						this.travelWithInput(new Vec3(0, vector.y(), 0));
						this.lerpSteps = 0;
					} else {
						this.calculateEntityAnimation(false);
						this.setDeltaMovement(Vec3.ZERO);
					}
				} else {
					this.travelWithInput(new Vec3(0, vector.y(), 0));
				}
			}
		}
	}

	/**
	 * Handles cooldowns, remaining jumps, and particles when jumping.
	 * @param mob The jumping {@link Mob}.
	 */
	@Override
	public void onJump(Mob mob) {
		super.onJump(mob);
		this.setJumpCooldown(10);
		if (!this.onGround()) {
			this.setRemainingJumps(this.getRemainingJumps() - 1);
			this.spawnExplosionParticle();
		}
		this.setFlapCooldown(0); // Causes the flap sound to be played in Moa#riderTick().
	}

	/**
	 * Various interaction behaviors for Moas.
	 * @param player The interacting {@link Player}.
	 * @param hand The {@link InteractionHand}.
	 * @return The {@link InteractionResult}.
	 */
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (this.isPlayerGrown() && itemStack.is(AetherItems.NATURE_STAFF.get())) { // Sits a tamed Moa down when right-clicked with a Nature Staff.
			itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
			this.setSitting(!this.isSitting());
			this.spawnExplosionParticle();
			return InteractionResult.sidedSuccess(this.level().isClientSide());
		} else if (this.isPlayerGrown() && itemStack.isEmpty() && player.isShiftKeyDown()) { // Toggles whether a tamed Moa will follow the player.
			if (this.getFollowing() == null) {
				this.setFollowing(player.getUUID());
			} else {
				this.setFollowing(null);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide());
		} else if (!this.level().isClientSide() && this.isPlayerGrown() && this.isBaby() && this.isHungry() && this.getAmountFed() < 3 && itemStack.is(AetherTags.Items.MOA_FOOD_ITEMS)) { // Feeds a hungry baby Moa.
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			this.setAmountFed(this.getAmountFed() + 1);
			switch(this.getAmountFed()) {
				case 0 -> this.setAge(-24000);
				case 1 -> this.setAge(-16000);
				case 2 -> this.setAge(-8000);
				case 3 -> this.setBaby(false);
			}
			if (this.getAmountFed() > 3 && !this.isBaby()) {
				this.setBaby(false);
			}
			this.setHungry(false);
			PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, new MoaInteractPacket(player.getId(), hand == InteractionHand.MAIN_HAND)); // Packet necessary to play animation because this code segment is server-side only, so no animations.
			return InteractionResult.CONSUME;
		} else if (this.isPlayerGrown() && !this.isBaby() && this.getHealth() < this.getMaxHealth() && itemStack.is(AetherTags.Items.MOA_FOOD_ITEMS)) { // Heals a tamed Moa.
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			this.heal(5.0F);
			return InteractionResult.sidedSuccess(this.level().isClientSide());
		} else {
			return super.mobInteract(player, hand);
		}
	}

	public void spawnExplosionParticle() {
		for (int i = 0; i < 20; ++i) {
			EntityUtil.spawnMovementExplosionParticles(this);
		}
	}

	/**
	 * When a player dismounts, the Moa will follow them.
	 * @param passenger The passenger {@link Entity}.
	 */
	@Override
	protected void removePassenger(Entity passenger) {
		super.removePassenger(passenger);
		if (passenger instanceof Player player && (this.getFollowing() == null || this.getFollowing().toString().equals(player.getUUID().toString()))) {
			this.setFollowing(player.getUUID());
		}
	}

	/**
	 * Generates a {@link UUID} for this Moa; used for Moa Skin tracking.
	 */
	public void generateMoaUUID() {
		if (this.getMoaUUID() == null) {
			this.setMoaUUID(UUID.randomUUID());
		}
	}

	/**
	 * @return The {@link UUID} for this Moa.
	 */
	@Nullable
	public UUID getMoaUUID() {
		return this.getEntityData().get(DATA_MOA_UUID_ID).orElse(null);
	}

	/**
	 * Sets this Moa's {@link UUID}.
	 * @param uuid THe {@link UUID}.
	 */
	private void setMoaUUID(@Nullable UUID uuid) {
		this.getEntityData().set(DATA_MOA_UUID_ID, Optional.ofNullable(uuid));
	}

	/**
	 * @return This Moa's {@link MoaType}.
	 */
	@Nullable
	public MoaType getMoaType() {
		return AetherMoaTypes.get(this.getEntityData().get(DATA_MOA_TYPE_ID));
	}

	/**
	 * Sets this Moa's {@link MoaType}.
	 * @param moaType The {@link MoaType}.
	 */
	public void setMoaType(MoaType moaType) {
		this.getEntityData().set(DATA_MOA_TYPE_ID, moaType.toString());
	}

	/**
	 * @return The {@link UUID} of the current rider of this Moa.
	 */
	@Nullable
	public UUID getRider() {
		return this.getEntityData().get(DATA_RIDER_UUID).orElse(null);
	}

	/**
	 * Sets the current rider of this Moa.
	 * @param uuid The {@link UUID}.
	 */
	public void setRider(@Nullable UUID uuid) {
		this.getEntityData().set(DATA_RIDER_UUID, Optional.ofNullable(uuid));
	}

	/**
	 * @return The {@link UUID} of the last rider of this Moa (including the current rider).
	 */
	@Nullable
	public UUID getLastRider() {
		return this.getEntityData().get(DATA_LAST_RIDER_UUID).orElse(null);
	}

	/**
	 * Sets the last rider of this Moa (including the current rider).
	 * @param uuid The {@link UUID}.
	 */
	public void setLastRider(@Nullable UUID uuid) {
		this.getEntityData().set(DATA_LAST_RIDER_UUID, Optional.ofNullable(uuid));
	}

	/**
	 * @return The {@link Integer} value for the remaining jumps.
	 */
	public int getRemainingJumps() {
		return this.getEntityData().get(DATA_REMAINING_JUMPS_ID);
	}

	/**
	 * Sets the remaining jumps.
	 * @param remainingJumps The {@link Integer} value.
	 */
	public void setRemainingJumps(int remainingJumps) {
		this.getEntityData().set(DATA_REMAINING_JUMPS_ID, remainingJumps);
	}

	/**
	 * @return Whether this Moa is hungry, as a {@link Boolean}.
	 */
	public boolean isHungry() {
		return this.getEntityData().get(DATA_HUNGRY_ID);
	}

	/**
	 * Sets whether this Moa is hungry.
	 * @param hungry The {@link Boolean} value.
	 */
	public void setHungry(boolean hungry) {
		this.getEntityData().set(DATA_HUNGRY_ID, hungry);
	}

	/**
	 * @return The {@link Integer} value for how many times this Moa has been fed.
	 */
	public int getAmountFed() {
		return this.getEntityData().get(DATA_AMOUNT_FED_ID);
	}

	/**
	 * Sets the amount of times this Moa has been fed.
	 * @param amountFed The {@link Integer} value.
	 */
	public void setAmountFed(int amountFed) {
		this.getEntityData().set(DATA_AMOUNT_FED_ID, amountFed);
	}

	/**
	 * @return Whether this Moa was raised by the player, as a {@link Boolean}.
	 */
	public boolean isPlayerGrown() {
		return this.getEntityData().get(DATA_PLAYER_GROWN_ID);
	}

	/**
	 * Sets whether this Moa was raised by the player.
	 * @param playerGrown The {@link Boolean} value.
	 */
	public void setPlayerGrown(boolean playerGrown) {
		this.getEntityData().set(DATA_PLAYER_GROWN_ID, playerGrown);
	}

	/**
	 * @return Whether this Moa is sitting, as a {@link Boolean}.
	 */
	public boolean isSitting() {
		return this.getEntityData().get(DATA_SITTING_ID);
	}

	/**
	 * Sets whether this Moa is sitting.
	 * @param isSitting The {@link Boolean} value.
	 */
	public void setSitting(boolean isSitting) {
		this.getEntityData().set(DATA_SITTING_ID, isSitting);
	}

	/**
	 * @return Whether this Moa is following the player, as a {@link Boolean}.
	 */
	@Nullable
	public UUID getFollowing() {
		return this.getEntityData().get(DATA_FOLLOWING_ID).orElse(null);
	}

	/**
	 * Sets whether this Moa is following the player.
	 * @param uuid The {@link Boolean} value.
	 */
	public void setFollowing(@Nullable UUID uuid) {
		this.getEntityData().set(DATA_FOLLOWING_ID, Optional.ofNullable(uuid));
	}

	/**
	 * @return The {@link Float} value for the wing rotation; used for animation.
	 */
	@Override
	public float getWingRotation() {
		return this.wingRotation;
	}

	/**
	 * Sets the wing rotation for animation.
	 * @param rotation The {@link Float} value.
	 */
	@Override
	public void setWingRotation(float rotation) {
		this.wingRotation = rotation;
	}

	/**
	 * @return The previous {@link Float} value for the wing rotation; used for animation.
	 */
	@Override
	public float getPrevWingRotation() {
		return this.prevWingRotation;
	}

	/**
	 * Sets the previous wing rotation for animation.
	 * @param rotation The {@link Float} value.
	 */
	@Override
	public void setPrevWingRotation(float rotation) {
		this.prevWingRotation = rotation;
	}

	/**
	 * @return The {@link Float} value for the amplitude of how far the wings should rotate during animation.
	 */
	@Override
	public float getWingDestPos() {
		return this.destPos;
	}

	/**
	 * Sets the amplitude of how far the wings should rotate during animation.
	 * @param pos The {@link Float} value.
	 */
	@Override
	public void setWingDestPos(float pos) {
		this.destPos = pos;
	}

	/**
	 * @return The previous {@link Float} value for the amplitude of how far the wings should rotate during animation.
	 */
	@Override
	public float getPrevWingDestPos() {
		return this.prevDestPos;
	}

	/**
	 * Sets the previous amplitude of how far the wings should rotate during animation.
	 * @param pos The {@link Float} value.
	 */
	@Override
	public void setPrevWingDestPos(float pos) {
		this.prevDestPos = pos;
	}

	/**
	 * @return The {@link Integer} value for how long until the Moa can jump again.
	 */
	public int getJumpCooldown() {
		return this.jumpCooldown;
	}

	/**
	 * Sets how long until the Moa can jump again.
	 * @param jumpCooldown The {@link Integer} value.
	 */
	public void setJumpCooldown(int jumpCooldown) {
		this.jumpCooldown = jumpCooldown;
	}

	/**
	 * @return The {@link Integer} value for how long until the Moa can play the flap sound effect again.
	 */
	public int getFlapCooldown() {
		return this.flapCooldown;
	}

	/**
	 * Sets how long until the Moa can play the flap sound effect again.
	 * @param flapCooldown The {@link Integer} value.
	 */
	public void setFlapCooldown(int flapCooldown) {
		this.flapCooldown = flapCooldown;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
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
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(AetherSoundEvents.ENTITY_MOA_STEP.get(), 0.15F, 1.0F);
	}

	/**
	 * @return The {@link Integer} for the maximum amount of jumps from the {@link MoaType}.
	 */
	public int getMaxJumps() {
		MoaType moaType = this.getMoaType();
		return moaType != null ? moaType.getMaxJumps() : AetherMoaTypes.BLUE.get().getMaxJumps();
	}

	/**
	 * @return The {@link Integer} for how long until an egg is laid.
	 */
	public int getEggTime() {
		return this.random.nextInt(6000) + 6000;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	/**
	 * Makes player-raised Moas immune to Inebriation.
	 * @param effect The {@link MobEffectInstance} to check whether this mob is affected by.
	 * @return Whether the mob is affected.
	 */
	@Override
	public boolean canBeAffected(MobEffectInstance effect) {
		return (effect.getEffect() != AetherEffects.INEBRIATION.get() || !this.isPlayerGrown()) && super.canBeAffected(effect);
	}

	/**
	 * @return The {@link Float} for the movement speed from the {@link MoaType}.
	 */
	@Override
	public float getSpeed() {
		MoaType moaType = this.getMoaType();
		return moaType != null ? moaType.getSpeed() : AetherMoaTypes.BLUE.get().getSpeed();
	}

	/**
	 * @return A {@link Boolean} for whether the Moa can jump, determined by remaining jumps and jump cooldown.
	 */
	@Override
	public boolean canJump() {
		return this.getRemainingJumps() > 0 && this.getJumpCooldown() == 0;
	}

	@Override
	public boolean isSaddleable() {
		return super.isSaddleable() && this.isPlayerGrown();
	}

	/**
	 * @see MountableMob#getMountJumpStrength()
	 */
	@Override
	public double getMountJumpStrength() {
		return this.onGround() ? 0.95 : 0.90;
	}

	/**
	 * @return The {@link Float} for the steering speed from the {@link MoaType}.
	 */
	@Override
	public float getSteeringSpeed() {
        MoaType moaType = this.getMoaType();
		return moaType != null ? moaType.getSpeed() : AetherMoaTypes.BLUE.get().getSpeed();
	}

	/**
	 * @return A {@link Float} for the calculated movement speed, both when mounted and not mounted.
	 */
	@Override
	public float getFlyingSpeed() {
		if (this.isVehicle()) {
			return this.getSteeringSpeed() * 0.45F;
		} else {
			return this.getSteeringSpeed() * 0.025F;
		}
	}

	/**
	 * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
	 */
	@Override
	public int getMaxFallDistance() {
		return this.onGround() ? super.getMaxFallDistance() : 14;
	}

	@Override
	public double getPassengersRidingOffset() {
		return this.isSitting() ? 0.25 : 1.25;
	}

	/**
	 * @return The float for the Moa's hitbox scaling. Set to a flat value, as Moa hitbox scaling is handled by {@link Moa#getDimensions(Pose)}.
	 */
	@Override
	public float getScale() {
		return 1.0F;
	}

	/**
	 * Handles the hitbox size for Moas. The height is scaled down whether the Moa is sitting or is a baby.
	 * @param pose The {@link Pose} to get dimensions for.
	 * @return The {@link EntityDimensions}.
	 */
	@Override
	public EntityDimensions getDimensions(Pose pose) {
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
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
		return null;
	}

	@Override
	public boolean canBreed() {
		return false;
	}

	/**
	 * Only allow modifying the Moa's age if its being set to one of the manually specified baby values (% -8000) or as grown up (0).
	 * @param age The {@link Integer} value for the age.
	 */
    @Override
    public void setAge(int age) {
		if (age % -8000 == 0 || (age == 0 && this.getAmountFed() >= 3)) {
            super.setAge(age);
        }
    }

	/**
	 * @return A Moa Egg {@link ItemStack} corresponding to the Moa's {@link MoaType}.
	 */
	@Nullable
	@Override
	public ItemStack getPickResult() {
		MoaEggItem moaEggItem = MoaEggItem.byId(this.getMoaType());
		return moaEggItem == null ? null : new ItemStack(moaEggItem);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("MoaUUID")) {
			this.setMoaUUID(tag.getUUID("MoaUUID"));
		}
		if (tag.contains("IsBaby")) {
			this.setBaby(tag.getBoolean("IsBaby"));
		}
		MoaType moaType = AetherMoaTypes.get(tag.getString("MoaType"));
		if (tag.contains("MoaType") && moaType != null) {
			this.setMoaType(moaType);
		} else {
			this.setMoaType(AetherMoaTypes.getWeightedChance(this.getRandom()));
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
	public void addAdditionalSaveData(CompoundTag tag) {
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