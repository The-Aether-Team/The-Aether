package com.gildedgames.aether.core.capability.capabilities.eternal_day;

import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class EternalDayStorage implements Capability.IStorage<IEternalDay>
{
    @Override
    public INBT writeNBT(Capability<IEternalDay> capability, IEternalDay instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IEternalDay> capability, IEternalDay instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }
}
