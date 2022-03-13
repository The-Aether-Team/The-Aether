package com.gildedgames.aether.core.capability.cape;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class CapeEntityProvider implements ICapabilitySerializable<CompoundTag> {
    private final CapeEntity capeEntity;

    public CapeEntityProvider(CapeEntity capeEntity) {
        this.capeEntity = capeEntity;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.capeEntity.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.capeEntity.deserializeNBT(tag);
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
