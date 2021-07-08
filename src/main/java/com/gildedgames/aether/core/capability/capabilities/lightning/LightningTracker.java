package com.gildedgames.aether.core.capability.capabilities.lightning;

import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.nbt.CompoundNBT;

public class LightningTracker implements ILightningTracker
{
    private final LightningBoltEntity lightningBolt;

    private Entity owner;

    public LightningTracker(LightningBoltEntity lightningBolt) {
        this.lightningBolt = lightningBolt;
    }

    @Override
    public LightningBoltEntity getLightningBolt() {
        return this.lightningBolt;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Owner", this.getOwner().getId());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
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
