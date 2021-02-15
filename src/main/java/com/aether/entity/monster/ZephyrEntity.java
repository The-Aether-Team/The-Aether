package com.aether.entity.monster;

import com.aether.registry.AetherEntityTypes;
import com.aether.entity.projectile.ZephyrSnowballEntity;
import com.aether.registry.AetherSoundEvents;
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
	public static final DataParameter<Integer> ATTACK_CHARGE = EntityDataManager.createKey(ZephyrEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Boolean> IS_ATTACKING = EntityDataManager.createKey(ZephyrEntity.class, DataSerializers.BOOLEAN);

	public ZephyrEntity(EntityType<? extends FlyingEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveController = new ZephyrEntity.MoveHelperController(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new ZephyrEntity.RandomFlyGoal(this));
		this.goalSelector.addGoal(7, new ZephyrEntity.LookAroundGoal(this));
		this.goalSelector.addGoal(7, new ZephyrEntity.SnowballAttackGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, false));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return FlyingEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 5.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 100.0D);
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ATTACK_CHARGE, 0);
		this.dataManager.register(IS_ATTACKING, false);
	}

	public int getAttackCharge() {
		return this.dataManager.get(ATTACK_CHARGE);
	}
	
	public boolean isAttacking() {
		return this.dataManager.get(IS_ATTACKING);
	}

	/**
	 * Sets the value of the attack charge for the purposes of rendering on the
	 * client. This only sets the value if it's above 0 because that's when the
	 * zephyr begins to wind up for an attack.
	 */
	public void setAttackCharge(int attackTimer) {
		if (attackTimer > 0) {
			this.dataManager.set(ATTACK_CHARGE, attackTimer);
			this.dataManager.set(IS_ATTACKING, true);
		}
		else {
			this.dataManager.set(ATTACK_CHARGE, 0);
			this.dataManager.set(IS_ATTACKING, false);
		}
	}

	@Override
	protected boolean isDespawnPeaceful() {
		return true;
	}

	@Override
	public void livingTick() {
		super.livingTick();
		if (this.getPosY() < -2 || this.getPosY() > 255) {
			this.remove();
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
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
			&& worldIn.getEntitiesWithinAABB(ZephyrEntity.class, boundingBox).size() == 0
			&& !worldIn.containsAnyLiquid(boundingBox) && worldIn.getLight(pos) > 8
			&& canSpawnOn(zephyr, worldIn, reason, pos, random);
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
		public boolean shouldExecute() {
			return parentEntity.getAttackTarget() != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			this.attackTimer = 0;
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted
		 * by another one
		 */
		@Override
		public void resetTask() {
			this.parentEntity.setAttackCharge(0);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			LivingEntity target = parentEntity.getAttackTarget();
			if (target.getDistanceSq(this.parentEntity) < 64*64 && this.parentEntity.canEntityBeSeen(target)) {
				World world = this.parentEntity.world;
				++this.attackTimer;
				if (this.attackTimer == 10) {
					this.parentEntity.playSound(this.parentEntity.getAmbientSound(), 3.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
				}
				else if (this.attackTimer == 20) {
					Vector3d look = this.parentEntity.getLook(1.0F);
					double accelX = target.getPosX() - (this.parentEntity.getPosX() + look.x * 4.0);
					double accelY = target.getPosYHeight(0.5)  - (0.5 + this.parentEntity.getPosYHeight(0.5));
					double accelZ = target.getPosZ() - (this.parentEntity.getPosZ() + look.z * 4.0);
					this.parentEntity.playSound(AetherSoundEvents.ENTITY_ZEPHYR_SHOOT.get(), 3.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
					ZephyrSnowballEntity snowballentity = new ZephyrSnowballEntity(AetherEntityTypes.ZEPHYR_SNOWBALL.get(), world).construct(world, this.parentEntity, accelX, accelY, accelZ);
					snowballentity.setPosition(this.parentEntity.getPosX() + look.x * 4.0, this.parentEntity.getPosYHeight(0.5) + 0.5, this.parentEntity.getPosZ() + look.z * 4.0);
					world.addEntity(snowballentity);
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
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean shouldExecute() {
			MovementController movementcontroller = this.parentEntity.getMoveHelper();
			if (!movementcontroller.isUpdating()) {
				return true;
			}
			else {
				double d0 = movementcontroller.getX() - this.parentEntity.getPosX();
				double d1 = movementcontroller.getY() - this.parentEntity.getPosY();
				double d2 = movementcontroller.getZ() - this.parentEntity.getPosZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0 || d3 > 3600.0;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean shouldContinueExecuting() {
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			Random random = this.parentEntity.getRNG();
			double d0 = this.parentEntity.getPosX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d1 = this.parentEntity.getPosY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d2 = this.parentEntity.getPosZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0);
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
			if (this.action == MovementController.Action.MOVE_TO) {
				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
					Vector3d vec3d = new Vector3d(this.posX - this.parentEntity.getPosX(), this.posY - this.parentEntity.getPosY(), this.posZ - this.parentEntity.getPosZ());
					double d0 = vec3d.length();
					vec3d = vec3d.normalize();
					if (this.isNotColliding(vec3d, MathHelper.ceil(d0))) {
						this.parentEntity.setMotion(this.parentEntity.getMotion().add(vec3d.scale(0.1)));
					}
					else {
						this.action = MovementController.Action.WAIT;
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
				axisalignedbb = axisalignedbb.offset(pos);
				if (!this.parentEntity.world.hasNoCollisions(this.parentEntity, axisalignedbb)) {
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
			this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean shouldExecute() {
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			if (this.parentEntity.getAttackTarget() == null) {
				Vector3d vec3d = this.parentEntity.getMotion();
				this.parentEntity.rotationYaw = -((float)MathHelper.atan2(vec3d.x, vec3d.z)) * (180.0F / (float)Math.PI);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			}
			else {
				LivingEntity livingentity = this.parentEntity.getAttackTarget();
				if (livingentity.getDistanceSq(this.parentEntity) < 64*64) {
					double x = livingentity.getPosX() - this.parentEntity.getPosX();
					double z = livingentity.getPosZ() - this.parentEntity.getPosZ();
					this.parentEntity.rotationYaw = -((float)MathHelper.atan2(x, z)) * (180.0F / (float)Math.PI);
					this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
				}
			}

		}
	}

}
