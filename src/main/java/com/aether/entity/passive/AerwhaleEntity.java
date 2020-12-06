package com.aether.entity.passive;

import com.aether.api.AetherAPI;
import com.aether.entity.AetherEntityTypes;
import com.aether.util.AetherSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class AerwhaleEntity extends FlyingEntity implements IMob {
	public float motionYaw, motionPitch;
	
	public AerwhaleEntity(EntityType<? extends FlyingEntity> type, World worldIn) {
		super(type, worldIn);
		this.ignoreFrustumCheck = true;
		this.moveController = new AerwhaleEntity.MoveHelperController(this);
	}
	
	public AerwhaleEntity(World worldIn) {
		this(AetherEntityTypes.AERWHALE, worldIn);
		this.rotationYaw = 360.0F * this.rand.nextFloat();
		this.rotationPitch = 90.0F * this.rand.nextFloat() - 45.0F;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new AerwhaleEntity.RandomFlyGoal(this));
//		this.goalSelector.addGoal(7, new AerwhaleEntity.LookAroundGoal(this));
//		this.goalSelector.addGoal(1, new AerwhaleEntity.UnstuckGoal(this));
//		this.goalSelector.addGoal(5, new AerwhaleEntity.TravelCourseGoal(this));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return FlyingEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.0D)
				.createMutableAttribute(Attributes.MAX_HEALTH, 20.0D);
	}
	
	@Override
	public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
		BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getBoundingBox().minY), MathHelper.floor(this.getPosZ()));
		
		return this.rand.nextInt(65) == 0 && !worldIn.getCollisionShapes(this, this.getBoundingBox()).findAny().isPresent()
			&& !worldIn.containsAnyLiquid(this.getBoundingBox()) && worldIn.getLight(pos) > 8
			&& super.canSpawn(worldIn, spawnReasonIn);
	}
	
	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
	
	@Override
	public void tick() {
		super.tick();
		this.extinguish();
		
		if (this.getPosY() < -64) {
			this.remove();
		}
	}
	
	@Override
	public void travel(Vector3d positionIn) {
		List<Entity> passengers = this.getPassengers();
		if (!passengers.isEmpty()) {
			Entity entity = passengers.get(0);
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity)entity;
				
				this.motionYaw = this.prevRotationYaw = this.rotationYaw = player.rotationYaw;
				this.motionPitch = this.prevRotationPitch = this.rotationPitch = player.rotationPitch;
				
				this.motionYaw = this.rotationYawHead = player.rotationYawHead;
				
				positionIn = new Vector3d(player.moveStrafing, 0.0, (player.moveForward <= 0.0F)? player.moveForward * 0.25F : player.moveForward);
				
				if (AetherAPI.get(player).map(p -> p.isJumping()).orElse(false)) {
					this.setMotion(new Vector3d(0.0, 0.0, 0.0));
				} else {
					double d0 = Math.toRadians(player.rotationYaw - 90.0);
					double d1 = Math.toRadians(-player.rotationPitch);
					double d2 = Math.cos(d1);
					this.setMotion(
						0.98 * (this.getMotion().x + 0.05 * Math.cos(d0) * d2),
						0.98 * (this.getMotion().y + 0.02 * Math.sin(d1)),
						0.98 * (this.getMotion().z + 0.05 * Math.sin(d0) * d2)
					);
				}
				
				this.stepHeight = 1.0F;
				
				if (!this.world.isRemote) {
					this.jumpMovementFactor = this.getAIMoveSpeed() * 0.6F;
					super.travel(positionIn);
				}
				
				this.prevLimbSwingAmount = this.limbSwingAmount;
				double d0 = this.getPosX() - this.prevPosX;
				double d1 = this.getPosZ() - this.prevPosZ;
				float f4 = 4.0F * MathHelper.sqrt(d0*d0 + d1*d1);
				
				if (f4 > 1.0F) {
					f4 = 1.0F;
				}
				
				this.limbSwingAmount += 0.4F * (f4 - this.limbSwingAmount);
				this.limbSwing += this.limbSwingAmount;
			}
		} else {
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.travel(positionIn);
		}
	}
	
	@Override
	protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
		if (player.getUniqueID().getMostSignificantBits() == 220717875589366683L && player.getUniqueID().getLeastSignificantBits() == -7181826737698904209L) {
			player.startRiding(this);
			if (!this.world.isRemote) {
				TextComponent msg = new StringTextComponent("Serenity is the queen of W(h)ales!!");
				player.world.getPlayers().forEach(p -> p.sendMessage(msg, player.getUniqueID()));
			}
			return ActionResultType.func_233537_a_(this.world.isRemote);
		}
		return super.func_230254_b_(player, hand);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return AetherSoundEvents.ENTITY_AERWHALE_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return AetherSoundEvents.ENTITY_AERWHALE_DEATH;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return AetherSoundEvents.ENTITY_AERWHALE_DEATH;
	}
	
	@Override
	protected float getSoundVolume() {
		return 3.0F;
	}
	
	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return true;
	}

	/**
	 * Copied from {@link GhastEntity.RandomFlyGoal}
	 */
	static class MoveHelperController extends MovementController {
		private final AerwhaleEntity parentEntity;
		private int courseChangeCooldown;

		public MoveHelperController(AerwhaleEntity aerwhale) {
			super(aerwhale);
			this.parentEntity = aerwhale;
		}

		@Override
		public void tick() {
			if (this.action == MovementController.Action.MOVE_TO) {
				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
					Vector3d Vector3d = new Vector3d(this.posX - this.parentEntity.getPosX(), this.posY - this.parentEntity.getPosY(), this.posZ - this.parentEntity.getPosZ());
					double d0 = Vector3d.length();
					Vector3d = Vector3d.normalize();
					if (this.func_220673_a(Vector3d, MathHelper.ceil(d0))) {
						this.parentEntity.setMotion(this.parentEntity.getMotion().add(Vector3d.scale(0.1D)));
						double dx = this.posX - this.mob.getPosX();
						double dz = this.posZ - this.mob.getPosZ();
						double dy = this.posY - this.mob.getPosY();
						double d4 = dx * dx + dy * dy + dz * dz;
						if (d4 < 2.5000003E-7F) {
							this.mob.setMoveForward(0.0F);
							return;
						}

						this.parentEntity.prevRotationYaw = this.parentEntity.rotationYaw;
						this.parentEntity.rotationYaw = (float)(MathHelper.atan2(dz, dx) * (180F / (float)Math.PI)) - 90.0F;
						this.parentEntity.rotationPitch = -(float)(Math.atan(dy) * 73.0);
					}
					else {
						this.action = MovementController.Action.WAIT;
					}
				}
			}
		}

		private boolean func_220673_a(Vector3d p_220673_1_, int p_220673_2_) {
			AxisAlignedBB axisalignedbb = this.parentEntity.getBoundingBox();

			for (int i = 1; i < p_220673_2_; ++i) {
				axisalignedbb = axisalignedbb.offset(p_220673_1_);
				if (!this.parentEntity.world.hasNoCollisions(this.parentEntity, axisalignedbb)) {
					return false;
				}
			}

			return true;
		}
	}
	
	static class RandomFlyGoal extends Goal {
		private final AerwhaleEntity parentEntity;

		public RandomFlyGoal(AerwhaleEntity aerwhale) {
			this.parentEntity = aerwhale;
			this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
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
				return d3 < 1.0;
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
			// Move somewhere within a 16x16x16 box around the entity
			Random random = this.parentEntity.getRNG();
			float dx = (random.nextFloat() * 2.0F - 1.0F) * 32.0F;
			float dy = (random.nextFloat() * 2.0F - 1.0F) * 32.0F;
			float dz = (random.nextFloat() * 2.0F - 1.0F) * 32.0F;
			double x = this.parentEntity.getPosX() + dx;
			double y = this.parentEntity.getPosY() + dy;
			double z = this.parentEntity.getPosZ() + dz;
			this.parentEntity.getMoveHelper().setMoveTo(x, y, z, 0.5);
//			float pitch = (float)MathHelper.atan2(dx, -dz) * (180.0F / (float)Math.PI);
//			float yaw = (float)MathHelper.atan2(MathHelper.sqrt(dx*dx + dz*dz), dy) * (180.0F / (float)Math.PI);
//			this.parentEntity.rotationPitch = pitch;
//			this.parentEntity.rotationYawHead = 
//				this.parentEntity.renderYawOffset = 
//				this.parentEntity.rotationYaw = yaw;
//			if (!this.parentEntity.world.isRemote) { 
////				this.parentEntity.getLookController().setLookPosition(x, y, z, 360.0F, 360.0F);
//				float pitch = -(float)MathHelper.atan2(dy, MathHelper.sqrt(dx*dx + dz*dz)) * (180.0F / (float)Math.PI);
//				if (pitch == -0.0F) {
//					pitch = 0.0F;
//				}
//				float yaw = (float)Math.atan2(dz, dx) * (180.0F / (float)Math.PI) - 90.0F;
//				yaw = MathHelper.wrapDegrees(yaw);
//				this.parentEntity.rotationYaw = yaw;
//				this.parentEntity.rotationPitch = pitch;
//				this.parentEntity.setPositionAndRotationDirect(this.parentEntity.getPosX(), this.parentEntity.getPosY(), this.parentEntity.getPosZ(), yaw, pitch, 10, false);
//	//			this.parentEntity.motionPitch = pitch;
////				System.out.printf("Aerwhale world = %s\n", this.parentEntity.world);
//	//			this.parentEntity.motionYaw = yaw;
//				System.out.printf("Set aerwhale (pitch, yaw) to %+7.2f, %+7.2f  [(x, z): (%+6.2f, %+6.2f) --> (%+6.2f, %+6.2f) Δ (%+6.2f, %+6.2f)]\n", pitch, MathHelper.wrapDegrees(yaw), this.parentEntity.getPosX(), this.parentEntity.getPosZ(), x, z, dx, dz);
//			}
		}
	}
	
	/*
	public static class UnstuckGoal extends Goal {
		private final AerwhaleEntity aerwhale;
		private boolean stuckWarning = false;
		private long checkTime;
		private BlockPos checkPos;
		
		public UnstuckGoal(AerwhaleEntity aerwhale) {
			this.aerwhale = aerwhale;
			this.setMutexFlags(EnumSet.of(Flag.MOVE));
		}
		
		@Override
		public boolean shouldExecute() {
			return this.aerwhale.isAlive() && isColliding();
		}
		
		@Override
		public void tick() {
			System.out.println("UnstuckGoal tick");
			BlockPos pos = this.aerwhale.getPosition();
			BlockPos posUp = pos.up(3);
			BlockPos posDown = pos.down();
			BlockPos posNorth = pos.north(2).up();
			BlockPos posSouth = pos.south(2).up();
			BlockPos posEast = pos.east(2).up();
			BlockPos posWest = pos.west(2).up();
			
			double addMotionX = 0.0;
			double addMotionY = 0.0;
			double addMotionZ = 0.0;
			
			if (checkRegion(posUp.add(-1, 0, -1), posUp.add(1, 0, 1))) {
				addMotionY += 0.002;
			}
			if (checkRegion(posDown.add(-1, 0, -1), posDown.add(1, 0, 1))) {
				addMotionY -= 0.002;
			}
			if (checkRegion(posEast.add(0, -1, -1), posEast.add(0, 1, 1))) {
				addMotionX += 0.002;
				addMotionY += 0.002;
			}
			if (checkRegion(posWest.add(0, -1, -1), posWest.add(0, 1, 1))) {
				addMotionX -= 0.002;
				addMotionY += 0.002;
			}
			if (checkRegion(posSouth.add(-1, -1, 0), posSouth.add(1, 1, 0))) {
				addMotionZ += 0.002;
				addMotionY += 0.002;
			}
			if (checkRegion(posNorth.add(-1, -1, 0), posNorth.add(1, 1, 0))) {
				addMotionZ -= 0.002;
				addMotionY += 0.002;
			}
			this.aerwhale.setMotion(this.aerwhale.getMotion().add(addMotionX, addMotionY, addMotionZ));
		}
		
		public boolean checkRegion(BlockPos pos1, BlockPos pos2) {
			boolean allAir = BlockPos.getAllInBox(pos1, pos2).allMatch(pos -> this.aerwhale.world.getBlockState(pos).isAir(this.aerwhale.world, pos));
			return !allAir;
		}
		
		@Override
		public void resetTask() {
			this.stuckWarning = false;
			this.checkPos = null;
		}
		
		public boolean isColliding() {
			long curtime = System.currentTimeMillis();
			if (curtime <= checkTime + 1000L) {
				return false;
			}
			
			if (this.checkPos != null) {
			
				double distanceTravelledSquared = this.aerwhale.getPosition().distanceSq(this.checkPos);
				
				if (distanceTravelledSquared < 9) {
					if (!stuckWarning) {
						stuckWarning = true;
					} else {
						return true;
					}
				}
			
			}
			
			this.checkPos = this.aerwhale.getPosition();
			this.checkTime = curtime;
			
			return false;
		}
	}
	
	public static class TravelCourseGoal extends Goal {
		private final AerwhaleEntity aerwhale;
		private double motionYaw, motionPitch;
		private double originDir, westDir, eastDir, upDir, downDir;
		
		public TravelCourseGoal(AerwhaleEntity aerwhale) {
			this.aerwhale = aerwhale;
			this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}
		
		@Override
		public boolean shouldExecute() {
			return this.aerwhale.isAlive();
		}
		
		@Override
		public void tick() {
			System.out.println("TravelCourseGoal tick");
			if (this.aerwhale.isBeingRidden()) {
				return;
			}
			
			this.originDir = this.checkForTravelableCourse(0, 0);
			this.westDir = this.checkForTravelableCourse(45, 0);
			this.upDir = this.checkForTravelableCourse(0, 45);
			this.eastDir = this.checkForTravelableCourse(-45, 0);
			this.downDir = this.checkForTravelableCourse(0, -45);

			int course = this.getCorrectCourse();

			if (course == 0) {
				if (this.originDir == 50) {
					this.motionYaw *= 0.9F;
					this.motionPitch *= 0.9F;

					if (this.aerwhale.getPosY() > 100) {
						this.motionPitch -= 2.0F;
					}
					if (this.aerwhale.getPosY() < 20) {
						this.motionPitch += 2.0F;
					}
				}
				else {
					this.aerwhale.rotationPitch = -this.aerwhale.rotationPitch;
					this.aerwhale.rotationYaw = -this.aerwhale.rotationYaw;
				}
			}
			else if (course == 1) {
				this.motionYaw += 5.0F;
			}
			else if (course == 2) {
				this.motionPitch -= 5.0F;
			}
			else if (course == 3) {
				this.motionYaw -= 5.0F;
			}
			else {
				this.motionPitch += 5.0F;
			}

			this.motionYaw += 2.0F * this.aerwhale.getRNG().nextFloat() - 1.0F;
			this.motionPitch += 2.0F * this.aerwhale.getRNG().nextFloat() - 1.0F;

			this.aerwhale.rotationPitch += 0.1F * this.motionPitch;
			this.aerwhale.rotationYaw += 0.1F * this.motionYaw;

			this.aerwhale.rotationPitch += 0.1F * this.motionPitch;
			this.aerwhale.rotationYaw += 0.1F * this.motionYaw;

			if (this.aerwhale.rotationPitch < -60) {
				this.aerwhale.rotationPitch = -60;
			}

			if (this.aerwhale.rotationPitch < -60) {
				this.aerwhale.rotationPitch = -60;
			}

			if (this.aerwhale.rotationPitch > 60) {
				this.aerwhale.rotationPitch = 60;
			}

			if (this.aerwhale.rotationPitch > 60) {
				this.aerwhale.rotationPitch = 60;
			}

			this.aerwhale.rotationPitch *= 0.99D;

			double d0 = Math.toRadians(this.aerwhale.rotationYaw);
			double d1 = Math.toRadians(this.aerwhale.rotationPitch);
			double d2 = Math.cos(d1);

			this.aerwhale.setMotion(this.aerwhale.getMotion().add(0.005 * Math.cos(d0) * d2, 0.005 * Math.sin(d1), 0.005 * Math.sin(d0) * d2).scale(0.98));

			if (this.aerwhale.getMotion().x > 0 && !this.aerwhale.world.isAirBlock(this.aerwhale.getPosition().east())) {
				this.aerwhale.setMotion(-this.aerwhale.getMotion().x, this.aerwhale.getMotion().y, this.aerwhale.getMotion().z);
				this.motionYaw -= 10F;
			}
			else if (this.aerwhale.getMotion().x < 0 && !this.aerwhale.world.isAirBlock(this.aerwhale.getPosition().west())) {
				this.aerwhale.setMotion(-this.aerwhale.getMotion().x, this.aerwhale.getMotion().y, this.aerwhale.getMotion().z);
				this.motionYaw += 10F;
			}
			else if (this.aerwhale.getMotion().y > 0 && !this.aerwhale.world.isAirBlock(this.aerwhale.getPosition().up())) {
				this.aerwhale.setMotion(this.aerwhale.getMotion().x, -this.aerwhale.getMotion().y, this.aerwhale.getMotion().z);
				this.motionPitch -= 10F;
			}
			else if (this.aerwhale.getMotion().y < 0 && !this.aerwhale.world.isAirBlock(this.aerwhale.getPosition().down())) {
				this.aerwhale.setMotion(this.aerwhale.getMotion().x, -this.aerwhale.getMotion().y, this.aerwhale.getMotion().z);
				this.motionPitch += 10F;
			}

			if (this.aerwhale.getMotion().z > 0D && !this.aerwhale.world.isAirBlock(this.aerwhale.getPosition().south())) {
				this.aerwhale.setMotion(this.aerwhale.getMotion().x, this.aerwhale.getMotion().y, -this.aerwhale.getMotion().z);
				this.motionYaw -= 10F;
			}
			else if (this.aerwhale.getMotion().z < 0 && !this.aerwhale.world.isAirBlock(this.aerwhale.getPosition().north())) {
				this.aerwhale.setMotion(this.aerwhale.getMotion().x, this.aerwhale.getMotion().y, -this.aerwhale.getMotion().z);
				this.motionYaw += 10F;
			}

			this.aerwhale.move(MoverType.SELF, this.aerwhale.getMotion());
		}
		
		private double checkForTravelableCourse(float rotationYawOffset, float rotationPitchOffset) {
			double standard = 50D;

			float yaw = this.aerwhale.rotationYaw + rotationYawOffset;
			float pitch = this.aerwhale.rotationPitch + rotationPitchOffset;

			float f3 = MathHelper.cos(-yaw * 0.01745329F - (float)Math.PI);
			float f4 = MathHelper.sin(-yaw * 0.01745329F - (float)Math.PI);
			float f5 = MathHelper.cos(-pitch * 0.01745329F);
			float f6 = MathHelper.sin(-pitch * 0.01745329F);

			float f7 = f4 * f5;
			float f8 = f6;
			float f9 = f3 * f5;

			Vector3d Vector3d = new Vector3d(this.aerwhale.getPosX(), this.aerwhale.getBoundingBox().minY, this.aerwhale.getPosZ());
			Vector3d Vector3d1 = Vector3d.add(f7 * standard, f8 * standard, f9 * standard);

			RayTraceResult movingobjectposition = this.aerwhale.world.rayTraceBlocks(new RayTraceContext(Vector3d, Vector3d1, BlockMode.COLLIDER, FluidMode.NONE, this.aerwhale));

			if (movingobjectposition == null) {
				return standard;
			}

			if (movingobjectposition.getType() == RayTraceResult.Type.BLOCK) {
				double i = movingobjectposition.getHitVec().getX() - this.aerwhale.getPosX();
				double j = movingobjectposition.getHitVec().getY() - this.aerwhale.getBoundingBox().minY;
				double k = movingobjectposition.getHitVec().getZ() - this.aerwhale.getPosZ();
				return Math.sqrt(i * i + j * j + k * k);
			}

			return standard;
		}
		
		private int getCorrectCourse() {
			double[] distances = new double[] { originDir, westDir, upDir, eastDir, downDir };

			int correctCourse = 0;

			for (int i = 1; i < 5; i++) {
				if (distances[i] > distances[correctCourse]) {
					correctCourse = i;
				}
			}

			return correctCourse;
		}
	}
	/**/
}
