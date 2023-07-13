package com.aetherteam.aether.entity.monster.dungeon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.block.AetherBlocks;

import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.SentryExplosionParticlePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class Sentry extends Slime {
	public static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(Sentry.class, EntityDataSerializers.BOOLEAN);
	
	public float timeSpotted = 0.0F;
	
	public Sentry(EntityType<? extends Sentry> type, Level level) {
		super(type, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new Sentry.FloatGoal(this));
		this.goalSelector.addGoal(2, new Sentry.AttackGoal(this));
		this.goalSelector.addGoal(3, new Sentry.RandomDirectionGoal(this));
		this.goalSelector.addGoal(5, new Sentry.KeepOnJumpingGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity) -> Math.abs(entity.getY() - this.getY()) <= 4.0));
	}

	@Nonnull
	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10.0)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.ATTACK_DAMAGE);
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_AWAKE_ID, false);
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
		this.setSize(1, true);
		this.setLeftHanded(false);
		return spawnData;
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
		} else {
			this.setAwake(false);
		}
		super.tick();
	}

	@Override
	protected void jumpFromGround() {
		if (this.isAwake()) {
			super.jumpFromGround();
		}
	}

	@Override
	public void push(@Nonnull Entity entity) {
		super.push(entity);
		if (entity instanceof LivingEntity livingEntity && !(entity instanceof Sentry)) {
			this.explodeAt(livingEntity);
		}
	}

	@Override
	public void playerTouch(@Nonnull Player player) {
		if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)) {
			this.explodeAt(player);
		}
	}

	protected void explodeAt(LivingEntity livingEntity) {
		if (this.distanceToSqr(livingEntity) < 1.5D && this.isAwake() && this.hasLineOfSight(livingEntity) && livingEntity.hurt(this.damageSources().mobAttack(this), 1.0F) && this.tickCount > 20 && this.isAlive()) {
			livingEntity.push(0.3, 0.4, 0.3);
			this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, Level.ExplosionInteraction.MOB);
			this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.2F * (this.random.nextFloat() - this.random.nextFloat()) + 1);
			if (this.level instanceof ServerLevel level) {
				AetherPacketHandler.sendToNear(new SentryExplosionParticlePacket(this.getId()), this.getX(), this.getY(), this.getZ(), 10.0, level.dimension());
				level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.5);
			}
			this.doEnchantDamageEffects(this, livingEntity);
			this.discard();
		}
	}

	@Override
	public void remove(@Nonnull Entity.RemovalReason reason) {
		this.setRemoved(reason);
		if (reason == Entity.RemovalReason.KILLED) {
			this.gameEvent(GameEvent.ENTITY_DIE);
		}
		this.invalidateCaps();
	}

	public boolean isAwake() {
		return this.entityData.get(DATA_AWAKE_ID);
	}
	
	public void setAwake(boolean awake) {
		this.entityData.set(DATA_AWAKE_ID, awake);
	}

	@Override
	public void setSize(int size, boolean resetHealth) {}

	@Nonnull
	@Override
	protected ParticleOptions getParticleType() {
		return new BlockParticleOption(ParticleTypes.BLOCK, AetherBlocks.SENTRY_STONE.get().defaultBlockState());
	}

	@Nonnull
	@Override
	protected ResourceLocation getDefaultLootTable() {
		return this.getType().getDefaultLootTable();
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	public EntityType<? extends Sentry> getType() {
		return (EntityType<? extends Sentry>) super.getType();
	}

	@Override
	protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
		return AetherSoundEvents.ENTITY_SENTRY_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_SENTRY_DEATH.get();
	}

	@Nonnull
	@Override
	protected SoundEvent getSquishSound() {
		return AetherSoundEvents.ENTITY_SENTRY_JUMP.get();
	}

	@Nonnull
	@Override
	protected SoundEvent getJumpSound() {
		return AetherSoundEvents.ENTITY_SENTRY_JUMP.get();
	}

	@Nonnull
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(2*0.879F);
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	public static class AttackGoal extends SlimeAttackGoal {
		private final Sentry sentry;

		public AttackGoal(Sentry sentryIn) {
			super(sentryIn);
			this.sentry = sentryIn;
		}

		@Override
		public boolean canUse() {
			return this.sentry.isAwake() && super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			return this.sentry.isAwake() && super.canContinueToUse();
		}
	}

	public static class FloatGoal extends SlimeFloatGoal {
		private final Sentry sentry;

		public FloatGoal(Sentry sentryIn) {
			super(sentryIn);
			this.sentry = sentryIn;
		}

		@Override
		public boolean canUse() {
			return this.sentry.isAwake() && super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			return this.sentry.isAwake() && super.canContinueToUse();
		}
	}

	public static class KeepOnJumpingGoal extends SlimeKeepOnJumpingGoal {
		private final Sentry sentry;

		public KeepOnJumpingGoal(Sentry sentryIn) {
			super(sentryIn);
			this.sentry = sentryIn;
		}

		@Override
		public boolean canUse() {
			return this.sentry.isAwake() && super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			return this.sentry.isAwake() && super.canContinueToUse();
		}
	}

	public static class RandomDirectionGoal extends SlimeRandomDirectionGoal {
		private final Sentry sentry;

		public RandomDirectionGoal(Sentry sentryIn) {
			super(sentryIn);
			this.sentry = sentryIn;
		}

		@Override
		public boolean canUse() {
			return this.sentry.isAwake() && super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			return this.sentry.isAwake() && super.canContinueToUse();
		}
	}
}
