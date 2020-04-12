package com.aether.player;

import java.util.ArrayList;

import com.aether.inventory.IAccessoryInventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAetherPlayer extends INBTSerializable<CompoundNBT> {
	
	PlayerEntity getPlayerEntity();
	
	void onUpdate();
	
	void setInPortal();
	
	void setFocusedBoss(IAetherBoss boss);
	
	IAetherBoss getFocusedBoss();
	
//	void setAccessoryInventory(IAccessoryInventory inventory);
	
	IAccessoryInventory getAccessoryInventory();
	
	ArrayList<IAetherAbility> getAbilities();
	
	PlayerEntity getEntity();
	
	void inflictPoison(int ticks);
	
	boolean isPoisoned();
	
	void inflictCure(int ticks);
	
	boolean isCured();
	
	boolean setHammerCooldown(int cooldown, String hammerName);
	
	String getNammerName();
	
	int getHammerCooldown();
	
	int getHammerMaxCooldown();
	
	void setJumping(boolean isJumping);
	
	boolean isJumping();
	
	void updateShardCount(int amount);
	
	int getShardsUsed();
	
	@OnlyIn(Dist.CLIENT)
	float getWingSinage();
	
	@OnlyIn(Dist.CLIENT)
	float getPreviousPortalAnimationTime();
	
	@OnlyIn(Dist.CLIENT)
	float getPortalAnimationTime();
	
}
