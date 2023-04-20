package com.aetherteam.aether.capability.lightning;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface LightningTracker extends INBTSerializable<CompoundTag> {
    LightningBolt getLightningBolt();

    static LazyOptional<LightningTracker> get(LightningBolt arrow) {
        return arrow.getCapability(AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY);
    }

    void setOwner(Entity owner);
    Entity getOwner();
}
