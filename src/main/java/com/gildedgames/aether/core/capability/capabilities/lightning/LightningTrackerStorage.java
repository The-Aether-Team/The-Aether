package com.gildedgames.aether.core.capability.capabilities.lightning;

import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class LightningTrackerStorage implements Capability.IStorage<ILightningTracker>
{
    @Override
    public INBT writeNBT(Capability<ILightningTracker> capability, ILightningTracker instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<ILightningTracker> capability, ILightningTracker instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }
}
