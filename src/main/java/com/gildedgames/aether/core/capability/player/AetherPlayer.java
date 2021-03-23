package com.gildedgames.aether.core.capability.player;

import com.gildedgames.aether.common.entity.block.ParachuteEntity;
import com.gildedgames.aether.core.api.registers.ParachuteType;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;

import com.gildedgames.aether.core.registry.AetherParachuteTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;


public class AetherPlayer implements IAetherPlayer
{
	private final PlayerEntity player;
	private boolean isInAetherPortal = false;
	private int aetherPortalTimer = 0;

	//STORAGE
	private ParachuteEntity parachute = null;

	//DATA

	//VARIABLES
	private boolean isJumping;
	
	public AetherPlayer(PlayerEntity player) {
		this.player = player;
	}
	
	@Override
	public PlayerEntity getPlayer() {
		return this.player;
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		if (this.getParachute() != null) {
			nbt.putString("Parachute", this.getParachute().getParachuteType().getRegistryName().toString());
		}

		//Set<AetherRank> ranks = AetherRankings.getRanksOf(this.player.getUniqueID());
//		if (ranks.stream().anyMatch(AetherRank::hasHalo)) {
//			nbt.putBoolean("Halo", this.shouldRenderHalo);
//		}
		//this.accessories.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (nbt.contains("Parachute")) {
			this.setParachute(AetherParachuteTypes.PARACHUTES.get(new ResourceLocation(nbt.getString("Parachute"))));
		}
	}
	
	@Override
	public void copyFrom(IAetherPlayer other) {
		CompoundNBT nbt = new CompoundNBT();
		//this.shouldRenderGlow = other.shouldRenderGlow();
	}

	// TODO
	@Override
	public void onUpdate() {
		handleAetherPortal();
	}

	/**
	 * Increments or decrements the Aether portal timer depending on whether or not the player is inside an Aether portal.
	 */
	private void handleAetherPortal() {
		if(this.isInAetherPortal) {
			++this.aetherPortalTimer;
			this.isInAetherPortal = false;
		}
		else if (this.aetherPortalTimer > 0) {
			--this.aetherPortalTimer;
		}
	}

	@Override
	public void setParachute(ParachuteType parachuteType) {
		if (this.getParachute() != null) {
			this.getParachute().kill();
			this.parachute = null;
		}

		if (parachuteType != null) {
			ParachuteEntity parachute = (ParachuteEntity) parachuteType.getParachuteType().create(this.getPlayer().level);
			this.parachute = parachute;

			if (parachute != null) {
				parachute.setPlayer(this.getPlayer().getId());
				parachute.setParachuteType(parachuteType.getRegistryName().toString());
				parachute.absMoveTo(this.getPlayer().getX(), this.getPlayer().getY() - 1.0, this.getPlayer().getZ(), 0.0F, 0.0F);
				this.getPlayer().level.addFreshEntity(parachute);
				parachute.spawnExplosionParticle();
			}
		} else {
			this.parachute = null;
		}
	}

	@Override
	public ParachuteEntity getParachute() {
		return this.parachute;
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
	public void setInPortal(boolean inPortal) {
		this.isInAetherPortal = inPortal;
	}

	@Override
	public boolean isInPortal() {
		return this.isInAetherPortal;
	}

	@Override
	public void addPortalTime(int time) {
		this.aetherPortalTimer += time;
	}

	@Override
	public void setPortalTimer(int timer) {
		this.aetherPortalTimer = timer;
	}

	@Override
	public int getPortalTimer() {
		return this.aetherPortalTimer;
	}
}
