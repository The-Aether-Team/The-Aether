package com.gildedgames.aether.core.capability;

import com.gildedgames.aether.core.capability.player.IAetherPlayer;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AetherPlayerStorage implements IStorage<IAetherPlayer> {

	@Override
	public INBT writeNBT(Capability<IAetherPlayer> capability, IAetherPlayer instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<IAetherPlayer> capability, IAetherPlayer instance, Direction side, INBT nbt) {
		if (nbt instanceof CompoundNBT) {
			instance.deserializeNBT((CompoundNBT) nbt);
		}
	}
	
}