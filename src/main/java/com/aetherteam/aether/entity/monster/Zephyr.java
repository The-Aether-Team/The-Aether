package com.aetherteam.aether.entity.monster;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.projectile.ZephyrSnowball;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class Zephyr extends FlyingMob implements Enemy {
	private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(Zephyr.class, EntityDataSerializers.BOOLEAN);

	private int cloudScale;
	private int cloudScaleAdd;
	private float tailRot;
	private float tailRotAdd;

	public Zephyr(EntityType<? extends Zephyr> type, Level level) {
		super(type, level);
		this.moveControl = new ZephyrMoveControl(this);
		this.xpReward = 5;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new RandomFloatAroundGoal(this));
		this.goalSelector.addGoal(7, new ZephyrLookGoal(this));
		this.goalSelector.addGoal(7, new ZephyrShootSnowballGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return FlyingMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 5.0)
				.add(Attributes.FOLLOW_RANGE, 50.0);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_IS_CHARGING, false);
	}

	/**
	 * Zephyrs can spawn if {@link Mob#checkMobSpawnRules(EntityType, LevelAccessor, MobSpawnType, BlockPos, RandomSource)} is true, if they are spawning in view of the sky,
	 * if the difficulty isn't peaceful, and they spawn with a random chance of 1/11.
	 * @param zephyr The {@link Zephyr} {@link EntityType}.
	 * @param level The {@link LevelAccessor}.
	 * @param reason The {@link MobSpawnType} reason.
	 * @param pos The spawn {@link BlockPos}.
	 * @param random The {@link RandomSource}.
	 * @return Whether this entity can spawn, as a {@link Boolean}.
	 */
	public static boolean checkZephyrSpawnRules(EntityType<? extends Zephyr> zephyr, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
		return Mob.checkMobSpawnRules(zephyr, level, reason, pos, random)
				&& level.canSeeSky(pos)
				&& level.getDifficulty() != Difficulty.PEACEFUL
				&& (reason != MobSpawnType.NATURAL || random.nextInt(11) == 0);
	}

	/**
	 * Handles values used for the Zephyr's animation and removing the Zephyr if it goes below or above the build height.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getY() < this.getLevel().getMinBuildHeight() - 2 || this.getY() > this.getLevel().getMaxBuildHeight()) {
			this.discard();
		}
		if (this.getLevel().isClientSide()) {
			this.cloudScale += this.cloudScaleAdd;
			this.tailRot += this.tailRotAdd;
			if (this.isCharging() && this.cloudScale < 40) {
				this.cloudScaleAdd = 1;
			} else {
				this.cloudScaleAdd = 0;
				this.cloudScale = 0;
			}
			this.tailRotAdd = 0.015F;
			if (this.tailRot >= Mth.TWO_PI) {
				this.tailRot -= Mth.TWO_PI;
			}
		}
	}

	public boolean isCharging() {
		return this.getEntityData().get(DATA_IS_CHARGING);
	}

	public void setCharging(boolean isCharging) {
		this.getEntityData().set(DATA_IS_CHARGING, isCharging);
	}

	/**
	 * @return The {@link Integer} amount for the scale of the Zephyr.
	 */
	public int getCloudScale() {
		return this.cloudScale;
	}

	/**
	 * @return The {@link Integer} amount to add to the Zephyr's scale.
	 */
	public int getCloudScaleAdd() {
		return this.cloudScaleAdd;
	}

	/**
	 * @return The {@link Float} amount for the tail's rotation.
	 */
	public float getTailRot() {
		return this.tailRot;
	}

	/**
	 * @return The {@link Float} amount to add to the tail's rotation.
	 */
	public float getTailRotAdd() {
		return this.tailRotAdd;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return AetherSoundEvents.ENTITY_ZEPHYR_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return AetherSoundEvents.ENTITY_ZEPHYR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_ZEPHYR_DEATH.get();
	}

	@Override
	protected float getSoundVolume() {
		return 3.0F;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return true;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	/**
	 * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.GhastLookGoal}.
	 */
	protected static class ZephyrLookGoal extends Goal {
		private final Zephyr zephyr;

		public ZephyrLookGoal(Zephyr zephyr) {
			this.zephyr = zephyr;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return true;
		}

		@Override
		public void tick() {
			if (this.zephyr.getTarget() == null) {
				Vec3 vec3d = this.zephyr.getDeltaMovement();
				this.zephyr.setYRot(-((float) Mth.atan2(vec3d.x(), vec3d.z())) * (180.0F / (float) Math.PI));
				this.zephyr.yBodyRot = this.zephyr.getYRot();
			} else {
				LivingEntity livingEntity = this.zephyr.getTarget();
				if (livingEntity.distanceToSqr(this.zephyr) < 4096.0) {
					double x = livingEntity.getX() - this.zephyr.getX();
					double z = livingEntity.getZ() - this.zephyr.getZ();
					this.zephyr.setYRot(-((float) Mth.atan2(x, z)) * (180.0F / (float) Math.PI));
					this.zephyr.setYBodyRot(this.zephyr.getYRot());
				}
			}
		}
	}

	/**
	 * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.GhastMoveControl}.
	 */
	protected static class ZephyrMoveControl extends MoveControl {
		private final Zephyr zephyr;
		private int floatDuration;

		public ZephyrMoveControl(Zephyr zephyr) {
			super(zephyr);
			this.zephyr = zephyr;
		}

		@Override
		public void tick() {
			if (this.operation == MoveControl.Operation.MOVE_TO) {
				if (this.floatDuration-- <= 0) {
					this.floatDuration += this.zephyr.getRandom().nextInt(5) + 2;
					Vec3 vec3d = new Vec3(this.wantedX - this.zephyr.getX(), this.wantedY - this.zephyr.getY(), this.wantedZ - this.zephyr.getZ());
					double d0 = vec3d.length();
					vec3d = vec3d.normalize();
					if (this.canReach(vec3d, Mth.ceil(d0))) {
						this.zephyr.setDeltaMovement(this.zephyr.getDeltaMovement().add(vec3d.scale(0.1)));
					} else {
						this.operation = MoveControl.Operation.WAIT;
					}
				}
			}
		}

		private boolean canReach(Vec3 pos, int distance) {
			AABB axisalignedbb = this.zephyr.getBoundingBox();
			for (int i = 1; i < distance; ++i) {
				axisalignedbb = axisalignedbb.move(pos);
				if (!this.zephyr.getLevel().noCollision(this.zephyr, axisalignedbb)) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.GhastShootFireballGoal}.
	 */
	protected static class ZephyrShootSnowballGoal extends Goal {
		private final Zephyr zephyr;
		public int chargeTime;

		public ZephyrShootSnowballGoal(Zephyr zephyr) {
			this.zephyr = zephyr;
		}

		@Override
		public boolean canUse() {
			return zephyr.getTarget() != null;
		}

		@Override
		public void start() {
			this.chargeTime = 0;
		}

		@Override
		public void stop() {
			this.zephyr.setCharging(false);
		}

		@Override
		public void tick() {
			LivingEntity livingEntity = this.zephyr.getTarget();
			if (livingEntity != null) {
				if (livingEntity.distanceToSqr(this.zephyr) < 1600.0 && this.zephyr.hasLineOfSight(livingEntity)) {
					Level level = this.zephyr.getLevel();
					++this.chargeTime;
					if (this.chargeTime == 10) {
						if (this.zephyr.getAmbientSound() != null) {
							this.zephyr.playSound(this.zephyr.getAmbientSound(), this.zephyr.getSoundVolume(), (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
						}
					} else if (this.chargeTime == 20) {
						Vec3 look = this.zephyr.getViewVector(1.0F);
						double accelX = livingEntity.getX() - (this.zephyr.getX() + look.x() * 4.0);
						double accelY = livingEntity.getY(0.5) - (0.5 + this.zephyr.getY(0.5));
						double accelZ = livingEntity.getZ() - (this.zephyr.getZ() + look.z() * 4.0);
						this.zephyr.playSound(AetherSoundEvents.ENTITY_ZEPHYR_SHOOT.get(), this.zephyr.getSoundVolume(), (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
						ZephyrSnowball snowball = new ZephyrSnowball(level, this.zephyr, accelX, accelY, accelZ);
						snowball.setPos(this.zephyr.getX() + look.x() * 4.0, this.zephyr.getY(0.5) + 0.5, this.zephyr.getZ() + look.z() * 4.0);
						level.addFreshEntity(snowball);
						this.chargeTime = -40;
					}
				} else if (this.chargeTime > 0) {
					this.chargeTime--;
				}
				this.zephyr.setCharging(true);
			}
		}
	}

	/**
	 * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.RandomFloatAroundGoal}.
	 */
	protected static class RandomFloatAroundGoal extends Goal {
		private final Zephyr zephyr;

		public RandomFloatAroundGoal(Zephyr zephyr) {
			this.zephyr = zephyr;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			MoveControl moveControl = this.zephyr.getMoveControl();
			if (!moveControl.hasWanted()) {
				return true;
			} else {
				double d0 = moveControl.getWantedX() - this.zephyr.getX();
				double d1 = moveControl.getWantedY() - this.zephyr.getY();
				double d2 = moveControl.getWantedZ() - this.zephyr.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0 || d3 > 3600.0;
			}
		}

		@Override
		public boolean canContinueToUse() {
			return false;
		}

		@Override
		public void start() {
			RandomSource random = this.zephyr.getRandom();
			double d0 = this.zephyr.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d1 = this.zephyr.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d2 = this.zephyr.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			this.zephyr.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
		}
	}
}
