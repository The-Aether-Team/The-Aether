package com.aetherteam.aether.capability.time;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class AetherTimeProvider implements ICapabilitySerializable<CompoundTag> {
    private final AetherTime aetherTime;

    public AetherTimeProvider(AetherTime aetherTime) {
        this.aetherTime = aetherTime;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.aetherTime.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.aetherTime.deserializeNBT(tag);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.AETHER_TIME_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.aetherTime);
        }
        return LazyOptional.empty();
    }
}
