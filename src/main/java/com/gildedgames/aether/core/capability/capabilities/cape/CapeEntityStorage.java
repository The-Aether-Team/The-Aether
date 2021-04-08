package com.gildedgames.aether.core.capability.capabilities.cape;

import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapeEntityStorage implements IStorage<ICapeEntity>
{
    @Override
    public INBT writeNBT(Capability<ICapeEntity> capability, ICapeEntity instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<ICapeEntity> capability, ICapeEntity instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }
}
