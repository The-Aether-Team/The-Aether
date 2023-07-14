package com.aetherteam.aether.capability.lightning;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class LightningTrackerProvider implements ICapabilitySerializable<CompoundTag> {
    private final LightningTracker lightningTracker;

    public LightningTrackerProvider(LightningTracker lightningTracker) {
        this.lightningTracker = lightningTracker;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.lightningTracker.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.lightningTracker.deserializeNBT(tag);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.lightningTracker);
        }
        return LazyOptional.empty();
    }
}
