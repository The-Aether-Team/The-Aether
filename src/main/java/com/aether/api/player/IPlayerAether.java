package com.aether.api.player;

import java.util.ArrayList;

import com.aether.api.player.util.IAccessoryInventory;
import com.aether.api.player.util.IAetherAbility;
import com.aether.api.player.util.IAetherBoss;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface IPlayerAether {
	
	void onUpdate();
	
	void setInPortal();
	
	void saveNBTData(CompoundNBT compound);
	
	void loadNBTData(CompoundNBT compound);
	
	void setFocusedBoss(IAetherBoss boss);
	
	IAetherBoss getFocusedBoss();
	
	void setAccessoryInventory(IAccessoryInventory inventory);
	
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
	
}
