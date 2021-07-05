package com.gildedgames.aether.core.capability.capabilities.entity;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IAetherEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class AetherEntityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
{
    private final IAetherEntity aetherEntity;

    public AetherEntityProvider(IAetherEntity aetherEntity) {
        this.aetherEntity = aetherEntity;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return aetherEntity.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        aetherEntity.deserializeNBT(nbt);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.AETHER_ENTITY_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.aetherEntity);
        }
        return LazyOptional.empty();
    }
}
