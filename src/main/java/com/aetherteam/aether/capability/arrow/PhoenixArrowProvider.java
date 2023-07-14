package com.aetherteam.aether.capability.arrow;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class PhoenixArrowProvider implements ICapabilitySerializable<CompoundTag> {
    private final PhoenixArrow phoenixArrow;

    public PhoenixArrowProvider(PhoenixArrow phoenixArrow) {
        this.phoenixArrow = phoenixArrow;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.phoenixArrow.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.phoenixArrow.deserializeNBT(tag);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.PHOENIX_ARROW_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.phoenixArrow);
        }
        return LazyOptional.empty();
    }
}
