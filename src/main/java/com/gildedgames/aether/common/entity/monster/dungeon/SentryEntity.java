package com.gildedgames.aether.common.entity.monster.dungeon;

import javax.annotation.Nullable;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;

public class SentryEntity extends Slime {
	public static final EntityDataAccessor<Boolean> SENTRY_AWAKE = SynchedEntityData.defineId(SentryEntity.class, EntityDataSerializers.BOOLEAN);
	
	public float timeSpotted = 0.0F;
	
	public SentryEntity(EntityType<? extends SentryEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public SentryEntity(Level worldIn) {
		super(AetherEntityTypes.SENTRY.get(), worldIn);
	}

	@Override
	protected void registerGoals() {
//		this.goalSelector.addGoal(1, new SentryEntity.FloatGoal(this));
//		this.goalSelector.addGoal(2, new SentryEntity.AttackGoal(this));
//		this.goalSelector.addGoal(3, new SentryEntity.FaceRandomGoal(this));
//		this.goalSelector.addGoal(5, new SentryEntity.HopGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity) -> Math.abs(entity.getY() - this.getY()) <= 4.0));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE);
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SENTRY_AWAKE, false);
	}
	
	@Override
	public void playerTouch(Player entityIn) {
		if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entityIn)) {
			this.explodeAt(entityIn);
		}
	}
	
	@Override
	public void tick() {
		if (this.level.getNearestPlayer(this.getX(), this.getY(), this.getZ(), 8.0, EntitySelector.NO_SPECTATORS) != null) {
			if (!this.isAwake()) {
				if (this.timeSpotted >= 24) {
					this.setAwake(true);
				}
				this.timeSpotted++;
			}
		}
		else {
			this.setAwake(false);
		}
		
		super.tick();
	}
	
//	@Override
//	protected ResourceLocation getLootTable() {
//		return this.getType().getLootTable();
//	}
	
	@Override
	protected ParticleOptions getParticleType() {
		return new BlockParticleOption(ParticleTypes.BLOCK, AetherBlocks.SENTRY_STONE.get().defaultBlockState());
	}
	
	@Override
	public void push(Entity entityIn) {
		super.push(entityIn);
		
		if (!(entityIn instanceof SentryEntity) && entityIn instanceof LivingEntity) {
			this.explodeAt((LivingEntity)entityIn);
		}
	}

	protected void explodeAt(LivingEntity entityIn) {
		if (this.isAwake() && this.hasLineOfSight(entityIn) && entityIn.hurt(DamageSource.mobAttack(this), 1.0F) && this.tickCount > 20) {
			entityIn.push(0.5, 0.5, 0.5);
			
			this.level.explode(this, this.getX(), this.getY(), this.getZ(), 0.1F, Explosion.BlockInteraction.DESTROY);
			this.setHealth(0.0F);
			this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.2F*(this.random.nextFloat() - this.random.nextFloat()) + 1);
			this.doEnchantDamageEffects(this, entityIn);
		}
	}
	
	@Override
	protected void jumpFromGround() {
		if (this.isAwake()) {
			super.jumpFromGround();
		}
	}

	// This is here to override the slime's default finalizeSpawn behaviour
	// which randomizes the size of the spawned slime, since we don't want
	// that to happen for Sentries.
	// The code is just the MobEntity finalizeSpawn behaviour.
	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_,
			MobSpawnType p_213386_3_, @Nullable SpawnGroupData p_213386_4_, @Nullable CompoundTag p_213386_5_) {
		this.setSize(2, true); // 2 because internal size is 1 greater than displayed size in the nbt
		// BEGIN CODE FROM MobEntity.finalizeSpawn()
		this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05D, AttributeModifier.Operation.MULTIPLY_BASE));
		if (this.random.nextFloat() < 0.05F) {
			this.setLeftHanded(true);
		} else {
			this.setLeftHanded(false);
		}
		return p_213386_4_;
		// END CODE FROM MobEntity.finalizeSpawn()
	}

	@Override
	public boolean isTiny() {
		return this.getSize() < 1;
	 }
	
	public void setAwake(boolean isAwake) {
		this.entityData.set(SENTRY_AWAKE, isAwake);
	}
	
	public boolean isAwake() {
		return this.entityData.get(SENTRY_AWAKE);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EntityType<? extends SentryEntity> getType() {
		return (EntityType<? extends SentryEntity>) super.getType();
	}

//	public static class AttackGoal extends Slime {
//		private final SentryEntity sentry;
//
//		public AttackGoal(SentryEntity sentryIn) {
//			super(sentryIn);
//			this.sentry = sentryIn;
//		}
//
//		/**
//		 * Returns whether the EntityAIBase should begin execution.
//		 */
//		@Override
//		public boolean canUse() {
//			return this.sentry.isAwake() && super.canUse();
//		}
//
//		/**
//		 * Returns whether an in-progress EntityAIBase should continue executing
//		 */
//		@Override
//		public boolean canContinueToUse() {
//			return this.sentry.isAwake() && super.canContinueToUse();
//		}
//
//	}
//
//	public static class FaceRandomGoal extends Slime.SlimeRandomDirectionGoal {
//		private final SentryEntity sentry;
//
//		public FaceRandomGoal(SentryEntity sentryIn) {
//			super(sentryIn);
//			this.sentry = sentryIn;
//		}
//
//		/**
//		 * Returns whether the EntityAIBase should begin execution.
//		 */
//		@Override
//		public boolean canUse() {
//			return this.sentry.isAwake() && super.canUse();
//		}
//
//		/**
//		 * Returns whether an in-progress EntityAIBase should continue executing
//		 */
//		@Override
//		public boolean canContinueToUse() {
//			return this.sentry.isAwake() && super.canContinueToUse();
//		}
//
//	}
//
//	public static class FloatGoal extends Slime.SlimeFloatGoal {
//		private final SentryEntity sentry;
//
//		public FloatGoal(SentryEntity sentryIn) {
//			super(sentryIn);
//			this.sentry = sentryIn;
//		}
//
//		/**
//		 * Returns whether the EntityAIBase should begin execution.
//		 */
//		@Override
//		public boolean canUse() {
//			return this.sentry.isAwake() && super.canUse();
//		}
//
//		/**
//		 * Returns whether an in-progress EntityAIBase should continue executing
//		 */
//		@Override
//		public boolean canContinueToUse() {
//			return this.sentry.isAwake() && super.canContinueToUse();
//		}
//
//	}
//
//	public static class HopGoal extends Slime.SlimeKeepOnJumpingGoal {
//		private final SentryEntity sentry;
//
//		public HopGoal(SentryEntity sentryIn) {
//			super(sentryIn);
//			this.sentry = sentryIn;
//		}
//
//		/**
//		 * Returns whether the EntityAIBase should begin execution.
//		 */
//		@Override
//		public boolean canUse() {
//			return this.sentry.isAwake() && super.canUse();
//		}
//
//		/**
//		 * Returns whether an in-progress EntityAIBase should continue executing
//		 */
//		@Override
//		public boolean canContinueToUse() {
//			return this.sentry.isAwake() && super.canContinueToUse();
//		}
//
//	}
	
}
