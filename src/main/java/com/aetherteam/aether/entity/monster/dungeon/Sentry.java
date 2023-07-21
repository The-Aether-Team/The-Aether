package com.aetherteam.aether.entity.monster.dungeon;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

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
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
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
	public void push(Entity entity) {
		super.push(entity);
		if (entity instanceof LivingEntity livingEntity && !(entity instanceof Sentry)) {
			this.explodeAt(livingEntity);
		}
	}

	@Override
	public void playerTouch(Player player) {
		if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)) {
			this.explodeAt(player);
		}
	}

	protected void explodeAt(LivingEntity livingEntity) {
		if (this.distanceToSqr(livingEntity) < 1.5 && this.isAwake() && this.hasLineOfSight(livingEntity) && livingEntity.hurt(this.damageSources().mobAttack(this), 1.0F) && this.tickCount > 20 && this.isAlive()) {
			livingEntity.push(0.3, 0.4, 0.3);
			this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, Level.ExplosionInteraction.MOB);
			this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.2F * (this.random.nextFloat() - this.random.nextFloat()) + 1);
			if (this.level instanceof ServerLevel level) {
				level.broadcastEntityEvent(this, (byte) 70);
				level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.5);
			}
			this.doEnchantDamageEffects(this, livingEntity);
			this.discard();
		}
	}

	@Override
	public void remove(Entity.RemovalReason reason) {
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

	@Override
	protected ParticleOptions getParticleType() {
		return new BlockParticleOption(ParticleTypes.BLOCK, AetherBlocks.SENTRY_STONE.get().defaultBlockState());
	}

	@Override
	protected ResourceLocation getDefaultLootTable() {
		return this.getType().getDefaultLootTable();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EntityType<? extends Sentry> getType() {
		return (EntityType<? extends Sentry>) super.getType();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return AetherSoundEvents.ENTITY_SENTRY_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_SENTRY_DEATH.get();
	}

	@Override
	protected SoundEvent getSquishSound() {
		return AetherSoundEvents.ENTITY_SENTRY_JUMP.get();
	}

	@Override
	protected SoundEvent getJumpSound() {
		return AetherSoundEvents.ENTITY_SENTRY_JUMP.get();
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale(2*0.879F);
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 70) {
			for (int i = 0; i < 40; i++) {
				double x = this.getX() + (this.getRandom().nextFloat() * 0.25);
				double y = this.getY() + 0.5;
				double z = this.getZ() + (this.getRandom().nextFloat() * 0.25);
				float f1 = this.getRandom().nextFloat() * 360.0F;
				this.getLevel().addParticle(ParticleTypes.POOF, x, y, z, -Math.sin((Math.PI / 180.0F) * f1) * 0.75, 0.125, Math.cos((Math.PI / 180.0F) * f1) * 0.75);
			}
		} else {
			super.handleEntityEvent(id);
		}
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
