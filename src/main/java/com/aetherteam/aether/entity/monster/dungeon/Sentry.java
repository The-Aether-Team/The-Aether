package com.aetherteam.aether.entity.monster.dungeon;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Sentry extends Slime {
	private static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(Sentry.class, EntityDataSerializers.BOOLEAN);

	private float timeSpotted = 0.0F;
	
	public Sentry(EntityType<? extends Sentry> type, Level level) {
		super(type, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SentryFloatGoal(this));
		this.goalSelector.addGoal(2, new SentryAttackGoal(this));
		this.goalSelector.addGoal(3, new SentryRandomDirectionGoal(this));
		this.goalSelector.addGoal(5, new SentryKeepOnJumpingGoal(this));
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
		this.getEntityData().define(DATA_AWAKE_ID, false);
	}

	/**
	 * Handles waking the Sentry up if a target is spotted for long enough.
	 */
	@Override
	public void tick() {
		if (this.getLevel().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 8.0, EntitySelector.NO_SPECTATORS) != null) {
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

	/**
	 * Only allows jumping when the Sentry is awake.
	 */
	@Override
	protected void jumpFromGround() {
		if (this.isAwake()) {
			super.jumpFromGround();
		}
	}

	/**
	 * When this entity is pushed.
	 * @param entity The pushing {@link Entity}.
	 */
	@Override
	public void push(Entity entity) {
		super.push(entity);
		if (entity instanceof LivingEntity livingEntity && !(entity instanceof Sentry)) {
			this.explodeAt(livingEntity);
		}
	}

	/**
	 * Handles exploding when a player touches the Sentry.
	 * @param player The {@link Player}.
	 */
	@Override
	public void playerTouch(Player player) {
		if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)) {
			this.explodeAt(player);
		}
	}

	/**
	 * Handles explosion behavior if the Sentry is close enough to an entity.
	 * @param entity The colliding {@link Entity}.
	 */
	protected void explodeAt(LivingEntity entity) {
		if (this.distanceToSqr(entity) < 1.5 && this.isAwake() && this.hasLineOfSight(entity) && entity.hurt(this.damageSources().mobAttack(this), 1.0F) && this.tickCount > 20 && this.isAlive()) {
			entity.push(0.3, 0.4, 0.3);
			this.getLevel().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, Level.ExplosionInteraction.MOB);
			this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.2F * (this.getRandom().nextFloat() - this.getRandom().nextFloat()) + 1);
			if (this.getLevel() instanceof ServerLevel level) {
				level.broadcastEntityEvent(this, (byte) 70);
				level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.5);
			}
			this.doEnchantDamageEffects(this, entity);
			this.discard();
		}
	}

	/**
	 * [CODE COPY] - {@link Entity#remove(RemovalReason)}.
	 */
	@Override
	public void remove(Entity.RemovalReason pReason) {
		this.setRemoved(pReason);
		this.invalidateCaps();
	}

	/**
	 * @return Whether the Sentry is awake, as a {@link Boolean}.
	 */
	public boolean isAwake() {
		return this.getEntityData().get(DATA_AWAKE_ID);
	}

	/**
	 * Sets whether the Sentry is awake.
	 * @param awake The {@link Boolean} value.
	 */
	public void setAwake(boolean awake) {
		this.getEntityData().set(DATA_AWAKE_ID, awake);
	}

	/**
	 * This method is overridden to be empty to remove the behavior from {@link Slime#setSize(int, boolean)}.
	 * @param size The size {@link Integer}.
	 * @param resetHealth Whether to reset the entity's health, as a {@link Boolean}.
	 */
	@Override
	public void setSize(int size, boolean resetHealth) { }

	@Override
	protected ParticleOptions getParticleType() {
		return new BlockParticleOption(ParticleTypes.BLOCK, AetherBlocks.SENTRY_STONE.get().defaultBlockState());
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
		return super.getDimensions(pose).scale(1.76F);
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

	static class SentryAttackGoal extends SlimeAttackGoal {
		private final Sentry sentry;

		public SentryAttackGoal(Sentry sentry) {
			super(sentry);
			this.sentry = sentry;
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

	static class SentryFloatGoal extends SlimeFloatGoal {
		private final Sentry sentry;

		public SentryFloatGoal(Sentry sentry) {
			super(sentry);
			this.sentry = sentry;
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

	static class SentryKeepOnJumpingGoal extends SlimeKeepOnJumpingGoal {
		private final Sentry sentry;

		public SentryKeepOnJumpingGoal(Sentry sentry) {
			super(sentry);
			this.sentry = sentry;
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

	static class SentryRandomDirectionGoal extends SlimeRandomDirectionGoal {
		private final Sentry sentry;

		public SentryRandomDirectionGoal(Sentry sentry) {
			super(sentry);
			this.sentry = sentry;
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
