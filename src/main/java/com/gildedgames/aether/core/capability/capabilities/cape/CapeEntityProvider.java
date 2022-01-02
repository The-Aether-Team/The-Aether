package com.gildedgames.aether.core.capability.capabilities.cape;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class CapeEntityProvider implements ICapabilitySerializable<CompoundTag>
{
    private final ICapeEntity capeEntity;

    public CapeEntityProvider(ICapeEntity capeEntity) {
        this.capeEntity = capeEntity;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.capeEntity.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.capeEntity.deserializeNBT(nbt);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.CAPE_ENTITY_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.capeEntity);
        }
        return LazyOptional.empty();
    }
}
