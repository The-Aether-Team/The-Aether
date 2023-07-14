package com.aetherteam.aether.capability.player;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class AetherPlayerProvider implements ICapabilitySerializable<CompoundTag> {
	private final AetherPlayer aetherPlayer;
	
	public AetherPlayerProvider(AetherPlayer aetherPlayer) {
		this.aetherPlayer = aetherPlayer;
	}
	
	@Override
	public CompoundTag serializeNBT() {
		return this.aetherPlayer.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.aetherPlayer.deserializeNBT(tag);
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
