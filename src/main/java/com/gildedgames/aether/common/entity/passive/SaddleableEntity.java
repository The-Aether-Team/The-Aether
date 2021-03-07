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
	public static final DataParameter<Boolean> SADDLE = EntityDataManager.createKey(SaddleableEntity.class, DataSerializers.BOOLEAN);
	
	protected SaddleableEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerData() {
		super.registerData();

		this.dataManager.register(SADDLE, false);
	}
	
	public boolean isSaddled() {
		return this.dataManager.get(SADDLE);
	}
	
	public void setSaddled(boolean isSaddled) {
		this.dataManager.set(SADDLE, isSaddled);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() instanceof PlayerEntity && !this.getPassengers().isEmpty() && this.getPassengers().get(0) == source.getImmediateSource()) {
			return false;
		}
		
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public boolean canBeSteered() {
		return true;
	}
	
	@Override
	protected boolean canTriggerWalking() {
		return this.onGround;
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		
		if (!this.world.isRemote && this.isSaddled()) {
			this.entityDropItem(Items.SADDLE);
		}
	}
	
	public boolean canBeSaddled() {
		return true;
	}
	
	@Override
	public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) { //idk what to do here
		LogManager.getLogger(this.getClass()).debug("SaddleableEntity processInteract");
		if (!this.canBeSaddled()) {
			LogManager.getLogger(this.getClass()).debug("Couldn't be saddled");
			return super.func_230254_b_(player, hand);
		}
		
		ItemStack heldItem = player.getHeldItem(hand);
		if (!this.isSaddled()) {
			if (heldItem.getItem() == Items.SADDLE && !this.isChild()) {
				if (!player.isCreative()) {
					heldItem.shrink(1);
				}
				
				if (player.world.isRemote) {
					player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.AMBIENT, 1.0F, 1.0F);
				}
				
				this.setSaddled(true);
				
				return super.func_230254_b_(player, hand);
			}
		}
		else {
			LogManager.getLogger(this.getClass()).debug("Not Saddled");
			if (!this.isBeingRidden() && this.getRidingEntity() == null) {
				LogManager.getLogger(this.getClass()).debug("Try riding entity");
				if (!player.world.isRemote) {
					player.startRiding(this);
					player.prevRotationYaw = player.rotationYaw = this.rotationYaw;
				}
				
				return super.func_230254_b_(player, hand);
			}
		}
		
		return super.func_230254_b_(player, hand);
	}
	
	@Override
	public boolean isEntityInsideOpaqueBlock() {
		if (!this.getPassengers().isEmpty()) {
			return false;
		}
		return super.isEntityInsideOpaqueBlock();
	}
	
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setSaddled(compound.getBoolean("Saddled"));
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
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
	public void setJumpPower(int jumpPowerIn) {
		if (this.isSaddled()) {
			super.setJumpPower(jumpPowerIn);
		}
	}
	
}
