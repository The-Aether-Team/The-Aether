package com.gildedgames.aether.core.capability.capabilities.arrow;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PhoenixArrowStorage implements Capability.IStorage<IPhoenixArrow>
{
    @Override
    public Tag writeNBT(Capability<IPhoenixArrow> capability, IPhoenixArrow instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IPhoenixArrow> capability, IPhoenixArrow instance, Direction side, Tag nbt) {
        if (nbt instanceof CompoundTag) {
            instance.deserializeNBT((CompoundTag) nbt);
        }
    }
}
