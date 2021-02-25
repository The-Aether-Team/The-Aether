package com.gildedgames.aether.player;

import java.util.UUID;

import javax.annotation.Nullable;

import com.gildedgames.aether.inventory.IAccessoryInventory;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAetherPlayer extends INBTSerializable<CompoundNBT> {
	
	PlayerEntity getPlayer();
	
	default UUID getUniqueID() { return this.getPlayer().getUniqueID(); }
	
	AttributeModifier getHealthModifier();
	
	AttributeModifier getReachModifier();
	
	void onUpdate();
	
//	void setInPortal();
	
	IAetherBoss getFocusedBoss();
	
	void setFocusedBoss(IAetherBoss boss);
	
//	void setAccessoryInventory(IAccessoryInventory inventory);
	
	IAccessoryInventory getAccessoryInventory();
	
	NonNullList<IAetherAbility> getAbilities();
	
//	void inflictPoison(int ticks);
	
//	boolean isPoisoned();
	
//	void inflictCure(int ticks);
	
//	boolean isCured();
	
	@Nullable
	String getHammerName();
	
	/**
	 * Set the name of the Hammer of Notch the player is using
	 * @param hammerName The hammer name, {@code null} or empty for no hammer name
	 */
	void setHammerName(@Nullable String hammerName);
	
	int getHammerCooldown();
	
	int getHammerMaxCooldown();
	
	/**
	 * @param cooldown The hammer cooldown
	 * @return {@code true} if the cooldown was set, {@code false} if the current cooldown hasn't expired yet
	 */
	boolean setHammerCooldown(int cooldown);
	
	boolean shouldRenderGlow();
	
	void setShouldRenderGlow(boolean shouldRenderGlow);
	
	boolean shouldRenderHalo();
	
	void setShouldRenderHalo(boolean shouldRenderHalo);
	
	boolean hasSeenSunSpiritDialogue();
	
	void setHasSeenSunSpiritDialogue(boolean hasSeenSunSpiritDialogue);
	
	boolean isJumping();
	
	void setJumping(boolean isJumping);
	
	int getLifeShardsUsed();
	
	void setLifeShardsUsed(int lifeShardsUsed);
	
	default void incrementLifeShardsUsed(int amount) { this.setLifeShardsUsed(this.getLifeShardsUsed() + amount); }
	
	@OnlyIn(Dist.CLIENT)
	float getWingSinage();
	
//	@OnlyIn(Dist.CLIENT)
//	float getPreviousPortalAnimationTime();
	
//	@OnlyIn(Dist.CLIENT)
//	float getPortalAnimationTime();
	
	void copyFrom(IAetherPlayer other);
	
}
