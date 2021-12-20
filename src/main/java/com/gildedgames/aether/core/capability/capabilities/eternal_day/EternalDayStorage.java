package com.gildedgames.aether.core.capability.capabilities.eternal_day;

import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class EternalDayStorage implements Capability.IStorage<IEternalDay>
{
    @Override
    public Tag writeNBT(Capability<IEternalDay> capability, IEternalDay instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IEternalDay> capability, IEternalDay instance, Direction side, Tag nbt) {
        if (nbt instanceof CompoundTag) {
            instance.deserializeNBT((CompoundTag) nbt);
        }
    }
}
