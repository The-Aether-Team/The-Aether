package com.gildedgames.aether.core.capability.capabilities.eternal_day;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class EternalDayProvider implements ICapabilitySerializable<CompoundTag>
{
    private final IEternalDay eternalDay;

    public EternalDayProvider(IEternalDay eternalDay) {
        this.eternalDay = eternalDay;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.eternalDay.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.eternalDay.deserializeNBT(nbt);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.ETERNAL_DAY_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.eternalDay);
        }
        return LazyOptional.empty();
    }
}
