package com.aetherteam.aether.capability.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import java.util.Collection;

/**
 * Capability class used to track {@link ItemEntity}s dropped by player death.
 * @see com.aetherteam.aether.event.hooks.EntityHooks#trackDrops(LivingEntity, Collection)
 * @see com.aetherteam.aether.event.hooks.DimensionHooks#fallFromAether(Level)
 */
public class DroppedItemCapability implements DroppedItem {
    private final ItemEntity itemEntity;

    private Entity owner;

    public DroppedItemCapability(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }

    @Override
    public ItemEntity getItemEntity() {
        return this.itemEntity;
    }

    /**
     * Saves data on world close.
     */
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (this.getOwner() != null) {
            tag.putInt("Owner", this.getOwner().getId());
        }
        return tag;
    }

    /**
     * Restores data from world on open.
     */
    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Owner")) {
            this.setOwner(this.getItemEntity().level().getEntity(tag.getInt("Owner")));
        }
    }

    @Override
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    /**
     * @return The owner {@link Entity} of the dropped item.
     */
    @Override
    public Entity getOwner() {
        return this.owner;
    }
}
