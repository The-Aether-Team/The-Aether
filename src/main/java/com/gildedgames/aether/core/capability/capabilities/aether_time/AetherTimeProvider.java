package com.gildedgames.aether.core.capability.capabilities.aether_time;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IAetherTime;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class AetherTimeProvider implements ICapabilitySerializable<CompoundTag>
{
    private final IAetherTime aetherTime;

    public AetherTimeProvider(IAetherTime eternalDay) {
        this.aetherTime = eternalDay;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.aetherTime.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.aetherTime.deserializeNBT(nbt);
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
