package com.gildedgames.aether.capability.rankings;

import com.gildedgames.aether.capability.AetherCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class AetherRankingsProvider implements ICapabilitySerializable<CompoundTag> {
    private final AetherRankings aetherRankings;

    public AetherRankingsProvider(AetherRankings aetherRankings) {
        this.aetherRankings = aetherRankings;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.aetherRankings.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.aetherRankings.deserializeNBT(tag);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == AetherCapabilities.AETHER_RANKINGS_CAPABILITY) {
            return LazyOptional.of(() -> (T) this.aetherRankings);
        }
        return LazyOptional.empty();
    }
}
