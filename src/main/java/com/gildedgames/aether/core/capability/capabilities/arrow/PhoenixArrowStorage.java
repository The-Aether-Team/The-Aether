package com.gildedgames.aether.core.capability.capabilities.arrow;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PhoenixArrowStorage implements Capability.IStorage<IPhoenixArrow>
{
    @Override
    public INBT writeNBT(Capability<IPhoenixArrow> capability, IPhoenixArrow instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IPhoenixArrow> capability, IPhoenixArrow instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }
}
