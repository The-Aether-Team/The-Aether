package com.gildedgames.aether.core.capability.capabilities.arrow;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class PhoenixArrowProvider implements ICapabilitySerializable<CompoundTag>
{
    private final IPhoenixArrow phoenixArrow;

    public PhoenixArrowProvider(IPhoenixArrow phoenixArrow) {
        this.phoenixArrow = phoenixArrow;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.phoenixArrow.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.phoenixArrow.deserializeNBT(nbt);
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
