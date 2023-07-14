package com.aetherteam.aether.capability.lightning;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;

public class LightningTrackerCapability implements LightningTracker {
    private final LightningBolt lightningBolt;

    private Entity owner;

    public LightningTrackerCapability(LightningBolt lightningBolt) {
        this.lightningBolt = lightningBolt;
    }

    @Override
    public LightningBolt getLightningBolt() {
        return this.lightningBolt;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (this.getOwner() != null) {
            tag.putInt("Owner", this.getOwner().getId());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Owner")) {
            this.setOwner(this.getLightningBolt().level.getEntity(tag.getInt("Owner")));
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
