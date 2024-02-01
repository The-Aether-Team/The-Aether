package com.aetherteam.aether.capability.lightning;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.common.util.LazyOptional;

public interface LightningTracker extends INBTSerializable<CompoundTag> {
    LightningBolt getLightningBolt();

    static LazyOptional<LightningTracker> get(LightningBolt lightningBolt) {
        return lightningBolt.getCapability(AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY);
    }

    void setOwner(Entity owner);
    Entity getOwner();
}
