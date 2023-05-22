package com.aetherteam.aether.capability.item;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class DroppedItemProvider implements ICapabilitySerializable<CompoundTag> {
    private final DroppedItem droppedItem;

    public DroppedItemProvider(DroppedItem droppedItem) {
        this.droppedItem = droppedItem;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.droppedItem.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.droppedItem.deserializeNBT(tag);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.DROPPED_ITEM_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.droppedItem);
        }
        return LazyOptional.empty();
    }
}
