package com.gildedgames.aether.core.capability.player;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class AetherPlayer implements IAetherPlayer
{
	private final PlayerEntity player;

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
		//Set<AetherRank> ranks = AetherRankings.getRanksOf(this.player.getUniqueID());
//		if (ranks.stream().anyMatch(AetherRank::hasHalo)) {
//			nbt.putBoolean("Halo", this.shouldRenderHalo);
//		}
//		nbt.putInt("LifeShardCount", this.lifeShardsUsed);
//		if (this.hammerName != null) {
//			nbt.putString("HammerName", this.hammerName);
//		}
		//this.accessories.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		//this.lifeShardsUsed = nbt.getInt("LifeShardCount");
	}
	
	@Override
	public void copyFrom(IAetherPlayer other) {
		CompoundNBT nbt = new CompoundNBT();
		//this.shouldRenderGlow = other.shouldRenderGlow();
	}

	// TODO
	@Override
	public void onUpdate() {
		Aether.LOGGER.info(true);
	}

	@Override
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	@Override
	public boolean isJumping() {
		return this.isJumping;
	}
}
