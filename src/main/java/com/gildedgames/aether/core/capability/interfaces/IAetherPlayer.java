package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IAetherPlayer extends INBTSerializable<CompoundNBT>
{
	PlayerEntity getPlayer();

	static LazyOptional<IAetherPlayer> get(PlayerEntity player) {
		return player.getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY);
	}

	void copyFrom(IAetherPlayer other);
	void copyHealth(IAetherPlayer other, boolean wasDeath);

	void sync();

	void onUpdate();

	void givePortalItem();
	void setCanGetPortal(boolean canGetPortal);
	boolean canGetPortal();

	void setInPortal(boolean inPortal);
	boolean isInPortal();

	void addPortalTime(int time);
	void setPortalTimer(int timer);
	int getPortalTimer();

	float getPortalAnimTime();
	float getPrevPortalAnimTime();

	void setJumping(boolean isJumping);
	boolean isJumping();

	void setJumpBoost(boolean jumpBoost);
	boolean hasJumpBoost();

	void setRemedyMaximum(int max);
	int getRemedyMaximum();
	void setRemedyTimer(int timer);
	int getRemedyTimer();

	void setProjectileImpactedMaximum(int max);
	int getProjectileImpactedMaximum();
	void setProjectileImpactedTimer(int timer);
	int getProjectileImpactedTimer();

	void addToLifeShardCount(int amountToAdd);
	void setLifeShardCount(int amount);
	int getLifeShardLimit();
	int getLifeShardCount();
}
