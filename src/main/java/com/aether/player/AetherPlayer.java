package com.aether.player;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.logging.log4j.util.Strings;

import com.aether.inventory.AccessoryInventory;
import com.aether.inventory.IAccessoryInventory;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherPlayer implements IAetherPlayer {

	private static final UUID HEALTH_UUID = new UUID(-2346749345421374890L, -8027384656528210170L);
	private static final UUID EXTENDED_REACH_UUID = new UUID(-2346749345421374890L, -8027384656528210169L);
	
	private final PlayerEntity player;
	private AttributeModifier healthModifier, reachModifier;
	private AccessoryInventory accessories;
	private float wingSinage;
	private IAetherBoss focusedBoss;
	private int lifeShardsUsed;
//	private float prevPortalAnimTime, portalAnimTime;
//	private int timeInPortal; // TODO remove this?
//	private boolean hasTeleported, inPortal; // TODO remove this??
//	private boolean shouldPlayPortalSound; // TODO remove this??
	private boolean seenSpiritDialog;
	private boolean shouldRenderHalo, shouldRenderGlow;
	private boolean isJumping;
	private int hammerCooldown, hammerCooldownMax;
	private String hammerName;
	private final NonNullList<IAetherAbility> abilities = NonNullList.create();
//	private final ArrayList<Entity> clouds = new ArrayList<>(2); // TODO
	
	public AetherPlayer(PlayerEntity player) {
		this.player = player;
		this.accessories = new AccessoryInventory(player);
	}
	
	@Override
	public PlayerEntity getPlayer() {
		return this.player;
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		Set<AetherRank> ranks = AetherRankings.getRanksOf(this.player.getUniqueID()); 
		if (ranks.stream().anyMatch(AetherRank::hasHalo)) {
			nbt.putBoolean("Halo", this.shouldRenderHalo);
		}
		if (ranks.stream().anyMatch(AetherRank::hasDevGlow)) {
			nbt.putBoolean("Glow", this.shouldRenderGlow);
		}
		nbt.putBoolean("SeenSunSpirit", this.seenSpiritDialog);
		nbt.putInt("HammerCooldown", this.hammerCooldown);
		nbt.putInt("MaxHammerCooldown", this.hammerCooldownMax);
		if (this.hammerName != null) {
			nbt.putString("HammerName", this.hammerName);
		}
		nbt.putInt("LifeShardCount", this.lifeShardsUsed);
		if (this.hammerName != null) {
			nbt.putString("HammerName", this.hammerName);
		}
		this.accessories.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		this.shouldRenderGlow = nbt.getBoolean("Glow");
		this.shouldRenderHalo = nbt.getBoolean("Halo");
		this.seenSpiritDialog = nbt.getBoolean("SeenSunSpirit");
		this.hammerCooldown = nbt.getInt("HammerCooldown");
		this.hammerCooldownMax = nbt.getInt("MaxHammerCooldown");
		String hammerName = nbt.getString("HammerName");
		this.hammerName = hammerName.isEmpty()? null : hammerName;
		this.lifeShardsUsed = nbt.getInt("LifeShardCount");
		this.accessories.readFromNBT(nbt);
	}
	
	@Override
	public void copyFrom(IAetherPlayer other) {
		CompoundNBT nbt = new CompoundNBT();
		other.getAccessoryInventory().writeToNBT(nbt);
		this.accessories.readFromNBT(nbt);
		this.shouldRenderGlow = other.shouldRenderGlow();
		this.shouldRenderHalo = other.shouldRenderHalo();
		this.seenSpiritDialog = other.hasSeenSunSpiritDialogue();
		this.hammerCooldown = other.getHammerCooldown();
		this.hammerCooldownMax = other.getHammerMaxCooldown();
		this.hammerName = other.getHammerName();
		this.lifeShardsUsed = other.getLifeShardsUsed();
	}

	// TODO
	@Override
	public void onUpdate() {}
	
	@Override
	public AttributeModifier getHealthModifier() {
		return this.healthModifier;
	}
	
	@Override
	public AttributeModifier getReachModifier() {
		return this.reachModifier;
	}

	// TODO remove this??
//	@Override
//	public void setInPortal() {}

	@Override
	public void setFocusedBoss(IAetherBoss boss) {
		this.focusedBoss = boss;
	}

	@Override
	public IAetherBoss getFocusedBoss() {
		return this.focusedBoss;
	}

	@Override
	public IAccessoryInventory getAccessoryInventory() {
		return this.accessories;
	}

	@Override
	public NonNullList<IAetherAbility> getAbilities() {
		return this.abilities;
	}

	// TODO
//	@Override
//	public void inflictPoison(int ticks) {}

	// TODO
//	@Override
//	public boolean isPoisoned() {
//		return false;
//	}

	// TODO
//	@Override
//	public void inflictCure(int ticks) {}

	// TODO
//	@Override
//	public boolean isCured() {
//		return false;
//	}
	
	@Override
	public boolean shouldRenderGlow() {
		return this.shouldRenderGlow;
	}
	
	@Override
	public void setShouldRenderGlow(boolean shouldRenderGlow) {
		this.shouldRenderGlow = shouldRenderGlow;
	}
	
	@Override
	public boolean shouldRenderHalo() {
		return this.shouldRenderHalo;
	}
	
	@Override
	public void setShouldRenderHalo(boolean shouldRenderHalo) {
		this.shouldRenderHalo = shouldRenderHalo;
	}
	
	@Override
	public boolean hasSeenSunSpiritDialogue() {
		return this.seenSpiritDialog;
	}
	
	@Override
	public void setHasSeenSunSpiritDialogue(boolean hasSeenSunSpiritDialogue) {
		this.seenSpiritDialog = hasSeenSunSpiritDialogue;
	}

	@Override
	public boolean setHammerCooldown(int cooldown) {
		if (this.hammerCooldown == 0) {
			this.hammerCooldown = this.hammerCooldownMax = cooldown;
			return true;
		}
		return false;
	}

	@Nullable
	@Override
	public String getHammerName() {
		return this.hammerName;
	}
	
	@Override
	public void setHammerName(@Nullable String hammerName) {
		this.hammerName = Strings.isEmpty(hammerName)? null : hammerName;
	}

	@Override
	public int getHammerCooldown() {
		return this.hammerCooldown;
	}

	@Override
	public int getHammerMaxCooldown() {
		return this.hammerCooldownMax;
	}

	@Override
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	@Override
	public boolean isJumping() {
		return this.isJumping;
	}
	
	@Override
	public int getLifeShardsUsed() {
		return this.lifeShardsUsed;
	}

	@Override
	public void setLifeShardsUsed(int lifeShardsUsed) {
		this.lifeShardsUsed = lifeShardsUsed;
		
		IAttributeInstance attribute = this.player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
		attribute.removeModifier(HEALTH_UUID);
		
		if (lifeShardsUsed != 0) {
			this.healthModifier = new AttributeModifier(HEALTH_UUID, "Aether Health Modifier", 2 * lifeShardsUsed, AttributeModifier.Operation.ADDITION);
			attribute.applyModifier(this.healthModifier);
		}
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getWingSinage() {
		return wingSinage;
	}
	
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public float getPreviousPortalAnimationTime() {
//		return prevPortalAnimTime;
//	}
//	
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public float getPortalAnimationTime() {
//		return portalAnimTime;
//	}
	
}
