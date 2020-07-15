package com.aether.entity.passive;

import com.aether.entity.MountableEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public abstract class SaddleableEntity extends MountableEntity {
	public static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(SaddleableEntity.class, DataSerializers.BOOLEAN);
	
	protected SaddleableEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
	}
	
	public boolean getSaddled() {
		return this.dataManager.get(SADDLED);
	}
	
	public void setSaddled(boolean isSaddled) {
		this.dataManager.set(SADDLED, isSaddled);
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
		
		if (!this.world.isRemote && this.getSaddled()) {
			this.entityDropItem(Items.SADDLE);
		}
	}
	
	public boolean canBeSaddled() {
		return true;
	}
	
	@Override
	public boolean processInteract(PlayerEntity player, Hand hand) {		
		if (!this.canBeSaddled()) {
			return super.processInteract(player, hand);
		}
		
		ItemStack heldItem = player.getHeldItem(hand);
		if (!this.getSaddled()) {
			if (heldItem.getItem() == Items.SADDLE && !this.isChild()) {
				if (!player.isCreative()) {
					heldItem.shrink(1);
				}
				
				if (player.world.isRemote) {
					player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.AMBIENT, 1.0F, 1.0F);
				}
				
				this.setSaddled(true);
				
				return true;
			}
		}
		else {
			if (!this.isBeingRidden() && this.getRidingEntity() != null) {
				if (!player.world.isRemote) {
					player.startRiding(this);
					player.prevRotationYaw = player.rotationYaw = this.rotationYaw;
				}
				
				return true;
			}
		}
		
		return super.processInteract(player, hand);
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
		compound.putBoolean("Saddled", this.getSaddled());
	}
	
	@Override
	public boolean shouldRiderFaceForward(PlayerEntity player) {
		return false;
	}
	
}
