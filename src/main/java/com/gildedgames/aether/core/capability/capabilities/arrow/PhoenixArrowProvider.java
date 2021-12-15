package com.gildedgames.aether.core.capability.capabilities.arrow;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PhoenixArrowProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    private final IPhoenixArrow phoenixArrow;

    public PhoenixArrowProvider(IPhoenixArrow phoenixArrow) {
        this.phoenixArrow = phoenixArrow;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return phoenixArrow.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        phoenixArrow.deserializeNBT(nbt);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.PHOENIX_ARROW_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.phoenixArrow);
        }
        return LazyOptional.empty();
    }
}
