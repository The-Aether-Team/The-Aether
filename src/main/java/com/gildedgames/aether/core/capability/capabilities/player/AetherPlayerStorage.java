package com.gildedgames.aether.core.capability.capabilities.player;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AetherPlayerStorage implements IStorage<IAetherPlayer>
{
	@Override
	public Tag writeNBT(Capability<IAetherPlayer> capability, IAetherPlayer instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<IAetherPlayer> capability, IAetherPlayer instance, Direction side, Tag nbt) {
		if (nbt instanceof CompoundTag) {
			instance.deserializeNBT((CompoundTag) nbt);
		}
	}
}