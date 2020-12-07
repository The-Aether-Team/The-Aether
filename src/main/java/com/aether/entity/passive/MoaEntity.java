package com.aether.entity.passive;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;

import com.aether.api.AetherAPI;
import com.aether.api.moa.MoaType;
import com.aether.api.moa.MoaTypes;
import com.aether.block.AetherBlocks;
import com.aether.entity.AetherEntityTypes;
import com.aether.item.AetherItems;
import com.aether.util.AetherSoundEvents;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

public class MoaEntity extends SaddleableEntity {	
	public static final DataParameter<String> MOA_TYPE = EntityDataManager.createKey(MoaEntity.class, DataSerializers.STRING);
	public static final DataParameter<Integer> REMAINING_JUMPS = EntityDataManager.createKey(MoaEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Byte> AMOUNT_FED = EntityDataManager.createKey(MoaEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Boolean> PLAYER_GROWN = EntityDataManager.createKey(MoaEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Optional<UUID>> OWNER_UUID = EntityDataManager.createKey(MoaEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	public static final DataParameter<Boolean> HUNGRY = EntityDataManager.createKey(MoaEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(MoaEntity.class, DataSerializers.BOOLEAN);

	protected final Random rand = new Random();
	
	private MoaType moaType;
	
	protected float jumpPower;
	protected boolean mountJumping;

	public float wingRotation, prevWingRotation, destPos, prevDestPos;
	protected int ticksOffGround, ticksUntilFlap, secsUntilFlying, secsUntilWalking, secsUntilHungry, secsUntilEgg;

	{
		this.stepHeight = 1.0F;
		this.canJumpMidAir = true;
	}

	public MoaEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);

		this.secsUntilEgg = this.getRandomEggTime();
	}

	public MoaEntity(World worldIn) {
		super(AetherEntityTypes.MOA, worldIn);

		this.secsUntilEgg = this.getRandomEggTime();
	}

	public MoaEntity(World worldIn, MoaType moaType) {
		this(worldIn);

		this.setMoaType(moaType);
	}

	@Nullable
	@Override
	public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
		return new MoaEntity(this.world, this.getMoaType());
	}

	protected int getRandomEggTime() {
		return this.rand.nextInt(50) + 775;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.3F));
		//TODO this.goalSelector.addGoal(2, new TemptGoal(this, 1.25, Ingredient.fromItems(AetherItems.NATURE_STAFF), false));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(7, new BreedGoal(this, 0.25F));
	}

	@Override
	protected void registerData() {
		super.registerData();

		MoaType moaType = AetherAPI.getRandomMoaType();

		this.dataManager.register(MOA_TYPE, moaType.getRegistryName().toString());
		this.dataManager.register(REMAINING_JUMPS, moaType.getMaxJumps());

		this.dataManager.register(PLAYER_GROWN, false);
		this.dataManager.register(OWNER_UUID, Optional.empty());
		this.dataManager.register(AMOUNT_FED, (byte)0);
		this.dataManager.register(HUNGRY, false);
		this.dataManager.register(SITTING, false);
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return AnimalEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 35.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.0D);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
		return worldIn.getBlockState(pos.down()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK? 10.0F : worldIn.getBrightness(pos) - 0.5F;
	}
	
	public boolean isSitting() {
		return this.dataManager.get(SITTING);
	}
	
	public void setSitting(boolean isSitting) {
		this.dataManager.set(SITTING, isSitting);
	}
	
	public boolean isHungry() {
		return this.dataManager.get(HUNGRY);
	}
	
	public void setHungry(boolean isHungry) {
		this.dataManager.set(HUNGRY, isHungry);
	}
	
	public byte getAmountFed() {
		return this.dataManager.get(AMOUNT_FED);
	}
	
	public void setAmountFed(int amountFed) {
		this.dataManager.set(AMOUNT_FED, (byte)amountFed);
	}
	
	public void increaseAmountFed(int increaseAmount) {
		this.setAmountFed(this.getAmountFed() + increaseAmount);
	}
	
	public boolean isPlayerGrown() {
		return this.dataManager.get(PLAYER_GROWN);
	}
	
	public void setPlayerGrown(boolean isPlayerGrown) {
		this.dataManager.set(PLAYER_GROWN, isPlayerGrown);
	}
	
	@Nullable
	public UUID getOwnerID() {
		return this.dataManager.get(OWNER_UUID).orElse(null);
	}
	
	public void setOwnerUUID(@Nullable UUID uuid) {
		this.dataManager.set(OWNER_UUID, Optional.ofNullable(uuid));
	}
	
	public int getMaxJumps() {
		return this.getMoaType().getMaxJumps();
	}
	
	public int getRemainingJumps() {
		return this.dataManager.get(REMAINING_JUMPS);
	}
	
	public void setRemainingJumps(int remainingJumps) {
		this.dataManager.set(REMAINING_JUMPS, remainingJumps);
	}
	
	public MoaType getMoaType() {
		MoaType moaType = this.moaType;
		return (moaType == null)? this.moaType = AetherAPI.getMoaType(new ResourceLocation(this.dataManager.get(MOA_TYPE))) : moaType;
	}
	
	public void setMoaType(MoaType moaType) {
		this.setAIMoveSpeed(moaType.getMoaSpeed());
		this.dataManager.set(MOA_TYPE, moaType.getRegistryName().toString());
		this.moaType = moaType;
	}
	
	@Override
	public void move(MoverType typeIn, Vector3d pos) {
		if (!this.isSitting()) {
			super.move(typeIn, pos);
		}
		else {
			super.move(typeIn, new Vector3d(0, pos.getY(), 0));
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public void tick() {
		super.tick();
		
		if (this.isJumping) {
			this.addVelocity(0.0, 0.05, 0.0);
		}
		
		updateWingRotation: {
			if (!this.onGround) {
				if (this.ticksUntilFlap == 0) {
					this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), AetherSoundEvents.ENTITY_MOA_FLAP, SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7F, 1.0F) + MathHelper.clamp(this.rand.nextFloat(), 0.0F, 0.3F));
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

			Vector3d vec3d = this.getMotion();
			if (!this.onGround && vec3d.y < 0.0) {
				this.setMotion(vec3d.mul(1.0, 0.6, 1.0));
			}
			
			if (this.onGround) {
				this.setRemainingJumps(this.getMaxJumps());
			}
		}
		
		if (this.secsUntilHungry > 0) {
			if (this.ticksExisted % 20 == 0) {
				--this.secsUntilHungry;
			}
		}
		else if (!this.isHungry()) {
			this.setHungry(true);
		}
		
		if (this.world.isRemote && this.isHungry() && this.isChild()) {
			if (this.rand.nextInt(10) == 0) {
				this.world.addParticle(ParticleTypes.ANGRY_VILLAGER, this.getPosX() + (this.rand.nextDouble() - 0.5) * this.getWidth(), this.getPosY() + 1, this.getPosZ() + (this.rand.nextDouble() - 0.5) * this.getWidth(), 0.0, 0.0, 0.0);
			}
		}
		
		if (!this.world.isRemote && !this.isChild() && this.getPassengers().isEmpty()) {
			if (this.secsUntilEgg > 0) {
				if (this.ticksExisted % 20 == 0) {
					--this.secsUntilEgg;
				}
			}
			else {
				this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.entityDropItem(this.getMoaType().getItemStack());
				
				this.secsUntilEgg = this.getRandomEggTime();
			}
		}
		
		this.fallDistance = 0.0F;
	}
	
	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}
	
	public void resetHunger() {
		if (!this.world.isRemote) {
			this.setHungry(false);
		}
		
		this.secsUntilHungry = 40 + this.rand.nextInt(40);
	}
	
	public void onMountedJump() {
		if (this.getRemainingJumps() > 0 && this.getMotion().getY() < 0.0) {
			if (!this.onGround) {
				float jumpPower = this.jumpPower;
				if (jumpPower < 0.7F) {
					jumpPower = 0.7F;
				}
				this.setMotion(this.getMotion().getX(), jumpPower, this.getMotion().getZ());
				this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), AetherSoundEvents.ENTITY_MOA_FLAP, SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7F, 1.0F) + MathHelper.clamp(this.rand.nextFloat(), 0.0F, 0.3F));
				
				if (!this.world.isRemote) {
					this.setRemainingJumps(this.getRemainingJumps() - 1);
					ForgeHooks.onLivingJump(this);
					this.spawnExplosionParticle();
				}
			}
			else {
				this.setMotion(this.getMotion().getX(), 0.89, this.getMotion().getZ());
			}
		}
	}
	
	@Override
	public float getAIMoveSpeed() {
		return 0.6F;//this.getMoaType().getMoaSpeed();
	}

	@Override
	public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
		if (!stack.isEmpty() && this.isPlayerGrown()) {
			if (this.isChild() && this.isHungry()) {
				if (this.getAmountFed() < 3 && stack.getItem() == AetherItems.AECHOR_PETAL) {
					if (!player.isCreative()) {
						stack.shrink(1);
					}
					
					this.increaseAmountFed(1);
					
					if (this.getAmountFed() >= 3) {
						this.setGrowingAge(0);
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
		
		return super.func_230254_b_(player, hand);
	}
	
	@Override
	public boolean canBeSaddled() {
		return !this.isChild() && this.isPlayerGrown();
	}
	
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		
		this.setPlayerGrown(compound.getBoolean("PlayerGrown"));
		UUID uuid = compound.getUniqueId("OwnerUUID");
		if (uuid.getMostSignificantBits() != 0L || uuid.getLeastSignificantBits() != 0L) {
			this.setOwnerUUID(uuid);
		}
		else {
			this.setOwnerUUID(null);
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
				this.setMoaType(MoaTypes.BLUE);
			}
		}
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		
		compound.putBoolean("PlayerGrown", this.isPlayerGrown());
		if (this.getOwnerID() != null) {
			compound.putUniqueId("OwnerUUID", this.getOwnerID());
		}
		compound.putBoolean("Sitting", this.isSitting());
		compound.putInt("RemainingJumps", this.getRemainingJumps());
		compound.putInt("AmountFed", this.getAmountFed());
		compound.putBoolean("Hungry", this.isHungry());
		compound.putString("MoaType", this.getMoaType().getRegistryName().toString());
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_MOA_AMBIENT;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		super.playStepSound(pos, blockIn);
	}
	
	@Override
	protected void jump() {
		if (!this.isSitting() && this.getPassengers().isEmpty()) {
			super.jump();
		}
	}
	
	@Override
	public double getMountedYOffset() {
		return this.isSitting()? 0.25 : 1.25;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void setJumpPower(int jumpPowerIn) {
		if (this.getRemainingJumps() > 0) {
			LogManager.getLogger(MoaEntity.class).debug("Set moa jump power to {}", jumpPowerIn);
			if (jumpPowerIn < 0) {
				jumpPowerIn = 0;
			}
			
			if (jumpPowerIn >= 90) {
				this.jumpPower = 1.0F;
			}
			else {
				this.jumpPower = 0.7F + 0.3F * jumpPowerIn / 90.0F;
			}
		}
	}

	@Override
	public boolean canJump() {
		return this.getRemainingJumps() > 0 && super.canJump();
	}
	
	@Override
	public void handleStartJump(int jumpPower) {
		super.handleStartJump(jumpPower);
		this.onMountedJump();
	}
	
	@Override
	public void handleStopJump() {
		super.handleStopJump();
	}
	
}
