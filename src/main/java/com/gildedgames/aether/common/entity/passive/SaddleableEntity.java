package com.gildedgames.aether.common.entity.passive;

import net.minecraft.util.*;
import org.apache.logging.log4j.LogManager;

import com.gildedgames.aether.common.entity.MountableEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public abstract class SaddleableEntity extends MountableEntity {
	public static final DataParameter<Boolean> SADDLE = EntityDataManager.defineId(SaddleableEntity.class, DataSerializers.BOOLEAN);
	
	protected SaddleableEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();

		this.entityData.define(SADDLE, false);
	}
	
	public boolean isSaddled() {
		return this.entityData.get(SADDLE);
	}
	
	public void setSaddled(boolean isSaddled) {
		this.entityData.set(SADDLE, isSaddled);
	}
	
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof PlayerEntity && !this.getPassengers().isEmpty() && this.getPassengers().get(0) == source.getDirectEntity()) {
			return false;
		}
		
		return super.hurt(source, amount);
	}
	
	@Override
	public boolean canBeControlledByRider() {
		return true;
	}
	
	@Override
	protected boolean isMovementNoisy() {
		return this.onGround;
	}
	
	@Override
	public void die(DamageSource cause) {
		super.die(cause);
		
		if (!this.level.isClientSide && this.isSaddled()) {
			this.spawnAtLocation(Items.SADDLE);
		}
	}
	
	public boolean canBeSaddled() {
		return true;
	}
	
	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) { //idk what to do here
		LogManager.getLogger(this.getClass()).debug("SaddleableEntity processInteract");
		if (!this.canBeSaddled()) {
			LogManager.getLogger(this.getClass()).debug("Couldn't be saddled");
			return super.mobInteract(player, hand);
		}
		
		ItemStack heldItem = player.getItemInHand(hand);
		if (!this.isSaddled()) {
			if (heldItem.getItem() == Items.SADDLE && !this.isBaby()) {
				if (!player.isCreative()) {
					heldItem.shrink(1);
				}
				
				if (player.level.isClientSide) {
					player.level.playSound(player, player.blockPosition(), SoundEvents.PIG_SADDLE, SoundCategory.AMBIENT, 1.0F, 1.0F);
				}
				
				this.setSaddled(true);
				
				return super.mobInteract(player, hand);
			}
		}
		else {
			LogManager.getLogger(this.getClass()).debug("Not Saddled");
			if (!this.isVehicle() && this.getVehicle() == null) {
				LogManager.getLogger(this.getClass()).debug("Try riding entity");
				if (!player.level.isClientSide) {
					player.startRiding(this);
					player.yRotO = player.yRot = this.yRot;
				}
				
				return super.mobInteract(player, hand);
			}
		}
		
		return super.mobInteract(player, hand);
	}
	
	@Override
	public boolean isInWall() {
		if (!this.getPassengers().isEmpty()) {
			return false;
		}
		return super.isInWall();
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
	
	@Override
	public boolean shouldRiderFaceForward(PlayerEntity player) {
		return false;
	}
	
	@Override
	public boolean canJump() {
		return this.isSaddled();
	}
	
	@Override
	public void onPlayerJump(int jumpPowerIn) {
		if (this.isSaddled()) {
			super.onPlayerJump(jumpPowerIn);
		}
	}
	
}
