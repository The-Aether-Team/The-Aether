package com.gildedgames.aether.common.entity.passive;

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

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

public class MoaEntity extends MountableEntity {
	public static final DataParameter<String> MOA_TYPE = EntityDataManager.defineId(MoaEntity.class, DataSerializers.STRING);
	public static final DataParameter<Integer> REMAINING_JUMPS = EntityDataManager.defineId(MoaEntity.class, DataSerializers.INT);
	public static final DataParameter<Byte> AMOUNT_FED = EntityDataManager.defineId(MoaEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Boolean> PLAYER_GROWN = EntityDataManager.defineId(MoaEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Optional<UUID>> OWNER_UUID = EntityDataManager.defineId(MoaEntity.class, DataSerializers.OPTIONAL_UUID);
	public static final DataParameter<Boolean> HUNGRY = EntityDataManager.defineId(MoaEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> SITTING = EntityDataManager.defineId(MoaEntity.class, DataSerializers.BOOLEAN);

	protected final Random rand = new Random();
	
	private MoaType moaType;
	
	protected float jumpPower;
	protected boolean mountJumping;

	public float wingRotation, prevWingRotation, destPos, prevDestPos;
	protected int ticksOffGround, ticksUntilFlap, secsUntilFlying, secsUntilWalking, secsUntilHungry, secsUntilEgg;

	{
		this.maxUpStep = 1.0F;
		//this.canJumpMidAir = true;
	}

	public MoaEntity(EntityType<? extends MoaEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public MoaEntity(World worldIn) {
		super(AetherEntityTypes.MOA.get(), worldIn);

		this.secsUntilEgg = this.getRandomEggTime();
	}

	public MoaEntity(World worldIn, MoaType moaType) {
		this(worldIn);
		this.setMoaType(moaType);
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld serverWorld, AgeableEntity ageableEntity) {
		return new MoaEntity(this.level, this.getMoaType());
	}

	protected int getRandomEggTime() {
		return this.rand.nextInt(50) + 775;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.goalSelector.addGoal(0, new SwimGoal(this));
		/*
		//the second number in panic goal increases or decreases speed...
		I don't quite know how fast you would like this. But I've slowed it down for now.
		It's faster than random walking, while not being too fast.
		 */
		this.goalSelector.addGoal(1, new PanicGoal(this, 0.35));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.3F));
		//TODO this.goalSelector.addGoal(2, new TemptGoal(this, 1.25, Ingredient.fromItems(AetherItems.NATURE_STAFF), false));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
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

	public static AttributeModifierMap.MutableAttribute createMobAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 35.0D)
				.add(Attributes.MOVEMENT_SPEED, 1.0D);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getWalkTargetValue(BlockPos pos, IWorldReader worldIn) {
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
	public void move(MoverType typeIn, Vector3d pos) {
		if (!this.isSitting()) {
			super.move(typeIn, pos);
		}
		else {
			super.move(typeIn, new Vector3d(0, pos.y(), 0));
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
					this.level.playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7F, 1.0F) + MathHelper.clamp(this.rand.nextFloat(), 0.0F, 0.3F));
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
				this.destPos = MathHelper.clamp(this.destPos, 0.01F, 1.0F);
			}
			
			this.wingRotation += 1.233F;
		}
		
		fall: {
//			boolean blockBeneath = !this.world.isAirBlock(this.getPositionUnderneath());

			Vector3d vec3d = this.getDeltaMovement();
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
				// if the moa didn't just spawn in, lay the egg.
				if(this.tickCount != 1){
				this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.spawnAtLocation(this.getMoaType().getItemStack());}
				
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
				this.level.playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7F, 1.0F) + MathHelper.clamp(this.rand.nextFloat(), 0.0F, 0.3F));
				
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
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);
		
		if (!stack.isEmpty() && this.isPlayerGrown()) {
			if (this.isBaby() && this.isHungry()) {
				if (this.getAmountFed() < 3 && stack.getItem() == AetherItems.AECHOR_PETAL.get()) {
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
				}
			}
			
//			if (stack.getItem() == AetherItems.NATURE_STAFF) {
//				stack.damageItem(2, player, p -> p.sendBreakAnimation(hand));
//				
//				this.setSitting(!this.isSitting());
//				if (!this.world.isRemote) {
//					this.spawnExplosionParticle();
//				}
//				
//				return true;
//			}
		}
		
		return super.mobInteract(player, hand);
	}
	
//	@Override
//	public boolean canBeSaddled() {
//		return !this.isBaby() && this.isPlayerGrown();
//	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
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
	public void addAdditionalSaveData(CompoundNBT compound) {
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
	
}
