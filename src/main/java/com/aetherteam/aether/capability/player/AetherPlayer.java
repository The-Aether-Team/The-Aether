package com.aetherteam.aether.capability.player;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import com.aetherteam.aether.entity.passive.Aerbunny;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AetherPlayer extends INBTSynchable<CompoundTag>, PlayerComponent<AetherPlayer> {
	Player getPlayer();

	static Optional<AetherPlayer> getOptional(Player player) {
		return AetherCapabilities.AETHER_PLAYER_CAPABILITY.maybeGet(player);
	}

	static AetherPlayer get(Player player) {
		return AetherCapabilities.AETHER_PLAYER_CAPABILITY.get(player);
	}

	void onLogout();
	void onLogin();

	void onJoinLevel();

	void copyFrom(AetherPlayer other, boolean isWasDeath);

	void onUpdate();

	void setCanSpawnInAether(boolean canSpawnInAether);
	boolean canSpawnInAether();

	void givePortalItem();
	void setCanGetPortal(boolean canGetPortal);
	boolean canGetPortal();

	void setInPortal(boolean inPortal);
	boolean isInPortal();

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

	void setGravititeJumpActive(boolean isGravititeJumpActive);
	boolean isGravititeJumpActive();

	void setSeenSunSpiritDialogue(boolean seenDialogue);
	boolean hasSeenSunSpiritDialogue();

	void setGoldenDartCount(int count);
	int getGoldenDartCount();
	void setPoisonDartCount(int count);
	int getPoisonDartCount();
	void setEnchantedDartCount(int count);
	int getEnchantedDartCount();

	void setRemedyStartDuration(int duration);
	int getRemedyStartDuration();

	void setProjectileImpactedMaximum(int max);
	int getProjectileImpactedMaximum();
	void setProjectileImpactedTimer(int timer);
	int getProjectileImpactedTimer();

	void setVampireHealing(boolean performVampireHealing);
	boolean performVampireHealing();

	void setMountedAerbunny(@Nullable Aerbunny mountedAerbunny);
	@Nullable
	Aerbunny getMountedAerbunny();
	void setMountedAerbunnyTag(@Nullable CompoundTag mountedAerbunny);
	@Nullable
	CompoundTag getMountedAerbunnyTag();

	void setLastRiddenMoa(@Nullable UUID lastRiddenMoa);
	@Nullable
	UUID getLastRiddenMoa();

	void setCloudMinions(CloudMinion cloudMinionRight, CloudMinion cloudMinionLeft);
	List<CloudMinion> getCloudMinions();

	int getWingRotationO();
	int getWingRotation();

	void setAttackedWithInvisibility(boolean attacked);
	boolean attackedWithInvisibility();

	void setInvisibilityEnabled(boolean enabled);
	boolean isInvisibilityEnabled();

	void setWearingInvisibilityCloak(boolean wearing);
	boolean isWearingInvisibilityCloak();

	int getFlightTimerMax();

	float getFlightModifierMax();

	void setFlightTimer(int timer);
	int getFlightTimer();

	void setFlightModifier(float modifier);
	float getFlightModifier();

	void setSavedHealth(float health);
	float getSavedHealth();

	void setNeptuneSubmergeLength(double length);
	double getNeptuneSubmergeLength();

	void setPhoenixSubmergeLength(double length);
	double getPhoenixSubmergeLength();

	int getObsidianConversionTimerMax();

	void setObsidianConversionTime(int time);
	int getObsidianConversionTime();

	void setLifeShardCount(int amount);
	int getLifeShardCount();
	int getLifeShardLimit();
	AttributeModifier getLifeShardHealthAttributeModifier();
}
