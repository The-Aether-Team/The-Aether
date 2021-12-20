package com.gildedgames.aether.core.capability.capabilities.cape;

import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapeEntityStorage implements IStorage<ICapeEntity>
{
    @Override
    public Tag writeNBT(Capability<ICapeEntity> capability, ICapeEntity instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<ICapeEntity> capability, ICapeEntity instance, Direction side, Tag nbt) {
        if (nbt instanceof CompoundTag) {
            instance.deserializeNBT((CompoundTag) nbt);
        }
    }
}
