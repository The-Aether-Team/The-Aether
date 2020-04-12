package com.aether.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.aether.inventory.AccessoryInventory;
import com.aether.inventory.IAccessoryInventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherPlayer implements IAetherPlayer {

	private static final UUID healthUUID = UUID.fromString("df6eabe7-6947-4a56-9099-002f90370706");
	private static final UUID extendedReachUUID = UUID.fromString("df6eabe7-6947-4a56-9099-002f90370707");
	
	private final PlayerEntity player;
	private AttributeModifier healthModifier, reachModifier;
	private AccessoryInventory accessories;
	private float wingSinage;
	private IAetherBoss focusedBoss;
	private boolean jumping;
	private int lifeShardsUsed = 0;
	private float prevPortalAnimTime, portalAnimTime;
	private int timeInPortal = 0;
	private boolean hasTeleported = false, inPortal = false;
	private boolean shouldPlayPortalSound;
	private boolean seenSpiritDialog = false;
	private final List<IAetherAbility> abilities = new ArrayList<>();
	private final List<Entity> clouds = new ArrayList<>(2);
	
	public AetherPlayer(PlayerEntity player) {
		this.player = player;
	
		this.accessories = new AccessoryInventory(player);
	}
	
	@Override
	public PlayerEntity getPlayerEntity() {
		return this.player;
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		return null;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {}

	@Override
	public void onUpdate() {}

	@Override
	public void setInPortal() {}

	@Override
	public void setFocusedBoss(IAetherBoss boss) {}

	@Override
	public IAetherBoss getFocusedBoss() {
		return null;
	}

	@Override
	public IAccessoryInventory getAccessoryInventory() {
		return null;
	}

	@Override
	public ArrayList<IAetherAbility> getAbilities() {
		return null;
	}

	@Override
	public PlayerEntity getEntity() {
		return null;
	}

	@Override
	public void inflictPoison(int ticks) {}

	@Override
	public boolean isPoisoned() {
		return false;
	}

	@Override
	public void inflictCure(int ticks) {}

	@Override
	public boolean isCured() {
		return false;
	}

	@Override
	public boolean setHammerCooldown(int cooldown, String hammerName) {
		return false;
	}

	@Override
	public String getNammerName() {
		return null;
	}

	@Override
	public int getHammerCooldown() {
		return 0;
	}

	@Override
	public int getHammerMaxCooldown() {
		return 0;
	}

	@Override
	public void setJumping(boolean isJumping) {}

	@Override
	public boolean isJumping() {
		return false;
	}

	@Override
	public void updateShardCount(int amount) {}

	@Override
	public int getShardsUsed() {
		return 0;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getWingSinage() {
		return wingSinage;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getPreviousPortalAnimationTime() {
		return prevPortalAnimTime;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getPortalAnimationTime() {
		return portalAnimTime;
	}
	
}
