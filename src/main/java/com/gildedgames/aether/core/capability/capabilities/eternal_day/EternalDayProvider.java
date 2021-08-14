package com.gildedgames.aether.core.capability.capabilities.eternal_day;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class EternalDayProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    private final IEternalDay eternalDay;

    public EternalDayProvider(IEternalDay eternalDay) {
        this.eternalDay = eternalDay;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return eternalDay.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        eternalDay.deserializeNBT(nbt);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.ETERNAL_DAY_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.eternalDay);
        }
        return LazyOptional.empty();
    }
}
