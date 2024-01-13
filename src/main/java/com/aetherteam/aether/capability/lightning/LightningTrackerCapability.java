package com.aetherteam.aether.capability.lightning;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;

/**
 * Capability class used to track {@link LightningBolt}s created by entities and weapons.
 * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#lightningTracking(Entity, LightningBolt)
 */
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

    /**
     * Saves data on world close.
     */
    @Override
    public void writeToNbt(CompoundTag tag) {
        if (this.getOwner() != null) {
            tag.putInt("Owner", this.getOwner().getId());
        }
    }

    /**
     * Restores data from world on open.
     */
    @Override
    public void readFromNbt(CompoundTag tag) {
        if (tag.contains("Owner")) {
            this.setOwner(this.getLightningBolt().level().getEntity(tag.getInt("Owner")));
        }
    }

    @Override
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    /**
     * @return The owner {@link Entity} of the lightning.
     */
    @Override
    public Entity getOwner() {
        return this.owner;
    }
}
