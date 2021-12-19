package com.gildedgames.aether.common.entity.passive;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class MountableEntity extends AetherAnimalEntity implements IRideable, IEquipable
{
	private static final DataParameter<Boolean> DATA_SADDLE_ID = EntityDataManager.defineId(MountableEntity.class, DataSerializers.BOOLEAN);
	protected boolean playerTriedToCrouch;
	protected boolean playerJumped;
	protected boolean mountJumping;

	protected MountableEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_SADDLE_ID, false);
	}

	@Override
	public void tick() {
		this.riderTick();
		super.tick();
	}

	public void riderTick() {
		if (this.getControllingPassenger() instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) this.getControllingPassenger();
			IAetherPlayer.get(playerEntity).ifPresent(aetherPlayer -> {
				if (aetherPlayer.isJumping() && !this.isMountJumping() && this.onGround) {
					this.playerJumped = true;
				}
			});
			if (playerEntity.level.isClientSide && playerEntity instanceof ClientPlayerEntity && !this.isOnGround()) {
				ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) playerEntity;
				this.playerTriedToCrouch = clientPlayerEntity.input.shiftKeyDown;
				clientPlayerEntity.input.shiftKeyDown = false;
			}
		}
	}

	@Override
	public void travel(Vector3d vector3d) {
		if (this.isAlive()) {
			if (this.isVehicle() && this.canBeControlledByRider() && this.getControllingPassenger() instanceof PlayerEntity) {
				boolean slime = this.isSlime();
				PlayerEntity entity = (PlayerEntity) this.getControllingPassenger();
				this.yRot = entity.yRot;
				this.yRotO = this.yRot;
				this.xRot = entity.xRot * 0.5F;
				this.setRot(this.yRot, this.xRot);
				this.yBodyRot = this.yRot;
				this.yHeadRot = this.yBodyRot;
				float f = entity.xxa * 0.5F;
				float f1 = entity.zza;
				if (f1 <= 0.0F) {
					f1 *= 0.25F;
				}
				if(this.onGround) {
					if (this.playerJumped && !this.isMountJumping()) {
						double jumpStrength = slime ? this.getMountJumpStrength() : this.getMountJumpStrength() * (double) this.getBlockJumpFactor();
						this.setDeltaMovement(this.getDeltaMovement().x(), jumpStrength, this.getDeltaMovement().z());
						if (this.hasEffect(Effects.JUMP)) {
							this.push(0.0, 0.1 * (this.getEffect(Effects.JUMP).getAmplifier() + 1), 0.0);
						}
						this.setMountJumping(true);
						this.hasImpulse = true;
						net.minecraftforge.common.ForgeHooks.onLivingJump(this);
						this.playerJumped = false;
					} else if(slime && !this.playerJumped && (this.getDeltaMovement().x != 0 || this.getDeltaMovement().z != 0)) {
						this.setDeltaMovement(this.getDeltaMovement().x(), 0.42F, this.getDeltaMovement().z);
					}
				}
				this.maxUpStep = 1.0F;
				this.flyingSpeed = this.getSteeringSpeed() * 0.25F;
				if (this.isControlledByLocalInstance()) {
					float speed = this.getSteeringSpeed();
					this.setSpeed(speed);
					this.travelWithInput(new Vector3d(f, vector3d.y, f1));
					this.lerpSteps = 0;
				} else {
					this.calculateEntityAnimation(this, false);
					this.setDeltaMovement(Vector3d.ZERO);
				}
				if (this.onGround) {
					this.playerJumped = false;
					this.setMountJumping(false);
				}
			} else {
				this.maxUpStep = 0.5F;
				this.flyingSpeed = 0.02F;
				this.travelWithInput(vector3d);
			}
		}
	}

	@Override
	public void travelWithInput(Vector3d vector3d) {
		super.travel(vector3d);
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity playerEntity, Hand hand) {
		boolean flag = this.isFood(playerEntity.getItemInHand(hand));
		if (!flag && this.isSaddled() && !this.isVehicle() && !playerEntity.isSecondaryUseActive()) {
			if (!this.level.isClientSide) {
				playerEntity.startRiding(this);
			}
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else {
			ActionResultType actionresulttype = super.mobInteract(playerEntity, hand);
			if (!actionresulttype.consumesAction()) {
				ItemStack itemstack = playerEntity.getItemInHand(hand);
				return itemstack.getItem() == Items.SADDLE ? itemstack.interactLivingEntity(playerEntity, this, hand) : ActionResultType.PASS;
			} else {
				return actionresulttype;
			}
		}
	}

	@Override
	public Vector3d getDismountLocationForPassenger(LivingEntity livingEntity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() != Direction.Axis.Y) {
			int[][] aint = TransportationHelper.offsetsForDirection(direction);
			BlockPos blockpos = this.blockPosition();
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
			for (Pose pose : livingEntity.getDismountPoses()) {
				AxisAlignedBB axisalignedbb = livingEntity.getLocalBoundsForPose(pose);
				for (int[] aint1 : aint) {
					blockpos$mutable.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
					double d0 = this.level.getBlockFloorHeight(blockpos$mutable);
					if (TransportationHelper.isBlockFloorValid(d0)) {
						Vector3d vector3d = Vector3d.upFromBottomCenterOf(blockpos$mutable, d0);
						if (TransportationHelper.canDismountTo(this.level, livingEntity, axisalignedbb.move(vector3d))) {
							livingEntity.setPose(pose);
							return vector3d;
						}
					}
				}
			}
		}
		return super.getDismountLocationForPassenger(livingEntity);
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		if (this.isSaddled()) {
			this.spawnAtLocation(Items.SADDLE);
		}
	}

	@Nullable
	@Override
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	@Override
	public boolean canBeControlledByRider() {
		Entity entity = this.getControllingPassenger();
		return entity instanceof PlayerEntity && this.isSaddled();
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return true;
	}

	@Override
	public boolean rideableUnderWater() {
		return true;
	}

	@Override
	public void equipSaddle(@Nullable SoundCategory soundCategory) {
		this.setSaddled(true);
		if (soundCategory != null && this.getSaddledSound() != null) {
			this.level.playSound(null, this, this.getSaddledSound(), soundCategory, 0.5F, 1.0F);
		}
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby();
	}

	@Override
	public boolean isSaddled() {
		return this.entityData.get(DATA_SADDLE_ID);
	}

	public void setSaddled(boolean isSaddled) {
		this.entityData.set(DATA_SADDLE_ID, isSaddled);
	}

	public boolean canJump() {
		return this.isSaddled();
	}

	public boolean isMountJumping() {
		return this.mountJumping;
	}

	public void setMountJumping(boolean isMountJumping) {
		this.mountJumping = isMountJumping;
	}

	protected double getMountJumpStrength() {
		return 1.8D;
	}

	protected boolean isSlime() {
		return false;
	}

	@Override
	public float getSteeringSpeed() {
		return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.625F;
	}

	public boolean playerTriedToCrouch() {
		return this.playerTriedToCrouch;
	}

	@Override
	public boolean boost() {
		return false;
	}

	@Nullable
	protected SoundEvent getSaddledSound() {
		return null;
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.setSaddled(compound.getBoolean("Saddled"));
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Saddled", this.isSaddled());
	}
}
