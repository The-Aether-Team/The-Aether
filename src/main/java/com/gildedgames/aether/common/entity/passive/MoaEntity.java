package com.gildedgames.aether.common.entity.passive;

import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.api.AetherAPI;
import com.gildedgames.aether.core.api.AetherMoaTypes;
import com.gildedgames.aether.core.api.MoaType;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ResourceLocationException;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

public class MoaEntity extends MountableEntity {
	public static final EntityDataAccessor<String> MOA_TYPE = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> REMAINING_JUMPS = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Byte> AMOUNT_FED = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Boolean> PLAYER_GROWN = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(MoaEntity.class, EntityDataSerializers.BOOLEAN);

	protected final Random rand = new Random();
	
	private MoaType moaType;
	
	protected float jumpPower;
	protected boolean mountJumping;

	public float wingRotation, prevWingRotation, destPos, prevDestPos;
	protected int ticksOffGround, ticksUntilFlap, secsUntilFlying, secsUntilWalking, secsUntilHungry, secsUntilEgg;

	{
		this.maxUpStep = 1.0F;
		//this.canJumpMidAir = true;
		this.secsUntilEgg = this.getRandomEggTime();
	}

	public MoaEntity(EntityType<? extends MoaEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public MoaEntity(Level worldIn) {
		super(AetherEntityTypes.MOA.get(), worldIn);

		this.secsUntilEgg = this.getRandomEggTime();
	}

	public MoaEntity(Level worldIn, MoaType moaType) {
		this(worldIn);
		this.setMoaType(moaType);
	}

	@Nullable
	@Override
	public AgableMob getBreedOffspring(ServerLevel serverWorld, AgableMob ageableEntity) {
		return new MoaEntity(this.level, this.getMoaType());
	}

	protected int getRandomEggTime() {
		return this.rand.nextInt(50) + 775;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.3F));
		//TODO this.goalSelector.addGoal(2, new TemptGoal(this, 1.25, Ingredient.fromItems(AetherItems.NATURE_STAFF), false));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(7, new BreedGoal(this, 0.25F));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();

		MoaType moaType = AetherAPI.getRandomMoaType();

		this.entityData.define(MOA_TYPE, moaType.getRegistryName().toString());
		this.entityData.define(REMAINING_JUMPS, moaType.getMaxJumps());

		this.entityData.define(PLAYER_GROWN, false);
		this.entityData.define(OWNER_UUID, Optional.empty());
		this.entityData.define(AMOUNT_FED, (byte)0);
		this.entityData.define(HUNGRY, false);
		this.entityData.define(SITTING, false);
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 35.0D)
				.add(Attributes.MOVEMENT_SPEED, 1.0D);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
		return worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get() ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
	}
	
	public boolean isSitting() {
		return this.entityData.get(SITTING);
	}
	
	public void setSitting(boolean isSitting) {
		this.entityData.set(SITTING, isSitting);
	}
	
	public boolean isHungry() {
		return this.entityData.get(HUNGRY);
	}
	
	public void setHungry(boolean isHungry) {
		this.entityData.set(HUNGRY, isHungry);
	}
	
	public byte getAmountFed() {
		return this.entityData.get(AMOUNT_FED);
	}
	
	public void setAmountFed(int amountFed) {
		this.entityData.set(AMOUNT_FED, (byte)amountFed);
	}
	
	public void increaseAmountFed(int increaseAmount) {
		this.setAmountFed(this.getAmountFed() + increaseAmount);
	}
	
	public boolean isPlayerGrown() {
		return this.entityData.get(PLAYER_GROWN);
	}
	
	public void setPlayerGrown(boolean isPlayerGrown) {
		this.entityData.set(PLAYER_GROWN, isPlayerGrown);
	}
	
	@Nullable
	public UUID getOwnerID() {
		return this.entityData.get(OWNER_UUID).orElse(null);
	}
	
	public void setOwnerUUID(@Nullable UUID uuid) {
		this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
	}
	
	public int getMaxJumps() {
		return this.getMoaType().getMaxJumps();
	}
	
	public int getRemainingJumps() {
		return this.entityData.get(REMAINING_JUMPS);
	}
	
	public void setRemainingJumps(int remainingJumps) {
		this.entityData.set(REMAINING_JUMPS, remainingJumps);
	}
	
	public MoaType getMoaType() {
		MoaType moaType = this.moaType;
		return (moaType == null)? this.moaType = AetherAPI.getMoaType(new ResourceLocation(this.entityData.get(MOA_TYPE))) : moaType;
	}
	
	public void setMoaType(MoaType moaType) {
		this.setSpeed(moaType.getMoaSpeed());
		this.entityData.set(MOA_TYPE, moaType.getRegistryName().toString());
		this.moaType = moaType;
	}
	
	@Override
	public void move(MoverType typeIn, Vec3 pos) {
		if (!this.isSitting()) {
			super.move(typeIn, pos);
		}
		else {
			super.move(typeIn, new Vec3(0, pos.y(), 0));
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public void tick() {
		super.tick();
		
		if (this.jumping) {
			this.push(0.0, 0.05, 0.0);
		}
		
		updateWingRotation: {
			if (!this.onGround) {
				if (this.ticksUntilFlap == 0) {
					this.level.playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundSource.NEUTRAL, 0.15F, Mth.clamp(this.rand.nextFloat(), 0.7F, 1.0F) + Mth.clamp(this.rand.nextFloat(), 0.0F, 0.3F));
					this.ticksUntilFlap = 8;
				}
				else {
					--this.ticksUntilFlap;
				}
			}
			
			this.prevWingRotation = this.wingRotation;
			this.prevDestPos = this.destPos;
			
			if (this.onGround) {
				this.destPos = 0.0F;
			}
			else {			
				this.destPos += 0.2;
				this.destPos = Mth.clamp(this.destPos, 0.01F, 1.0F);
			}
			
			this.wingRotation += 1.233F;
		}
		
		fall: {
//			boolean blockBeneath = !this.world.isAirBlock(this.getPositionUnderneath());

			Vec3 vec3d = this.getDeltaMovement();
			if (!this.onGround && vec3d.y < 0.0) {
				this.setDeltaMovement(vec3d.multiply(1.0, 0.6, 1.0));
			}
			
			if (this.onGround) {
				this.setRemainingJumps(this.getMaxJumps());
			}
		}
		
		if (this.secsUntilHungry > 0) {
			if (this.tickCount % 20 == 0) {
				--this.secsUntilHungry;
			}
		}
		else if (!this.isHungry()) {
			this.setHungry(true);
		}
		
		if (this.level.isClientSide && this.isHungry() && this.isBaby()) {
			if (this.rand.nextInt(10) == 0) {
				this.level.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + (this.rand.nextDouble() - 0.5) * this.getBbWidth(), this.getY() + 1, this.getZ() + (this.rand.nextDouble() - 0.5) * this.getBbWidth(), 0.0, 0.0, 0.0);
			}
		}
		
		if (!this.level.isClientSide && !this.isBaby() && this.getPassengers().isEmpty()) {
			if (this.secsUntilEgg > 0) {
				if (this.tickCount % 20 == 0) {
					--this.secsUntilEgg;
				}
			}
			else {
				this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.spawnAtLocation(this.getMoaType().getItemStack());
				
				this.secsUntilEgg = this.getRandomEggTime();
			}
		}
		
		this.fallDistance = 0.0F;
	}
	
	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}
	
	public void resetHunger() {
		if (!this.level.isClientSide) {
			this.setHungry(false);
		}
		
		this.secsUntilHungry = 40 + this.rand.nextInt(40);
	}
	
	public void onMountedJump() {
		if (this.getRemainingJumps() > 0 && this.getDeltaMovement().y() < 0.0) {
			if (!this.onGround) {
				float jumpPower = this.jumpPower;
				if (jumpPower < 0.7F) {
					jumpPower = 0.7F;
				}
				this.setDeltaMovement(this.getDeltaMovement().x(), jumpPower, this.getDeltaMovement().z());
				this.level.playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundSource.NEUTRAL, 0.15F, Mth.clamp(this.rand.nextFloat(), 0.7F, 1.0F) + Mth.clamp(this.rand.nextFloat(), 0.0F, 0.3F));
				
				if (!this.level.isClientSide) {
					this.setRemainingJumps(this.getRemainingJumps() - 1);
					ForgeHooks.onLivingJump(this);
					this.spawnAnim();
				}
			}
			else {
				this.setDeltaMovement(this.getDeltaMovement().x(), 0.89, this.getDeltaMovement().z());
			}
		}
	}
	
	@Override
	public float getSpeed() {
		return 0.6F;//this.getMoaType().getMoaSpeed();
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		
		if (!stack.isEmpty()) {
			if (stack.getItem() == AetherItems.AECHOR_PETAL.get()) {
				if (this.isPlayerGrown() && this.isBaby() && this.isHungry() && this.getAmountFed() < 3) {
					if (!player.abilities.instabuild) {
						stack.shrink(1);
					}
					
					this.increaseAmountFed(1);
					
					if (this.getAmountFed() >= 3) {
						this.setAge(0);
					}
					else {
						this.resetHunger();
					}
					return InteractionResult.SUCCESS;
				}
			}	
//			else if (stack.getItem() == AetherItems.NATURE_STAFF.get()) {
//				stack.damageItem(2, player, p -> p.sendBreakAnimation(hand));
//				
//				this.setSitting(!this.isSitting());
//				if (!this.world.isRemote) {
//					this.spawnExplosionParticle();
//				}
//				
//				return ActionResultType.SUCCESS;
//			}
			else if (stack.getItem() == AetherItems.MOA_DEBUG_STICK.get()) {
				if (this.isBaby()) {
					this.setAge(0);
					this.addParticlesAroundSelf(ParticleTypes.ENCHANTED_HIT);
					this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F, false);
				} else if (!this.isPlayerGrown()) {
					this.setPlayerGrown(true);
					UUID ownerUUID = player.getUUID();
					CompoundTag tag = stack.getTag();
					if (tag != null) {
						if (tag.contains("OwnerUUID", 8)) {
							String str = tag.getString("OwnerUUID");
							if (str.isEmpty()) {
								ownerUUID = UUID.randomUUID();
							} else {
								try {
									ownerUUID = UUID.fromString(str);
								} catch (IllegalArgumentException e) {
									/* do nothing */
								}
							}
						} else if (tag.hasUUID("OwnerUUID")) {
							ownerUUID = tag.getUUID("OwnerUUID");
						}
					}
					this.setOwnerUUID(ownerUUID);
					this.addParticlesAroundSelf(ParticleTypes.HEART);
					this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F, false);
				} else {
					Iterator<MoaType> moaTypes = AetherAPI.getMoaTypes().iterator();
					MoaType currType = moaTypes.next();
					MoaType lastType = null;
					MoaType firstType = currType;
					while (moaTypes.hasNext() && currType != this.moaType) {
						lastType = currType;
						currType = moaTypes.next();
					}
					if (currType == this.moaType) {
						if (player.isCrouching()) {
							if (lastType == null) {
								if (moaTypes.hasNext()) {
									do {
										lastType = moaTypes.next();
									} while (moaTypes.hasNext());
									this.setMoaType(lastType);
								}
							} else {
								this.setMoaType(lastType);
							}
						} else {
							this.setMoaType(moaTypes.hasNext()? moaTypes.next() : firstType);
						}
					}
					this.addParticlesAroundSelf(ParticleTypes.LARGE_SMOKE);
					this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F, false);
				}
				return InteractionResult.SUCCESS;
			}
		}
		
		return super.mobInteract(player, hand);
	}
	
//	@Override
//	public boolean canBeSaddled() {
//		return !this.isBaby() && this.isPlayerGrown();
//	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		
		this.setPlayerGrown(compound.getBoolean("PlayerGrown"));
		if(compound.hasUUID("OwnerUUID")) {
			this.setOwnerUUID(compound.getUUID("OwnerUUID"));
		}
		this.setSitting(compound.getBoolean("Sitting"));
		this.setRemainingJumps(compound.getInt("RemainingJumps"));
		this.setAmountFed(compound.getInt("AmountFed"));
		this.setHungry(compound.getBoolean("Hungry"));
		String moaTypeStr = compound.getString("MoaType");
		if (moaTypeStr == null || moaTypeStr.isEmpty()) {
			this.setMoaType(AetherAPI.getRandomMoaType());
		}
		else {
			try {
				this.setMoaType(AetherAPI.getMoaType(new ResourceLocation(compound.getString("MoaType"))));
			}
			catch (ResourceLocationException e) {
				this.setMoaType(AetherMoaTypes.BLUE);
			}
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		
		compound.putBoolean("PlayerGrown", this.isPlayerGrown());
		if (this.getOwnerID() != null) {
			compound.putUUID("OwnerUUID", this.getOwnerID());
		}
		compound.putBoolean("Sitting", this.isSitting());
		compound.putInt("RemainingJumps", this.getRemainingJumps());
		compound.putInt("AmountFed", this.getAmountFed());
		compound.putBoolean("Hungry", this.isHungry());
		compound.putString("MoaType", this.getMoaType().getRegistryName().toString());
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		super.playStepSound(pos, blockIn);
	}
	
	@Override
	protected void jumpFromGround() {
		if (!this.isSitting() && this.getPassengers().isEmpty()) {
			super.jumpFromGround();
		}
	}
	
	@Override
	public double getPassengersRidingOffset() {
		return this.isSitting()? 0.25 : 1.25;
	}

//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public void onPlayerJump(int jumpPowerIn) {
//		if (this.getRemainingJumps() > 0) {
//			LogManager.getLogger(MoaEntity.class).debug("Set moa jump power to {}", jumpPowerIn);
//			if (jumpPowerIn < 0) {
//				jumpPowerIn = 0;
//			}
//
//			if (jumpPowerIn >= 90) {
//				this.jumpPower = 1.0F;
//			}
//			else {
//				this.jumpPower = 0.7F + 0.3F * jumpPowerIn / 90.0F;
//			}
//		}
//	}

	@Override
	public boolean canJump() {
		return this.getRemainingJumps() > 0 && super.canJump();
	}
	
//	@Override
//	public void handleStartJump(int jumpPower) {
//		super.handleStartJump(jumpPower);
//		this.onMountedJump();
//	}
//
//	@Override
//	public void handleStopJump() {
//		super.handleStopJump();
//	}

	@OnlyIn(Dist.CLIENT)
	protected void addParticlesAroundSelf(ParticleOptions p_213718_1_) {
		for (int i = 0; i < 5; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level.addParticle(p_213718_1_, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0,
					d1, d2);
		}

	}
	
}
