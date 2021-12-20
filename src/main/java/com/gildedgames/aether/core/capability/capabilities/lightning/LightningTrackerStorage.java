package com.gildedgames.aether.core.capability.capabilities.lightning;

import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class LightningTrackerStorage implements Capability.IStorage<ILightningTracker>
{
    @Override
    public Tag writeNBT(Capability<ILightningTracker> capability, ILightningTracker instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<ILightningTracker> capability, ILightningTracker instance, Direction side, Tag nbt) {
        if (nbt instanceof CompoundTag) {
            instance.deserializeNBT((CompoundTag) nbt);
        }
    }
}
