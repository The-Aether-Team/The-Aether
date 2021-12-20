package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface ILightningTracker extends INBTSerializable<CompoundTag>
{
    LightningBolt getLightningBolt();

    static LazyOptional<ILightningTracker> get(LightningBolt arrow) {
        return arrow.getCapability(AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY);
    }

    void setOwner(Entity owner);
    Entity getOwner();
}
