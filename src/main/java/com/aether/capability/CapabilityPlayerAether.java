package com.aether.capability;

import com.aether.api.player.IPlayerAether;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityPlayerAether {

	@CapabilityInject(IPlayerAether.class)
	public static final Capability<IPlayerAether> AETHER_PLAYER_CAPABILITY = null;
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IPlayerAether.class, new PlayerAetherStorage(), () -> null);
	}
	
	private static class PlayerAetherStorage implements IStorage<IPlayerAether> {

		@Override
		public INBT writeNBT(Capability<IPlayerAether> capability, IPlayerAether instance, Direction side) {
			CompoundNBT compound = new CompoundNBT();
			instance.saveNBTData(compound);
			return compound;
		}

		@Override
		public void readNBT(Capability<IPlayerAether> capability, IPlayerAether instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				instance.loadNBTData((CompoundNBT) nbt);
			}
		}
		
	}
	
}
