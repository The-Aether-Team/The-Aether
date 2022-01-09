package com.gildedgames.aether.core.capability.capabilities.lightning;

import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.nbt.CompoundTag;

public class LightningTracker implements ILightningTracker
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
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Owner", this.getOwner().getId());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Owner")) {
            this.setOwner(this.getOwner().level.getEntity(nbt.getInt("Owner")));
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
