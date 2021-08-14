package com.gildedgames.aether.core.capability.capabilities.lightning;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class LightningTrackerProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    private final ILightningTracker lightningTracker;

    public LightningTrackerProvider(ILightningTracker lightningTracker) {
        this.lightningTracker = lightningTracker;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return lightningTracker.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        lightningTracker.deserializeNBT(nbt);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.lightningTracker);
        }
        return LazyOptional.empty();
    }
}
