package com.gildedgames.aether.core.capability.capabilities.entity;

import com.gildedgames.aether.core.capability.interfaces.IAetherEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class AetherEntityStorage implements Capability.IStorage<IAetherEntity>
{
    @Override
    public INBT writeNBT(Capability<IAetherEntity> capability, IAetherEntity instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IAetherEntity> capability, IAetherEntity instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }
}
