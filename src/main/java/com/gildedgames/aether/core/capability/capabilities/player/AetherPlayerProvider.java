package com.gildedgames.aether.core.capability.capabilities.player;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class AetherPlayerProvider implements ICapabilitySerializable<CompoundTag>
{
	private final IAetherPlayer aetherPlayer;
	
	public AetherPlayerProvider(IAetherPlayer aetherPlayer) {
		this.aetherPlayer = aetherPlayer;
	}
	
	@Override
	public CompoundTag serializeNBT() {
		return this.aetherPlayer.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.aetherPlayer.deserializeNBT(nbt);
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
		if (cap == AetherCapabilities.AETHER_PLAYER_CAPABILITY) {
			return LazyOptional.of(() -> (T) this.aetherPlayer);
		}
		return LazyOptional.empty();
	}
}
