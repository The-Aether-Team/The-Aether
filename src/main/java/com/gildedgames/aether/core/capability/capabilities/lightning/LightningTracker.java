package com.gildedgames.aether.core.capability.capabilities.lightning;

import com.gildedgames.aether.core.capability.interfaces.LightningTrackerSerializable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.nbt.CompoundTag;

public class LightningTracker implements LightningTrackerSerializable
{
    private final LightningBolt lightningBolt;

    private Entity owner;

    public LightningTracker(LightningBolt lightningBolt) {
        this.lightningBolt = lightningBolt;
    }

    @Override
    public LightningBolt getLightningBolt() {
        return this.lightningBolt;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putInt("Owner", this.getOwner().getId());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        if (compound.contains("Owner")) {
            this.setOwner(this.getOwner().level.getEntity(compound.getInt("Owner")));
        }
    }

    @Override
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    @Override
    public Entity getOwner() {
        return this.owner;
    }
}
