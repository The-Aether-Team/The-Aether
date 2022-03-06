package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.common.entity.miscellaneous.CloudMinion;
import com.gildedgames.aether.common.entity.passive.Aerbunny;
import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public interface IAetherPlayer extends INBTSerializable<CompoundTag> {
	Player getPlayer();

	static LazyOptional<IAetherPlayer> get(Player player) {
		return player.getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY);
	}

	void defineSynchedData();

	void onLogout();
	void onLogin();

	void copyFrom(IAetherPlayer other, boolean isWasDeath);

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

	void setHitting(boolean isHitting);
	boolean isHitting();

	void setMoving(boolean isMoving);
	boolean isMoving();

	void setJumping(boolean isJumping);
	boolean isJumping();

	void setGoldenDartCount(int count);
	int getGoldenDartCount();
	void setPoisonDartCount(int count);
	int getPoisonDartCount();
	void setEnchantedDartCount(int count);
	int getEnchantedDartCount();

	void setRemedyMaximum(int max);
	int getRemedyMaximum();
	void setRemedyTimer(int timer);
	int getRemedyTimer();

	void setProjectileImpactedMaximum(int max);
	int getProjectileImpactedMaximum();
	void setProjectileImpactedTimer(int timer);
	int getProjectileImpactedTimer();

	void setMountedAerbunny(Aerbunny mountedAerbunny);
	Aerbunny getMountedAerbunny();
	void setMountedAerbunnyTag(CompoundTag mountedAerbunny);
	CompoundTag getMountedAerbunnyTag();

	void setCloudMinions(CloudMinion cloudMinionRight, CloudMinion cloudMinionLeft);
	List<CloudMinion> getCloudMinions();

	void setSavedHealth(float health);
	float getSavedHealth();

	void addToLifeShardCount(int amountToAdd);
	void setLifeShardCount(int amount);
	int getLifeShardCount();
	int getLifeShardLimit();
	AttributeModifier getLifeShardHealthAttributeModifier();
}
