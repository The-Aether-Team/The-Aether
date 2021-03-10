package com.gildedgames.aether.common.entity.monster;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.entity.projectile.ZephyrSnowballEntity;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class ZephyrEntity extends FlyingEntity implements IMob {
	public static final DataParameter<Integer> ATTACK_CHARGE = EntityDataManager.defineId(ZephyrEntity.class, DataSerializers.INT);
	public static final DataParameter<Boolean> IS_ATTACKING = EntityDataManager.defineId(ZephyrEntity.class, DataSerializers.BOOLEAN);

	public ZephyrEntity(EntityType<? extends ZephyrEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveControl = new ZephyrEntity.MoveHelperController(this);
	}

	public ZephyrEntity(World worldIn) {
		this(AetherEntityTypes.ZEPHYR.get(), worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new ZephyrEntity.RandomFlyGoal(this));
		this.goalSelector.addGoal(7, new ZephyrEntity.LookAroundGoal(this));
		this.goalSelector.addGoal(7, new ZephyrEntity.SnowballAttackGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, false));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return FlyingEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 5.0D)
				.add(Attributes.FOLLOW_RANGE, 100.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACK_CHARGE, 0);
		this.entityData.define(IS_ATTACKING, false);
	}

	public int getAttackCharge() {
		return this.entityData.get(ATTACK_CHARGE);
	}
	
	public boolean isAttacking() {
		return this.entityData.get(IS_ATTACKING);
	}

	/**
	 * Sets the value of the attack charge for the purposes of rendering on the
	 * client. This only sets the value if it's above 0 because that's when the
	 * zephyr begins to wind up for an attack.
	 */
	public void setAttackCharge(int attackTimer) {
		if (attackTimer > 0) {
			this.entityData.set(ATTACK_CHARGE, attackTimer);
			this.entityData.set(IS_ATTACKING, true);
		}
		else {
			this.entityData.set(ATTACK_CHARGE, 0);
			this.entityData.set(IS_ATTACKING, false);
		}
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getY() < -2 || this.getY() > 255) {
			this.remove();
		}
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return true;
	}

	@Override
	protected float getSoundVolume() {
		return 3.0F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return AetherSoundEvents.ENTITY_ZEPHYR_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AetherSoundEvents.ENTITY_ZEPHYR_AMBIENT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_ZEPHYR_AMBIENT.get();
	}

	public static boolean canZephyrSpawn(EntityType<? extends ZephyrEntity> zephyr, IWorld worldIn, SpawnReason reason,
		BlockPos pos, Random random) {
		AxisAlignedBB boundingBox = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 4, pos.getY() + 4, pos.getZ() + 4);
		return worldIn.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(65) == 0 //TODO: change the bounds of nextInt to a config value.
			&& worldIn.getEntitiesOfClass(ZephyrEntity.class, boundingBox).size() == 0
			&& !worldIn.containsAnyLiquid(boundingBox) && worldIn.getMaxLocalRawBrightness(pos) > 8
			&& checkMobSpawnRules(zephyr, worldIn, reason, pos, random);
	}

	/**
	 * Copy of {@link GhastEntity.FireballAttackGoal} but changed GhastEntity to ZephyrEntity
	 */
	static class SnowballAttackGoal extends Goal {
		private final ZephyrEntity parentEntity;
		public int attackTimer;

		public SnowballAttackGoal(ZephyrEntity zephyr) {
			this.parentEntity = zephyr;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache
		 * any state necessary for execution in this method as well.
		 */
		@Override
		public boolean canUse() {
			return parentEntity.getTarget() != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			this.attackTimer = 0;
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted
		 * by another one
		 */
		@Override
		public void stop() {
			this.parentEntity.setAttackCharge(0);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			LivingEntity target = parentEntity.getTarget();
			if (target.distanceToSqr(this.parentEntity) < 64*64 && this.parentEntity.canSee(target)) {
				World world = this.parentEntity.level;
				++this.attackTimer;
				if (this.attackTimer == 10) {
					this.parentEntity.playSound(this.parentEntity.getAmbientSound(), 3.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);
				}
				else if (this.attackTimer == 20) {
					Vector3d look = this.parentEntity.getViewVector(1.0F);
					double accelX = target.getX() - (this.parentEntity.getX() + look.x * 4.0);
					double accelY = target.getY(0.5)  - (0.5 + this.parentEntity.getY(0.5));
					double accelZ = target.getZ() - (this.parentEntity.getZ() + look.z * 4.0);
					this.parentEntity.playSound(AetherSoundEvents.ENTITY_ZEPHYR_SHOOT.get(), 3.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);
					ZephyrSnowballEntity snowballentity = new ZephyrSnowballEntity(world, this.parentEntity, accelX, accelY, accelZ);
					snowballentity.setPos(this.parentEntity.getX() + look.x * 4.0, this.parentEntity.getY(0.5) + 0.5, this.parentEntity.getZ() + look.z * 4.0);
					world.addFreshEntity(snowballentity);
					this.attackTimer = -40;
				}
			}
			else if (this.attackTimer > 0) {
				this.attackTimer--;
			}
			
			this.parentEntity.setAttackCharge(attackTimer);
		}
	}

	/**
	 * Copy of {@link GhastEntity.RandomFlyGoal} but changed GhastEntity to ZephyrEntity
	 */
	static class RandomFlyGoal extends Goal {
		private final ZephyrEntity parentEntity;

		public RandomFlyGoal(ZephyrEntity entity) {
			this.parentEntity = entity;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			MovementController movementcontroller = this.parentEntity.getMoveControl();
			if (!movementcontroller.hasWanted()) {
				return true;
			}
			else {
				double d0 = movementcontroller.getWantedX() - this.parentEntity.getX();
				double d1 = movementcontroller.getWantedY() - this.parentEntity.getY();
				double d2 = movementcontroller.getWantedZ() - this.parentEntity.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0 || d3 > 3600.0;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean canContinueToUse() {
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			Random random = this.parentEntity.getRandom();
			double d0 = this.parentEntity.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d1 = this.parentEntity.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d2 = this.parentEntity.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
		}
	}

	/**
	 * Copy of {@link GhastEntity.MoveHelperController} but changed GhastEntity to ZephyrEntity
	 */
	static class MoveHelperController extends MovementController {
		private final ZephyrEntity parentEntity;
		private int courseChangeCooldown;

		public MoveHelperController(ZephyrEntity zephyr) {
			super(zephyr);
			this.parentEntity = zephyr;
		}

		@Override
		public void tick() {
			if (this.operation == MovementController.Action.MOVE_TO) {
				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRandom().nextInt(5) + 2;
					Vector3d vec3d = new Vector3d(this.wantedX - this.parentEntity.getX(), this.wantedY - this.parentEntity.getY(), this.wantedZ - this.parentEntity.getZ());
					double d0 = vec3d.length();
					vec3d = vec3d.normalize();
					if (this.isNotColliding(vec3d, MathHelper.ceil(d0))) {
						this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vec3d.scale(0.1)));
					}
					else {
						this.operation = MovementController.Action.WAIT;
					}
				}

			}
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(Vector3d pos, int distance) {
			AxisAlignedBB axisalignedbb = this.parentEntity.getBoundingBox();

			for (int i = 1; i < distance; ++i) {
				axisalignedbb = axisalignedbb.move(pos);
				if (!this.parentEntity.level.noCollision(this.parentEntity, axisalignedbb)) {
					return false;
				}
			}

			return true;
		}

	}

	/**
	 * Copy of {@link GhastEntity.LookAroundGoal} but changed GhastEntity to ZephyrEntity
	 */
	static class LookAroundGoal extends Goal {
		private final ZephyrEntity parentEntity;

		public LookAroundGoal(ZephyrEntity zephyr) {
			this.parentEntity = zephyr;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			if (this.parentEntity.getTarget() == null) {
				Vector3d vec3d = this.parentEntity.getDeltaMovement();
				this.parentEntity.yRot = -((float)MathHelper.atan2(vec3d.x, vec3d.z)) * (180.0F / (float)Math.PI);
				this.parentEntity.yBodyRot = this.parentEntity.yRot;
			}
			else {
				LivingEntity livingentity = this.parentEntity.getTarget();
				if (livingentity.distanceToSqr(this.parentEntity) < 64*64) {
					double x = livingentity.getX() - this.parentEntity.getX();
					double z = livingentity.getZ() - this.parentEntity.getZ();
					this.parentEntity.yRot = -((float)MathHelper.atan2(x, z)) * (180.0F / (float)Math.PI);
					this.parentEntity.yBodyRot = this.parentEntity.yRot;
				}
			}

		}
	}

}
