package com.gildedgames.aether.core.capability.capabilities.lightning;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class LightningTrackerProvider implements ICapabilitySerializable<CompoundTag>
{
    private final ILightningTracker lightningTracker;

    public LightningTrackerProvider(ILightningTracker lightningTracker) {
        this.lightningTracker = lightningTracker;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.lightningTracker.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.lightningTracker.deserializeNBT(nbt);
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
