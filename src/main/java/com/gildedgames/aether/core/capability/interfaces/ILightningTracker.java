package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface ILightningTracker extends INBTSerializable<CompoundNBT>
{
    LightningBoltEntity getLightningBolt();

    static LazyOptional<ILightningTracker> get(LightningBoltEntity arrow) {
        return arrow.getCapability(AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY);
    }

    void setOwner(Entity owner);
    Entity getOwner();
}
