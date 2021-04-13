package com.gildedgames.aether.core.capability.capabilities.cape;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapeEntityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    private final ICapeEntity capeEntity;

    public CapeEntityProvider(ICapeEntity capeEntity) {
        this.capeEntity = capeEntity;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return capeEntity.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        capeEntity.deserializeNBT(nbt);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.CAPE_ENTITY_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.capeEntity);
        }
        return LazyOptional.empty();
    }
}
